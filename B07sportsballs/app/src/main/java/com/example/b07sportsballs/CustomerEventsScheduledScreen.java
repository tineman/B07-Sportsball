package com.example.b07sportsballs;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class CustomerEventsScheduledScreen extends AppCompatActivity {

    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        //We need a customer class to access their hashset of

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_events_scheduled_screen);




        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);




        //The following should be replaced by getExtra and getting list of events from customer

        Event event1 = new Event();
        Event event2 = new Event();
        Event event3 = new Event();

        event1.bindToDatabase(FirebaseDatabase.getInstance("https://sportsballtesting-default-rtdb.firebaseio.com/").getReference("Tian-Testing/Venues/UOFT/Events/Chess Boxing"), new EventBinder.Updater() {
            @Override
            public void onUpdate() {
                //This represents the previous onUpdate method
            }
        });
        event2.bindToDatabase(FirebaseDatabase.getInstance("https://sportsballtesting-default-rtdb.firebaseio.com/").getReference("Tian-Testing/Venues/UOFT/Events/Nettle Eating"), new EventBinder.Updater() {
            @Override
            public void onUpdate() {
                //This represents the previous onUpdate method
            }
        });
        event3.bindToDatabase(FirebaseDatabase.getInstance("https://sportsballtesting-default-rtdb.firebaseio.com/").getReference("Tian-Testing/Venues/UOFT/Events/Water Polo"), new EventBinder.Updater() {
            @Override
            public void onUpdate() {
                //This represents the previous onUpdate method
            }
        });

        List<Event> events = new ArrayList<>();
        events.add(event1);
        events.add(event2);
        events.add(event3);

        //

        for(Event event : events)
        {
            event.changeOnUpdate(new EventBinder.Updater() {
                @Override
                public void onUpdate() {
                    new EventRecyclerviewConfig().setConfig(recyclerView, CustomerEventsScheduledScreen.this, events);
                }
            });
        }

    }
}