package com.escape;

import com.escape.Model.SceneManager;
import javafx.application.Application;
import javafx.stage.Stage;

/**
 * JavaFX App
 */
public class App extends Application {


    @Override
    public void start(Stage primaryStage) {
        SceneManager sceneManager = SceneManager.getInstance();
        sceneManager.initialize(primaryStage);
        sceneManager.switchToScene("TitleScreen");

         primaryStage.setOnCloseRequest(event -> {
            System.out.println("Window closing - saving progress...");
            sceneManager.onAppClose();
        });
    }

    public static void returnToMenu() {
        SceneManager.getInstance().returnToMenu();
    }

    public static void showCertificate() {
        SceneManager.getInstance().showCertificate();
    }

    public static SceneManager getSceneManager() {
        return SceneManager.getInstance();
    }

    public static void main(String[] args) {
        launch();
    }

}