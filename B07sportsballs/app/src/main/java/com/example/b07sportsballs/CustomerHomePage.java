package com.example.b07sportsballs;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class CustomerHomePage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_home_page);

        Intent intent = getIntent();
        String username = intent.getStringExtra(CustomerLoginScreen.EXTRA_MESSAGE);
        TextView textView = findViewById(R.id.CustomerHomePage_Username);
        String[] splitName = username.split("@");
        textView.setText(splitName[0]);

        Button eventsJoinedButton = findViewById(R.id.CustomerHomePage_EventsJoined);
        Button scheduledEventsButton = findViewById(R.id.CustomerHomePage_EventsScheduled);
        Button upcomingEventsButton = findViewById(R.id.CustomerHomePage_UpcomingEvents);
        Button venuesButton = findViewById(R.id.CustomerHomePage_ViewVenues);
        Button createEventButton = findViewById(R.id.CustomerHomePage_ScheduleEvent);
        Button logOutButton = findViewById(R.id.CustomerHomePage_LogOut);
        Button quitButton = findViewById(R.id.CustomerHomePage_QuitButton);


        eventsJoinedButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openEventsJoined();
            }
        });

        scheduledEventsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openScheduledEvents();
            }
        });
        upcomingEventsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openUpcomingEvents();
            }
        });
        venuesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openVenues();
            }
        });
        createEventButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openCreateAnEvent();
            }
        });
        logOutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                backToLoginScreen();
            }
        });
        quitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                quitApp(view);
            }
        });


    }

    public void quitApp(View view) {
        this.finishAffinity();
    }

    // add the following to the functions below (and don't need to use the onClick command in the
    // attributes):
    // Intent intent = new Intent(this, "ActivityClassName".class);
    // startActivity(intent);

    public void openEventsJoined(){
        Intent intent = new Intent(this, CustomerEventsJoinedScreen.class);
        startActivity(intent);
    }
    public void openScheduledEvents(){
        Intent intent = new Intent(this, CustomerEventsScheduledScreen.class);
        startActivity(intent);
    }
    public void openUpcomingEvents(){

    }
    public void openVenues(){

    }
    public void openCreateAnEvent(){
        Intent intent = new Intent(this, ScheduleEventScreen.class);
        startActivity(intent);
    }
    public void backToLoginScreen(){
        Intent intent= getParentActivityIntent();
        startActivity(intent);

    }


}