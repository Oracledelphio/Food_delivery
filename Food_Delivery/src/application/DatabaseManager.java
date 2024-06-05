package application;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DatabaseManager {
    private static final String URL = "jdbc:mysql://127.0.0.1:3307/food_delivery";
    private static final String USER = "root";
    private static final String PASSWORD = "";

    public static Connection connect() {
        Connection conn = null;
        try {
        	Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("Connection successful");
        } catch (SQLException e) {
            System.out.println("Connection failed: " + e.getMessage());
        } catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
        return conn;
    }

    public static boolean testConnection() {
        try (Connection conn = connect()) {
            if (conn != null && !conn.isClosed()) {
                System.out.println("Database connection is successful.");
                return true;
            }
        } catch (SQLException e) {
            System.out.println("Failed to check connection: " + e.getMessage());
        }
        System.out.println("Database connection failed.");
        return false;
    }

    public void createUser(String name, String username, String password, String phoneNo, String address, Date dob, String email) {
        String sql = "INSERT INTO customer_info (Name, User_name, Password, Phone_no, Address, DOB, Email) VALUES (?, ?, ?, ?, ?, ?, ?)";

        try {
        	Class.forName("com.mysql.cj.jdbc.Driver");
        	Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
            PreparedStatement pstmt = conn.prepareStatement(sql);

            pstmt.setString(1, name);
            pstmt.setString(2, username);
            pstmt.setString(3, password);
            pstmt.setString(4, phoneNo);
            pstmt.setString(5, address);
            pstmt.setDate(6, dob);
            pstmt.setString(7, email);

            pstmt.executeUpdate();
            System.out.println("User created successfully.");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
    }
    public static void main(String[] args) {
        boolean isConnected = testConnection();
        System.out.println("Connection test result: " + isConnected);
    }
}
