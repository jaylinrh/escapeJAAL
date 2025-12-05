package com.escape.Controller;

import com.escape.Model.Facade;
import com.escape.Model.SceneManager;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import java.net.URL;
import java.util.ResourceBundle;

public class DifficultySelectController implements Initializable {

    @FXML private Button backButton;
    @FXML private Button easyButton;
    @FXML private Button mediumButton;
    @FXML private Button hardButton;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }

    @FXML
    private void onBackClicked() {
        SceneManager.getInstance().switchToScene("MainMenu");
    }

    @FXML
    private void onEasyClicked() {
        startGameWithDifficulty("Easy", 45);
    }

    @FXML
    private void onMediumClicked() {
        startGameWithDifficulty("Medium", 30);
    }

    @FXML
    private void onHardClicked() {
        startGameWithDifficulty("Hard", 20);
    }

    private void startGameWithDifficulty(String difficulty, int timeMinutes) {
        Facade.getInstance().createNewGame(difficulty);
        SceneManager sceneManager = SceneManager.getInstance();
        sceneManager.setSelectedDifficulty(difficulty);
        sceneManager.setDifficultyTimeLimit(timeMinutes);
        sceneManager.launchGame();
    }
}
