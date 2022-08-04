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

        /**
         * The following button will take the user to the Customer's Home Page once it is clicked
         */
        Button loginButton = findViewById(R.id.CustomerLoginScreen_Button_Login);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openCustomerHomePage();
            }
        });

        /**
         * The following button will take the user to Admin Login page once it is clicked
         */
        Button adminButton = findViewById(R.id.CustomerLoginScreen_Button_GoToAdmin);
        adminButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gotoAdmin();
            }
        });

        /**
         * The following button will take the user to the "Customer Sign Up" page once it is clicked
         */
        Button registerButton = findViewById(R.id.CustomerLoginScreen_Button_Register);
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                displayCustomerSignUpScreen();
            }
        });

        /**
         * The following button will quit the program once it is clicked
         */
        Button quitButton = findViewById(R.id.CustomerLoginScreen_Button_Quit);
        quitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                quitApp();
            }
        });
    }

    /**
     * This method is called when the "Login" button is pressed
     * This will direct the user to the Customer Home page
     */
    public void openCustomerHomePage(){
        Intent intent = new Intent(this, CustomerHomePage.class);

        EditText editText = findViewById(R.id.CustomerLoginScreen_EditText_Username);
        String username = editText.getText().toString();
        intent.putExtra(EXTRA_MESSAGE, username);


        startActivity(intent);
    }

    /**
     * This method is called when the "Go To Admin Login Page?" button is clicked
     * This will direct the user to Admin Login Activity
     */
    public void gotoAdmin(){
        Intent intent = new Intent(this, AdminLoginPage.class);

        startActivity(intent);
    }

    /**
     * This method is called when the "Register" button is pressed
     * This will direct the user to the "Customer Sign Up" page
     */
    public void displayCustomerSignUpScreen() {
        Intent intent = new Intent(this, CustomerSignUpScreen.class);

        startActivity(intent);
    }

    /**
     * This method is called when the "Quit" button is pressed
     */
    public void quitApp() {
        this.finishAffinity();
    }
}