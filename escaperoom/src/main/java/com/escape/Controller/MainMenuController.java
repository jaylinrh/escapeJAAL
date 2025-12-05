package com.escape.Controller;

import com.escape.Model.Facade;
import com.escape.Model.SceneManager;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;


public class MainMenuController {

    @FXML private Button newGameButton;
    @FXML private Button continueButton;
    @FXML private Button logoutButton;

    @FXML
    private void onNewGameClicked() {
        SceneManager.getInstance().switchToScene("DifficultySelect");
    }

    @FXML
    private void onContinueClicked() {
        SceneManager.getInstance().switchToScene("SavesScreen");
    }

    @FXML
    private void onLogoutClicked() {
        Facade.getInstance().logoutUser();
        SceneManager.getInstance().setCurrentUsername(null);
        SceneManager.getInstance().switchToScene("TitleScreen");
    }
}
