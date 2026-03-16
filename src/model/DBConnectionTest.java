package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Handles MySQL database connection.
 * Call getConnection() anywhere to get a live connection.
 */
public class DBConnectionTest {

    private static final String URL      = "jdbc:mysql://localhost:3306/petvet";
    private static final String USER     = "root";
    private static final String PASSWORD = "hero123"; // ← put your MySQL root password here

    public Connection getConnection() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("DB connected successfully.");
            return conn;
        } catch (ClassNotFoundException e) {
            System.out.println("MySQL Driver not found. Add the JAR to build path.");
            e.printStackTrace();
        } catch (SQLException e) {
            System.out.println("Connection failed: " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }
}