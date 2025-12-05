package com.escape.Controller;

import com.escape.Model.Facade;
import com.escape.Model.SceneManager;
import com.escape.Model.User;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.ResourceBundle;


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
        
        if (currentUser != null) {
            HBox saveEntry = createSaveEntry(
                currentUser.getUserName(),
                formatPlayTime(0), // TODO implement actual time tracking
                getRoomDisplayName(currentUser.getCurrentRoomID()),
                () -> loadGame(currentUser)
            );
            savesContainer.getChildren().add(saveEntry);
            noSavesLabel.setVisible(false);
        } else {
            noSavesLabel.setVisible(true);
        }
    }

    private HBox createSaveEntry(String name, String time, String room, Runnable loadAction) {
        HBox entry = new HBox(15);
        entry.setAlignment(Pos.CENTER);
        
        VBox info = new VBox(5);
        info.setAlignment(Pos.CENTER);
        info.getStyleClass().add("save-entry");
        info.setPrefWidth(350);
        
        Label nameLabel = new Label(name);
        nameLabel.getStyleClass().add("save-name");
        
        Label timeLabel = new Label(time);
        timeLabel.getStyleClass().add("save-info");
        
        Label roomLabel = new Label(room);
        roomLabel.getStyleClass().add("save-info");
        roomLabel.setStyle("-fx-text-fill: #AAAAAA;");
        
        info.getChildren().addAll(nameLabel, timeLabel, roomLabel);
        info.setOnMouseClicked(e -> loadAction.run());
        
        Button deleteButton = new Button("✕");
        deleteButton.getStyleClass().add("delete-button");
        deleteButton.setOnAction(e -> confirmDelete(name));
        
        entry.getChildren().addAll(info, deleteButton);
        return entry;
    }

    private String formatPlayTime(long milliseconds) {
        long totalSeconds = milliseconds / 1000;
        long hours = totalSeconds / 3600;
        long minutes = (totalSeconds % 3600) / 60;
        long seconds = totalSeconds % 60;
        return String.format("%d:%02d:%02d", hours, minutes, seconds);
    }

    private String getRoomDisplayName(String roomId) {
        if (roomId == null) return "Foyer";
        
        switch (roomId) {
            case "room_exterior": return "Exterior";
            case "room_foyer": return "Foyer";
            case "room_parlor": return "Parlor";
            case "room_library": return "Library";
            case "room_kitchen": return "Kitchen";
            case "room_greenhouse": return "Greenhouse";
            case "room_cellar": return "Cellar";
            default: return roomId;
        }
    }

    private void loadGame(User user) {
        SceneManager.getInstance().launchGame();
    }

    private void confirmDelete(String saveName) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Delete Save");
        alert.setHeaderText("Delete save: " + saveName + "?");
        alert.setContentText("This action cannot be undone.");
        
        alert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                deleteSave(saveName);
            }
        });
    }

    private void deleteSave(String saveName) {
        System.out.println("Deleting save: " + saveName);
        loadSaves();
    }
}