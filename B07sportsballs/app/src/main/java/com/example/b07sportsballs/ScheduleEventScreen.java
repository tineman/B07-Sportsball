package com.example.b07sportsballs;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ScheduleEventScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule_event_screen);



    }

    /*
    Called when the submit button is pressed. Used to show Event
     */
    public void onSubmit(View view)
    {



    }
    public void onDisplay(View view)
    {
        //Temporary - to display information in real time
    }


}