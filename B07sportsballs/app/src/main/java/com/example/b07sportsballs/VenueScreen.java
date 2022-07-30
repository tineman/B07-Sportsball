package com.example.b07sportsballs;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class VenueScreen extends AppCompatActivity {

    ListView listView;
    LinearLayout linearLayout;
    ArrayAdapter arrayAdapter;

    //Placeholder
    ArrayList<String> venues = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_venue_screen);

        listView = (ListView) findViewById(R.id.listofvenues);

        arrayAdapter = new ArrayAdapter(VenueScreen.this, android.R.layout.simple_list_item_1, venues);

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
}