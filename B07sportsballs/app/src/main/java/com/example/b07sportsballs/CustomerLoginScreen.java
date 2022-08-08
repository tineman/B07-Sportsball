package com.example.b07sportsballs;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.*;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class CustomerLoginScreen extends AppCompatActivity {


    DatabaseReference reference;

    //TEST COMMENT
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_login_screen);
        reference = FirebaseDatabase.getInstance("https://sportsballtesting-default-rtdb.firebaseio.com/").getReference();

        Log.i("MainActivity", "Startup");
        Log.i("MainActivity", "Use \"https://b07sportsballs-default-rtdb.firebaseio.com/\"");

        /**
         * The following button will take the user to the Customer's Home Page once it is clicked
         */
        Button loginButton = findViewById(R.id.CustomerLoginScreen_Button_Login);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loginAccount();
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


    /**
     * following method will read username and password from the edittext
     * and compare it against the data base
     * 
     * when username and password match,user is directed to Customer Home Page */
    public void loginAccount(){
        EditText usernameET = findViewById(R.id.CustomerLoginScreen_EditText_Username);
        EditText passwordET = findViewById(R.id.CustomerLoginScreen_EditText_Password);
        String username = usernameET.getText().toString();
        String password = passwordET.getText().toString();


        reference.child(Constants.DATABASE.ROOT).child(Constants.DATABASE.CUSTOMER_PATH).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                boolean match = false;
                for(DataSnapshot infoSnapshot : snapshot.getChildren()){
                    if(infoSnapshot!=null){
                        String dataName = infoSnapshot.getKey();
                        System.out.println(dataName);
                        if(username.isEmpty() || password.isEmpty()){
                            Toast.makeText(CustomerLoginScreen.this, "Field must not be empty", Toast.LENGTH_SHORT).show();
                            match = true;
                        }else if(dataName.equals(username)){
                            match = true;
                            if(infoSnapshot.child(Constants.DATABASE.PASSWORD_KEY).getValue().toString().equals(password)){
                                Customer customer = new Customer(username, password, infoSnapshot.getRef());
                                Customer.readFromDatabase(new Updater() {
                                    @Override
                                    public void onUpdate() {
                                        openCustomerHomePage();
                                    }
                                });
//                                openCustomerHomePage();
                            }else{
                                Toast.makeText(CustomerLoginScreen.this, "Wrong password", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                }
                if(match==false) {Toast.makeText(CustomerLoginScreen.this, "Username not found", Toast.LENGTH_SHORT).show();}
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }


}