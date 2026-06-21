package com.example.cw1;

import java.sql.Connection;
import java.sql.DriverManager;


public class DatabaseConnection {
    private static Connection DatabaseLink;

    public static Connection getConnection() {
        String databaseName = "cw1_database";
        String databaseUser = "root";
        String databasePassword = "Lush2005";
        String databaseURL = "jdbc:mysql://localhost:3307/" + databaseName;

        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            DatabaseLink = DriverManager.getConnection(databaseURL, databaseUser, databasePassword);
        } catch (Exception e) {
            e.printStackTrace();
            e.getCause();
        }

        return DatabaseLink;
    }

}
