package com.example.b07sportsballs;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseError;

public class CustomerSignUpScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_sign_up_screen);
    }

    /**
     * This method is called when the "Register" button is pressed
     * @param view The View object that is being considered
     */
    public void customerSignUp(View view) {
        // Should we declare these variables at the top and initialize in onCreate()?
        EditText editUsername = findViewById(R.id.editText_CustomerLoginPage_Username);
        EditText editPassword = findViewById(R.id.editText_CustomerLoginPage_Password);
        final Context context = getApplicationContext();

        Customer customer = new Customer(editUsername.getText().toString(),
                editPassword.getText().toString());
        customer.signUp(new Updater() {
            @Override
            public void onStart() {}

            @Override
            public void onSuccess(int returnCode) {
                Toast.makeText(context, "Signup success!", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(context, VenueScreen.class);
                startActivity(intent);
            }

            @Override
            public void onFailure(DatabaseError error) {
                Toast.makeText(context, "Something went wrong :(", Toast.LENGTH_LONG).show();
                Log.e("customerSignUp", error.getMessage());
            }
        });
    }

    /**
     * This method is called when the "Quit" button is pressed
     * @param view The View object that is being considered
     */
    public void quitApp(View view) {
        this.finishAffinity();
    }
}