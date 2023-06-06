package com.example.taskoptimizer;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;




public class MainActivity extends AppCompatActivity {

    boolean nightMode;
    SharedPreferences sharedPreferences;
    private EditText username, password;
    private Button loginButton, signupButton;
    private Button skip;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Check the theme preference
        sharedPreferences = getSharedPreferences("MODE", Context.MODE_PRIVATE);
        nightMode = sharedPreferences.getBoolean("night", false);
        if (nightMode) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }

        // Set the layout based on the theme
        setContentView(R.layout.activity_main);

        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        loginButton = findViewById(R.id.loginbtn);
        signupButton = findViewById(R.id.signupbtn);
        skip = findViewById(R.id.skip_btn);

        SQLiteHandler db = new SQLiteHandler(this);

        skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                db.createDefault();
                Intent intent = new Intent(MainActivity.this, NavBarControl.class);
                startActivity(intent);
            }
        });

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {





               /* Toast.makeText(MainActivity.this, "Login successful", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(MainActivity.this, NavBarControl.class);
                startActivity(intent);

                finish();
                */


                 String name = username.getText().toString().trim();
                 String pass = password.getText().toString().trim();

                 if (name.isEmpty() && pass.isEmpty()) {

                 } else {
                 Toast.makeText(MainActivity.this, "Please enter username and password", Toast.LENGTH_SHORT).show();
                 }

            }
        });

        signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SignUp.class);
                startActivity(intent);
            }
        });
    }

}
