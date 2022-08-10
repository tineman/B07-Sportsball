package com.example.b07sportsballs;

import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;


public class Admin extends User {
    static Venue venue;
    static HashSet<String> venuesCreated;
    static HashSet<Admin> admins;
    static DatabaseReference adminRef;
    static DatabaseReference CVRef;
    Statusdemo status;

    public Admin() {
    }

    public Admin(String username, String password, DatabaseReference ref) {
        super(username, password, ref, Constants.DATABASE.ADMIN_TYPE);
        this.venue = new Venue();
        this.venuesCreated = new HashSet<String>();
        this.admins = new HashSet<Admin>();
    }

    public void setReference() {
        adminRef = ref.child(Constants.DATABASE.ROOT).child(Constants.DATABASE.ADMIN_PATH);
        CVRef = adminRef.child(Constants.DATABASE.ADMIN_CREATED_VENUES_KEY);
    }


    public void setUsername(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    public void setVenuesCreated(HashSet<String> venuesCreated) {
        venue.readFromDataBase(ref);
        this.venuesCreated = venue.allVenues;

    }


    public HashSet<String> getVenuesCreated() {
        return venuesCreated;
    }



    public void readToDatabase(final Statusdemo status) {
        adminRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                admins.clear();
                List<String> keys = new ArrayList<>();
                for (DataSnapshot node : snapshot.getChildren()) {
                    keys.add(node.getKey());
                    Admin admin = node.getValue(Admin.class);
                    admins.add(admin);
                }
                status.Loading(admins, keys);
            }

            @Override
            public void onCancelled(DatabaseError error) {

            }
        });
    }

    public void writeToDatabase(Admin admin) {
        String key = adminRef.push().getKey();
        adminRef.child(key).setValue(admin);
    }



    public void createVenue(String venue) {

        if(!venuesCreated.contains(venue)){
            Venue avenue = new Venue();
            avenue.setName(venue);
            venuesCreated.add(venue);
            avenue.writeToDataBase(ref);
        }



    }


    @Override
    public int hashCode() {
        return Objects.hash(username) + Objects.hash(password);
    }


    @Override
    public boolean equals(Object obj) {

        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (this.getClass() != obj.getClass()) {
            return false;
        }
        Admin a = (Admin) obj;

        return this.username.equals(a.username) && this.password.equals(a.password);
    }


}





