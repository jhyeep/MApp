package com.example.junhan.mapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class LoginPage extends AppCompatActivity {

    private EditText name;
    private EditText pwd;
    private Button loginButton;
    private TextView regButton;

    private String uname = "";
    private String upassword = "";

    private final String sharedPrefFile = "com.example.android.mainsharedprefs";
    public static final String NAME = "savedName";
    public static final String PASSWORD = "savedPassword";

    SharedPreferences mPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page);

        name = findViewById(R.id.username);
        pwd = findViewById(R.id.password);
        loginButton = findViewById(R.id.login_button);
        regButton = findViewById(R.id.goToReg);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validate(name.getText().toString(), pwd.getText().toString());
            }
        });


        mPreferences = getSharedPreferences(sharedPrefFile, MODE_PRIVATE);
        String Username = mPreferences.getString(NAME,"");
        String Password = mPreferences.getString(PASSWORD,"");

        name.setText(Username);
        pwd.setText(Password);



        /*
        regButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginPage.this, RegisterPage.class));
            }
        });
        */
    }

    @Override
    protected void onPause() {
        super.onPause();
        SharedPreferences.Editor preferencesEditor =
                mPreferences.edit();
        preferencesEditor.putString(NAME, uname);
        preferencesEditor.putString(PASSWORD, upassword);
        preferencesEditor.apply();
    }

    private void validate(String userName, String userPassword){
        Log.i("button", "buttonpressed");
        if (true){
        //if (FetchingEmail.getEmails(userName, userPassword) != null) {  TODO LAT when firebase part done
            if (userName.equals("firebase") | userName.equals("marauder.mapp36@gmail.com")) { // TODO: ZAC change if statement to check if usersame in firebase
                //Log.i("button", "firebase ok");
                // ZAC get password in firebase

                if (userPassword.equals("firePassword") | userPassword.equals("Group3-6") ) {  // TODO: ZAC change if statement to check if password match username
                    //Log.i("button", "pwd ok")

                    uname = userName;
                    upassword = userPassword;

                    Intent intent = new Intent(LoginPage.this, MainActivity.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(this, "Please enter valid password", Toast.LENGTH_SHORT).show();
                }

            } else {
                Toast.makeText(this, "Please enter valid username", Toast.LENGTH_SHORT).show();
            }
        }else {
            Toast.makeText(this, "Please enter valid username", Toast.LENGTH_SHORT).show();
        }
    }
}
