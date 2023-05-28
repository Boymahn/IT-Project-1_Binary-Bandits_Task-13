package com.example.taskoptimizer;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    private static final String SERVER_NAME = "taskoptimizer.mysql.database.azure.com";
    private static final String USERNAME = "myadminuser";
    private static final String PASSWORD = "BinaryBandits69";
    private static final String DATABASE_NAME = "taskoptimizer";

    private Connection connection;

    public Connection getConnection() {
        return connection;
    }

    public void connectToDatabase() {
        try {
            // Load the MySQL Connector/J driver
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Create the database connection URL
            String url = String.format("jdbc:mysql://%s/%s", SERVER_NAME, DATABASE_NAME);

            // Create the database connection
            connection = DriverManager.getConnection(url, USERNAME, PASSWORD);

            // Connection successful
            System.out.println("Database connection established");

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
