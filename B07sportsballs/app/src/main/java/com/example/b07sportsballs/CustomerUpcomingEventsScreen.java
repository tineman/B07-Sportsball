package com.example.b07sportsballs;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.example.b07sportsballs.R;

import java.sql.Array;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashSet;

public class CustomerUpcomingEventsScreen extends AppCompatActivity {

    // Initialize layout elements.
    RecyclerView eventsView;
    CustomerUpcomingEventAdapter recyclerAdapter;

    // Initialize data
    private ArrayList<Event> events = new ArrayList<Event>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Customer.readFromDatabase(new Updater() {
            @Override
            public void onUpdate() {}
        });
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_upcoming_events_screen);

        // Set up layout elements.
        eventsView = findViewById(R.id.upcoming_events);
        eventsView.addItemDecoration(new DividerItemDecoration(eventsView.getContext(), DividerItemDecoration.VERTICAL));
        setAdapter();

        // Fetch data from database.
//        events.clear();
        setEvents();

        Button backButton = findViewById(R.id.CustomerUpcomingEventsScreen_Button_Back);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                backToCustomerHomePage();
            }
        });

        Button quitButton = findViewById(R.id.CustomerUpcomingEventsScreen_Button_Quit);
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
    public void backToCustomerHomePage() {
        this.finish();
    }

    /**
     * This method is called when the "Quit" button is pressed
     */
    public void quitApp() {
        this.finishAffinity();
    }

    private void setAdapter() {
        recyclerAdapter = new CustomerUpcomingEventAdapter(this, events);
        eventsView.setLayoutManager(new LinearLayoutManager(this));
        eventsView.setItemAnimator(new DefaultItemAnimator());
        eventsView.setAdapter(recyclerAdapter);
    }

    private void setEvents() {
        events.clear();
        Customer.collectUpcomingEvents(events, new Updater() { 
            @Override
            public void onUpdate() {
                // Remove events that customer already joins.
                for (Event event : Customer.joinedEvents) events.remove(event);
                // Sort events by starting time from newest to oldest.
                Collections.sort(events, new Comparator<Event>() {
                    @Override
                    public int compare(Event event, Event t1) {
                        return event.getStartTime().compareTo(t1.getStartTime());
                    }
                });

                Collections.reverse(events);
                recyclerAdapter.notifyDataSetChanged();
            }
        });
    }
}