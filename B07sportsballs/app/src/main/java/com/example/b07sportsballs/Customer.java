package com.example.b07sportsballs;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashSet;

public class Customer extends User {
    static HashSet<Event> joinedEvents;
    static HashSet<Event> scheduledEvents;

    // This constructor is called only once when the user logs in/signs up successfully.
    public Customer(String username, String password, DatabaseReference ref) {
        super(username, password, ref);
        joinedEvents = new HashSet<Event>();
        scheduledEvents = new HashSet<Event>();
    }

    /**
     * Writes all events joined and scheduled by the customer to respective branch of database.
     */
    public static void writeToDatabase(Event e, boolean toJoinedEvents, boolean toScheduledEvents) {
        if (toJoinedEvents) {
            DatabaseReference joinedEventsRoot = ref.child
                    (Constants.DATABASE.CUSTOMER_JOINED_EVENTS_KEY);
            joinedEventsRoot.child(e.getLocation()).push().setValue(e.getName());
        }
        if (toScheduledEvents) {
            DatabaseReference scheduledEventsRoot = ref.child
                    (Constants.DATABASE.CUSTOMER_SCHEDULED_EVENTS_KEY);
            scheduledEventsRoot.child(e.getLocation()).push().setValue(e.getName());
        }
    }

    private static void readJoinedEventsFromDatabase(Updater updater) {
        DatabaseReference joinedEventsRoot = ref.child(Constants.DATABASE.CUSTOMER_JOINED_EVENTS_KEY);
        joinedEventsRoot.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                long venuesCount = snapshot.getChildrenCount();
                long eventsCount = 0L;
                for (DataSnapshot venue : snapshot.getChildren()) {
                    venuesCount--;
                    eventsCount += venue.getChildrenCount();
                    for (DataSnapshot event : venue.getChildren()) {
                        eventsCount--;
                        Event e = new Event();
                        DatabaseReference ref = FirebaseDatabase.
                                getInstance(Constants.DATABASE.DB_URL).
                                getReference(Constants.DATABASE.ROOT+"/"+
                                        Constants.DATABASE.VENUE_PATH+"/"+
                                        Constants.DATABASE.VENUE_EVENTS_KEY+"/"+
                                        event.getValue(String.class));
                        Log.i("test", ref.toString());
                        long finalEventsCount = eventsCount;
                        long finalVenuesCount = venuesCount;
                        e.bindToDatabase(ref, new Updater() {
                            @Override
                            public void onUpdate() {
                                e.setName(event.getValue(String.class));
                                e.setLocation(venue.getKey());
                                joinedEvents.add(e);
                                if (finalVenuesCount == 0 && finalEventsCount == 0)
                                    updater.onUpdate();
                            }
                        });
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
                long venuesCount = snapshot.getChildrenCount();
                long eventsCount = 0L;
                for (DataSnapshot venue : snapshot.getChildren()) {
                    venuesCount--;
                    eventsCount += venue.getChildrenCount();
                    for (DataSnapshot event : venue.getChildren()) {
                        eventsCount--;
                        Event e = new Event();
                        DatabaseReference ref = FirebaseDatabase.
                                getInstance(Constants.DATABASE.DB_URL).
                                getReference(Constants.DATABASE.ROOT+"/"+
                                        Constants.DATABASE.VENUE_PATH+"/"+
                                        Constants.DATABASE.VENUE_EVENTS_KEY+"/"+
                                        event.getValue(String.class));
                        long finalEventsCount = eventsCount;
                        long finalVenuesCount = venuesCount;
                        e.bindToDatabase(ref, new Updater() {
                            @Override
                            public void onUpdate() {
                                e.setName(event.getValue(String.class));
                                e.setLocation(venue.getKey());
                                scheduledEvents.add(e);
                                if (finalVenuesCount == 0 && finalEventsCount == 0)
                                    updater.onUpdate();
                            }
                        });
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
        if (e.increment()) {
            joinedEvents.add(e);
            writeToDatabase(e, true, false);
        }
    }

    // Assume that location is valid (ie. venue exists). Assume that setData() has been called on e
    // to set user-input info.
    public static void scheduleEvent(Event e, Updater updater) {
        // Check for duplicate event names inside venue
        String name = e.getName();
        String location = e.getLocation();
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
                            e.setName(name);
                            e.setLocation(location);
                            e.setWriter();
                            Log.i("Customer", snapshot.getRef().toString());
                            e.writeToDatabase();
                            // Write new event to corresponding customer branch.
                            scheduledEvents.add(e);
                            writeToDatabase(e, false, true);
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
