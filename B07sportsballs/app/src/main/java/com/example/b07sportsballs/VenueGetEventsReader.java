package com.example.b07sportsballs;

import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.HashSet;

public class VenueGetEventsReader {
    private static HashSet<String> events = new HashSet<String>();
    public DatabaseReference ref;
    public static boolean isRunning = true;
    public VenueGetEventsReader() {}

    public void readData(Updater updater) {
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                Log.i("demo", "data changed");
                for (DataSnapshot child : snapshot.getChildren()) {
                    String key = child.getKey();
                    events.add(key);
                }
                updater.onUpdate();
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w("VenueReader warning", "Error while reading events from the venue",
                        databaseError.toException());
            }

        });
    }

    public void read(DatabaseReference ref, Venue v) {
        this.ref = ref;
        readData(new Updater(){
            @Override
            public void onUpdate()
            {
                isRunning = false;
                v.storeAllEvents(events);
            }
        });
    }
}
