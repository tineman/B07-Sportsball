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

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CustomerSignUpScreen extends AppCompatActivity {

    public DatabaseReference reference;
    public static final String EXTRA_MESSAGE = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_sign_up_screen);
        reference = FirebaseDatabase.getInstance("https://sportsballtesting-default-rtdb.firebaseio.com/").getReference();
        /**
         * The following button will quit the program once it is clicked
         */
        Button quitButton = findViewById(R.id.CustomerSignUpScreen_Button_Quit);
        quitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                quitApp();
            }
        });

        Button createAccountButton = findViewById(R.id.CustomerSignUpScreen_Button_CreateAccount);
        createAccountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createAccount();
            }
        });
    }

    /**
     * This method is called when the "Quit" button is pressed
     */
    public void quitApp() {
        this.finishAffinity();
    }

    public void createAccount(){
        //get username and password in edit text
        EditText usernameET = findViewById(R.id.CustomerSignUpScreen_EditText_Username);
        EditText passwordET = findViewById(R.id.CustomerSignUpScreen_EditText_Password);
        String username = usernameET.getText().toString();
        String password = passwordET.getText().toString();
        Pattern usernamePattern = Pattern.compile("^[a-zA-Z0-9]+$");
        Matcher matcher = usernamePattern.matcher(username);

        reference.child("Root").child("Customer").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                List<String> usernameList = new ArrayList();
                for(DataSnapshot infoSnapshot : snapshot.getChildren()){
                        if(infoSnapshot!=null){
                        String dataName = infoSnapshot.child("Username").getValue().toString();
                        System.out.println(dataName);
                        usernameList.add((String)dataName);}
                }
                if(username.isEmpty() || password.isEmpty()){
                    Toast.makeText(CustomerSignUpScreen.this, "field must not be empty", Toast.LENGTH_SHORT).show();
                }else if(matcher.matches()==false){
                    Toast.makeText(CustomerSignUpScreen.this, "Username must be alphanumeric", Toast.LENGTH_SHORT).show();
                }else if(password.length()<6){
                    Toast.makeText(CustomerSignUpScreen.this, "Password must at least be 6 characters", Toast.LENGTH_SHORT).show();
                }
                else if(usernameList.contains(username)==true){
                    Toast.makeText(CustomerSignUpScreen.this, "Username is taken", Toast.LENGTH_SHORT).show();
                }else{
                    DatabaseReference commentRef = reference.child("Root").child("Customer").push();
                    commentRef.child("Username").setValue(username);
                    commentRef.child("Password").setValue(password);
                    openCustomerHomePage();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void openCustomerHomePage(){
        Intent intent = new Intent(this, CustomerHomePage.class);
        EditText editText = findViewById(R.id.CustomerSignUpScreen_EditText_Username);
        String username = editText.getText().toString();
        intent.putExtra(EXTRA_MESSAGE, username);
        startActivity(intent);
    }
}