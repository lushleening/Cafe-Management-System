package com.example.cw1.Controllers;

import com.example.cw1.Controllers.Admin.AdminController;
import com.example.cw1.DatabaseConnection;
import com.example.cw1.data;
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
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class LoginController {
    @FXML
    public ChoiceBox<String> acc_selector;
    @FXML
    public Label username_lbl;
    @FXML
    public PasswordField password_fld;
    @FXML
    private TextField username_fld;
    @FXML
    private Button login_btn;
    @FXML
    private Label error_lbl;
    @FXML
    private Button cancel_btn;

    private Connection connect;
    private PreparedStatement prepare;
    private ResultSet result;

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

    public void cancel_btnOnAction(ActionEvent actionEvent) {
        Stage stage = (Stage) cancel_btn.getScene().getWindow();
        stage.close();
    }

    public void login_btn_action(ActionEvent actionEvent) {
        // Check whether account type has been selected or not
        if (acc_selector.getValue() == null) {
            error_lbl.setText("Please select an account type");
            showAlert(AlertType.ERROR, "Login Error", "Missing Information", "Please select an account type");
            return;
        }

        // Check if username and password are empty or not
        if (username_fld.getText().isBlank() == false && password_fld.getText().isBlank() == false) {
            validate_login();
        } else {
            error_lbl.setText("Please Enter Username and Password");
            showAlert(AlertType.ERROR, "Login Error", "Missing Information", "Username and password cannot be empty");
        }
    }

    public void validate_login() {
        DatabaseConnection connect = new DatabaseConnection();
        Connection connectDb = connect.getConnection();

        String username = username_fld.getText();
        String password = password_fld.getText();
        String selectedAccountType = acc_selector.getValue();

        String verifyLogin = "SELECT * FROM user_acc WHERE username = ? AND password = ?";

        try {
            PreparedStatement statement = connectDb.prepareStatement(verifyLogin);
            statement.setString(1, username);
            statement.setString(2, password);

            ResultSet queryResult = statement.executeQuery();

            if (queryResult.next()) {
                String role = queryResult.getString("role");

                // Check if matches in database
                if (!role.equals(selectedAccountType)) {
                    error_lbl.setText("Account type does not match");
                    showAlert(AlertType.ERROR, "Login Failed",
                            "Incorrect Account Type",
                            "You selected " + selectedAccountType + " but your account is registered as " + role);
                    return;
                }

                data.username = username_fld.getText();

                error_lbl.setText("Login successful!");
                showAlert(AlertType.INFORMATION, "Login Successful",
                        "Welcome " + username,
                        "You are now logged in as " + role);

                FXMLLoader loader;
                if (role.equals("Admin")) {
                    loader = new FXMLLoader(getClass().getResource("/Fxml/Admin/AdminMenu.fxml"));
                } else {
                    loader = new FXMLLoader(getClass().getResource("/Fxml/User/UserMenu.fxml"));
                }
                Scene scene = new Scene(loader.load());
                Stage stage = (Stage) login_btn.getScene().getWindow();
                stage.setScene(scene);
                stage.show();

                if (role.equals("Admin")) {
                    AdminController adminController = loader.getController();
                    adminController.displayUsername();
                    adminController.inventoryTypeList();
                    adminController.inventoryShowData();
                }

            } else {
                error_lbl.setText("Invalid username or password.");
                showAlert(AlertType.ERROR, "Login Failed",
                        "Invalid Credentials",
                        "Username or password is incorrect");
            }

        } catch (Exception e) {
            e.printStackTrace();
            showAlert(AlertType.ERROR, "System Error",
                    "Database Error",
                    "Could not connect to the database: " + e.getMessage());
        }
    }

    @FXML
    public Button newAcc_btn;

    @FXML
    private void switchToSignUp(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/Fxml/Signup.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            Stage stage = (Stage) newAcc_btn.getScene().getWindow();
            stage.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
            showAlert(AlertType.ERROR, "Navigation Error",
                    "Could not load the signup screen",
                    "Details: " + e.getMessage());
        }
    }

}