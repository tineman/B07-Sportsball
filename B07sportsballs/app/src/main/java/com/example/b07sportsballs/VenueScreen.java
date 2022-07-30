package com.example.b07sportsballs;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class VenueScreen extends AppCompatActivity {

    ScrollView scrollView;
    LinearLayout linearLayout;

    //Placeholder
    ArrayList<String> Venues = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_venue_screen);

        //Creating ScrollView
        scrollView = (ScrollView) findViewById(R.id.listofvenues);

        //Creating LinearLayout. All new views should go into this layout since ScrollView can only have one child
        linearLayout = new LinearLayout(this);
        setContentView(linearLayout);
        linearLayout.setOrientation(LinearLayout.VERTICAL);

        //To implement clickable text I'll probably add an onClickListener

        //Placeholder
        Venues.add("blargghhhh!");
        Venues.add("yarghhhh!");
        Venues.add("bleughhhh!");
        Venues.add("blargghhhh!");
        Venues.add("yarghhhh!");
        Venues.add("bleughhhh!");
        Venues.add("blargghhhh!");
        Venues.add("yarghhhh!");
        Venues.add("bleughhhh!");
        Venues.add("blargghhhh!");
        Venues.add("yarghhhh!");
        Venues.add("bleughhhh!");
        Venues.add("blargghhhh!");
        Venues.add("yarghhhh!");
        Venues.add("bleughhhh!");

        for(String venue : Venues)
        {
            TextView textview = new TextView(this);
            textview.setText(venue);
            linearLayout.addView(textview);
        }

    }
}