package com.example.b07sportsballs;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Spinner;

import com.google.android.gms.tasks.*;
import com.google.firebase.database.*;

import java.util.*;

public class MainActivity extends AppCompatActivity {
    ArrayList<String> list = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.i("MainActivity", "Startup");
        Log.i("MainActivity", "Use \"https://b07sportsballs-default-rtdb.firebaseio.com/\"");

        DatabaseReference ref = FirebaseDatabase.getInstance("https://sportsballtesting-default-rtdb.firebaseio.com/").getReference(); //update URL!!!
        ref.child("students").child("s1").setValue("bob");

        ref = FirebaseDatabase.getInstance("https://sportsballtesting-default-rtdb.firebaseio.com/").getReference("students"); //update URL!!

        ref.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (!task.isSuccessful()) {
                    Log.e("demo", "Error getting data", task.getException());
                } else {
                    Log.i("demo", task.getResult().getValue().toString());
                    for(DataSnapshot child:task.getResult().getChildren()) {
                        String student = child.getValue(String.class);
                        list.add(student);
                        Log.i("demo", student);
                    }
                }
            }
        });
        System.out.println("BEFORE: " + list);
    }

    public void buttonPressed(View view) {
        System.out.println("AFTER: " + list);
    }
}