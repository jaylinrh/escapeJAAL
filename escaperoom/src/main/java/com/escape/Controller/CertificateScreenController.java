package com.escape.Controller;

import com.escape.Model.Facade;
import com.escape.Model.GameSave;
import com.escape.Model.SceneManager;
import com.escape.Model.User;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class CertificateScreenController {

    @FXML private Label presentedToLabel;
    @FXML private Label difficultyLabel;
    @FXML private Label timeLabel;
    @FXML private Label dateLabel;

    @FXML
    public void initialize() {
        populateCertificate();
    }

    private void populateCertificate() {
        Facade facade = Facade.getInstance();
        User currentUser = facade.getCurrentUser();
        GameSave currentSave = facade.getCurrentSave();

        // username
        if (currentUser != null) {
            presentedToLabel.setText(currentUser.getUserName());
        } else {
            presentedToLabel.setText("Brave Escapee");
        }

        // difficulty
        if (currentSave != null) {
            difficultyLabel.setText("Difficulty: " + currentSave.getDifficulty());
            
            // play time
            String formattedTime = currentSave.getFormattedPlayTime();
            timeLabel.setText("Time: " + formattedTime);
        } else {
            difficultyLabel.setText("Difficulty: Unknown");
            timeLabel.setText("Time: --:--");
        }

        // date
        LocalDate today = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        dateLabel.setText(today.format(formatter));
    }

    @FXML
    private void onReturnToMenu() {
        SceneManager.getInstance().switchToScene("MainMenu");
    }
}