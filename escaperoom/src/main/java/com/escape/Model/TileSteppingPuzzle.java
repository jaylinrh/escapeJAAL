package com.escape.Model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class TileSteppingPuzzle {
    GameApp ga = GameApp.getInstance();

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
    
    public void spawnBook(String itemId, int col, int row) {
        Item itemData = Items.getInstance().getItemById(itemId);
        if (itemData == null) return;
        
        InteractableObject bookObj = new InteractableObject(
            itemId, 
            itemData.getName(), 
            itemData.getDescription(), 
            col * ga.tileSize, 
            row * ga.tileSize, 
            "/items/" + itemId + ".png", 
            InteractableObject.ObjectType.ITEM
        );
        bookObj.setItem(itemData);
        ga.gameObjects.add(bookObj);
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
                   
                    if (ga != null) {
                        UI ui = ga.ui; 
                        List<String> list = new ArrayList<>(); 
                        list.add("this haunted house was originally the mansion of the Aurele family before they abandoned it in 1891.");
                        list.add("I came in just to take a look, but I'm getting out immediately after.");

                        ui.setCutsceneImageFromPath("/com/escape/images/bkg.png");
                        ui.dialogues = list.toArray(new String[0]);
                        ga.gameState = ga.cutsceneState;
                        System.out.println("Cutscene");
                        ui.currentDialogueIndex = 0;
                        ui.currentText = ui.dialogues[0];

            
                        
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