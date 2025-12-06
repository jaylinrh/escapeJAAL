package com.escape.Controller;

import com.escape.Model.Facade;
import com.escape.Model.SceneManager;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.util.ResourceBundle;

public class SettingsScreenController implements Initializable {

    @FXML private Button backButton;
    @FXML private Slider volumeSlider;
    @FXML private Slider sfxSlider;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Facade facade = Facade.getInstance();
        
        volumeSlider.setValue(facade.getVolume());
        sfxSlider.setValue(facade.getSfx());
        
        volumeSlider.valueProperty().addListener((obs, oldVal, newVal) -> {
            facade.setVolume(newVal.doubleValue());
            applyVolume(newVal.doubleValue());
        });
        
        sfxSlider.valueProperty().addListener((obs, oldVal, newVal) -> {
            facade.setSfx(newVal.doubleValue());
            applySFX(newVal.doubleValue());
        });
    }

    @FXML
    private void onBackClicked() {
        Facade.getInstance().saveUserProgress();
        
        String username = SceneManager.getInstance().getCurrentUsername();
        if (username != null && !username.isEmpty()) {
            SceneManager.getInstance().switchToScene("MainMenu");
        } else {
            SceneManager.getInstance().switchToScene("TitleScreen");
        }
    }

    private void applyVolume(double volume) {
        SceneManager.getInstance().setMusicVolume(volume);
    }

    private void applySFX(double sfx) {
        // TODO implement SFX control when audio is added
        System.out.println("SFX set to: " + sfx);
    }

    public static double getVolume() {
        return Facade.getInstance().getVolume();
    }

    public static double getSFX() {
        return Facade.getInstance().getSfx();
    }
}