package com.example.b07sportsballs;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashSet;
import java.util.List;

public class CreateVenueScreen extends AppCompatActivity {

    private EditText VenueName;
    private Button addVenue;
    private Button Quit;
    private Button venueList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_venue_screen);

        Log.i("CreateVenue", "Use \"https://b07sportsballs-default-rtdb.firebaseio.com/\"");
        VenueName = (EditText) findViewById(R.id.CreateVenueScreen_EditText_VenueName);

        addVenue = (Button) findViewById(R.id.button6);

        Quit = (Button) findViewById(R.id.button5);

        venueList = (Button) findViewById(R.id.button7);

        venueList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listVenue();
            }
        });

        Quit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Quitcreate();
            }
        });

        addVenue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Venue venue = new Venue(VenueName.getText().toString(), new HashSet<Event>());
                DatabaseReference ref = FirebaseDatabase.getInstance("https://b07sportsballs-default-rtdb.firebaseio.com/").getReference();
                venue.writeToDataBase(ref);
//                venue.setName(VenueName.getText().toString());
//                venue.setEvents(new HashSet<Event>());
//                new AdminVenueWriter().addVenue(venue, new AdminVenueWriter.datastatus() {
//                    @Override
//                    public void isLoaded(List<Admin> admins, List<String> keys) {
//
//                    }
//
//                    @Override
//                    public void isInserted() {
//                        Toast.makeText(CreateVenueScreen.this, "This venue has been inserted successfully",
//                        Toast.LENGTH_LONG).show();
//                    }
//
//                    @Override
//                    public void isDeleted() {
//
//                    }
//
//                    @Override
//                    public void isUpdated() {
//
//                    }
//                });


            }
        });

    }

    private void listVenue() {
        Intent intent = new Intent(this, VenueScreen.class);
        startActivity(intent);
    }

    private void Quitcreate() {
        this.finishAffinity();
    }


}