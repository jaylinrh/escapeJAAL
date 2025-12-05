package com.escape.Controller;

import com.escape.Model.Facade;
import com.escape.Model.Progression;
import com.escape.Model.SceneManager;
import com.escape.Model.User;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.util.ResourceBundle;


public class CertificateScreenController implements Initializable {

    @FXML private Label presentedToLabel;
    @FXML private Label hintsLabel;
    @FXML private Label itemsLabel;
    @FXML private Label timeLabel;
    @FXML private Label difficultyLabel;
    @FXML private Label puzzlesLabel;
    @FXML private Label roomsLabel;
    @FXML private Button mainMenuButton;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        populateCertificate();
    }

    private void populateCertificate() {
        SceneManager sceneManager = SceneManager.getInstance();
        Facade facade = Facade.getInstance();
        
        String playerName = sceneManager.getCurrentUsername();
        if (playerName == null || playerName.isEmpty()) {
            playerName = "Player";
        }
        presentedToLabel.setText("Presented to \"" + playerName + "\"");
        
        String difficulty = sceneManager.getSelectedDifficulty();
        difficultyLabel.setText(difficulty != null ? difficulty : "Medium");
        
        Progression progression = facade.getProgression();
        
        if (progression != null) {
            itemsLabel.setText(String.format("%02d", progression.getItemsCollected()));
            puzzlesLabel.setText(String.format("%02d", progression.getPuzzlesSolved()));
            roomsLabel.setText(String.format("%02d", progression.getRoomsCompleted()));
            timeLabel.setText(progression.getFormattedPlayTime());
            
            // Hints used (placeholder - implement hint tracking)
            hintsLabel.setText("00");
        } else {
            itemsLabel.setText("00");
            puzzlesLabel.setText("00");
            roomsLabel.setText("00");
            timeLabel.setText("00:00:00");
            hintsLabel.setText("00");
        }
    }

    @FXML
    private void onMainMenuClicked() {
        SceneManager.getInstance().switchToScene("MainMenu");
    }
}
