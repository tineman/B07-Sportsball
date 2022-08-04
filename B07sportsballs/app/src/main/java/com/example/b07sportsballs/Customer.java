package com.example.b07sportsballs;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Customer extends User {


    public Customer(String username, String password) {
        super(username, password);
    }

    @Override
    public void logIn(final Updater updater) {
        DatabaseReference customersRef = FirebaseDatabase.
                getInstance(Constants.DATABASE.DB_URL).
                getReference("chau-testing/" + Constants.DATABASE.CUSTOMER_PATH);
        updater.onStart();
        customersRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot customer : snapshot.getChildren()) {
                    if (customer.child(Constants.DATABASE.USERNAME_KEY).getValue(String.class).equals(username)) {
                        if (customer.child(Constants.DATABASE.PASSWORD_KEY).getValue(String.class).equals(password)) {
                            ref = customer.getRef();
                            updater.onSuccess(Constants.LOGIN_CODE.SUCCESS);
                        }
                        else {
                            ref = null;
                            updater.onSuccess(Constants.LOGIN_CODE.WRONG_PASSWORD);
                        }
                        return;
                    }
                }
                ref = null;
                updater.onSuccess(Constants.LOGIN_CODE.NO_USER);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                updater.onFailure(error);
            }
        });
    }

    public void signUp(Updater updater) {
        DatabaseReference customersRef = FirebaseDatabase.
                getInstance(Constants.DATABASE.DB_URL).
                getReference("chau-testing/Customers");
        customersRef.push().setValue(new Customer(username, password), new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                if (error != null) {
                    updater.onFailure(error);
                }
                else updater.onSuccess(Constants.SIGNUP_CODE.SUCCESS);
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
