package com.example.b07sportsballs;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

public class CustomerLoginScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customerloginscreen);

        Log.i("MainActivity", "Startup");
        Log.i("MainActivity", "Use \"https://b07sportsballs-default-rtdb.firebaseio.com/\"");

    }

    /**
     * This method is called when the "Quit" button is pressed
     * @param view The View object in consideration
     */
    public void quitButton(View view) {
        this.finishAffinity();
    }
}