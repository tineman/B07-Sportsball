package com.example.b07sportsballs;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

public class Customer extends User {
    static HashSet<Event> joinedEvents;
    static HashSet<Event> scheduledEvents;

    // This constructor is called only once when the user logs in/signs up successfully.
    public Customer(String username, String password, DatabaseReference ref) {
        super(username, password, ref, Constants.DATABASE.CUSTOMER_TYPE);
        joinedEvents = new HashSet<Event>();
        scheduledEvents = new HashSet<Event>();
    }

    /**
     * Writes all events joined and scheduled by the customer to respective branch of database.
     */
    public static void writeToDatabase() {
        ref.child(Constants.DATABASE.CUSTOMER_JOINED_EVENTS_KEY).setValue(null);
        for (Event e:joinedEvents) {
            ref.child(Constants.DATABASE.CUSTOMER_JOINED_EVENTS_KEY).child(e.getLocation()).push().
                    setValue(e.getName());
        }
        ref.child(Constants.DATABASE.CUSTOMER_SCHEDULED_EVENTS_KEY).setValue(null);
        for (Event e:scheduledEvents) {
            ref.child(Constants.DATABASE.CUSTOMER_SCHEDULED_EVENTS_KEY).child(e.getLocation()).push().
                    setValue(e.getName());
        }
    }

