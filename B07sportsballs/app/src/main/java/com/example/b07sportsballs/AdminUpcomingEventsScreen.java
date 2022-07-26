package com.example.b07sportsballs;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class AdminUpcomingEventsScreen extends AppCompatActivity
        implements AdapterView.OnItemSelectedListener {

    // Initialize layout elements.
    RecyclerView eventsView;
    AdminUpcomingEventAdapter recyclerAdapter;
    Spinner spinnerVenue;
    ArrayAdapter spinnerAdapter;

    // Initialize data.
    private ArrayList<Event> events = new ArrayList<Event>();
    private ArrayList<String> venues = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_upcoming_events_screen);

        // Set up layout elements.
        eventsView = findViewById(R.id.upcoming_events);
        setRecyclerAdapter();
        spinnerVenue = findViewById(R.id.spinnerVenue);
        setSpinnerAdapter();

        // Fetch data from database.
        setEvents();
        setVenues();

        Button backButton = findViewById(R.id.AdminUpcomingEventsScreen_Button_Back);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                backToAdminHomePage();
            }
        });

        Button quitButton = findViewById(R.id.AdminUpcomingEventsScreen_Button_Quit);
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
    private void backToAdminHomePage() {
        this.finish();
    }

    /**
     * This method is called when the "Quit" button is pressed
     */
    private void quitApp() {
        this.finishAffinity();
    }

    private void setRecyclerAdapter() {
        recyclerAdapter = new AdminUpcomingEventAdapter(this, events);
        eventsView.setLayoutManager(new LinearLayoutManager(this));
        eventsView.setItemAnimator(new DefaultItemAnimator());
        eventsView.setAdapter(recyclerAdapter);
    }

    private void setSpinnerAdapter() {
        spinnerAdapter = new ArrayAdapter(this,
                android.R.layout.simple_spinner_item, venues);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerVenue.setAdapter(spinnerAdapter);
        spinnerVenue.setOnItemSelectedListener(this);
    }

    // Display events by venue as selected by user.
    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        recyclerAdapter.getFilter().filter(adapterView.getItemAtPosition(i).toString());
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
        recyclerAdapter.getFilter().filter("All Venues");
    }

    private void setEvents() {
        Admin.collectUpcomingEvents(events, new Updater() {
            @Override
            public void onUpdate() {
                // Sort events by startTime from newest to oldest.
                Collections.sort(events, new Comparator<Event>() {
                    @Override
                    public int compare(Event event, Event t1) {
                        return event.getStartTime().compareTo(t1.getStartTime());
                    }
                });

                Collections.reverse(events);
                setRecyclerAdapter();
            }
        });
    }


    private void setVenues() {
        Admin.collectVenuesNames(venues, new Updater() {
            @Override
            public void onUpdate() {
                venues.add(0, "All Venues");
                spinnerAdapter.notifyDataSetChanged();
            }
        });
    }
}