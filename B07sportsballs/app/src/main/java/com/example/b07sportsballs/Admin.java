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
    private static HashSet<String> venuesCreated;
    private static String username;
    private static String password;
    static HashSet<Admin> admins;
    DatabaseReference ref;

    public Admin() {
    }

    public Admin(HashSet<String> venuesCreated, String username, String password, DatabaseReference ref) {
        this.venuesCreated = venuesCreated;
        this.username = username;
        this.password = password;
        this.ref = ref;
    }



//    public AdminReader getReader() {
//        return reader;
//    }

//    public void setReader(AdminReader reader) {
//        this.reader = reader;
//    }

//    public AdminWriter getWriter() {
//        return writer;
//    }

//    public void setWriter(AdminWriter writer) {
//        this.writer = writer;
//    }



    public void setVenuesCreated(HashSet<String> venuesCreated) {
        this.venuesCreated = venuesCreated;
    }

    public HashSet<String> getVenuesCreated() {
        return venuesCreated;
    }
    public void readAdmin(){
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                admins.clear();
                List<String> keys = new ArrayList<>();
                for(DataSnapshot node: snapshot.getChildren()){
                    keys.add(node.getKey());
                    Admin admin = node.getValue(Admin.class);
                    admins.add(admin);
                }
            }
            @Override
            public void onCancelled(DatabaseError error) {

            }
        });
    }

    public void writeAdmin(Admin admin){
        DatabaseReference adminRef = ref.child("Root").child("Admin");
        String key = adminRef.push().getKey();
        adminRef.child(key).setValue(admin);
    }





    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

//    public String getPassword() {
//        return password;
//    }

    public void setPassword(String password) {
        this.password = password;
    }

//    public void createVenue(String venue){
////        DatabaseReference ref = FirebaseDatabase.getInstance("https://b07sportsballs-default-rtdb.firebaseio.com/").getReference();
//        Venue avenue = new Venue();
//        avenue.name = venue;
//        if(venuesCreated.contains(venue)){
//            Log.e("Duplicate input");
//        }
//        if(!venuesCreated.contains(venue)){
//            venuesCreated.add(venue);
//            avenue.writeToDataBase(ref);
//            DatabaseReference cvenueRef = ref.child("Root").child("Admin").child("VenuesCreated");
//            String key = cvenueRef.push().getKey();
//            cvenueRef.child(key).setValue(avenue.name);
//        }



    }





