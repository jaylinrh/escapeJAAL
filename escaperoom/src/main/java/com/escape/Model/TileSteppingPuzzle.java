package com.escape.Model;

import java.util.HashSet;
import java.util.Set;

public class TileSteppingPuzzle {
    
    private Set<TilePosition> requiredTiles;
    private Set<TilePosition> activatedTiles;
    private int tileSize;
    private boolean puzzleComplete;
    
    
    public TileSteppingPuzzle(int tileSize) {
        this.tileSize = tileSize;
        this.requiredTiles = new HashSet<>();
        this.activatedTiles = new HashSet<>();
        this.puzzleComplete = false;
    }
    
    
     //Add a specific tile coordinate that the player must step on.
     
    public void addTargetTile(int col, int row) {
        requiredTiles.add(new TilePosition(col, row));
    }
    
    public boolean update(int playerWorldX, int playerWorldY) {
        //use center of player
        int centerX = playerWorldX + (tileSize / 2);
        int centerY = playerWorldY + (tileSize / 2);

        // Convert pixels to grid coordinates
        int gridX = centerX / tileSize;
        int gridY = centerY / tileSize;
        
        TilePosition currentPos = new TilePosition(gridX, gridY);
        
        // If the player steps on a required tile they haven't triggered yet
        if (requiredTiles.contains(currentPos) && !activatedTiles.contains(currentPos)) {
            activatedTiles.add(currentPos);
            System.out.println("Activated Tile at: " + gridX + ", " + gridY);
            
            if(gridX == 24 && gridY == 24) {
                    GameApp ga = GameApp.getInstance(); // ONLY IF you use a singleton for GameApp
                    if (ga != null) {
                        UI ui = ga.ui; 
                        ui.setCutsceneImageFromPath("/com/escape/images/bkg.png");
                        ga.gameState = ga.cutsceneState;
                        System.out.println("Cutscene");
                        
                    }
    
            }

            if (activatedTiles.size() == requiredTiles.size()) {
                puzzleComplete = true;
            }
            
            return true; 
        }
        return false;
    }

    public boolean isPuzzleComplete() { return puzzleComplete; }
    public boolean isTileActivated(int col, int row) { return activatedTiles.contains(new TilePosition(col, row)); }
    public boolean isRequiredTile(int col, int row) { return requiredTiles.contains(new TilePosition(col, row)); }
    public int getActivatedCount() { return activatedTiles.size(); }
    public int getRequiredCount() { return requiredTiles.size(); }

    public void reset() {
        activatedTiles.clear();
        puzzleComplete = false;
    }

    // Helper class for coordinates
    private static class TilePosition {
        final int x, y;
        TilePosition(int x, int y) { this.x = x; this.y = y; }
        
        @Override
        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (!(obj instanceof TilePosition)) return false;
            TilePosition other = (TilePosition) obj;
            return x == other.x && y == other.y;
        }
        
        @Override
        public int hashCode() { return 31 * x + y; }
    }
}