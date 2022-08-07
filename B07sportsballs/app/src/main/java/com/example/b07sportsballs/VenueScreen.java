package com.example.b07sportsballs;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
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

        listView = (ListView) findViewById(R.id.VenueScreen_List);
        listView.setOnItemClickListener(this::onItemClick);
        arrayAdapter = new ArrayAdapter(VenueScreen.this, android.R.layout.simple_list_item_1, venues);

        /*
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_venue_screen);

        listView = (ListView) findViewById(R.id.VenueScreen_List);
        arrayAdapter = new ArrayAdapter(VenueScreen.this, android.R.layout.simple_list_item_1, venues);

        if(Venue.getAllVenues() == null)
        {
            Toast.makeText(VenueScreen.this, "No venues available at this moment", Toast.LENGTH_LONG).show();
            Intent intent = new Intent(VenueScreen.this, CustomerHomePage.class);
            startActivity(intent);
            return;
        }

        venues = new ArrayList<>(Venue.getAllVenues());
        listView.setAdapter(arrayAdapter);
         */


        //Placeholder
        venues.add("blargghhhh!");
        venues.add("yarghhhh!");
        venues.add("bleughhhh!");
        venues.add("blargghhhh!");
        venues.add("yarghhhh!");
        venues.add("bleughhhh!");
        venues.add("blargghhhh!");
        venues.add("yarghhhh!");
        venues.add("bleughhhh!");
        venues.add("blargghhhh!");
        venues.add("yarghhhh!");
        venues.add("bleughhhh!");
        venues.add("blargghhhh!");
        venues.add("yarghhhh!");
        venues.add("bleughhhh!");

        listView.setAdapter(arrayAdapter);

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