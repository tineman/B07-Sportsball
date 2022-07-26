package com.example.b07sportsballs;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class AdminLoginPage extends AppCompatActivity {
    public static DatabaseReference ref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_login_page);
        Intent intent2 = getIntent();
        ref = FirebaseDatabase.getInstance(Constants.DATABASE.DB_URL).getReference();

        Log.i("AdminLogin", "Startup");
        Log.i("AdminLogin", "Use \"https://b07sportsballs-default-rtdb.firebaseio.com/\"");

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

    /**
     * This method is called when the user wants to go back to the Customer Login Screen
     * */
    private void getCustomer() {
        this.finish();
    }

    public void logInAdmin(){
        EditText editText = (EditText) findViewById(R.id.AdminLoginPage_EditText_Username);
        EditText editText1 = (EditText) findViewById(R.id.AdminLoginPage_EditText_Password);
        String ausername = editText.getText().toString();
        String apassword = editText1.getText().toString();


        ref.child(Constants.DATABASE.ROOT).child(Constants.DATABASE.ADMIN_PATH).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                boolean match = false;
                for(DataSnapshot infoSnapshot : snapshot.getChildren()){
                    if(infoSnapshot!=null){
                        String dataName = infoSnapshot.getKey();
                        if(ausername.isEmpty() || apassword.isEmpty()){
                            Toast.makeText(AdminLoginPage.this, "field must not be empty", Toast.LENGTH_LONG).show();
                            match = true;
                            break;
                        }else if(dataName.equals(ausername)){
                            match = true;
                            if(infoSnapshot.child(Constants.DATABASE.PASSWORD_KEY).getValue().toString().equals(apassword)){
                                Admin admin = new Admin(ausername, apassword, ref);
                                goAdminHome();
                            }else{
                                Toast.makeText(AdminLoginPage.this, "Wrong password", Toast.LENGTH_SHORT).show();
                                break;
                            }
                        }
                    }
                }
                if(!match) {Toast.makeText(AdminLoginPage.this, "Username not found", Toast.LENGTH_SHORT).show();}
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


//        intent1.putExtra(EXTRA_MESSAGE, message);
//        intent1.putExtra(EXTRA_MESSAGE, message1);
//        startActivity(intent1);
//        DatabaseReference ref =
//                FirebaseDatabase.getInstance("https://b07sportsballs-default-rtdb.firebaseio.com/").getReference();
    }


    public void goAdminHome(){
        Intent intent1 = new Intent(this, AdminHomePage.class);
        startActivity(intent1);
    }

    public void quitAdmin(){
        this.finishAffinity();
    }

}