package com.example.b07sportsballs;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class AdminLoginPage extends AppCompatActivity {
    public static final String EXTRA_MESSAGE = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_login_page);
        Intent intent2 = getIntent();

        Button loginButton = findViewById(R.id.AdminLoginPage_Button_Login);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                logInAdmin();
            }
        });

        Button goToCustomerLoginScreenButton = findViewById(R.id.AdminLoginPage_Button_LogInAsCustomer);
        goToCustomerLoginScreenButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getCustomer();
            }
        });

        Button quitButton = findViewById(R.id.AdminLoginPage_Button_Quit);
        quitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                quitAdmin();
            }
        });


// Get the Intent that started this activity and extract the string
//        String message = intent.getStringExtra(MainActivity.EXTRA_MESSAGE);

        // Capture the layout's TextView and set the string as its text
//        TextView textView = findViewById(R.id.textView);
//        textView.setText(message);


    }

    private void getCustomer() {
        Intent intent1 = new Intent(this, CustomerLoginScreen.class);
        startActivity(intent1);
    }

    public void logInAdmin(){
        Intent intent1 = new Intent(this, AdminHomePage.class);
        EditText editText = (EditText) findViewById(R.id.AdminLoginPage_EditText_Username);
        EditText editText1 = (EditText) findViewById(R.id.AdminLoginPage_EditText_Password);
        String message = editText.getText().toString();
        String message1 = editText1.getText().toString();
        intent1.putExtra(EXTRA_MESSAGE, message);
        intent1.putExtra(EXTRA_MESSAGE, message1);
        startActivity(intent1);
//        DatabaseReference ref =
//                FirebaseDatabase.getInstance("https://b07sportsballs-default-rtdb.firebaseio.com/").getReference();
    }

    public void quitAdmin(){
        this.finishAffinity();
    }

}