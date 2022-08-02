package com.example.b07sportsballs;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class EventWriter {

    public EventWriter() {
    }

    /*
    Writes Event event to the database
    DatabaseReference ref points to the root node of the database
     */
    public void write(DatabaseReference ref, Event event)
    {

        //Add a transaction

        ref.child("Venues").child(event.getLocation()).child("Events").child(event.getName()).setValue(event);

    }
}
