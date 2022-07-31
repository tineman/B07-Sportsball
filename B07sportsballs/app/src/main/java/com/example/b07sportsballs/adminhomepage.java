package com.example.b07sportsballs;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;

public class adminhomepage extends AppCompatActivity {
    public boolean clicked=false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adminhomepage);

        Button button = (Button) findViewById(R.id.button4);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v)
            {
                clicked=true;
            }

        });
        quit();

    }


    //method for when quit button is clicked
    public void quit()
    {
        if(clicked)
        {
            this.finish();
        }
    }


}
