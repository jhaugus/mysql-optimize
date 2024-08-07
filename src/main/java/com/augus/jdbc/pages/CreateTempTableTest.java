package com.augus.jdbc.pages;

import java.sql.*;

/**
 * 创建临时表测试
 */
public class CreateTempTableTest {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/slow_query_test";
    private static final String USER = "root";
    private static final String PASS = "123456";
    private static final int NUM_RUNS = 1;

    private static Connection connection;
    private static Statement statement;
    private static ResultSet resultSet;



    public static void main(String[] args) {
        String query1 = "SELECT * FROM `t_user_batch` ORDER BY `add_time` LIMIT 10000;";
        String query2 = "SELECT * FROM `t_user_batch` ORDER BY `add_time` LIMIT 10000,10000;";
        String query3 = "SELECT * FROM `t_user_batch` ORDER BY `add_time` LIMIT 20000,10000;";
        String query4 = "SELECT * FROM `t_user_batch` ORDER BY `add_time` LIMIT 30000,10000;";


//        String query8 = "SELECT * FROM `t_user_batch` WHERE add_time > 30000 ORDER BY `add_time` LIMIT 10000; ";
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS)) {
                long avgTime1 = measureQueryTime(conn, query1, NUM_RUNS);
                long avgTime2 = measureQueryTime(conn, query2, NUM_RUNS);
                long avgTime3 = measureQueryTime(conn, query3, NUM_RUNS);
                long avgTime4 = measureQueryTime(conn, query4, NUM_RUNS);

                long avgTime5 = test();

                avgTime1 += (avgTime2 + avgTime3 + avgTime4);
                avgTime1 /= 4;

                System.out.println("Average time for All table: " + avgTime1/ 1_000_000 + " milliseconds");
                System.out.println("Average time for Covering Index: " + avgTime5/ 1_000_000 + " milliseconds");
                System.out.println("使用覆盖索引后，查询速度提升显著" + avgTime1/avgTime5 + "倍");
//                System.out.println("Execution time in milliseconds: " + (executionTime / 1_000_000));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static long measureQueryTime(Connection conn, String query, int numRuns) throws SQLException {
        long totalTime = 0;

        for (int i = 0; i < numRuns; i++) {
            long startTime = System.nanoTime();

            try (PreparedStatement stmt = conn.prepareStatement(query);
                 ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    // Process result set if necessary
                }
            }

            long endTime = System.nanoTime();
            totalTime += (endTime - startTime);
        }

        return totalTime / numRuns;
    }


    public static long test(){
        long totalTime = 0;
        long startTime = 0;
        long endTime = 0;
        try {
            // Load MySQL JDBC Driver
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Establish the connection
            connection = DriverManager.getConnection(DB_URL, USER, PASS);
            statement = connection.createStatement();
            startTime = System.nanoTime();
            // Execute SQL statements
            String createTempTable = "CREATE TEMPORARY TABLE temp_t_user_batch_add_time AS " +
                    "SELECT add_time FROM t_user_batch ORDER BY add_time LIMIT 10000, 1;";
            String selectQuery = "SELECT * FROM t_user_batch " +
                    "WHERE add_time > (SELECT add_time FROM temp_t_user_batch_add_time) " +
                    "ORDER BY add_time LIMIT 10000;";
            String dropTempTable = "DROP TEMPORARY TABLE temp_t_user_batch_add_time;";

            // Execute create temporary table statement
            statement.execute(createTempTable);

            // Execute the select query and get the result set
            resultSet = statement.executeQuery(selectQuery);

            // Process the result set
            while (resultSet.next()) {
                int uid = resultSet.getInt("uid");
                String account = resultSet.getString("account");
                String mobile = resultSet.getString("mobile");
                int addTime = resultSet.getInt("add_time");

                System.out.println("UID: " + uid + ", Account: " + account +
                        ", Mobile: " + mobile + ", Add Time: " + addTime);
            }

            // Execute drop temporary table statement
            statement.execute(dropTempTable);
            endTime = System.nanoTime();
            totalTime += (endTime - startTime);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        } finally {
            // Close resources
            try {
                if (resultSet != null) resultSet.close();
                if (statement != null) statement.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return totalTime;
    }
}
