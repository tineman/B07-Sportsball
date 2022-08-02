package com.example.b07sportsballs;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class EventDatabaseHelper {

        /*
    private FirebaseDatabase db;
    private DatabaseReference ref;
    private List<Event> events = new ArrayList<>();

    public EventDatabaseHelper() { //change database reference
        db = FirebaseDatabase.getInstance("https://sportsballtesting-default-rtdb.firebaseio.com/");
        ref = db.getReference("Tian-Testing/Venues/UOFT/Events");
    }

    public interface DataHelper
    {
        void DataisLoaded(List<Event> events, List<String> keys);
    }

    public void readEvents(final DataHelper dataHelper)
    {
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                events.clear();

                List<String> keys = new ArrayList<>();
                for (DataSnapshot keyNode : snapshot.getChildren())
                {
                    keys.add(keyNode.getKey());
                    Event event = keyNode.getValue(Event.class);
                    events.add(event);
                }

                dataHelper.DataisLoaded(events, keys);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    */

}
