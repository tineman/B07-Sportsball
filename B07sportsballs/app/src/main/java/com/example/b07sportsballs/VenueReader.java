package com.example.b07sportsballs;

import static com.example.b07sportsballs.Venue.allVenues;

import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.HashSet;

public class VenueReader extends AppCompatActivity {
    private static HashSet<String> keys = new HashSet<String>();
    public static DatabaseReference ref;
    public static boolean isRunning = true;
    public VenueReader() {}

    public void readData(Updater updater) {
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                Log.i("demo", "data changed");
                for (DataSnapshot child : snapshot.getChildren()) {
                    String key = child.getKey();
                    keys.add(key);
                }
                updater.onUpdate();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w("VenueReader warning", "Error while reading venues from database",
                        databaseError.toException());
            }

        });
    }

    public void read(DatabaseReference ref) {
        readData(new Updater(){
            @Override
            public void onUpdate()
            {
                Venue.allVenues.addAll(keys);
                isRunning = false;
                Venue.getAllVenues();

            }
        });
    }
}










