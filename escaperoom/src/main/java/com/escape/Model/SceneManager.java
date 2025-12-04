package com.escape.Model;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Singleton class that manages scene transitions throughout the game.
 * Handles loading FXML files, caching scenes, and switching between screens.
 */
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
    
    /**
     * Gets the singleton instance of SceneManager.
     */
    public static SceneManager getInstance() {
        if (instance == null) {
            instance = new SceneManager();
        }
        return instance;
    }
    
    /**
     * Initializes the SceneManager with the primary stage.
     * Call this once from App.start()
     */
    public void initialize(Stage stage) {
        this.primaryStage = stage;
        primaryStage.setTitle("HollowMore Manor");
        primaryStage.setResizable(false);
    }
    
    /**
     * Switches to a new scene by name.
     * @param sceneName Name of the scene (matches FXML filename without extension)
     */
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
    
    /**
     * Loads an FXML file by name.
     * @param fxml The FXML filename without extension
     * @return The loaded Parent node
     */
    private Parent loadFXML(String fxml) throws IOException {
        // For menu screens, don't cache to ensure fresh data each time
        FXMLLoader loader = new FXMLLoader(
            getClass().getResource("/fxml/" + fxml + ".fxml")
        );
        return loader.load();
    }
    
    /**
     * Launches the actual game (GameApp).
     */
    public void launchGame() {
        try {
            // Stop any existing game
            if (gameApp != null) {
                gameApp.stopGameThread();
            }
            
            // Create new game instance
            gameApp = new GameApp();
            
            // Create game scene
            Scene gameScene = new Scene(gameApp, SCREEN_WIDTH, SCREEN_HEIGHT);
            primaryStage.setScene(gameScene);
            
            // Setup and start the game
            gameApp.setupGame();
            gameApp.startGameThread();
            gameApp.requestFocus();
            
        } catch (Exception e) {
            System.err.println("Failed to launch game: " + e.getMessage());
            e.printStackTrace();
            // Fall back to main menu on error
            switchToScene("MainMenu");
        }
    }
    
    /**
     * Returns to the main menu from the game.
     */
    public void returnToMenu() {
        if (gameApp != null) {
            // Save progress before exiting
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
    
    /**
     * Shows the certificate screen after game completion.
     */
    public void showCertificate() {
        if (gameApp != null) {
            gameApp.stopGameThread();
            gameApp = null;
        }
        
        switchToScene("CertificateScreen");
    }
    
    // ==================== Session Data Getters/Setters ====================
    
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
    
    /**
     * Clears all cached scenes.
     * Useful when data has changed and screens need to be refreshed.
     */
    public void clearCache() {
        sceneCache.clear();
    }
}