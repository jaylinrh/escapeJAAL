package com.escape.Controller;

import com.escape.Model.Facade;
import com.escape.Model.Progression;
import com.escape.Model.Inventory;
import com.escape.Model.GameSave;
import com.escape.Model.SceneManager;
import com.escape.Model.User;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class CertificateScreenController {

    @FXML private Label presentedToLabel;
    @FXML private Label itemsLabel;
    @FXML private Label roomsLabel;
    @FXML private Label timeLabel;
    @FXML private Label difficultyLabel;
    @FXML private Label puzzlesLabel;
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

        int itemsCollected = 0;
        if (currentSave != null && currentSave.getInventory() != null) {
            itemsCollected = currentSave.getInventory().getItems().size();
        }
        itemsLabel.setText(String.format("%02d", itemsCollected));
        System.out.println("Items collected: " + itemsCollected);

        
        int roomsCompleted = 0;
        if (currentSave != null) {
            roomsCompleted = currentSave.getCompletedRooms().size();
        }
        roomsLabel.setText(String.format("%02d", roomsCompleted));
        System.out.println("Rooms completed: " + roomsCompleted);

        // time - just the value, no prefix
        if (currentSave != null) {
            timeLabel.setText(currentSave.getFormattedPlayTime());
        } else {
            timeLabel.setText("00:00");
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

        int puzzlesSolved = 0;
        if (currentSave != null) {
            puzzlesSolved = currentSave.getSolvedPuzzles().size();
            System.out.println("Puzzles from save: " + currentSave.getSolvedPuzzles());
        }
        puzzlesLabel.setText(String.format("%02d", puzzlesSolved));
        System.out.println("Puzzles solved: " + puzzlesSolved);


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