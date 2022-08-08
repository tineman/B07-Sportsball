package com.example.b07sportsballs;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ScheduleEventScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule_event_screen);

        Button backButton = findViewById(R.id.ScheduleEventScreen_Button_Back);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                closeActivity();
            }
        });

        Button quitButton = findViewById(R.id.ScheduleEventScreen_Button_Quit);
        quitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                quitApp();
            }
        });
    }

    /**
     * This method is called when the "Back" button is pressed
     */
    public void closeActivity() {
        this.finish();
    }

    /**
     * This method is called when the "Quit" button is pressed
     */
    public void quitApp() {
        this.finishAffinity();
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