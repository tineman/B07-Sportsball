package com.example.b07sportsballs;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.*;

public class CustomerLoginScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // These two lines of code below will remove the Title Bar From the Activity in Android
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide();

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