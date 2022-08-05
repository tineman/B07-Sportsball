package com.example.b07sportsballs;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public abstract class User {
    String username;
    String password;

    public boolean logIn(){
        List<String> usernameList = new ArrayList<String>();
        DatabaseReference Rtdb = FirebaseDatabase.getInstance("https://sportsballtesting-default-rtdb.firebaseio.com/").getReference("Root").child("Customer");
        Rtdb.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
             public void onComplete(@NonNull Task<DataSnapshot> task) {
                 if (task.isSuccessful()) {
                     //List listCustomer = (List)snapshot.getValue();
                     for (DataSnapshot aCustomer : task.getResult().getChildren()) {
                         String aUsername = aCustomer.child("Username").getValue().toString();
                         System.out.println(aUsername);
                         usernameList.add(aUsername);
                     }
                 }
             }
         });
        Log.i("tester", "Please see this message !!!!!!!!!!!!!!");
        //System.out.println("HELLO");
        System.out.println(usernameList);
        return false;

    }
}
