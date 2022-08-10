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

    /**
     * Write a specific event to a specific location.
     * @param e event to be written
     * @param toJoinedEvents true if event is to be added to joinedEvents
     * @param toScheduledEvents true if event is to be added to scheduledEvents
     */
    public static void writeToDatabase(Event e, boolean toJoinedEvents, boolean toScheduledEvents) {
        if (toJoinedEvents) {
            DatabaseReference joinedEventsRoot = ref.child
                    (Constants.DATABASE.CUSTOMER_JOINED_EVENTS_KEY).child(e.getLocation());
            joinedEventsRoot.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DataSnapshot> task) {
                    for (DataSnapshot event:task.getResult().getChildren())
                        if (event.getValue(String.class) == e.getName()) return;
                    if (Customer.joinedEvents.size() == 0) {
                        Map<String, String> joinedEventsData = new HashMap<String, String>();
                        joinedEventsData.put(String.valueOf(0), e.getName());
                        ref.child(Constants.DATABASE.CUSTOMER_JOINED_EVENTS_KEY).child(e.getLocation()).setValue(joinedEventsData);
                    }
                    else {
                        DatabaseReference joinedEventsRoot = ref.child
                                (Constants.DATABASE.CUSTOMER_JOINED_EVENTS_KEY);
                        joinedEventsRoot.child(e.getLocation()).push().setValue(e.getName());
                    }
                }
            });
        }
        if (toScheduledEvents) {
            DatabaseReference scheduledEventsRoot = ref.child
                    (Constants.DATABASE.CUSTOMER_JOINED_EVENTS_KEY).child(e.getLocation());
            scheduledEventsRoot.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DataSnapshot> task) {
                    for (DataSnapshot event:task.getResult().getChildren())
                        if (event.getValue(String.class) == e.getName()) return;
                    if (Customer.scheduledEvents.size() == 0) {
                        Map<String, String> scheduledEventsData = new HashMap<String, String>();
                        scheduledEventsData.put(String.valueOf(0), e.getName());
                        ref.child(Constants.DATABASE.CUSTOMER_SCHEDULED_EVENTS_KEY).child(e.getLocation()).setValue(scheduledEventsData);
                    }
                    else {
                        DatabaseReference scheduledEventsRoot = ref.child
                                (Constants.DATABASE.CUSTOMER_SCHEDULED_EVENTS_KEY);
                        scheduledEventsRoot.child(e.getLocation()).push().setValue(e.getName());
                    }
                }
            });
        }
    }

    private static void readJoinedEventsFromDatabase(Updater updater) {
        DatabaseReference joinedEventsRoot = ref.child(Constants.DATABASE.CUSTOMER_JOINED_EVENTS_KEY);
        joinedEventsRoot.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                // Store all events that have been removed. Could be empty.
                Log.i("Customer", String.format("data changed, now have %d joined events",
                        snapshot.getChildrenCount()));
//                HashSet<Event> joinedEventsOG = new HashSet<Event>(joinedEvents);
                joinedEvents.clear();
                if (!snapshot.exists()) {
                    updater.onUpdate();
                } else {
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
                                    Log.i("CustomerTest", e.collectRef().toString());
                                    Log.i("CustomerJoin", "added "+e.getName());
                                    joinedEvents.add(e);
//                                    joinedEventsOG.remove(e);
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
//                HashSet<Event> scheduledEventsOG = new HashSet<Event>(scheduledEvents);
                scheduledEvents.clear();
                if (!snapshot.exists()) updater.onUpdate();
                else {
                    long venuesCount = snapshot.getChildrenCount();
                    Log.i("Customer", String.format("venues count: %d", venuesCount));
                    long eventsCount = 0L;
                    for (DataSnapshot venue : snapshot.getChildren()) {
                        venuesCount--;
                        Log.i("Customer", String.format("venues left: %d", venuesCount));
                        eventsCount += venue.getChildrenCount();
                        Log.i("Customer", String.format("events count: %d", eventsCount));
                        for (DataSnapshot event : venue.getChildren()) {
                            eventsCount--;
                            Log.i("Customer", String.format("events count: %d", eventsCount));
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
                                    Log.i("CustomerSchedule", "added "+e.getName());
//                                    scheduledEventsOG.remove(e);
                                    if (finalVenuesCount == 0 && finalEventsCount == 0) {
//                                        for (Event removedEvent : scheduledEventsOG) {
//                                            ref.setValue(null);
//                                        }
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
//            writeToDatabase(e, true, false);
            writeToDatabase();
        }
    }

//    public static void removeJoinedEvent(Event e) {
//        e.setCurrPlayers(e.getCurrPlayers()-1);
//        e.setWriter();
//        e.writeToDatabase();
//    }

    // Assume that location is valid (ie. venue exists). Assume that setData() has been called on e
    // to set user-input info.
    public static void scheduleEvent(Event e, Updater updater) {
        // Check for duplicate event names inside venue
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
                            Log.i("Customer", snapshot.getRef().toString());
                            e.writeToDatabase();
                            // Write new event to corresponding customer branch.
                            if (!scheduledEvents.contains(e))
                                scheduledEvents.add(e);
//                                writeToDatabase(e, false, true);
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
