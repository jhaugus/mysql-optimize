package com.augus.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class JdbcQueryExample2 {
    // Database URL, username, and password
    private static final String DB_URL = "jdbc:mysql://localhost:3306/slow_query_test";
    private static final String USER = "root";
    private static final String PASS = "123456";

    public static void main(String[] args) {
        // SQL query
        String query = "SELECT * FROM user WHERE status='Active'";


        long start = System.nanoTime();
        // Load and register JDBC driver
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return;
        }

        // Establish database connection
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            // Measure start time
            long startTime = System.nanoTime();

            // Execute query and process the result set
            while (rs.next()) {
                int id = rs.getInt("id");
            }

            // Measure end time
            long endTime = System.nanoTime();
            long executionTime = endTime - startTime;
            System.out.println("Execution time in nanoseconds: " + executionTime);
            System.out.println("Execution time in milliseconds: " + (executionTime / 1_000_000));

        } catch (SQLException e) {
            e.printStackTrace();
        }
        long end = System.nanoTime();
        long Time = end - start;
        System.out.println("connection time in nanoseconds: " + Time);
        System.out.println("connection time in milliseconds: " + (Time / 1_000_000));

    }
}
