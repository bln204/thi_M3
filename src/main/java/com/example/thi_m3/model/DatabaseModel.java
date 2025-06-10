package com.example.thi_m3.model;

import java.sql.Connection;
import java.sql.DriverManager;

public class DatabaseModel {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/thi_md3";
    private static final String USER = "root";
    private static final String PASS = "1234";

    public static Connection getConnection() {
        Connection connection = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(DB_URL, USER, PASS);
            System.out.println("Connection database Successful!!!");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return connection;
    }
}
