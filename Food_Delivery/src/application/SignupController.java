package application;

import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.DatePicker;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class SignupController {

    @FXML
    private TextField adress_field;

    @FXML
    private PasswordField confirm_pass_field;

    @FXML
    private TextField email_field;

    @FXML
    private TextField name_field;

    @FXML
    private PasswordField password_field;

    @FXML
    private TextField phone_field;

    @FXML
    private TextField username_field;

    @FXML
    private DatePicker dob_field;

    public void signupHandler(ActionEvent event) throws IOException {
        String jdbcUrl = "jdbc:mysql://localhost:3306/food_delivery"; // Replace with your database name
        String username = "root"; // Replace with your database username
        String password = "pass"; // Replace with your database password

        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            // Load the MySQL JDBC driver
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Establish the connection
            connection = DriverManager.getConnection(jdbcUrl, username, password);
            System.out.println("Connection established successfully!");
            String sql = "INSERT INTO customer_info (Name, User_name, Password, Phone_no, Address, DOB, Email) VALUES (?, ?, ?, ?, ?, ?, ?)";

            // Create a PreparedStatement
            preparedStatement = connection.prepareStatement(sql);

            // Get user input
            String name = name_field.getText();
            String user_name = username_field.getText();
            String passw = password_field.getText();
            String phone = phone_field.getText();
            String address = adress_field.getText();
            LocalDate dob = dob_field.getValue();
            Date birthDate = Date.valueOf(dob); // Convert LocalDate to java.sql.Date
            String email = email_field.getText();

            // Ensure fields are not empty
            if (name.isEmpty() || user_name.isEmpty() || passw.isEmpty() || phone.isEmpty() || address.isEmpty() || dob == null || email.isEmpty()) {
                System.out.println("Please fill in all fields.");
                return;
            }

            // Set the values in the PreparedStatement
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, user_name);
            preparedStatement.setString(3, passw);
            preparedStatement.setString(4, phone);
            preparedStatement.setString(5, address);
            preparedStatement.setDate(6, birthDate);
            preparedStatement.setString(7, email);

            // Execute the PreparedStatement
            int rowsAffected = preparedStatement.executeUpdate();
            System.out.println(rowsAffected + " row(s) inserted.");

            if (rowsAffected > 0) {
                // Switch back to the main page
                switchtoScene1(event);
            } else {
                System.out.println("Failed to insert the record.");
            }

        } catch (ClassNotFoundException e) {
            System.out.println("MySQL JDBC Driver not found.");
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("SQL Exception: " + e.getMessage());
        } finally {
            if (preparedStatement != null) {
                try {
                    preparedStatement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                    System.out.println("Failed to close PreparedStatement: " + e.getMessage());
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                    System.out.println("Failed to close Connection: " + e.getMessage());
                }
            }
        }
    }

    public void switchtoScene1(ActionEvent e) throws IOException {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("Login.fxml"));
            Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException ex) {
            System.out.print("Could not link the Scene1 due to IOException");
            throw ex;
        }
    }
}