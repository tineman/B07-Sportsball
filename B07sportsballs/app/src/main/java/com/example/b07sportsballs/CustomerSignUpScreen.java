package com.example.b07sportsballs;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;

public class CustomerSignUpScreen extends AppCompatActivity {

    public static final String EXTRA_MESSAGE = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_sign_up_screen);

        Button createAccountButton = findViewById(R.id.button_CustomerSignUpPage_CreateAccount);
        createAccountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openCustomerHomePage();
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
    public void openCustomerHomePage(){
        Intent intent = new Intent(this, CustomerHomePage.class);
        EditText editText = findViewById(R.id.editText_CustomerLoginPage_Username);
        String username = editText.getText().toString();
        intent.putExtra(EXTRA_MESSAGE, username);
        startActivity(intent);
    }
}