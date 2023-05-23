package com.example.taskoptimizer;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.taskoptimizer.ui.home.HomeFragment;
import com.google.android.material.button.MaterialButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

public class MainActivity extends AppCompatActivity {

    private TextView username;
    private TextView password;
    private MaterialButton loginbtn;
    private MaterialButton singupbtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        loginbtn = findViewById(R.id.loginbtn);
        singupbtn = findViewById(R.id.signupbtn);

        loginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (username.getText().toString().equals("admin") && password.getText().toString().equals("admin")) {
                    // Correct credentials
                    Toast.makeText(MainActivity.this, "LOGIN SUCCESSFUL", Toast.LENGTH_SHORT).show();
                    // Open another activity after successful login
                    openDashboardPage();
                } else {
                    // Incorrect credentials
                    Toast.makeText(MainActivity.this, "LOGIN FAILED", Toast.LENGTH_SHORT).show();
                }
            }
        });

        singupbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openSignUpPage();
            }
        });
    }

    private void openDashboardPage() {
        Intent intent = new Intent(MainActivity.this, NavBarControl.class);
        startActivity(intent);

    }

    private void openSignUpPage() {
        Intent intent = new Intent(MainActivity.this, SignUp.class);
        startActivity(intent);
    }
}