package com.escape.Controller;

import com.escape.Model.SceneManager;
import com.escape.Model.User;
import com.escape.Model.UserList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;


public class TitleScreenController implements Initializable {

    @FXML private javafx.scene.image.ImageView backgroundImage;
    @FXML private Button startButton;
    @FXML private Button settingsButton;
    @FXML private VBox leaderboardPanel;
    @FXML private VBox leaderboardEntries;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            String imagePath = getClass().getResource("/com/escape/images/menu_background.png").toExternalForm();
            Image bg = new Image(imagePath);
            backgroundImage.setImage(bg);
        } catch (Exception e) {
            System.out.println("ERROR: Could not load background image!");
            e.printStackTrace();
        }
        populateLeaderboard();
    }

    private void populateLeaderboard() {
        leaderboardEntries.getChildren().clear();
        
        try {
            ArrayList<User> users = UserList.getInstance().getUsers();
            users.sort((u1, u2) -> Integer.compare(u2.getLevel(), u1.getLevel()));
            
            int count = 0;
            for (User user : users) {
                if (count >= 5) break;
                
                HBox entry = new HBox(30);
                entry.setAlignment(Pos.CENTER);
                
                Label nameLabel = new Label(user.getUserName());
                nameLabel.getStyleClass().add("leaderboard-name");
                nameLabel.setPrefWidth(120);
                
                Label scoreLabel = new Label(formatTime(user.getLevel()));
                scoreLabel.getStyleClass().add("leaderboard-score");
                
                entry.getChildren().addAll(nameLabel, scoreLabel);
                leaderboardEntries.getChildren().add(entry);
                
                count++;
            }
            
            if (users.isEmpty()) {
                Label noPlayers = new Label("No players yet!");
                noPlayers.getStyleClass().add("label-gray");
                leaderboardEntries.getChildren().add(noPlayers);
            }
        } catch (Exception e) {
            Label errorLabel = new Label("Could not load leaderboard");
            errorLabel.getStyleClass().add("label-gray");
            leaderboardEntries.getChildren().add(errorLabel);
        }
    }


    private String formatTime(int level) {
        // TODO - in real game, store and display actual completion time
        int minutes = 20 + (level * 5);
        int seconds = (level * 13) % 60;
        return String.format("%d:%02d", minutes, seconds);
    }

    @FXML
    private void onStartClicked() {
        SceneManager.getInstance().switchToScene("LoginScreen");
    }

    @FXML
    private void onSettingsClicked() {
        SceneManager.getInstance().switchToScene("SettingsScreen");
    }
}