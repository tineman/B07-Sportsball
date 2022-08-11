package com.example.b07sportsballs;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.view.ViewGroup;
import android.widget.Toast;


import java.util.ArrayList;
import java.util.List;

public class CustomerEventsJoinedScreen extends AppCompatActivity {

    private RecyclerView recyclerView;

    private View noEvents;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_events_joined_screen);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView2);

        noEvents = findViewById(R.id.customerEventJoinedScreen_View_NoEvents);
        noEvents.setVisibility(View.VISIBLE);

        Customer.readFromDatabase(new Updater() {
            @Override
            public void onUpdate() {

                List<Event> events = new ArrayList<>(Customer.getJoinedEvents());

                //Updating the recyclerview
                for(Event event : events)
                {
                    //Notifes user of no events. Cannot use toast because loading a list of events
                    //is asynchronous
                    noEvents.setVisibility(View.GONE);
                    event.changeOnUpdate(new Updater() {
                        @Override
                        public void onUpdate() {
                            new EventRecyclerviewConfig().setConfig(recyclerView, CustomerEventsJoinedScreen.this, events);
                        }
                    });
                }

            }
        });

        //Back button
        Button backButton = findViewById(R.id.CustomerEventsJoinedScreen_Button_Back);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                backToCustomerHomePage();
            }
        });

        //Quit button
        Button quitButton = findViewById(R.id.CustomerEventsJoinedScreen_Button_Quit);
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
        Intent intent = new Intent(this, CustomerHomePage.class);
        startActivity(intent);
    }

    /**
     * This method is called when the "Quit" button is pressed
     */
    public void quitApp() {
        this.finishAffinity();
    }
}