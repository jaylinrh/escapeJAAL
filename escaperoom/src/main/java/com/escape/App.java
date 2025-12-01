package com.escape;

import java.io.IOException;

import com.escape.Model.GameApp;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * JavaFX App
 */
public class App extends Application {

    private static Scene scene;

    @Override
    public void start(Stage stage) throws IOException {
        System.out.println("/images/player.png -> " + GameApp.class.getResource("/images/player.png"));
        GameApp game = new GameApp();        
        Scene scene = new Scene(game, 
                            GameApp.getGameConfig().getScreenWidth(),
                            GameApp.getGameConfig().getScreenHeight());


        ///scene = new Scene(loadFXML("primary"), 1280, 720);
        stage.setScene(scene);
        stage.show();

        
        game.setupGame();
        game.startGameThread();
    }


    static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    public static void main(String[] args) {
        launch();
    }

}