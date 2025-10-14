package com.escape.Model;

public class GameConfig {
    // Display settings
    private int originalTileSize;
    private int scale;
    private int tileSize;
    private int maxScreenCol;
    private int maxScreenRow;
    private int screenWidth;
    private int screenHeight;
    
    // World settings
    private int maxWorldCol;
    private int maxWorldRow;
    private int worldWidth;
    private int worldHeight;
    
    // Gameplay settings
    private int fps;
    private int playState;
    private int pauseState;
    private int dialogueState;
    private int inventoryState;
    
    public GameConfig(int originalTileSize, int scale, int tileSize, 
                     int maxScreenCol, int maxScreenRow, int screenWidth, 
                     int screenHeight, int maxWorldCol, int maxWorldRow, 
                     int worldWidth, int worldHeight, int fps, 
                     int playState, int pauseState, int dialogueState, int inventoryState) {
        this.originalTileSize = originalTileSize;
        this.scale = scale;
        this.tileSize = tileSize;
        this.maxScreenCol = maxScreenCol;
        this.maxScreenRow = maxScreenRow;
        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;
        this.maxWorldCol = maxWorldCol;
        this.maxWorldRow = maxWorldRow;
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
    public int getMaxScreenCol() { return maxScreenCol; }
    public int getMaxScreenRow() { return maxScreenRow; }
    public int getScreenWidth() { return screenWidth; }
    public int getScreenHeight() { return screenHeight; }
    
    // World getters
    public int getMaxWorldCol() { return maxWorldCol; }
    public int getMaxWorldRow() { return maxWorldRow; }
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