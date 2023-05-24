package com.example.taskoptimizer;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.material.button.MaterialButton;
import androidx.appcompat.app.AppCompatActivity;

public class SignUp extends AppCompatActivity {

    private TextView username;
    private TextView password;
    private TextView confpassword;
    private MaterialButton singupbtn;
    private MaterialButton backbtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);

        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        confpassword = findViewById(R.id.confpassword);
        singupbtn = findViewById(R.id.signupbtn);
        backbtn = findViewById(R.id.backbtn);

        singupbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (confpassword.getText().toString().equals(password.getText().toString())) {
                    // Correct credentials
                    Toast.makeText(SignUp.this, "SIGNUP SUCCESSFUL", Toast.LENGTH_SHORT).show();
                    // Open another activity after successful login
                    openDashboardPage();
                } else {
                    // Incorrect credentials
                    Toast.makeText(SignUp.this, "PASSWORDS DO NOT MATCH", Toast.LENGTH_SHORT).show();
                }
            }
        });

        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openLoginPage();
            }
        });
    }

    private void openDashboardPage() {
        Intent intent = new Intent(SignUp.this, NavBarControl.class);
        startActivity(intent);
    }

    private void openLoginPage() {
        Intent intent = new Intent(SignUp.this, MainActivity.class);
        startActivity(intent);
    }
}
