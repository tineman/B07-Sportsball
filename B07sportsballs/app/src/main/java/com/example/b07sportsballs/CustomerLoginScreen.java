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

public class CustomerLoginScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_login_screen);

        Log.i("MainActivity", "Startup");
        Log.i("MainActivity", "Use \"https://b07sportsballs-default-rtdb.firebaseio.com/\"");
    }

    /**
     * This method is called when the "Login" button is pressed
     * @param view The View object that is being considered
     */
    public void customerLogIn(View view) {
        // Should we declare these variables at the top and initialize in onCreate()?
        EditText editUsername = findViewById(R.id.editText_CustomerLoginPage_Username);
        EditText editPassword = findViewById(R.id.editText_CustomerLoginPage_Password);
        final Context context = getApplicationContext();

        Customer customer = new Customer(editUsername.getText().toString(),
                editPassword.getText().toString());
        customer.logIn(new Updater() {
            @Override
            public void onStart() {}

            @Override
            public void onSuccess(int returnCode) {
                if (returnCode == Constants.LOGIN_CODE.NO_USER)
                    Toast.makeText(context, "User does not exist.", Toast.LENGTH_LONG).show();
                else if (returnCode == Constants.LOGIN_CODE.WRONG_PASSWORD)
                    Toast.makeText(context, "Wrong password.", Toast.LENGTH_LONG).show();
                else {
                    //TODO: Change to CustomerHomePageScreen.
                    Intent intent = new Intent(context, VenueScreen.class);
                    // Send the database reference
                    intent.putExtra("CUSTOMER_REF", customer.ref.toString());
                    intent.putExtra("ADMIN_REF", "INVALID");
                    // To get the database reference from ref.toString(), use:
                    // FirebaseDatabase.getInstance().getReferenceFromUrl(str)
                    startActivity(intent);
                }
            }

            @Override
            public void onFailure(DatabaseError error) {}
        });
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