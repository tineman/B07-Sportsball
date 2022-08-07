package com.example.b07sportsballs;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.*;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;

import java.util.Date;

public class CustomerLoginScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_login_screen);

        Log.i("MainActivity", "Startup");
        Log.i("MainActivity", "Use \"https://b07sportsballs-default-rtdb.firebaseio.com/\"");


//        Event event1;
//        event1 = new Event(new EventWriter(), new EventBinder(event1), "Chess Boxing", "Hikaru Nakamura", "UOFT", new Date(2023, 1, 1, 22, 0), new Date(2023, 1, 1, 24, 0), 0, 12, EventRef);
//        event1.setData("Chess Boxing", "Hikaru Nakamura", "UOFT", new Date(2023, 1, 1, 22, 0), new Date(2023, 1, 1, 24, 0), 0, 12);
//        Event event2 = new Event(writer, reader, "Nettle Eating", "Brady Haren", "UOFT", new Date(2023, 1, 1, 11, 0), new Date(2023, 1, 1, 13, 0), 0, 32, EventRef);
//        Event event3 = new Event(writer, reader, "Water Polo", "John Paul Jones", "UOFT", new Date(2023, 1, 1, 22, 0), new Date(2023, 1, 1, 23, 0), 0, 12, EventRef);
    }

    /**
     * This method is called when the "Register" button is pressed
     * @param view The View object that is being considered
     */
    public void displayCustomerSignUpScreen(View view) {
        Intent intent = new Intent(this, CustomerSignUpScreen.class);
        startActivity(intent);
    }

    /**
     * This method is called when the "Quit" button is pressed
     * @param view The View object that is being considered
     */
    public void quitApp(View view) {
        this.finishAffinity();
    }
}