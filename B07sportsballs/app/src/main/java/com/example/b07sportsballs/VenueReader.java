package com.example.b07sportsballs;

import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashSet;

public class VenueReader extends AppCompatActivity
{
    //stores all of the venue names in this HashSet
    public HashSet<String> keys = new HashSet<String>();

    public void read(DatabaseReference ref)
    {
        ref = ref.child("Root").child("Venues");

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot)
            {
                Log.i("demo", "data changed");
                for (DataSnapshot child : dataSnapshot.getChildren()) {
                    String key = child.getKey();
                    keys.add(key);
                    Venue v = child.getValue(Venue.class);
                    Log.i("demo", "Venue and events for the venue: " + child);
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError)
            {
                Log.w("VenueReader warning", "Error while reading venues from database",
                        databaseError.toException());
            }
        });

    }


}