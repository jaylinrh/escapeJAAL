package com.escape.Controller;

import com.escape.Model.Facade;
import com.escape.Model.GameSave;
import com.escape.Model.SceneManager;
import com.escape.Model.User;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.UUID;

public class SavesScreenController implements Initializable {

    @FXML private Button backButton;
    @FXML private VBox savesContainer;
    @FXML private Label noSavesLabel;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        loadSaves();
    }

    @FXML
    private void onBackClicked() {
        SceneManager.getInstance().switchToScene("MainMenu");
    }

    private void loadSaves() {
        savesContainer.getChildren().clear();
        
        User currentUser = Facade.getInstance().getCurrentUser();
        
        if (currentUser == null) {
            noSavesLabel.setVisible(true);
            return;
        }
        
        ArrayList<GameSave> saves = currentUser.getGameSaves();
        
        if (saves == null || saves.isEmpty()) {
            noSavesLabel.setVisible(true);
            return;
        }
        
        noSavesLabel.setVisible(false);
        
        for (GameSave save : saves) {
            HBox saveEntry = createSaveEntry(save);
            savesContainer.getChildren().add(saveEntry);
        }
    }

    private HBox createSaveEntry(GameSave save) {
        HBox entry = new HBox(15);
        entry.setAlignment(Pos.CENTER);
        
        VBox info = new VBox(5);
        info.setAlignment(Pos.CENTER_LEFT);
        info.getStyleClass().add("save-entry");
        info.setPrefWidth(350);
        HBox.setHgrow(info, Priority.ALWAYS);
        
        Label nameLabel = new Label(save.getSaveName());
        nameLabel.getStyleClass().add("save-name");
        
        Label timeLabel = new Label(save.getFormattedPlayTime() + " • " + save.getDifficulty());
        timeLabel.getStyleClass().add("save-time");
        
        Label roomLabel = new Label(save.getCurrentRoomDisplayName());
        roomLabel.getStyleClass().add("save-room");
        
        info.getChildren().addAll(nameLabel, timeLabel, roomLabel);
        
        info.setOnMouseClicked(e -> loadGame(save.getSaveId()));
        
        Button deleteButton = new Button("✕");
        deleteButton.getStyleClass().add("delete-button");
        deleteButton.setOnAction(e -> confirmDelete(save));
        
        entry.getChildren().addAll(info, deleteButton);
        return entry;
    }

    private void loadGame(UUID saveId) {
        boolean loaded = Facade.getInstance().loadGameSave(saveId);
        if (loaded) {
            SceneManager.getInstance().launchGame();
        } else {
            System.err.println("Failed to load save: " + saveId);
        }
    }

    private void confirmDelete(GameSave save) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Delete Save");
        alert.setHeaderText("Delete save: " + save.getSaveName() + "?");
        alert.setContentText("This action cannot be undone.");
        
        alert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                deleteSave(save.getSaveId());
            }
        });
    }

    private void deleteSave(UUID saveId) {
        Facade.getInstance().deleteGameSave(saveId);
        loadSaves();
    }
}