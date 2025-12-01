package com.escape.Model;

import com.escape.Model.Facade;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;

/**
 * Base interface for all puzzles in the game
 */
public interface PuzzleBase {
    /**
     * Initialize the puzzle with starting state
     */
    void initialize();
    
    /**
     * Update puzzle logic
     */
    void update();
    
    /**
     * Draw the puzzle UI
     */
    void draw(GraphicsContext gc);
    
    /**
     * Handle key input
     */
    void handleKeyPress(KeyEvent e);
    
    /**
     * Handle mouse click
     */
    void handleMouseClick(MouseEvent e);
    
    /**
     * Check if puzzle is solved
     */
    boolean isSolved();
    
    /**
     * Get puzzle ID
     */
    String getPuzzleId();
    
    /**
     * Reset puzzle to initial state
     */
    void reset();
    
    /**
     * Called when puzzle is completed
     */
    void onComplete(Facade facade);
}