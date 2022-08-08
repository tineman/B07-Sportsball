package com.example.b07sportsballs;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class VenueScreen extends AppCompatActivity implements OnItemClickListener {

    ListView listView;
    ArrayAdapter arrayAdapter;

    //Placeholder
    ArrayList<String> venues = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_venue_screen);

        venues = new ArrayList<>(Venue.getAllVenues());

        listView = (ListView) findViewById(R.id.VenueScreen_List);
        listView.setOnItemClickListener(this::onItemClick);
        arrayAdapter = new ArrayAdapter(VenueScreen.this, android.R.layout.simple_list_item_1, venues);

        if(Venue.getAllVenues() == null)
        {
            Toast.makeText(VenueScreen.this, "No venues available at this moment", Toast.LENGTH_LONG).show();
            Intent intent = new Intent(VenueScreen.this, CustomerHomePage.class);
            startActivity(intent);
            return;
        }

        listView.setAdapter(arrayAdapter);


        Button backButton = findViewById(R.id.VenueScreen_Button_Back);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                back();
            }
        });

        Button quitButton = findViewById(R.id.VenueScreen_Button_Quit);
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
    public void back() {
        this.finish();
    }

    /**
     * This method is called when the "Quit" button is pressed
     */
    public void quitApp() {
        this.finishAffinity();
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        if(Customer.username == null)
        {
            Toast.makeText(VenueScreen.this, "Admins cannot create events", Toast.LENGTH_LONG).show();
            return;
        }

        Intent intent = new Intent(VenueScreen.this, ScheduleEventScreen.class);
        intent.putExtra("VENUE_PREF", venues.get(i));
        startActivity(intent);
        return;

    }
}