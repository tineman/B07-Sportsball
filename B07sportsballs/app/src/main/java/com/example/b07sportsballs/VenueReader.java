package com.example.b07sportsballs;

import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;


import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.HashSet;

public class VenueReader extends AppCompatActivity
{
    public static HashSet<String> keys = new HashSet<String>();

    public VenueReader()
    {

    }
    public void read(DatabaseReference ref)
    {
        ref = ref.child("Root").child("Venues");

        ref.addValueEventListener(new ValueEventListener()
                                  {
                                      @Override
                                      public void onDataChange(DataSnapshot dataSnapshot)
                                      {
                                          HashSet<String> venues = new HashSet<String>();
                                          Log.i("demo", "data changed");
                                          for (DataSnapshot child : dataSnapshot.getChildren()) {
                                              String key = child.getKey();
                                              Log.i("demo", "Key: " +key);
                                              venues.add(key);
                                          }
                                          updateKeys(venues);
                                      }

                                      @Override
                                      public void onCancelled(DatabaseError databaseError)
                                      {
                                          Log.w("VenueReader warning", "Error while reading venues from database",
                                                  databaseError.toException());
                                      }
                                  }

        );


    }
    public void updateKeys(HashSet<String> venues)
    {
        for (String key: venues)
        {
            if (!keys.contains(key))
            {
                keys.add(key);
            }
        }
    }




}


