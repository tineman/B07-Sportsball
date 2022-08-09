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
                Venue venue = new Venue();
                venue.setName(avenuename);
                venue.writeToDataBase(ref);
//                ref.child("Root").child("Venue").addValueEventListener(new ValueEventListener() {
//                    @Override
//                    public void onDataChange(@NonNull DataSnapshot snapshot) {
//                        for(DataSnapshot infoSnapshot : snapshot.getChildren()){
//                            if(infoSnapshot!=null){
//                                String vname = infoSnapshot.child("VenuesName").getValue().toString();
//                                if(avenuename.isEmpty()){
//                                    Toast.makeText(CreateVenueScreen.this, "Venue name cannot be empty", Toast.LENGTH_SHORT).show();
//                                }
//                                if(vname.equals(avenuename)){
//                                    Toast.makeText(CreateVenueScreen.this, "This venue already exists, please try input other venues", Toast.LENGTH_SHORT).show();
//                                }
//                                if (!vname.equals(avenuename)){
//                                    Venue venue = new Venue();
//                                    venue.setName(avenuename);
//                                    venue.writeToDataBase(ref);
//                                    Admin admin = new Admin();
//                                    admin.createVenue(avenuename);
//                                }
//                            }
//                        }
//                    }
//
//                    @Override
//                    public void onCancelled(@NonNull DatabaseError error) {
//
//                    }
//                });
//                Venue venue = new Venue(VenueName.getText().toString(), new HashSet<Event>());
//                DatabaseReference ref = FirebaseDatabase.getInstance("https://b07sportsballs-default-rtdb.firebaseio.com/").getReference();
//                venue.writeToDataBase(ref);
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
        Intent intent = new Intent(this, VenueScreen.class);
        startActivity(intent);
    }

    /**
     * This method is called when the "Quit" button is pressed
     */
    private void quitApp() {
        this.finishAffinity();
    }
}