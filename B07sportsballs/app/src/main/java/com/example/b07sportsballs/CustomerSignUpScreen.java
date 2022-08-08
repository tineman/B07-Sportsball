package com.example.b07sportsballs;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
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
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class CustomerSignUpScreen extends AppCompatActivity {
    public DatabaseReference reference;
    public static final String EXTRA_MESSAGE = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_sign_up_screen);
        reference = FirebaseDatabase.getInstance(Constants.DATABASE.DB_URL).getReference();

        Button createAccountButton = findViewById(R.id.CustomerSignUpScreen_Button_CreateAccount);
        createAccountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createAccount();
            }
        });

        Button backToLoginScreenButton = findViewById(R.id.CustomerSignUpScreen_Button_BackToLogin);
        backToLoginScreenButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openLoginScreen();
            }
        });

        Button quitButton = findViewById(R.id.CustomerSignUpScreen_Button_Quit);
        quitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                quitApp();
            }
        });
    }

    /**
     * This method is called when the "Quit" button is pressed
     */
    public void quitApp() {
        this.finishAffinity();
    }

    /**
     * This method is called when "Create Account" button is pressed
     * It takes reads the text in Username and Password edit text and
     * check them against the database.
     *
     * account creation only occurs when: both fields are filled, username
     * is alphanumeric, password is at least 6 characters and no duplicate
     * username is found in the database.
     *
     * Once account is created Customer is directed to the Home Page*/
    public void createAccount(){
        //get username and password in edit text
        EditText usernameET = findViewById(R.id.CustomerSignUpScreen_EditText_Username);
        EditText passwordET = findViewById(R.id.CustomerSignUpScreen_EditText_Password);
        String username = usernameET.getText().toString();
        String password = passwordET.getText().toString();
        Pattern usernamePattern = Pattern.compile("^[a-zA-Z0-9]+$");
        Matcher matcher = usernamePattern.matcher(username);

        reference.child(Constants.DATABASE.ROOT).child(Constants.DATABASE.CUSTOMER_PATH).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                List<String> usernameList = new ArrayList();
                for(DataSnapshot infoSnapshot : snapshot.getChildren()){
                        if(infoSnapshot!=null){
                        String dataName = infoSnapshot.getKey();
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
                    DatabaseReference commentRef = reference.child(Constants.DATABASE.ROOT).child(Constants.DATABASE.CUSTOMER_PATH);
                    commentRef.child(username);
                    commentRef.child(username).child(Constants.DATABASE.PASSWORD_KEY).setValue(password);
                    Customer customer = new Customer(username, password, commentRef);
                    openCustomerHomePage();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    /**
     * This method is called when the user wants to go back to the login screen
     * */
    public void openLoginScreen(){
        this.finish();
    }

    /**
     * This method is called after account creation is successful
     * */
    public void openCustomerHomePage(){
        Intent intent = new Intent(this, CustomerHomePage.class);
        EditText editText = findViewById(R.id.CustomerSignUpScreen_EditText_Username);
        String username = editText.getText().toString();
        intent.putExtra(EXTRA_MESSAGE, username);
        startActivity(intent);
    }
}