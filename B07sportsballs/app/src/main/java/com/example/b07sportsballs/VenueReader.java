package com.example.b07sportsballs;

import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.HashSet;

public class VenueReader extends AppCompatActivity {
    public static HashSet<String> keys = new HashSet<String>();
    public static DatabaseReference ref;
    public static boolean isRunning = true;
    public VenueReader() {}

    public void readData(FirebaseCallback myCallback) {
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                Log.i("demo", "data changed");
                for (DataSnapshot child : snapshot.getChildren()) {
                    String key = child.getKey();
                    keys.add(key);
                }
                myCallback.onCallback(keys);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w("VenueReader warning", "Error while reading venues from database",
                        databaseError.toException());
            }

        });
    }

    public void read(DatabaseReference ref) {
        readData(new FirebaseCallback(){
            @Override
            public void onCallback(HashSet<String> keys) {
                Venue.allVenues = keys;
                isRunning = false;
                Venue v = new Venue();
                v.getAllVenues();
            }
        });
    }






}










