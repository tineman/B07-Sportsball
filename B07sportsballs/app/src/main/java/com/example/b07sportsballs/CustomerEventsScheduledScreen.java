package com.example.b07sportsballs;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.view.ViewGroup;
import android.widget.Toast;


import java.util.ArrayList;
import java.util.List;

public class CustomerEventsScheduledScreen extends AppCompatActivity {

    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_events_scheduled_screen);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);

        Customer.readFromDatabase(new Updater() {
            @Override
            public void onUpdate() {

                List<Event> events = new ArrayList<>(Customer.getScheduledEvents());

                //Update the recyclerview with constantly updating events
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
        });


        //Back button
        Button backButton = findViewById(R.id.CustomerEventsScheduledScreen_Button_Back);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                backToCustomerHomePage();
            }
        });

        //Quit button
        Button quitButton = findViewById(R.id.CustomerEventsScheduledScreen_Button_Quit);
        quitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                quitApp();
            }
        });

        if (Customer.getScheduledEvents() == null || Customer.getJoinedEvents().isEmpty())
        {
            Toast.makeText(CustomerEventsScheduledScreen.this, "No events found!", Toast.LENGTH_LONG).show();
        }

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