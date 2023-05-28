package com.example.taskoptimizer;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    private Connection connection;

    public Connection getConnection() {
        return connection;
    }

    public void connectToDatabase() {
        try {
            // Load the MySQL Connector/J driver
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Create the database connection URL
            String url = "jdbc:mysql://taskoptimizer.mysql.database.azure.com:3306/taskoptimizer?useSSL=true";
            connection = DriverManager.getConnection(url, "myadminuser", "BinaryBandits69");

            // Connection successful
            System.out.println("Database connection established");

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
