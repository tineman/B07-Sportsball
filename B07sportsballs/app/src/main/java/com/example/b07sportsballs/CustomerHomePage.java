package com.example.b07sportsballs;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class CustomerHomePage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_home_page);

        Intent intent = getIntent();
        String username = intent.getStringExtra(CustomerLoginScreen.EXTRA_MESSAGE);
        TextView textView = findViewById(R.id.CustomerHomePage_Username);
        textView.setText(username);

    }
}