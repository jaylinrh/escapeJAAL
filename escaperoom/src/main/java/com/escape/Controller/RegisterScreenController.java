package com.escape.Controller;

import com.escape.Model.Facade;
import com.escape.Model.SceneManager;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.util.ResourceBundle;

public class RegisterScreenController implements Initializable {

    @FXML private Button backButton;
    @FXML private TextField usernameField;
    @FXML private PasswordField passwordField;
    @FXML private PasswordField confirmPasswordField;
    @FXML private Button registerButton;
    @FXML private Label errorLabel;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        errorLabel.setText("");
        
        confirmPasswordField.setOnAction(e -> onRegisterClicked());
    }

    @FXML
    private void onBackClicked() {
        SceneManager.getInstance().switchToScene("LoginScreen");
    }

    @FXML
    private void onRegisterClicked() {
        String username = usernameField.getText().trim();
        String password = passwordField.getText();
        String confirmPassword = confirmPasswordField.getText();

        Facade facade = Facade.getInstance();
        System.out.println("=== DEBUG: ALL EXISTING USERS ===");
        System.out.println("Total users: " + facade.getTotalUsers());
        facade.getLeaderboard(); 
        System.out.println("Trying to register: '" + username + "'");
        System.out.println("usernameExists returns: " + facade.usernameExists(username));
        System.out.println("=================================");


        if (username.isEmpty()) {
            showError("Please enter a username");
            return;
        }

        if (username.length() < 3) {
            showError("Username must be at least 3 characters");
            return;
        }

        if (password.isEmpty()) {
            showError("Please enter a password");
            return;
        }

        if (password.length() < 4) {
            showError("Password must be at least 4 characters");
            return;
        }

        if (!password.equals(confirmPassword)) {
            showError("Passwords do not match");
            confirmPasswordField.clear();
            return;
        }    
        
        if (facade.usernameExists(username)) {
            showError("Username already exists");
            return;
        }

        boolean success = facade.registerUser(username, password);

        if (success) {
            SceneManager.getInstance().setCurrentUsername(username);
            SceneManager.getInstance().switchToScene("MainMenu");
        } else {
            showError("Registration failed. Please try again.");
        }
    }

    private void showError(String message) {
        errorLabel.setText(message);
    }
}