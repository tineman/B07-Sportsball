package com.example.b07sportsballs;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.example.b07sportsballs.R;

import java.sql.Array;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;

public class CustomerUpcomingEventsScreen extends AppCompatActivity {

    // Initialize layout elements.
    RecyclerView eventsView;
    CustomerUpcomingEventAdapter recyclerAdapter;

    // Initialize data
    private ArrayList<Event> events;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_upcoming_events_screen);

        // Set up layout elements.
        eventsView = findViewById(R.id.upcoming_events);
        setAdapter();

        // Fetch data from database.
        events = new ArrayList<Event>();
        setData();

    }

    private void setAdapter() {
        recyclerAdapter = new CustomerUpcomingEventAdapter(this, events);
        eventsView.setLayoutManager(new LinearLayoutManager(this));
        eventsView.setItemAnimator(new DefaultItemAnimator());
        eventsView.setAdapter(recyclerAdapter);
    }

    //TODO: Modify to read and store all events from database.
    private void setData() {
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

        // Sort events by starting time from newest to oldest.
        Collections.sort(events, new Comparator<Event>() {
            @Override
            public int compare(Event event, Event t1) {
                return event.startTime.compareTo(t1.startTime);
            }
        });

        Collections.reverse(events);
    }

}