    private static void readJoinedEventsFromDatabase(Updater updater) {
        DatabaseReference joinedEventsRoot = ref.child(Constants.DATABASE.CUSTOMER_JOINED_EVENTS_KEY);
        joinedEventsRoot.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                joinedEvents.clear();
                Log.i("CustomerTest", "joinedEvents cleared, now have "+
                        joinedEvents.size() + " events, ready to read data");
                if (!snapshot.exists()) {
                    Log.i("CustomerTest", "Data changed: no joined events");
                    updater.onUpdate();
                } else {
                    Log.i("CustomerTest", String.format("Data changed: %d joined events",
                            snapshot.getChildrenCount()));
                    long venuesCount = snapshot.getChildrenCount();
                    long eventsCount = 0L;
                    for (DataSnapshot venue : snapshot.getChildren()) {
                        venuesCount--;
                        eventsCount += venue.getChildrenCount();
                        for (DataSnapshot event : venue.getChildren()) {
                            eventsCount--;
                            Event e = new Event();
                            DatabaseReference eventRef = FirebaseDatabase.
                                    getInstance(Constants.DATABASE.DB_URL).
                                    getReference(Constants.DATABASE.ROOT + "/" +
                                            Constants.DATABASE.VENUE_PATH + "/" +
                                            venue.getKey() + "/" +
                                            Constants.DATABASE.VENUE_EVENTS_KEY + "/" +
                                            event.getValue(String.class));
                            long finalEventsCount = eventsCount;
                            long finalVenuesCount = venuesCount;
                            e.bindToDatabase(eventRef, new Updater() {
                                @Override
                                public void onUpdate() {
                                    joinedEvents.add(e);
                                    Log.i("CustomerTest", "Added "+e.getName()+" at " +
                                            e.collectRef().toString());
                                    Log.i("CustomerTest", "joinedEvents now has "+
                                            joinedEvents.size()+" events");
                                    if (finalVenuesCount == 0 && finalEventsCount == 0) {
                                        updater.onUpdate();
                                    }
                                }
                            });
                        }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.w("Customer Warning", "Error while reading joinedEvents from database",
                        error.toException());
            }
        });
    }

    private static void readScheduledEventsFromDatabase(Updater updater) {
        DatabaseReference scheduledEventsRoot = ref.child(Constants.DATABASE.CUSTOMER_SCHEDULED_EVENTS_KEY);
        scheduledEventsRoot.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                scheduledEvents.clear();
                Log.i("CustomerTest", "scheduledEvents cleared, now have "+
                        scheduledEvents.size() + " events, ready to read data");
                if (!snapshot.exists()) {
                    Log.i("CustomerTest", "Data changed: no scheduled events");
                    updater.onUpdate();
                }
                else {
                    Log.i("CustomerTest", String.format("Data changed: %d joined events",
                            snapshot.getChildrenCount()));
                    long venuesCount = snapshot.getChildrenCount();
                    long eventsCount = 0L;
                    for (DataSnapshot venue : snapshot.getChildren()) {
                        venuesCount--;
                        eventsCount += venue.getChildrenCount();
                        for (DataSnapshot event : venue.getChildren()) {
                            eventsCount--;
                            Event e = new Event();
                            DatabaseReference eventRef = FirebaseDatabase.
                                    getInstance(Constants.DATABASE.DB_URL).
                                    getReference(Constants.DATABASE.ROOT + "/" +
                                            Constants.DATABASE.VENUE_PATH + "/" +
                                            venue.getKey() + "/" +
                                            Constants.DATABASE.VENUE_EVENTS_KEY + "/" +
                                            event.getValue(String.class));
                            long finalEventsCount = eventsCount;
                            long finalVenuesCount = venuesCount;
                            e.bindToDatabase(eventRef, new Updater() {
                                @Override
                                public void onUpdate() {
                                    scheduledEvents.add(e);
                                    Log.i("CustomerTest", "Added "+e.getName()+" at " +
                                            e.collectRef().toString());
                                    Log.i("CustomerTest", "scheduledEvents now has "+
                                            scheduledEvents.size()+" events");
                                    if (finalVenuesCount == 0 && finalEventsCount == 0) {
                                        updater.onUpdate();
                                    }
                                }
                            });
                        }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.w("Customer Warning", "Error while reading scheduledEvents from database",
                        error.toException());
            }
        });
    }

    /**
     * Reads events the customer joined or scheduled into respective members. Attaches a listener
     * to each event to keep updated.
     */
    public static void readFromDatabase(Updater updater) {
        Customer.readJoinedEventsFromDatabase(new Updater() {
            @Override
            public void onUpdate() {
                Customer.readScheduledEventsFromDatabase(new Updater() {
                    @Override
                    public void onUpdate() {
                        updater.onUpdate();
                    }
                });
            }
        });
    }

    // Assume bindToDatabase has been called on event already.
    /**
     * If <code>e</code> is not full, add customer as a player and make changes to database.
     * @param e the event to join
     */
    public static void joinEvent(Event e) {
        e.setWriter();
        if (!joinedEvents.contains(e) && e.increment()) {
            joinedEvents.add(e);
            writeToDatabase();
        }
    }

    // Assume that location is valid (ie. venue exists). Assume that setData() has been called on e
    // to set user-input info.

    /**
     * Check for valid name and schedule a new event. Write to database under Customers and Venues.
     * @param e event to be scheduled
     * @param updater callback to notify writing is done
     */
    public static void scheduleEvent(Event e, Updater updater) {
        DatabaseReference eventRoot = FirebaseDatabase.getInstance(Constants.DATABASE.DB_URL).
                getReference(Constants.DATABASE.ROOT+"/"+
                        Constants.DATABASE.VENUE_PATH+"/"+e.getLocation()+"/"+
                        Constants.DATABASE.VENUE_EVENTS_KEY+"/"+e.getName());
        eventRoot.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                // No event with such name is located at the same venue.
                if (!snapshot.exists()) {
                    e.bindToDatabase(snapshot.getRef(), new Updater() {
                        @Override
                        public void onUpdate() {
                            // Write new event to corresponding venue branch.
                            e.setWriter();
                            e.writeToDatabase();
                            // Write new event to corresponding customer branch.
                            if (!scheduledEvents.contains(e))
                                scheduledEvents.add(e);
                                writeToDatabase();
                        }
                    });
                }
                updater.onUpdate();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


    public static HashSet<Event> getJoinedEvents() {
        return joinedEvents;
    }

    public static void setJoinedEvents(HashSet<Event> joinedEvents) {
        Customer.joinedEvents = joinedEvents;
    }

    public static HashSet<Event> getScheduledEvents() {
        return scheduledEvents;
    }

    public static void setScheduledEvents(HashSet<Event> scheduledEvents) {
        Customer.scheduledEvents = scheduledEvents;
    }
}
