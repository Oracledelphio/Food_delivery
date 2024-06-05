package application;

import java.io.IOException;
import java.sql.Date;
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
    	DatabaseManager dab = new DatabaseManager();

        try {
            String name = name_field.getText();
            String user_name = username_field.getText();
            String passw = password_field.getText();
            String phone = phone_field.getText();
            String address = adress_field.getText();
            LocalDate dob = dob_field.getValue();
            Date birthDate = Date.valueOf(dob); // Convert LocalDate to java.sql.Date
            String email = email_field.getText();
            
            dab.createUser(name, user_name, passw, phone, address, birthDate, email);
            switchtoScene1(event);

        }
        catch (Exception e) {
        	System.out.print("Unexpected error"+e);
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