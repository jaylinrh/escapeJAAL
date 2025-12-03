package com.escape.Model;

import com.escape.Model.GameApp;
import com.escape.Model.Room;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;

import java.util.HashMap;
import java.util.Map;

public class PuzzleManager {
    private GameApp gameApp;
    public Map<String, PuzzleBase> puzzles;
    private PuzzleBase currentPuzzle;
    
    public static final int PUZZLE_STATE = 5;
    
    public PuzzleManager(GameApp gameApp) {
        this.gameApp = gameApp;
        this.puzzles = new HashMap<>();
        this.currentPuzzle = null;
        
        initializePuzzles();
    }
    

    private void initializePuzzles() {
        puzzles.put("room_foyer", new FoyerPuzzle(gameApp));
    }
    
    public void activatePuzzle(String roomId) {
        if (puzzles.containsKey(roomId)) {
            currentPuzzle = puzzles.get(roomId);
            
            // only show puzzle if not already solved
            if (!currentPuzzle.isSolved()) {
                gameApp.gameState = PUZZLE_STATE;
            }
        }
    }
    
    public void activatePuzzleById(String puzzleId) {
        for (PuzzleBase puzzle : puzzles.values()) {
            if (puzzle.getPuzzleId().equals(puzzleId)) {
                currentPuzzle = puzzle;
                gameApp.gameState = PUZZLE_STATE;
                return;
            }
        }
    }
    
    public boolean roomHasPuzzle(String roomId) {
        return puzzles.containsKey(roomId);
    }
    
    public boolean isPuzzleSolved(String roomId) {
        if (puzzles.containsKey(roomId)) {
            return puzzles.get(roomId).isSolved();
        }
        return false;
    }
    
    public PuzzleBase getCurrentPuzzle() {
        return currentPuzzle;
    }
    
    public void update() {

         
        if (currentPuzzle != null && gameApp.gameState == PUZZLE_STATE) {
            currentPuzzle.update();
        }

        if (gameApp.gameState == gameApp.playState && currentPuzzle != null) {
       currentPuzzle.update();
        }
    }
    
    public void draw(GraphicsContext gc) {
        if (currentPuzzle != null && gameApp.gameState == PUZZLE_STATE) {
            currentPuzzle.draw(gc);
        }

        if (gameApp.gameState == gameApp.playState && currentPuzzle != null) {
            currentPuzzle.draw(gc);
        }
      }   
    
    public void handleKeyPress(KeyEvent e) {
        if (currentPuzzle != null && gameApp.gameState == PUZZLE_STATE) {
            currentPuzzle.handleKeyPress(e);
        }
    }
    
    public void handleMouseClick(MouseEvent e) {
        if (currentPuzzle != null && gameApp.gameState == PUZZLE_STATE) {
            currentPuzzle.handleMouseClick(e);
        }
    }
    
    public void exitPuzzle() {
        if (currentPuzzle != null) {
            gameApp.gameState = gameApp.playState;
            currentPuzzle = null;
        }
    }
    
    public void resetPuzzle(String roomId) {
        if (puzzles.containsKey(roomId)) {
            puzzles.get(roomId).reset();
        }
    }
    
    public void resetAllPuzzles() {
        for (PuzzleBase puzzle : puzzles.values()) {
            puzzle.reset();
        }
    }
    
    //NOT SURE IF WE NEED THIS YET. JUST leave commented. May be deleted later.
    /**
     * Notify puzzle when item is collected
     * Used for puzzles that require item collection
     */
    // public void notifyItemCollected(String itemId, String roomId) {
    //     if (!puzzles.containsKey(roomId)) return;
        
    //     PuzzleBase puzzle = puzzles.get(roomId);
        
    //     if (puzzle instanceof LibraryPuzzle) {
    //         LibraryPuzzle libraryPuzzle = (LibraryPuzzle) puzzle;
    //         // Handle cipher items
    //         if (itemId.startsWith("cipher_")) {
    //             String type = itemId.substring(7); 
    //             libraryPuzzle.collectItem(type);
    //         }
    //     } 
    // }
    
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
    
    public boolean allPuzzlesSolved() {
        for (PuzzleBase puzzle : puzzles.values()) {
            if (!puzzle.isSolved()) {
                return false;
            }
        }
        return true;
    }
}