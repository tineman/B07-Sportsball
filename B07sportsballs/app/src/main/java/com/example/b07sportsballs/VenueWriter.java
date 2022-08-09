package com.example.b07sportsballs;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class VenueWriter extends AppCompatActivity
{

    public VenueWriter() {}


    //Writes a venue into the database
    public void write(DatabaseReference ref, Venue venue)
    {
        String venue_name = venue.getName();
        ref.child(Constants.DATABASE.ROOT).child(Constants.DATABASE.VENUE_PATH).child(venue_name).child(Constants.DATABASE.VENUE_EVENTS_KEY).setValue("");
        
        String key = ref.child(Constants.DATABASE.ROOT).child(Constants.DATABASE.ADMIN_PATH).child(Admin.username).child(Constants.DATABASE.ADMIN_CREATED_VENUES_KEY).push().getKey();
        ref.child(Constants.DATABASE.ROOT).child(Constants.DATABASE.ADMIN_PATH).child(Admin.username).child(Constants.DATABASE.ADMIN_CREATED_VENUES_KEY).child(key).setValue(venue_name);
        Venue.allVenues.add(venue_name);
    }
}





