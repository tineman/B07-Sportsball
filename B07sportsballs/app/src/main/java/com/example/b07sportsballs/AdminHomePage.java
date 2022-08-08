package com.example.b07sportsballs;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AdminHomePage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_home_page);

        //buttons for AdminHomePage
        Button logout_button = (Button) findViewById(R.id.AdminHomePage_Button_Logout);
        Button create_venue = (Button) findViewById((R.id.AdminHomePage_Button_CreateVenue));
        Button view_all_venues = (Button) findViewById(R.id.AdminHomePage_Button_ViewVenues);
        Button display_upcoming_events = (Button) findViewById(R.id.AdminHomePage_Button_DisplayEvents);
        Button quit_button = (Button) findViewById(R.id.AdminHomePage_Button_Quit);

        logout_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                logout();
            }
        });

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
            public void onClick(View view) {
                quit();
            }
        });

        //Get venue for ScheduleEventScreen
        DatabaseReference ref = FirebaseDatabase.getInstance(Constants.DATABASE.DB_URL).getReference();
        Venue venue = new Venue();
        venue.readFromDataBase(ref);
    }

    /**
     * This method is called when the "Log Out" button is clicked, and it takes the current page
     * back to the admin login screen.
     */
    public void logout()
    {
        Intent intent = getParentActivityIntent();
        startActivity(intent);
    }

    /**
     * This method is called when the "Create a new venue" button is clicked, and it
     * opens the CreateVenueScreen.
    */
    public void openCreateVenuesScreen()
    {
       Intent intent = new Intent(this, CreateVenueScreen.class);
       startActivity(intent);
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
        Intent intent = new Intent(this, AdminUpcomingEventsScreen.class);
        startActivity(intent);
    }

    /**
     * This method is called when the "Quit" button is pressed
     */
    public void quit()
    {
        this.finishAffinity();
    }
}
