package com.example.b07sportsballs;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

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
        recyclerView.addItemDecoration(new DividerItemDecoration(recyclerView.getContext(), DividerItemDecoration.VERTICAL));
        //Set up spinner
        spinner = (Spinner) findViewById(R.id.EventVenueSpinner);
        venues = new ArrayList<>(Venue.getAllVenues());

        if(venues == null || venues.isEmpty())
        {
            Toast.makeText(EventbyVenueScreen.this, "No venues found!", Toast.LENGTH_LONG).show();
            return;
        }

        arrayAdapter = new ArrayAdapter(EventbyVenueScreen.this, android.R.layout.simple_spinner_item, venues);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(arrayAdapter);

        //Initialise events list
        events = new ArrayList<Event>();

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                UpdateRecycler();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        //Back button
        Button backButton = findViewById(R.id.EventVenueBack_Button);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                backToHomePage();
            }
        });

        //Quit button
        Button quitButton = findViewById(R.id.EventVenueQuit_Button);
        quitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                quitApp();
            }
        });
    }

    /**
     * This method is called when the "Back" button is pressed
     */
    public void backToHomePage() {
        this.finish();
    }

    /**
     * This method is called when the "Quit" button is pressed
     */
    public void quitApp() {
        this.finishAffinity();
    }

    /**
     * Gets the current venue selected by spinner and sets up the recyclerview
     */
    public void UpdateRecycler() {

        //Get desired venue
        venue = spinner.getSelectedItem().toString();
        //Clear previous screen
        events.clear();

        //Temporary measure
        DatabaseReference ref = FirebaseDatabase.
                getInstance(Constants.DATABASE.DB_URL).
                getReference(Constants.DATABASE.ROOT+"/"+
                        Constants.DATABASE.VENUE_PATH+"/"+
                        venue+"/"+
                        Constants.DATABASE.VENUE_EVENTS_KEY);

        //Gets events by venue and adds them to recyclerview
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot event : dataSnapshot.getChildren()) {

                    String eventName = event.getKey();
                    Event e = new Event();
                    events.add(e);
                    e.bindToDatabase(FirebaseDatabase.
                            getInstance(Constants.DATABASE.DB_URL).
                            getReference(Constants.DATABASE.ROOT + "/" +
                                    Constants.DATABASE.VENUE_PATH + "/" +
                                    venue + "/" +
                                    Constants.DATABASE.VENUE_EVENTS_KEY + "/" +
                            eventName), new Updater() {
                        @Override
                        public void onUpdate() {
                            new EventRecyclerviewConfig().setConfig(recyclerView, EventbyVenueScreen.this, events);
                        }
                    });
                }
                if(events == null || events.isEmpty())
                {
                    Toast.makeText(EventbyVenueScreen.this, "No events found!", Toast.LENGTH_LONG).show();
                    //To clear the EventRecycler
                    new EventRecyclerviewConfig().setConfig(recyclerView, EventbyVenueScreen.this, events);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.i("EventbyVenueScreen", databaseError.toString());
            }
        });

    }

    /**
     * Goes to ScheduleEventScreen
     */
    public void ScheduleEvent(View view) {

        Log.i("Usertype", User.usertype);
        if(User.usertype == Constants.DATABASE.ADMIN_TYPE)
        {
            Toast.makeText(EventbyVenueScreen.this, "Admins cannot create events", Toast.LENGTH_LONG).show();
            return;
        }

        Intent intent = new Intent(EventbyVenueScreen.this, ScheduleEventScreen.class);
        venue = spinner.getSelectedItem().toString();
        intent.putExtra(VenueScreen.CUSTOMVENUE, venue);
        startActivity(intent);
        return;

    }

}