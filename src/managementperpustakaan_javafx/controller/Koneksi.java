package managementperpustakaan_javafx.controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Koneksi {
    // URL koneksi ke database
    private static final String URL = "jdbc:mysql://localhost:3306/java_ManagementPerpustakaan";
    // Username untuk koneksi ke database
    private static final String USERNAME = "root";
    // Password untuk koneksi ke database
    private static final String PASSWORD = "";
    
    // Koneksi database
    private static Connection connection = null;
    
    /**
     * Mendapatkan koneksi ke database.
     */
    public static Connection getConnection() {
        try {
            if (connection == null || connection.isClosed()) {
                Class.forName("com.mysql.cj.jdbc.Driver");
                connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            }
            return connection;
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to connect to database: " + e.getMessage());
        }
    }
    
    public static void closeConnection() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
