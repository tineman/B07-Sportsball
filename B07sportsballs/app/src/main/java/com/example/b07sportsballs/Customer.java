package com.example.b07sportsballs;

import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;

public class Customer extends User {

    public Customer(String username, String password) {
        super(username, password);
    }

    @Override
    public void logIn() {
        DatabaseReference root = FirebaseDatabase.
                getInstance("https://sportsballtesting-default-rtdb.firebaseio.com").
                getReference("chau-testing/Customers");
        root.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot customer : snapshot.getChildren()) {
                    if (customer.child("name").getValue(String.class).equals(Customer.super.getUsername())
                    && customer.child("password").getValue(String.class).equals(Customer.super.getPassword()))
                    {
                        ref = customer.getRef();
                        Log.i("demo", ref.toString());
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

//    public void joinEvent(Event e) {
//        e.setCurrPlayers(e.getCurrPlayers+1);
//        joinedEvents.add(e);
//        //write to db
//    }

//    public void scheduleEvent(String name, String location, String startTime, String endTime,
//                              int maxPlayers) {
//        // Parse time into correct format
//        // Make this final global
//        SimpleDateFormat timeFormat = new SimpleDateFormat("d MMM yyyy HH:mm a");
//        try {
//            Event e = new Event(name, Customer.super.getUsername(), location,
//                    timeFormat.parse(startTime), timeFormat.parse(endTime), 0, maxPlayers);
//            scheduledEvents.add(e);
//            // add to venue
//            // write to db
//        }
//        catch (ParseException exception) {
//            Log.e("Schedule event", "Incorrect format of starting and end time.");
//        }
//
//    }

}
