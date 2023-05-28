package com.example.taskoptimizer;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButton;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MainActivity extends AppCompatActivity {

    private TextView username;
    private TextView password;
    private MaterialButton loginbtn;
    private MaterialButton signupbtn;
    private DatabaseConnection databaseConnection;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        databaseConnection = new DatabaseConnection();
        databaseConnection.connectToDatabase(); // Connect to the database

        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        loginbtn = findViewById(R.id.loginbtn);
        signupbtn = findViewById(R.id.signupbtn);

        loginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    login(username.getText().toString(), password.getText().toString());
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        });

        signupbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openSignUpPage();
            }
        });
    }

    public void login(String name, String pass) throws SQLException {
        Connection connection = databaseConnection.getConnection();

        if (connection == null) {
            // Handle connection error
            Toast.makeText(MainActivity.this, "Failed to connect to the database", Toast.LENGTH_SHORT).show();
            return;
        }

        // Create a PreparedStatement object with parameter placeholders
        String query = "SELECT * FROM users WHERE username = ? AND password = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(query);

        // Set the parameter values
        preparedStatement.setString(1, name);
        preparedStatement.setString(2, pass);

        // Execute the query
        ResultSet resultSet = preparedStatement.executeQuery();

        // Check if a matching user and password combination exists
        if (resultSet.next()) {
            // Correct credentials
            Toast.makeText(MainActivity.this, "LOGIN SUCCESSFUL", Toast.LENGTH_SHORT).show();
            // Open another activity after successful login
            openDashboardPage();
        } else {
            // Incorrect credentials
            Toast.makeText(MainActivity.this, "LOGIN FAILED", Toast.LENGTH_SHORT).show();
        }

        // Close the result set, statement, and connection
        try {
            resultSet.close();
            preparedStatement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
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
