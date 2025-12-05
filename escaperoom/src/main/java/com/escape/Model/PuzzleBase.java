package com.escape.Model;

import com.escape.Model.Facade;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;


public interface PuzzleBase {
    void initialize();
    
    void update();
    
    void draw(GraphicsContext gc);
    
    void handleKeyPress(KeyEvent e);
    
    /**
     * Handle mouse click
     */
    void handleMouseClick(MouseEvent e);
    
    boolean isSolved();
    
    String getPuzzleId();
    
    void reset();
    
    void onComplete(Facade facade);
    
    default void onRoomLoaded() {}
}