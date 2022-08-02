package com.example.b07sportsballs;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ScheduleEventScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule_event_screen);

        //Reference is to events node
        DatabaseReference EventRef = FirebaseDatabase.getInstance("https://sportsballtesting-default-rtdb.firebaseio.com/").getReference("Tian-Testing/Venues/UOFT/Events/Chess Boxing");

        /*
        EventWriter writer = new EventWriter();
        EventBinder reader = new EventBinder(EventRef);

        Event event1 = new Event(writer, reader, "Chess Boxing", "Hikaru Nakamura", "UOFT", new Date(2022, 1, 1, 22, 0), new Date(2022, 1, 1, 24, 0), 0, 12, EventRef);
        Event event2 = new Event(writer, reader, "Nettle Eating", "Brady Haren", "UOFT", new Date(2022, 1, 1, 11, 0), new Date(2022, 1, 1, 13, 0), 0, 32, EventRef);
        Event event3 = new Event(writer, reader, "Water Polo", "John Paul Jones", "UOFT", new Date(2022, 1, 1, 22, 0), new Date(2022, 1, 1, 23, 0), 0, 12, EventRef);

        event1.writeToDatabase(EventRef);
        event2.writeToDatabase(EventRef);
        event3.writeToDatabase(EventRef);
        */

        /*
        Typical usage of Event - create it with the empty initialiser and then bind it to a database
        and an updater

        Rewrite the value of currPlayers in /Tian-Testing/Venues/UOFT/Events/Chess Boxing and check logcat
         */
        Event event4 = new Event();
        event4.bindToDatabase(EventRef, new EventBinder.Updater() {
            @Override
            public void onUpdate() {
                Log.i("Testing: ", event4.getCurrPlayers() + " out of " + event4.getMaxPlayers());
            }
        });



    }

    /*
    Called when the submit button is pressed
     */
    public void onSubmit(View view)
    {
        //MISSING - INPUT VERIFICATION




    }

    public void onDisplay(View view)
    {
        //Temporary - to display information in real time
    }


}