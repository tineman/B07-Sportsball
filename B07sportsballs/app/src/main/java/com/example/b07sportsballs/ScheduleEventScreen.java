package com.example.b07sportsballs;

import androidx.appcompat.app.AppCompatActivity;

import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ScheduleEventScreen extends AppCompatActivity {

    Spinner venueSpinner;
    List<String> venueName = new ArrayList<>();

    EditText nameText;
    EditText maxText;

    Button startButton;
    Button endButton;

    int startHour, startMinute, endHour, endMinute; //set to negative 1


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule_event_screen);

        /*
        Access list of venues from customer
         */

        venueName.add("TPASC");
        venueName.add("UOFT");

        venueSpinner = (Spinner) findViewById(R.id.ScheduleEventScreen_Spinner_Venue);
        ArrayAdapter<String> adapter = new ArrayAdapter(ScheduleEventScreen.this, android.R.layout.simple_spinner_item, venueName);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        venueSpinner.setAdapter(adapter);

        nameText = (EditText) findViewById(R.id.ScheduleEventScreen_EditText_Name);
        startButton = (Button) findViewById(R.id.ScheduleEventScreen_EditText_StartTime);
        endButton = (Button) findViewById(R.id.ScheduleEventScreen_EditText_EndTime);
        maxText = (EditText) findViewById(R.id.ScheduleEventScreen_EditText_Max);

        startHour = -1;
        startMinute = -1;
        endHour = -1;
        endMinute = -1;
    }


    /*
    Called when the submit button is pressed. Used to show Event
     */
    public void onSubmit(View view)
    {

        String name = nameText.getText().toString();

        if(name.isEmpty())
        {
            Toast.makeText(ScheduleEventScreen.this, "Event Name cannot be empty", Toast.LENGTH_LONG).show();
            return;
        }
        if(name.contains("/"))
        {
            Toast.makeText(ScheduleEventScreen.this, "Event Name cannot contain slashes", Toast.LENGTH_LONG).show();
            return;
        }

        String venue = venueSpinner.getSelectedItem().toString();

        if(venue.isEmpty())
        {
            Toast.makeText(ScheduleEventScreen.this, "A venue must be selected", Toast.LENGTH_LONG).show();
            return;
        }

        if(startHour == -1 || startMinute == -1)
        {
            Toast.makeText(ScheduleEventScreen.this, "A start time must be selected", Toast.LENGTH_LONG).show();
            return;
        }

        if(endHour == -1 || endMinute == -1)
        {
            Toast.makeText(ScheduleEventScreen.this, "An end time must be selected", Toast.LENGTH_LONG).show();
            return;
        }

        Date Start = new Date(0, 1, 1, startHour, startMinute);
        Date End = new Date(0, 1, 1, endHour, endMinute);

        String max = maxText.getText().toString();
        if(max.isEmpty())
        {
            Toast.makeText(ScheduleEventScreen.this, "A maximum occupancy must be selected", Toast.LENGTH_LONG).show();
            return;
        }

        //Input field only accepts numbers so no try-catch statement neccessary here
        if(Integer.parseInt(max) <= 0)
        {
            Toast.makeText(ScheduleEventScreen.this, "A maximum occupancy of at least one is required", Toast.LENGTH_LONG).show();
            return;
        }

        int maxPlayer = Integer.parseInt(max);

        //Get Customer
        String host = "";

        Event newEvent = new Event(null, null, name, host, venue, Start, End, 0, maxPlayer, null);

        //Some sanitation before it gets to customer method
        //Name checking, date checking, and maximum occupancy checking are handled by customer

        Log.i("Event Created", newEvent.getName() + " at " + newEvent.getLocation() + " from " + newEvent.getStartTime() + " to " + newEvent.getEndTime() + " with " + newEvent.getCurrPlayers() + "/" + newEvent.getMaxPlayers());

    }

    public void pickStartTime(View view) {

        TimePickerDialog.OnTimeSetListener startListener = new TimePickerDialog.OnTimeSetListener()
        {
            @Override
            public void onTimeSet(TimePicker timePicker, int hour, int min) {
                startHour = hour;
                startMinute = min;
                startButton.setText(hour + ":" + min);
            }

        };

        TimePickerDialog startDialog = new TimePickerDialog(ScheduleEventScreen.this, startListener, startHour, startMinute, true);
        startDialog.setTitle("Select Time");
        startDialog.show();
    }

    public void pickEndTime(View view) {

        TimePickerDialog.OnTimeSetListener endListener = new TimePickerDialog.OnTimeSetListener()
        {
            @Override
            public void onTimeSet(TimePicker timePicker, int hour, int min) {
                endHour = hour;
                endMinute = min;
                endButton.setText(hour + ":" + min);
            }

        };

        TimePickerDialog endDialog = new TimePickerDialog(ScheduleEventScreen.this, endListener, endHour, endMinute, true);
        endDialog.setTitle("Select Time");
        endDialog.show();
    }
}