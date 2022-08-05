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

    Event event = new Event();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule_event_screen);

        DatabaseReference EventRef = FirebaseDatabase.getInstance("https://sportsballtesting-default-rtdb.firebaseio.com/").getReference("Tian-Testing/Venues/UOFT/Events/Chess Boxing");
        event.bindToDatabase(EventRef, new EventBinder.Updater() {
            @Override
            public void onUpdate() {

            }
        });

    }

    /*
    Called when the submit button is pressed. Used to show Event
     */
    public void onSubmit(View view)
    {

        Intent intent = new Intent(this, CustomerEventsJoinedScreen.class);
        intent.putExtra("Event", event);
        startActivity(intent);

    }
    public void onDisplay(View view)
    {
        //Temporary - to display information in real time
    }


}