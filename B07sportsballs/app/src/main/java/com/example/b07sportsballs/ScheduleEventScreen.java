package com.example.b07sportsballs;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

public class ScheduleEventScreen extends AppCompatActivity {

    Spinner venueSpinner;
    List<String> venueName;

    EditText nameText;
    EditText maxText;

    Button startButton;
    Button endButton;
    Button startDateButton;
    Button endDateButton;

    Date Start, End;
    //Checks if the user has picked a date or not
    Boolean startTime, startDate, endTime, endDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule_event_screen);

        //If no venues
        if(Venue.getAllVenues() == null)
        {
            Toast.makeText(ScheduleEventScreen.this, "No venues available at this moment", Toast.LENGTH_LONG).show();
            Intent intent = new Intent(ScheduleEventScreen.this, CustomerHomePage.class);
            startActivity(intent);
            return;
        }

        venueName = new ArrayList<>(Venue.getAllVenues());
        venueSpinner = (Spinner) findViewById(R.id.ScheduleEventScreen_Spinner_Venue);
        ArrayAdapter<String> adapter = new ArrayAdapter(ScheduleEventScreen.this, android.R.layout.simple_spinner_item, venueName);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        venueSpinner.setAdapter(adapter);

        nameText = (EditText) findViewById(R.id.ScheduleEventScreen_EditText_Name);
        startButton = (Button) findViewById(R.id.ScheduleEventScreen_EditText_StartTime);
        endButton = (Button) findViewById(R.id.ScheduleEventScreen_EditText_EndTime);
        maxText = (EditText) findViewById(R.id.ScheduleEventScreen_EditText_Max);

        startDateButton = (Button) findViewById(R.id.ScheduleEventScreen_StartDate);
        endDateButton = (Button) findViewById(R.id.ScheduleEventScreen_EndDate);

        Start = Calendar.getInstance().getTime(); //or get current date
        End = Calendar.getInstance().getTime();
        startTime = false;
        startDate = false;
        endTime = false;
        endDate = false;

        Button backButton = findViewById(R.id.ScheduleEventScreen_Button_Back);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                back();
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
    public void back() {
        this.finish();
    }

    /**
     * This method is called when the "Quit" button is pressed
     */
    public void quitApp() {
        this.finishAffinity();
    }

    /**
     * Called when AdminLoginPage_Button_Login is pressed
     * Adds a new event if it can be verified
     */
    public void onSubmit(View view)
    {

        /*
         * Name cannot be empty and cannot contain slashes
         */
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

        /*
         * Venue must be selected
         */
        String venue = venueSpinner.getSelectedItem().toString();

        if(venue.isEmpty())
        {
            Toast.makeText(ScheduleEventScreen.this, "A venue must be selected", Toast.LENGTH_LONG).show();
            return;
        }

        /*
         * Dates Start and End must be selected and Start must come before End
         */
        if(!startTime)
        {
            Toast.makeText(ScheduleEventScreen.this, "A start time must be selected", Toast.LENGTH_LONG).show();
            return;
        }

        if(!endTime)
        {
            Toast.makeText(ScheduleEventScreen.this, "An end time must be selected", Toast.LENGTH_LONG).show();
            return;
        }

        if(!startDate)
        {
            Toast.makeText(ScheduleEventScreen.this, "A start date must be selected", Toast.LENGTH_LONG).show();
            return;
        }

        if(!endDate)
        {
            Toast.makeText(ScheduleEventScreen.this, "An end date must be selected", Toast.LENGTH_LONG).show();
            return;
        }

        if(Start.compareTo(End) > 0)
        {
            Toast.makeText(ScheduleEventScreen.this, "Please make the end time come after start time", Toast.LENGTH_LONG).show();
            return;
        }

        String max = maxText.getText().toString();
        if(max.isEmpty())
        {
            Toast.makeText(ScheduleEventScreen.this, "A maximum occupancy must be selected", Toast.LENGTH_LONG).show();
            return;
        }

        /*
         * Ensures maxPlayer is a positive natural number
         */

        int maxPlayer = 0;

        try
        {
            maxPlayer = Integer.parseInt(max);
        }
        catch(NumberFormatException e)
        {
            Toast.makeText(ScheduleEventScreen.this, "The maximum integer must be a natural number. In this case, the natural numbers do not include 0 :)", Toast.LENGTH_LONG).show();
            return;
        }

        if(maxPlayer <= 0)
        {
            Toast.makeText(ScheduleEventScreen.this, "A maximum occupancy of at least one is required", Toast.LENGTH_LONG).show();
            return;
        }



        //Writing to database
        String host = Customer.username;

        Event newEvent = new Event(null, null, name, host, venue, Start, End, 0, maxPlayer, null);

        Customer.scheduleEvent(newEvent, new Updater() {
            @Override
            public void onUpdate() {
            }
        });

        Toast.makeText(ScheduleEventScreen.this, "Event created", Toast.LENGTH_LONG).show();

    }

    /**
     * Facade to update date and ensure the TimePickerDialog shows the correct time
     * @param date The date to be updated
     * @param button The button whose text is to be updated
     */
    public void pickTime(View view, Date date, Button button)
    {
        TimePickerDialog.OnTimeSetListener startListener = new TimePickerDialog.OnTimeSetListener()
        {
            @Override
            public void onTimeSet(TimePicker timePicker, int hour, int min) {
                date.setHours(hour);
                date.setMinutes(min);
                button.setText(new SimpleDateFormat("HH:mm").format(date));
            }

        };

        TimePickerDialog startDialog = new TimePickerDialog(ScheduleEventScreen.this, startListener, date.getHours(), date.getMinutes(), true);
        startDialog.setTitle("Select Time");
        startDialog.show();
    }

    /**
     * Facade to update date and ensure the DatePickerDialog shows the correct date
     * @param date The date to be updated
     * @param button The button whose text is to be updated
     */
    public void pickDate(View view, Date date, Button button)
    {
        DatePickerDialog.OnDateSetListener DateListener = new DatePickerDialog.OnDateSetListener()
        {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                date.setYear(year);
                date.setMonth(month);
                date.setDate(day);
                button.setText(new SimpleDateFormat("dd/MM/yyyy").format(date));
            }

        };

        DatePickerDialog dateDialog = new DatePickerDialog(ScheduleEventScreen.this, DateListener, date.getYear(), date.getMonth(), date.getDate());
        dateDialog.setTitle("Select Time");
        dateDialog.show();
    }

    /**
     * Called when ScheduleEventScreen_EditText_StartTime is pressed
     */
    public void pickStartTime(View view) {

        pickTime(view, Start, startButton);
        startTime = true;

    }

    /**
     * Called when ScheduleEventScreen_EditText_EndTime is pressed
     */
    public void pickEndTime(View view) {

        pickTime(view, End, endButton);
        endTime = true;

    }

    /**
     * Called when ScheduleEventScreen_StartDate is pressed
     */
    public void pickStartDate(View view) {
        pickDate(view, Start, startDateButton);
        startDate = true;

    }

    /**
     * Called when ScheduleEventScreen_EndDate is pressed
     */
    public void pickEndDate(View view) {
        pickDate(view, End, endDateButton);
        endDate = true;
    }
}