package com.example.cw1.Controllers;

import com.example.cw1.DatabaseConnection;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.event.ActionEvent;
import javafx.stage.Stage;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

import java.io.IOException;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.regex.Pattern;

public class SignUpController {
    @FXML
    public ChoiceBox<String> acc_selector;
    @FXML
    public Label username_lbl;
    @FXML
    public Label password_label;
    @FXML
    public Label error_lbl1;
    @FXML
    public Label success_msg;

    public Label username_constraint;
    @FXML
    private PasswordField password_fld1;
    @FXML
    private TextField username_fld;
    @FXML
    private PasswordField password_fld;
    @FXML
    private Button register_btn;
    @FXML
    private Label error_lbl;
    @FXML
    private Button cancel_btn;
    @FXML
    public Button newAcc_btn;

    // Username constraints
    private static final int MIN_USERNAME_LENGTH = 5;
    private static final int MAX_USERNAME_LENGTH = 10;
    private static final Pattern USERNAME_PATTERN = Pattern.compile("^[a-zA-Z0-9]+$");

    @FXML
    private void initialize() {
        acc_selector.getItems().addAll("Admin", "User");
    }

    private void showAlert(AlertType alertType, String title, String header, String content) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }

    public void registerButtonClicked(ActionEvent actionEvent) {
        //
        if (acc_selector.getValue() == null) {
            error_lbl.setText("Please select an account type");
            showAlert(AlertType.ERROR, "Registration Error", "Missing Information",
                    "Please select an account type (Admin or User)");
            return;
        }

        // Check if username and password are provided
        if (username_fld.getText().isBlank() || password_fld.getText().isBlank()) {
            error_lbl.setText("Please Enter Username and Password");
            showAlert(AlertType.ERROR, "Registration Error", "Missing Information",
                    "Username and password cannot be empty");
            return;
        }

        String username = username_fld.getText();
        if (!isValidUsername(username)) {
            error_lbl.setText("Invalid username format");
            showAlert(AlertType.ERROR, "Registration Error", "Invalid Username",
                    "Username must be 5-10 characters long and contain only letters and numbers");
            return;
        }

        // Check if passwords are matching
        if(!password_fld.getText().equals(password_fld1.getText())) {
            password_label.setText("Passwords do not match!");
            showAlert(AlertType.ERROR, "Registration Error", "Password Mismatch",
                    "The passwords you entered do not match");
            return;
        }

        // Check if username already exists
        if (usernameExists(username)) {
            error_lbl.setText("Username already exists");
            showAlert(AlertType.ERROR, "Registration Error", "Username Taken",
                    "This username is already registered. Please choose another one.");
            return;
        }

        password_label.setText("");
        error_lbl.setText("");
        registerUser();
    }

    private boolean isValidUsername(String username) {
        int length = username.length();

        // Check length constraint
        if (length < MIN_USERNAME_LENGTH || length > MAX_USERNAME_LENGTH) {
            return false;
        }

        // Check if username contains only letters and numbers
        return USERNAME_PATTERN.matcher(username).matches();
    }

    public void cancel_btnOnAction(ActionEvent actionEvent) {
        Stage stage = (Stage) cancel_btn.getScene().getWindow();
        stage.close();
    }


    private boolean usernameExists(String username) {
        DatabaseConnection connect = new DatabaseConnection();
        Connection connectDB = connect.getConnection();

        String checkUser = "SELECT * FROM user_acc WHERE username = ?";

        try {
            PreparedStatement statement = connectDB.prepareStatement(checkUser);
            statement.setString(1, username);
            ResultSet resultSet = statement.executeQuery();

            return resultSet.next();

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public void registerUser() {
        DatabaseConnection connect = new DatabaseConnection();
        Connection connectDB = connect.getConnection();

        String username = username_fld.getText();
        String password = password_fld.getText();
        String role = acc_selector.getValue();


        String insertQuery = "INSERT INTO user_acc(username, password, role) VALUES (?, ?, ?)";

        try {
            PreparedStatement preparedStatement = connectDB.prepareStatement(insertQuery);
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);
            preparedStatement.setString(3, role);

            int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected > 0) {
                success_msg.setText("User has been registered successfully!");
                showAlert(AlertType.INFORMATION, "Registration Successful",
                        "Account Created",
                        "Your " + role + " account has been created successfully!");

                username_fld.clear();
                password_fld.clear();
                password_fld1.clear();
                acc_selector.setValue(null);
            }

        } catch (Exception e) {
            e.printStackTrace();
            showAlert(AlertType.ERROR, "Registration Error",
                    "Database Error",
                    "Could not register user: " + e.getMessage());
        }
    }

    @FXML
    private void switchAcc(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/Fxml/Login.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            Stage stage = (Stage) newAcc_btn.getScene().getWindow();
            stage.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
            showAlert(AlertType.ERROR, "Navigation Error",
                    "Could not load the login screen",
                    "Details: " + e.getMessage());
        }
    }

}