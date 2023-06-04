package com.example.taskoptimizer;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class MainActivity extends AppCompatActivity {

    boolean nightMode;
    SharedPreferences sharedPreferences;
    private EditText username, password;
    private Button loginButton, signupButton;
    private static final String DB_HOSTNAME = "taskoptimizer.mysql.database.azure.com";
    private static final String DB_USERNAME = "myadminuser";
    private static final String DB_PASSWORD = "BinaryBandits69";
    private static final String DB_NAME = "taskoptimizer";

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
                    //login(name, pass);
                } else {
                    Toast.makeText(MainActivity.this, "Please enter username and password", Toast.LENGTH_SHORT).show();
                }

            }
        });

        signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, NavBarControl.class);
                startActivity(intent);
            }
        });
    }

   /** private void login(String u, String p) {
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;

        try {
            // Load the MySQL Connector/J driver
            Class.forName("com.mysql.cj.jdbc.Driver");
            //String url = "jdbc:mysql://" + DB_HOSTNAME + ":3306/" + DB_NAME;
            // Create the database connection URL
            //String url = "jdbc:mysql://" + DB_HOSTNAME + ":3306/" + DB_NAME;

            // Create the database connection
            //connection = DriverManager.getConnection(url, DB_USERNAME, DB_PASSWORD);
            connection = DriverManager.getConnection("jdbc:sqlserver://taskopt.database.windows.net:1433;" +
                    "database=Test;" +
                    "user=adminuser@taskopt;" +
                    "password=BinaryBandits69" +
                    ";" +
                    "encrypt=true;" +
                    "trustServerCertificate=false;" +
                    "hostNameInCertificate=*.database.windows.net;" +
                    "loginTimeout=30;");

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
    }**/
}
