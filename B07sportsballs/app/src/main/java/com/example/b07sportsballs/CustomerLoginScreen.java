package com.example.b07sportsballs;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.*;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;

public class CustomerLoginScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_login_screen);

        Log.i("MainActivity", "Startup");
        Log.i("MainActivity", "Use \"https://b07sportsballs-default-rtdb.firebaseio.com/\"");
    }

    public void customerLogIn(View view) {
        EditText editUsername = findViewById(R.id.editText_CustomerLoginPage_Username);
        EditText editPassword = findViewById(R.id.editText_CustomerLoginPage_Password);

        Customer customer = new Customer(editUsername.getText().toString(),
                editPassword.getText().toString());
        customer.logIn();
//        Log.i("demo", customer.ref.toString());
        if (customer.ref == null) Toast.makeText(this, "Wrong username or password.",
                Toast.LENGTH_LONG).show();
        else {
            Intent intent = new Intent(this, CustomerUpcomingEventsScreen.class);
            startActivity(intent);
        }
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