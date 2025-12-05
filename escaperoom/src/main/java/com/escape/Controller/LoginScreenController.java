package com.escape.Controller;

import com.escape.Model.Facade;
import com.escape.Model.SceneManager;
import com.escape.Model.User;
import com.escape.Model.UserList;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.util.ResourceBundle;

public class LoginScreenController implements Initializable {

    @FXML private Button backButton;
    @FXML private TextField usernameField;
    @FXML private PasswordField passwordField;
    @FXML private Button loginButton;
    @FXML private Hyperlink registerLink;
    @FXML private Label errorLabel;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        errorLabel.setText("");
        passwordField.setOnAction(e -> onLoginClicked());
    }

    @FXML
    private void onBackClicked() {
        SceneManager.getInstance().switchToScene("TitleScreen");
    }

    @FXML
    private void onLoginClicked() {
        String username = usernameField.getText().trim();
        String password = passwordField.getText();
        
        if (username.isEmpty()) {
            showError("Please enter a username");
            return;
        }

        if (password.isEmpty()) {
            showError("Please enter a password");
            return;
        }

        Facade facade = Facade.getInstance();
        boolean success = facade.loginUser(username, password);

        if (success) {
            SceneManager.getInstance().setCurrentUsername(username);
            SceneManager.getInstance().switchToScene("MainMenu");
        } else {
            showError("Invalid username or password");
            passwordField.clear();
        }
    }

    @FXML
    private void onRegisterClicked() {
        SceneManager.getInstance().switchToScene("RegisterScreen");
    }

    private void showError(String message) {
        errorLabel.setText(message);
    }
}
