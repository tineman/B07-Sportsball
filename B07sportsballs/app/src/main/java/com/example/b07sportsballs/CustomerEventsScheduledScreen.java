package com.example.b07sportsballs;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.Toast;

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

        if(Customer.getScheduledEvents() == null)
        {
            Toast.makeText(CustomerEventsScheduledScreen.this, "No events found!", Toast.LENGTH_LONG).show();
            ((ViewGroup) recyclerView.getParent()).removeView(recyclerView);
            return;
        }

        List<Event> events = new ArrayList<>(Customer.getScheduledEvents());


        for(Event event : events)
        {
            event.changeOnUpdate(new Updater() {
                @Override
                public void onUpdate() {
                    new EventRecyclerviewConfig().setConfig(recyclerView, CustomerEventsScheduledScreen.this, events);
                }
            });
        }

    }
}