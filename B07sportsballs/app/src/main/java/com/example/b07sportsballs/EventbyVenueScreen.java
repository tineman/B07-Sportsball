package com.example.b07sportsballs;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class EventbyVenueScreen extends AppCompatActivity {

    RecyclerView recyclerView;
    Spinner spinner;

    ArrayAdapter arrayAdapter;
    ArrayList<String> venues;

    //Current venue
    String venue;

    List<Event> events;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eventby_venue_screen);

        //Set up recyclerview
        recyclerView = (RecyclerView) findViewById(R.id.EventVenueRecyclerView);

        //Set up spinner
        spinner = (Spinner) findViewById(R.id.EventVenueSpinner);
        venues = new ArrayList<>(Venue.getAllVenues());
        arrayAdapter = new ArrayAdapter(EventbyVenueScreen.this, android.R.layout.simple_spinner_item, venues);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(arrayAdapter);

        //Initialise events list
        events = new ArrayList<Event>();

    }

    /**
     * Gets the current venue selected by spinner and sets up the recyclerview
     */
    public void UpdateRecycler(View view) {

        venue = spinner.getSelectedItem().toString();

        /*get list of events*/

        events.clear();
        events = new ArrayList<>(Customer.getJoinedEvents());

        //Updating the recyclerview
        for(Event event : events)
        {
            event.changeOnUpdate(new Updater() {
                @Override
                public void onUpdate() {
                    new EventRecyclerviewConfig().setConfig(recyclerView, EventbyVenueScreen.this, events);
                }
            });
        }

    }

    /**
     * Goes to ScheduleEventScreen
     */
    public void ScheduleEvent(View view) {

        if(User.ref.toString().contains(Constants.DATABASE.ADMIN_PATH)) //ask about User type field
        {
            Toast.makeText(EventbyVenueScreen.this, "Admins cannot create events", Toast.LENGTH_LONG).show();
            return;
        }

        Intent intent = new Intent(EventbyVenueScreen.this, ScheduleEventScreen.class);
        intent.putExtra(VenueScreen.CUSTOMVENUE, venue);
        startActivity(intent);
        return;

    }

}