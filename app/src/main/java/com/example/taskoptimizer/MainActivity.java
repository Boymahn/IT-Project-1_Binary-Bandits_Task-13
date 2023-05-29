package com.example.taskoptimizer;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class MainActivity extends AppCompatActivity {

    private EditText username, password;
    private Button loginButton, signupButton;
    private static final String DB_HOSTNAME = "172.20.10.5";
    private static final String DB_USERNAME = "root";
    private static final String DB_PASSWORD = "";
    private static final String DB_NAME = "taskoptimizer";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        loginButton = findViewById(R.id.loginbtn);
        signupButton = findViewById(R.id.signupbtn);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*
                Toast.makeText(MainActivity.this, "Login successful", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(MainActivity.this, NavBarControl.class);
                startActivity(intent);
                finish();

                */
                String name = username.getText().toString().trim();
                String pass = password.getText().toString().trim();

                if (!name.isEmpty() && !pass.isEmpty()) {
                    login(name, pass);
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

    private void login(String u, String p) {
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;

        try {
            // Load the MySQL Connector/J driver
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Create the database connection URL
            String url = "jdbc:mysql://" + DB_HOSTNAME + ":3306/" + DB_NAME;

            // Create the database connection
            connection = DriverManager.getConnection(url, DB_USERNAME, DB_PASSWORD);
            Toast.makeText(MainActivity.this, "here3", Toast.LENGTH_SHORT).show();

            // does not reach here
            if (connection == null) {
                // Handle connection error
                Toast.makeText(MainActivity.this, "Failed to connect to the database", Toast.LENGTH_SHORT).show();
                return;
            }
            else {
                Toast.makeText(MainActivity.this, "Connected", Toast.LENGTH_SHORT).show();
            }

            // Create the SQL query
            String query = "SELECT * FROM users WHERE username = '" + u + "' AND password = '" + p + "'";

            // Create the statement
            statement = connection.createStatement();

            // Execute the query
            resultSet = statement.executeQuery(query);

            // Check if the result set is not empty
            if (resultSet.next()) {
                Toast.makeText(MainActivity.this, "Login successful", Toast.LENGTH_SHORT).show();
                // Redirect to the next activity
                Intent intent = new Intent(MainActivity.this, NavBarControl.class);
                startActivity(intent);
                finish();
            } else {
                Toast.makeText(MainActivity.this, "Invalid credentials", Toast.LENGTH_SHORT).show();
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        } finally {
            // Close the result set, statement, and connection
            try {
                if (resultSet != null) {
                    resultSet.close();
                }
                if (statement != null) {
                    statement.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
