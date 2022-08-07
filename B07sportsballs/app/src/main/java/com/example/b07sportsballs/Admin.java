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

public class Admin {
    public static Venue venue = new Venue();
    public static HashSet<String> venuesCreated;
    public static String username;
    public static String password;
    static HashSet<Admin> admins = new HashSet<>();
    public static DatabaseReference ref;
    public static DatabaseReference adminRef;
    public static DatabaseReference CVRef;
//    datastatus status;

    public Admin() {
    }


    public Admin(HashSet<String> venuesCreated, String username, String password) {
        this.venuesCreated = venuesCreated;
        this.username = username;
        this.password = password;
    }

    public void setReference(){
        ref = FirebaseDatabase.getInstance("https://b07sportsballs-default-rtdb.firebaseio.com/").getReference();
        adminRef = ref.child("Root").child("Admin");
        CVRef = adminRef.child("VenuesCreated");
    }
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public HashSet<String> getVenuesCreated() {
    return venuesCreated;
}

    public void setVenuesCreated(HashSet<String> venuesCreated) {
        venuesCreated = venue.getAllVenues();
        this.venuesCreated = venuesCreated;
    }
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


    public void readAdmin(final Statusdemo status) {
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

    public void writeAdmin(Admin admin) {
        String key = adminRef.push().getKey();
        adminRef.child(key).setValue(admin);
    }






    public void createVenue(String venue) {

        Venue avenue = new Venue();
        avenue.name = venue;
        venuesCreated.add(venue);
        String key = CVRef.push().getKey();
        CVRef.child(key).setValue(venue);
//        if (venuesCreated.contains(venue)) {
//            status.duplicate();
//        }
//        if (!venuesCreated.contains(venue)) {
//            venuesCreated.add(venue);
//            avenue.writeToDataBase(ref);
//            DatabaseReference cvenueRef = ref.child("Root").child("Admin").child("VenuesCreated");
//            String key = cvenueRef.push().getKey();
//            cvenueRef.child(key).setValue(avenue.name);
//        }


    }


}





