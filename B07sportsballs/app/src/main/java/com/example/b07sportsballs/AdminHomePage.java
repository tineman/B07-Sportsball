package com.example.b07sportsballs;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class AdminHomePage extends AppCompatActivity {
    public boolean clicked_quit=false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_home_page);

        //buttons for AdminHomePage
        Button create_venue = (Button) findViewById((R.id.AdminHomePage_create_venue));
        Button view_all_venues = (Button) findViewById(R.id.AdminHomePage_view_venues);
        Button display_upcoming_events = (Button) findViewById(R.id.AdminHomePage_display_events);
        Button quit_button = (Button) findViewById(R.id.button4);

        create_venue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openCreateVenuesScreen();
            }
        });

        view_all_venues.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openVenueScreen();
            }
        });

        display_upcoming_events.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openDisplayUpcomingEventsScreen();
            }
        });

        quit_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                clicked_quit=true;
                quit();
            }
        });


    }
    /**
     * This method is called when the "Create a new venue" button is clicked, and it
     * opens the CreateVenueScreen.
    */
    public void openCreateVenuesScreen()
    {
        //uncomment the lines below once CreateVenueScreen.class has been added

        //Intent intent = new Intent(this, CreateVenueScreen.class);
        //startActivity(intent);
    }

    /**
     * This method is called when the "View all venues" button is clicked, and it
     * opens the VenueScreen.
     */
    public void openVenueScreen()
    {
        Intent intent = new Intent(this, VenueScreen.class);
        startActivity(intent);
    }

    /**
     * This method is called when the "Display Upcoming Events" button is clicked, and it
     * opens AdminUpcomingEventsScreen
     */
    public void openDisplayUpcomingEventsScreen()
    {
        //uncomment the lines below once DisplayUpcomingEvents.class has been added

        //Intent intent = new Intent(this, DisplayUpcomingEvents.class);
        //startActivity(intent);
    }


    /**
     * This method is called when the "Quit" button is clicked, and it takes the current page
     * back to the customer login screen.
     */
    public void quit()
    {
        if(clicked_quit)
        {
            this.finish();
        }
    }


}
