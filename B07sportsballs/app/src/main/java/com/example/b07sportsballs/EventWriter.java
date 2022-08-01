package com.example.b07sportsballs;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class EventWriter {

    public EventWriter() {
    }

    public void write(Event event)
    {

        DatabaseReference EventRef = FirebaseDatabase.getInstance("https://sportsballtesting-default-rtdb.firebaseio.com/").getReference("Tian-Testing/Events");
        EventRef.child(event.getName()).setValue(event);

    }
}
