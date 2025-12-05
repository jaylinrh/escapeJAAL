package com.escape.Model;

import java.util.HashSet;
import java.util.UUID;


public class GameSave {
    private UUID saveId;
    private String saveName;
    private long createdAt;
    private long lastPlayedAt;
    private String currentRoomId;
    private PlayerState playerState;
    private Inventory inventory;
    private HashSet<String> visitedRooms;
    private HashSet<String> completedRooms;
    private HashSet<String> solvedPuzzles;
    private String difficulty;
    private long playTimeSeconds;
    
    // constructor
    public GameSave(String saveName, String difficulty) {
        this.saveId = UUID.randomUUID();
        this.saveName = saveName;
        this.difficulty = difficulty;
        this.createdAt = System.currentTimeMillis();
        this.lastPlayedAt = System.currentTimeMillis();
        this.currentRoomId = "room_exterior";
        this.playerState = createDefaultPlayerState();
        this.inventory = new Inventory(UUID.randomUUID().toString(), 10);
        this.visitedRooms = new HashSet<>();
        this.completedRooms = new HashSet<>();
        this.solvedPuzzles = new HashSet<>();
        this.playTimeSeconds = 0;
    }
    
    public GameSave(UUID saveId, String saveName, String difficulty, long createdAt, 
                    long lastPlayedAt, String currentRoomId, PlayerState playerState,
                    Inventory inventory, HashSet<String> visitedRooms, 
                    HashSet<String> completedRooms, HashSet<String> solvedPuzzles,
                    long playTimeSeconds) {
        this.saveId = saveId;
        this.saveName = saveName;
        this.difficulty = difficulty;
        this.createdAt = createdAt;
        this.lastPlayedAt = lastPlayedAt;
        this.currentRoomId = currentRoomId;
        this.playerState = playerState;
        this.inventory = inventory;
        this.visitedRooms = visitedRooms != null ? visitedRooms : new HashSet<>();
        this.completedRooms = completedRooms != null ? completedRooms : new HashSet<>();
        this.solvedPuzzles = solvedPuzzles != null ? solvedPuzzles : new HashSet<>();
        this.playTimeSeconds = playTimeSeconds;
    }
    
    private PlayerState createDefaultPlayerState() {
        int defaultX = 48 * 24;
        int defaultY = 48 * 45;
        SolidArea solidArea = new SolidArea(8, 16, 32, 32);
        SpriteImages sprites = new SpriteImages(
            "/images/player.png", "/images/player.png",
            "/images/player.png", "/images/player.png",
            "/images/player.png", "/images/player.png",
            "/images/player.png", "/images/player.png"
        );
        return new PlayerState(defaultX, defaultY, 4, "down", solidArea, false, sprites);
    }
    
    // getters and setters
    public UUID getSaveId() { return saveId; }
    public String getSaveName() { return saveName; }
    public void setSaveName(String saveName) { this.saveName = saveName; }
    
    public String getDifficulty() { return difficulty; }
    public void setDifficulty(String difficulty) { this.difficulty = difficulty; }
    
    public long getCreatedAt() { return createdAt; }
    public long getLastPlayedAt() { return lastPlayedAt; }
    public void setLastPlayedAt(long lastPlayedAt) { this.lastPlayedAt = lastPlayedAt; }
    
    public String getCurrentRoomId() { return currentRoomId; }
    public void setCurrentRoomId(String currentRoomId) { this.currentRoomId = currentRoomId; }
    
    public PlayerState getPlayerState() { return playerState; }
    public void setPlayerState(PlayerState playerState) { this.playerState = playerState; }
    
    public Inventory getInventory() { return inventory; }
    public void setInventory(Inventory inventory) { this.inventory = inventory; }
    
    public HashSet<String> getVisitedRooms() { return visitedRooms; }
    public void setVisitedRooms(HashSet<String> visitedRooms) { this.visitedRooms = visitedRooms; }
    
    public HashSet<String> getCompletedRooms() { return completedRooms; }
    public void setCompletedRooms(HashSet<String> completedRooms) { this.completedRooms = completedRooms; }
    
    public HashSet<String> getSolvedPuzzles() { return solvedPuzzles; }
    public void setSolvedPuzzles(HashSet<String> solvedPuzzles) { this.solvedPuzzles = solvedPuzzles; }
    
    public long getPlayTimeSeconds() { return playTimeSeconds; }
    public void setPlayTimeSeconds(long playTimeSeconds) { this.playTimeSeconds = playTimeSeconds; }
    
    public void addPlayTime(long seconds) { this.playTimeSeconds += seconds; }
    
    public String getFormattedPlayTime() {
        long hours = playTimeSeconds / 3600;
        long minutes = (playTimeSeconds % 3600) / 60;
        long seconds = playTimeSeconds % 60;
        
        if (hours > 0) {
            return String.format("%d:%02d:%02d", hours, minutes, seconds);
        }
        return String.format("%02d:%02d", minutes, seconds);
    }
    
    public String getCurrentRoomDisplayName() {
        switch (currentRoomId) {
            case "room_foyer": return "Foyer";
            case "room_parlor": return "Parlor";
            case "room_library": return "Library";
            case "room_kitchen": return "Kitchen";
            case "room_greenhouse": return "Greenhouse";
            case "room_cellar": return "Cellar";
            default: return currentRoomId;
        }
    }
}
