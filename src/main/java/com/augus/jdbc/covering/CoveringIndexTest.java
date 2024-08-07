package com.augus.jdbc.covering;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * 使用覆盖索引避免回表
 */
public class CoveringIndexTest {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/slow_query_test";
    private static final String USER = "root";
    private static final String PASS = "123456";
    private static final int NUM_RUNS = 10;

    public static void main(String[] args) {
        String query1 = "SELECT * FROM t_user_batch WHERE `add_time`=1723041318;";
        String query2 = "SELECT `add_time`, mobile FROM t_user_batch WHERE `add_time`=1723041318;";

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS)) {
                long avgTime1 = measureQueryTime(conn, query1, NUM_RUNS);
                long avgTime2 = measureQueryTime(conn, query2, NUM_RUNS);

                System.out.println("Average time for All table: " + avgTime1/ 1_000_000 + " milliseconds");
                System.out.println("Average time for Covering Index: " + avgTime2/ 1_000_000 + " milliseconds");
                System.out.println("使用覆盖索引后，查询速度提升显著" + avgTime1/avgTime2 + "倍");
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
