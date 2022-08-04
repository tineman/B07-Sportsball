package com.example.b07sportsballs;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.MutableData;
import com.google.firebase.database.ServerValue;
import com.google.firebase.database.Transaction;

import java.util.HashMap;
import java.util.Map;

public class EventWriter {

    public EventWriter() {
    }

    /**
     * Writes Event to database
     * @param ref the reference to the place in the database to be written
     * @param event the event to be written to the database
     */
    public void write(DatabaseReference ref, Event event)
    {

        ref.setValue(event);

    }

    /**
     * Updates the number of people in the server with an atomic increment. This ensures multiple
     * people adding themselves to an event do not corrupt the currPlayer value
     *
     * @param ref A reference to the event in question
     */
    public void increment(DatabaseReference ref)
    {

        Map<String, Object> update = new HashMap<>();
        update.put("currPlayers", ServerValue.increment(1));
        ref.updateChildren(update);

    }


}
