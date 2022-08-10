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

public class CustomerEventsJoinedScreen extends AppCompatActivity {

    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_events_joined_screen);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView2);

        Customer.readFromDatabase(new Updater() {
            @Override
            public void onUpdate() {
                //Remove view to prevent a non-fatal error
                if(Customer.getJoinedEvents() == null)
                {
                    Toast.makeText(CustomerEventsJoinedScreen.this, "No events found!", Toast.LENGTH_LONG).show();
                    ((ViewGroup) recyclerView.getParent()).removeView(recyclerView);
                    return;
                }

                List<Event> events = new ArrayList<>(Customer.getJoinedEvents());

                //Updating the recyclerview
                for(Event event : events)
                {
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
//        this.finish();
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