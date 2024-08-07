package com.augus.user.test;

import java.io.FileWriter;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;

/**
 * 创建一个用户插入脚本生成器，用于生成插入用户的SQL语句。
 * 创建在父目录下sql文件为：insert_users.sql
 */
public class UserInsertScriptGenerator {

    public static void main(String[] args) {
        generateInsertStatements(5000, "insert_users.sql");
    }

    public static void generateInsertStatements(int count, String fileName) {
        Random random = new Random();
        StringBuilder insertStatements = new StringBuilder();

        for (int i = 1; i <= count; i++) {
            String username = "user" + i;
            String password = hashPassword("password" + i);
            String email = "user" + i + "@example.com";
            String firstName = capitalize(generateRandomString(5));
            String lastName = capitalize(generateRandomString(7));
            String birthDate = randomDate(1960, 2000);
            String gender = randomGender();
            String createdAt = randomTimestamp(2023);
            String status = randomStatus();
            String role = randomRole();

            String insertStatement = String.format(
                "INSERT INTO `user` (`username`, `password`, `email`, `first_name`, `last_name`, `birth_date`, `gender`, `created_at`, `status`, `role`) VALUES ('%s', '%s', '%s', '%s', '%s', '%s', '%s', '%s', '%s', '%s');",
                username, password, email, firstName, lastName, birthDate, gender, createdAt, status, role);
            insertStatements.append(insertStatement).append("\n");
        }

        try (FileWriter fileWriter = new FileWriter(fileName)) {
            fileWriter.write(insertStatements.toString());
            System.out.println("Insert statements written to " + fileName);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String generateRandomString(int length) {
        String letters = "abcdefghijklmnopqrstuvwxyz";
        Random random = new Random();
        StringBuilder stringBuilder = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            stringBuilder.append(letters.charAt(random.nextInt(letters.length())));
        }
        return stringBuilder.toString();
    }

    public static String capitalize(String str) {
        if (str == null || str.length() == 0) {
            return str;
        }
        return str.substring(0, 1).toUpperCase() + str.substring(1);
    }

    public static String hashPassword(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hash = md.digest(password.getBytes());
            StringBuilder hexString = new StringBuilder(2 * hash.length);
            for (byte b : hash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) {
                    hexString.append('0');
                }
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    public static String randomDate(int startYear, int endYear) {
        Random random = new Random();
        int year = random.nextInt(endYear - startYear + 1) + startYear;
        int month = random.nextInt(12) + 1;
        int day = random.nextInt(28) + 1; // to avoid dealing with different days in month
        return String.format("%04d-%02d-%02d", year, month, day);
    }

    public static String randomTimestamp(int year) {
        Random random = new Random();
        int month = random.nextInt(12) + 1;
        int day = random.nextInt(28) + 1; // to avoid dealing with different days in month
        int hour = random.nextInt(24);
        int minute = random.nextInt(60);
        int second = random.nextInt(60);
        return String.format("%04d-%02d-%02d %02d:%02d:%02d", year, month, day, hour, minute, second);
    }

    public static String randomGender() {
        String[] genders = {"Male", "Female", "Other"};
        Random random = new Random();
        return genders[random.nextInt(genders.length)];
    }

    public static String randomStatus() {
        String[] statuses = {"Active", "Inactive", "Suspended"};
        Random random = new Random();
        return statuses[random.nextInt(statuses.length)];
    }

    public static String randomRole() {
        String[] roles = {"User", "Admin", "Moderator"};
        Random random = new Random();
        return roles[random.nextInt(roles.length)];
    }
}
