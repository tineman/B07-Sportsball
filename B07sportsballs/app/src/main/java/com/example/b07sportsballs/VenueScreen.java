package com.example.b07sportsballs;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

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
        arrayAdapter = new ArrayAdapter(VenueScreen.this, android.R.layout.simple_list_item_1, venues);

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


        Button backButton = findViewById(R.id.VenueScreen_Button_Back);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                closeActivity();
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
    public void closeActivity() {
        this.finish();
    }

    /**
     * This method is called when the "Quit" button is pressed
     */
    public void quitApp() {
        this.finishAffinity();
    }

    //Function called when ListView clicked
    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

        //dummy implementation, not yet called
        Log.i("VenueScreen", ((TextView) view).getText().toString() + " I've been booped!");

    }
}