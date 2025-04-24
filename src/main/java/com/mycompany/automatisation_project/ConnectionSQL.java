package com.mycompany.automatisation_project;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionSQL {
    
    // Database credentials
    private static final String URL = "jdbc:sqlserver://DESKTOP-QG7QTMK:1433;databaseName=JeeProject;encrypt=true;TrustServerCertificate=true;";
    private static final String USER = "salma"; //change to your sql user, li darna nit f info dis
    private static final String PASSWORD = "salmazine2003";// nafs lhaja hna

    // Connection method
    public static Connection getConnection() {
        try {
            // Load the MySQL JDBC driver (optional for newer Java versions)
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");

            // Establish the connection
            Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("✅ Database connected successfully!");
            return connection;

        } catch (ClassNotFoundException e) {
            System.out.println("❌ MySQL JDBC Driver not found.");
            e.printStackTrace();
        } catch (SQLException e) {
            System.out.println("❌ Connection failed.");
            e.printStackTrace();
        }

        return null;
    }

    // For testing the connection
//    public static void main(String[] args) {
//        getConnection();
//    }
}
