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