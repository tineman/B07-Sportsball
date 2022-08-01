package com.example.b07sportsballs;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.*;
import android.widget.Button;
import android.widget.EditText;

public class CustomerLoginScreen extends AppCompatActivity {

    public static final String EXTRA_MESSAGE = "";

    //TEST COMMENT
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_login_screen);

        Log.i("MainActivity", "Startup");
        Log.i("MainActivity", "Use \"https://b07sportsballs-default-rtdb.firebaseio.com/\"");

        Button loginButton = findViewById(R.id.button_CustomerLoginPage_Login);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openCustomerHomePage();
            }
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

    public void openCustomerHomePage(){
        Intent intent = new Intent(this, CustomerHomePage.class);

        EditText editText = findViewById(R.id.editText_CustomerLoginPage_Username);
        String username = editText.getText().toString();
        intent.putExtra(EXTRA_MESSAGE, username);


        startActivity(intent);
    }
}