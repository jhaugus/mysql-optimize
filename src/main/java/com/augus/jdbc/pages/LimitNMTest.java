package com.augus.jdbc.pages;

import java.sql.*;

/**
 * 通过 优化 LIMIT N,M 的查询，来优化分页查询
 * 优化sql语句
 */
public class LimitNMTest {


    private static final String DB_URL = "jdbc:mysql://localhost:3306/slow_query_test";
    private static final String USER = "root";
    private static final String PASS = "123456";
    private static final int NUM_RUNS = 1;

    public static void main(String[] args) {
        String query1 = "SELECT * FROM `t_user_batch` ORDER BY `add_time` LIMIT 10000;";
        String query2 = "SELECT * FROM `t_user_batch` ORDER BY `add_time` LIMIT 10000,10000;";
        String query3 = "SELECT * FROM `t_user_batch` ORDER BY `add_time` LIMIT 20000,10000;";
        String query4 = "SELECT * FROM `t_user_batch` ORDER BY `add_time` LIMIT 30000,10000;";

        String query5 = "SELECT * FROM `t_user_batch` ORDER BY `add_time` LIMIT 10000;";
        String query6 = "SELECT * FROM `t_user_batch` WHERE add_time > 10000 ORDER BY `add_time` LIMIT 10000;";
        String query7 = "SELECT * FROM `t_user_batch` WHERE add_time > 20000 ORDER BY `add_time` LIMIT 10000;";
        String query8 = "SELECT * FROM `t_user_batch` WHERE add_time > 30000 ORDER BY `add_time` LIMIT 10000; ";
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS)) {
                long avgTime1 = measureQueryTime(conn, query1, NUM_RUNS);
                long avgTime2 = measureQueryTime(conn, query2, NUM_RUNS);
                long avgTime3 = measureQueryTime(conn, query3, NUM_RUNS);
                long avgTime4 = measureQueryTime(conn, query4, NUM_RUNS);
                long avgTime5 = measureQueryTime(conn, query5, NUM_RUNS);
                long avgTime6 = measureQueryTime(conn, query6, NUM_RUNS);
                long avgTime7 = measureQueryTime(conn, query7, NUM_RUNS);
                long avgTime8 = measureQueryTime(conn, query8, NUM_RUNS);
                avgTime1 += (avgTime2 + avgTime3 + avgTime4);
                avgTime5 += (avgTime6 + avgTime7 + avgTime8);
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
}
