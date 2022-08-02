package com.example.b07sportsballs;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class EventReader {

    private Event parent;

    public EventReader(Event event)
    {
        parent = event;
    }

    public interface Updater
    {
        void onUpdate();
    }

    /**
     *
     * @param ref
     * @param
     */
    public void read(DatabaseReference ref, Updater updater)
    {

        ref.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                //Null checking?
                Event event = snapshot.getValue(Event.class);
                parent.setData(event);
                updater.onUpdate();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

}
