package com.example.b07sportsballs;

import android.os.Build;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.lang.reflect.Array;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.TimeZone;

public abstract class User {
    static String username;
    static String password;
    static DatabaseReference ref;
    static String usertype;

    // Default constructor for reading from database.
    public User() {}

    // This constructor is called only once when the user logs in/signs up successfully.
    public User (String username, String password, DatabaseReference ref, String usertype) {
        this.username = username;
        this.password = password;
        this.ref = ref;
        this.usertype = usertype;
    }

    /**
     * Updates <code>venuesNames</code> to include names of all venues in the database.
     * @param venuesNames store all venues' names
     * @param updater callback to notify data has been retrieved
     */
    public static void collectVenuesNames(ArrayList<String> venuesNames, Updater updater) {
        DatabaseReference venuesRef = FirebaseDatabase.getInstance(Constants.DATABASE.DB_URL).
                getReference(Constants.DATABASE.ROOT+"/"+Constants.DATABASE.VENUE_PATH);
        venuesRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot data : snapshot.getChildren()) {
                    venuesNames.add(data.getKey());
                }
                updater.onUpdate();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.w("User Warning", "Error while reading venues from database",
                        error.toException());
            }
        });
    }

    /**
     * Updates <code>events</code> to include all events that have not passed in the database.
     * @param events store all upcoming events in the database
     * @param updater callback to notify data has been retrieved
     */
    public static void collectUpcomingEvents(ArrayList<Event> events, Updater updater) {
        Date now = new Date();
        DatabaseReference venuesRef = FirebaseDatabase.getInstance(Constants.DATABASE.DB_URL).
                getReference(Constants.DATABASE.ROOT+"/"+Constants.DATABASE.VENUE_PATH);
        venuesRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                // venuesCount and eventsCount keep track of the number of venues/events left
                // to check. Once there are no venues and events left, onUpdate() is called to
                // let the calling function know that reading is done.
                long venuesCount = snapshot.getChildrenCount();
                long eventsCount = 0L;
                for (DataSnapshot venue : snapshot.getChildren()) {
                    venuesCount--;
                    eventsCount += venue.child(Constants.DATABASE.VENUE_EVENTS_KEY).getChildrenCount();
                    for (DataSnapshot event :
                            venue.child(Constants.DATABASE.VENUE_EVENTS_KEY).getChildren()) {
                        eventsCount--;
                        Event e = new Event();
                        Log.i("UserTest", event.getRef().toString());
                        long finalEventsCount = eventsCount;
                        long finalVenuesCount = venuesCount;
                        e.bindToDatabase(event.getRef(), new Updater() {
                            @Override
                            public void onUpdate() {
                                if (e.getStartTime().compareTo(now) > 0 && !events.contains(e)) {
                                    events.add(e);
                                }
                                if (finalVenuesCount == 0 && finalEventsCount == 0) {
                                    updater.onUpdate();
                                }
                            }
                        });
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.w("UserWarning", "Error while reading events from database",
                        error.toException());
            }
        });
    }
}
