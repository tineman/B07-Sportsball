package com.example.b07sportsballs;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class CreateVenueScreen extends AppCompatActivity {
    public static DatabaseReference ref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_venue_screen);
        ref = FirebaseDatabase.getInstance("https://sportsballtesting-default-rtdb.firebaseio.com/").getReference();
        Log.i("CreateVenue", "Startup");
        Log.i("CreateVenue", "Use \"https://b07sportsballs-default-rtdb.firebaseio.com/\"");


        Button addVenueButton = findViewById(R.id.CreateVenueScreen_Button_AddVenue);
        addVenueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText VenueName = (EditText) findViewById(R.id.CreateVenueScreen_EditText_VenueName);
                String avenuename = VenueName.getText().toString();
                if(avenuename.isEmpty()){
                    Toast.makeText(CreateVenueScreen.this, "Field cannot be empty", Toast.LENGTH_SHORT).show();
                }
                else{
                    Venue venue = new Venue();
                    venue.setName(avenuename);
                    String vName = venue.getName();
                    venue.readFromDataBase(ref);
                    if(venue.allVenues.contains(vName)){
                        Toast.makeText(CreateVenueScreen.this, "A venue with this venue name already exists, please try another venue name", Toast.LENGTH_LONG).show();
                    }else if (vName.contains("/")){
                        Toast.makeText(CreateVenueScreen.this, "A venue's name cannot contain \"/\"", Toast.LENGTH_LONG).show();
                    }else{
                        Toast.makeText(CreateVenueScreen.this, "\"" + avenuename + "\" has been added", Toast.LENGTH_LONG).show();
                        venue.writeToDataBase(ref);
                    }

                }







            }
        });

        Button backButton = findViewById(R.id.CreateVenueScreen_Button_Back);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                backToAdminHomePage();
            }
        });

        Button viewVenuesButton = findViewById(R.id.CreateVenueScreen_Button_ViewVenues);
        viewVenuesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listVenue();
            }
        });

        Button quitButton = findViewById(R.id.CreateVenueScreen_Button_Quit);
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
    private void backToAdminHomePage() {
        this.finish();
    }

    /**
     * This method is called when the "View Venues" button is pressed
     */
    private void listVenue() {
        Intent intent = new Intent(this, EventbyVenueScreen.class);
        startActivity(intent);
    }

    /**
     * This method is called when the "Quit" button is pressed
     */
    private void quitApp() {
        this.finishAffinity();
    }
}