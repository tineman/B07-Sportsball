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

<<<<<<< Updated upstream
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
=======
public class Admin extends User{
    static Venue venue;
    static HashSet<String> venuesCreated;
    static HashSet<Admin> admins;
    static DatabaseReference adminRef;
    static DatabaseReference CVRef;
    Statusdemo status;
>>>>>>> Stashed changes

    public Admin() {
    }

    public Admin(HashSet<String> venuesCreated, String username, String password, DatabaseReference ref, Venue venue, HashSet<Admin> admins) {
        super(username, password, ref);
        this.venue = new Venue();
        this.venuesCreated = new HashSet<String>();
        this.username = username;
        this.password = password;
        this.admins = new HashSet<Admin>();
    }

    public void setReference(){
//        ref = FirebaseDatabase.getInstance("https://b07sportsballs-default-rtdb.firebaseio.com/").getReference();
        adminRef = ref.child("Root").child("Admin");
        CVRef = adminRef.child("VenuesCreated");
    }
    public String getUsername() {
        return username;
    }

<<<<<<< Updated upstream
    public void setUsername(String username) {
        this.username = username;
=======
    public void setVenuesCreated(HashSet<String> venuesCreated) {
        venue.readFromDataBase(ref);
        this.venuesCreated = venue.allVenues;
//        venuesCreated = venue.getAllVenues();
//        this.venuesCreated = venuesCreated;
>>>>>>> Stashed changes
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
        avenue.setName(venue);
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


<<<<<<< Updated upstream
=======
    @Override
    public int hashCode(){
        return username.hashCode()+ password.hashCode();
    }


    //@Override
    public boolean Equals(Object obj){
        if(this == obj){
            return true;
        }
        if(obj == null){
            return false;
        }
        if(this.getClass() != obj.getClass()){
            return false;
        }
        Admin a = (Admin) obj;
        return a.username == this.username &&
                a.password == this.password;
    }



>>>>>>> Stashed changes
}





