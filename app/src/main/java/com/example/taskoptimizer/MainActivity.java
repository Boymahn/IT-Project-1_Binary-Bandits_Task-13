package com.example.taskoptimizer;

import android.content.Intent;
import android.os.AsyncTask;
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
    private static final String DB_HOSTNAME = "taskoptimizer.mysql.database.azure.com";
    private static final String DB_USERNAME = "myadminuser";
    private static final String DB_PASSWORD = "BinaryBandits69";
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
        LoginTask loginTask = new LoginTask();
        loginTask.execute(u, p);
    }

    private class LoginTask extends AsyncTask<String, Void, Boolean> {

        @Override
        protected Boolean doInBackground(String... params) {
            String u = params[0];
            String p = params[1];

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

                // Check if the connection is successful
                if (connection == null) {
                    return false;
                }

                // Create the SQL query
                String query = "SELECT * FROM users WHERE username = '" + u + "' AND password = '" + p + "'";

                // Create the statement
                statement = connection.createStatement();

                // Execute the query
                resultSet = statement.executeQuery(query);

                // Check if the result set is not empty
                return resultSet.next();
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

            return false;
        }

        @Override
        protected void onPostExecute(Boolean loginSuccessful) {
            if (loginSuccessful) {
                Toast.makeText(MainActivity.this, "Login successful", Toast.LENGTH_SHORT).show();
                // Redirect to the next activity
                Intent intent = new Intent(MainActivity.this, NavBarControl.class);
                startActivity(intent);
                finish();
            } else {
                Toast.makeText(MainActivity.this, "Invalid credentials", Toast.LENGTH_SHORT).show();
            }
        }
    }

}
