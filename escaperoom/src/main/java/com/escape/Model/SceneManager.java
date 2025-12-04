package com.escape.Model;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


public class SceneManager {
    
    private static SceneManager instance;
    
    private Stage primaryStage;
    private Scene currentScene;
    private Map<String, Parent> sceneCache;
    
    // Screen dimensions
    private static final int SCREEN_WIDTH = 1280;
    private static final int SCREEN_HEIGHT = 720;
    
    // Session data
    private String currentUsername;
    private String selectedDifficulty = "Medium";
    private int difficultyTimeLimit = 30;
    
    // Game reference
    private GameApp gameApp;
    
    private SceneManager() {
        sceneCache = new HashMap<>();
    }
    
    public static SceneManager getInstance() {
        if (instance == null) {
            instance = new SceneManager();
        }
        return instance;
    }
    
    public void initialize(Stage stage) {
        this.primaryStage = stage;
        primaryStage.setTitle("HollowMore Manor");
        primaryStage.setResizable(false);
    }
    
    public void switchToScene(String sceneName) {
        try {
            Parent root = loadFXML(sceneName);
            
            if (currentScene == null) {
                currentScene = new Scene(root, SCREEN_WIDTH, SCREEN_HEIGHT);
                currentScene.getStylesheets().add(
                    getClass().getResource("/css/hollowmore.css").toExternalForm()
                );
            } else {
                currentScene.setRoot(root);
            }
            
            primaryStage.setScene(currentScene);
            primaryStage.show();
            
        } catch (IOException e) {
            System.err.println("Failed to load scene: " + sceneName);
            e.printStackTrace();
        }
    }
    
    private Parent loadFXML(String fxml) throws IOException {
        FXMLLoader loader = new FXMLLoader(
            getClass().getResource("/fxml/" + fxml + ".fxml")
        );
        return loader.load();
    }
    
    public void launchGame() {
        try {
            if (gameApp != null) {
                gameApp.stopGameThread();
            }
            gameApp = new GameApp();
            
            Scene gameScene = new Scene(gameApp, SCREEN_WIDTH, SCREEN_HEIGHT);
            primaryStage.setScene(gameScene);
            
            gameApp.setupGame();
            gameApp.startGameThread();
            gameApp.requestFocus();
            
        } catch (Exception e) {
            System.err.println("Failed to launch game: " + e.getMessage());
            e.printStackTrace();
            switchToScene("MainMenu");
        }
    }
    
    public void returnToMenu() {
        if (gameApp != null) {
            gameApp.savePlayerPositionOnExit();
            Facade.getInstance().saveUserProgress();
            gameApp.stopGameThread();
            gameApp = null;
        }
        
        switchToScene("MainMenu");
    }

    public void onAppClose() {
    if (gameApp != null) {
        System.out.println("Saving progress before exit...");
        gameApp.savePlayerPositionOnExit();
        Facade.getInstance().saveUserProgress();
        gameApp.stopGameThread();
        gameApp = null;
    }
}
    
    public void showCertificate() {
        if (gameApp != null) {
            gameApp.stopGameThread();
            gameApp = null;
        }
        
        switchToScene("CertificateScreen");
    }
    
    
    public String getCurrentUsername() {
        return currentUsername;
    }
    
    public void setCurrentUsername(String username) {
        this.currentUsername = username;
    }
    
    public String getSelectedDifficulty() {
        return selectedDifficulty;
    }
    
    public void setSelectedDifficulty(String difficulty) {
        this.selectedDifficulty = difficulty;
    }
    
    public int getDifficultyTimeLimit() {
        return difficultyTimeLimit;
    }
    
    public void setDifficultyTimeLimit(int minutes) {
        this.difficultyTimeLimit = minutes;
    }
    
    public Stage getPrimaryStage() {
        return primaryStage;
    }
    
    public GameApp getGameApp() {
        return gameApp;
    }
    
    public void clearCache() {
        sceneCache.clear();
    }
}