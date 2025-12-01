package com.escape.Model;

public class GameConfig {
    // Display settings
    private int originalTileSize;
    private int scale;
    private int tileSize;
    private int screenCols;
    private int screenRows;
    private int screenWidth;
    private int screenHeight;
    
    // World settings
    private int worldCols;
    private int worldRows;
    private int worldWidth;
    private int worldHeight;
    
    // Gameplay settings
    private int fps;
    private int playState;
    private int pauseState;
    private int dialogueState;
    private int inventoryState;
    
    public GameConfig(int originalTileSize, int scale, int tileSize, 
                     int screenCols, int screenRows, int screenWidth, 
                     int screenHeight, int worldCols, int worldRows, 
                     int worldWidth, int worldHeight, int fps, 
                     int playState, int pauseState, int dialogueState, int inventoryState) {
        this.originalTileSize = originalTileSize;
        this.scale = scale;
        this.tileSize = tileSize;
        this.screenCols = screenCols;
        this.screenRows = screenRows;
        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;
        this.worldCols = worldCols;
        this.worldRows = worldRows;
        this.worldWidth = worldWidth;
        this.worldHeight = worldHeight;
        this.fps = fps;
        this.playState = playState;
        this.pauseState = pauseState;
        this.dialogueState = dialogueState;
        this.inventoryState = inventoryState;
    }
    
    // Display getters
    public int getOriginalTileSize() { return originalTileSize; }
    public int getScale() { return scale; }
    public int getTileSize() { return tileSize; }
    public int getScreenCols() { return screenCols; }
    public int getScreenRows() { return screenRows; }
    public int getScreenWidth() { return screenWidth; }
    public int getScreenHeight() { return screenHeight; }
    
    // World getters
    public int getWorldCols() { return worldCols; }
    public int getWorldRows() { return worldRows; }
    public int getWorldWidth() { return worldWidth; }
    public int getWorldHeight() { return worldHeight; }
    
    // Gameplay getters
    public int getFps() { return fps; }
    public int getPlayState() { return playState; }
    public int getPauseState() { return pauseState; }
    public int getDialogueState() { return dialogueState; }
    public int getInventoryState() { return inventoryState; }
    
    @Override
    public String toString() {
        return String.format(
            "GameConfig[TileSize=%d, Screen=%dx%d, World=%dx%d, FPS=%d]",
            tileSize, screenWidth, screenHeight, worldWidth, worldHeight, fps
        );
    }
}