package com.escape.Model;

import com.escape.Model.GameApp;
import com.escape.Model.Room;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;

import java.util.HashMap;
import java.util.Map;

/**
 * Manages all puzzles in the game
 * Handles puzzle creation, activation, and lifecycle
 */
public class PuzzleManager {
    private GameApp gameApp;
    private Map<String, PuzzleBase> puzzles;
    private PuzzleBase currentPuzzle;
    
    // Game state for puzzles
    public static final int PUZZLE_STATE = 5;
    
    public PuzzleManager(GameApp gameApp) {
        this.gameApp = gameApp;
        this.puzzles = new HashMap<>();
        this.currentPuzzle = null;
        
        initializePuzzles();
    }
    
    /**
     * Create all puzzle instances
     */
    private void initializePuzzles() {
        puzzles.put("room_foyer", new FoyerPuzzle(gameApp));
        puzzles.put("room_parlor", new ParlorPuzzle(gameApp));
        puzzles.put("room_library", new LibraryPuzzle(gameApp));
        puzzles.put("room_kitchen", new KitchenPuzzle(gameApp));
        puzzles.put("room_greenhouse", new GreenhousePuzzle(gameApp));
        puzzles.put("room_cellar", new CellarPuzzle(gameApp));
    }
    
    /**
     * Activate puzzle for current room
     */
    public void activatePuzzle(String roomId) {
        if (puzzles.containsKey(roomId)) {
            currentPuzzle = puzzles.get(roomId);
            
            // Only show puzzle if not already solved
            if (!currentPuzzle.isSolved()) {
                gameApp.gameState = PUZZLE_STATE;
            }
        }
    }
    
    /**
     * Activate puzzle by puzzle ID (for testing)
     */
    public void activatePuzzleById(String puzzleId) {
        for (PuzzleBase puzzle : puzzles.values()) {
            if (puzzle.getPuzzleId().equals(puzzleId)) {
                currentPuzzle = puzzle;
                gameApp.gameState = PUZZLE_STATE;
                return;
            }
        }
    }
    
    /**
     * Check if room has a puzzle
     */
    public boolean roomHasPuzzle(String roomId) {
        return puzzles.containsKey(roomId);
    }
    
    /**
     * Check if puzzle is solved
     */
    public boolean isPuzzleSolved(String roomId) {
        if (puzzles.containsKey(roomId)) {
            return puzzles.get(roomId).isSolved();
        }
        return false;
    }
    
    /**
     * Get current active puzzle
     */
    public PuzzleBase getCurrentPuzzle() {
        return currentPuzzle;
    }
    
    /**
     * Update current puzzle
     */
    public void update() {
        if (currentPuzzle != null && gameApp.gameState == PUZZLE_STATE) {
            currentPuzzle.update();
        }
    }
    
    /**
     * Draw current puzzle
     */
    public void draw(GraphicsContext gc) {
        if (currentPuzzle != null && gameApp.gameState == PUZZLE_STATE) {
            currentPuzzle.draw(gc);
        }
    }
    
    /**
     * Handle key press for current puzzle
     */
    public void handleKeyPress(KeyEvent e) {
        if (currentPuzzle != null && gameApp.gameState == PUZZLE_STATE) {
            currentPuzzle.handleKeyPress(e);
        }
    }
    
    /**
     * Handle mouse click for current puzzle
     */
    public void handleMouseClick(MouseEvent e) {
        if (currentPuzzle != null && gameApp.gameState == PUZZLE_STATE) {
            currentPuzzle.handleMouseClick(e);
        }
    }
    
    /**
     * Exit current puzzle and return to play state
     */
    public void exitPuzzle() {
        if (currentPuzzle != null) {
            gameApp.gameState = gameApp.playState;
            currentPuzzle = null;
        }
    }
    
    /**
     * Reset a specific puzzle
     */
    public void resetPuzzle(String roomId) {
        if (puzzles.containsKey(roomId)) {
            puzzles.get(roomId).reset();
        }
    }
    
    /**
     * Reset all puzzles
     */
    public void resetAllPuzzles() {
        for (PuzzleBase puzzle : puzzles.values()) {
            puzzle.reset();
        }
    }
    
    /**
     * Notify puzzle when item is collected
     * Used for puzzles that require item collection
     */
    public void notifyItemCollected(String itemId, String roomId) {
        if (!puzzles.containsKey(roomId)) return;
        
        PuzzleBase puzzle = puzzles.get(roomId);
        
        if (puzzle instanceof LibraryPuzzle) {
            LibraryPuzzle libraryPuzzle = (LibraryPuzzle) puzzle;
            // Handle cipher items
            if (itemId.startsWith("cipher_")) {
                String type = itemId.substring(7); 
                libraryPuzzle.collectItem(type);
            }
        } 
    }
    
    /**
     * Get puzzle progress statistics
     */
    public String getPuzzleStats() {
        int total = puzzles.size();
        int solved = 0;
        
        for (PuzzleBase puzzle : puzzles.values()) {
            if (puzzle.isSolved()) {
                solved++;
            }
        }
        
        return String.format("Puzzles Solved: %d/%d", solved, total);
    }
    
    /**
     * Check if all puzzles are solved
     */
    public boolean allPuzzlesSolved() {
        for (PuzzleBase puzzle : puzzles.values()) {
            if (!puzzle.isSolved()) {
                return false;
            }
        }
        return true;
    }
}