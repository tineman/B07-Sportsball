package com.example.b07sportsballs;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
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
    private ArrayList<Event> events;
    private ArrayList<String> venues;

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
        events = new ArrayList<Event>();
        setEvents();
        venues = new ArrayList<String>();
        setVenues();
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
        recyclerAdapter.getFilter().filter(null);
    }

    //TODO: Modify to read and store all events from database.
    private void setEvents() {
        SimpleDateFormat timeFormat = new SimpleDateFormat("d MMM yyyy HH:mm a");
        try {
            events.add(new Event("Tennis", "Australia Open", "Wimbledon",
                    timeFormat.parse("4 Aug 2022 08:00 AM"),
                    timeFormat.parse("4 Dec 2022 08:00 AM"),
                    15, 20));
            events.add(new Event("AFC U-23 Asian Cup", "AFC", "Bunyodkor Stadium",
                    timeFormat.parse("1 Aug 2022 09:00 AM"),
                    timeFormat.parse("1 Aug 2022 09:00 AM"),
                    100, 100));
            events.add(new Event("sleeping", "me", "my bed",
                    timeFormat.parse("30 Aug 2022 12:00 PM"),
                    timeFormat.parse(" 20 Dec 2022 12:00 PM"),
                    0, 1));
        }
        catch (ParseException e) {
        }

        // Sort <code>events</code> by <code>startTime</code> from newest to oldest.
        Collections.sort(events, new Comparator<Event>() {
            @Override
            public int compare(Event event, Event t1) {
                return event.startTime.compareTo(t1.startTime);
            }
        });

        Collections.reverse(events);
    }

    //TODO: Modify to read and store all venues from database.
    private void setVenues() {
        venues.add("All Venues");
        venues.add("Wimbledon");
        venues.add("Bunyodkor Stadium");
        venues.add("my bed");
    }
}