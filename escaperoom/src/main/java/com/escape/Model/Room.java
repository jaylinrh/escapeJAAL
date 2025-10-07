package com.escape.Model;

import java.util.ArrayList;

public class Room {
    private String roomId;
    private String name;
    private String description;
    private String mapFile;
    private String music;
    private Puzzle puzzle;
    private Dialogue dialogue;
    private ArrayList<String> availableItemIds;
    
    public Room(String roomId, String name, String description, String mapFile, 
                String music, Puzzle puzzle, Dialogue dialogue, ArrayList<String> availableItemIds) {
        this.roomId = roomId;
        this.name = name;
        this.description = description;
        this.mapFile = mapFile;
        this.music = music;
        this.puzzle = puzzle;
        this.dialogue = dialogue;
        this.availableItemIds = availableItemIds;
    }
    
    public String getRoomId() {
        return roomId;
    }
    
    public String getName() {
        return name;
    }
    
    public String getDescription() {
        return description;
    }
    
    public String getMapFile() {
        return mapFile;
    }
    
    public String getMusic() {
        return music;
    }
    
    public Puzzle getPuzzle() {
        return puzzle;
    }
    
    public Dialogue getDialogue() {
        return dialogue;
    }
    
    public ArrayList<String> getAvailableItemIds() {
        return availableItemIds;
    }
    

    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    
    public void setMapFile(String mapFile) {
        this.mapFile = mapFile;
    }
    
    public void setMusic(String music) {
        this.music = music;
    }
    
    public void setPuzzle(Puzzle puzzle) {
        this.puzzle = puzzle;
    }
    
    public void setDialogue(Dialogue dialogue) {
        this.dialogue = dialogue;
    }
    
    public void setAvailableItemIds(ArrayList<String> availableItemIds) {
        this.availableItemIds = availableItemIds;
    }
    
    public boolean hasItem(String itemId) {
        return availableItemIds.contains(itemId);
    }
    
    public void addItem(String itemId) {
        if (!availableItemIds.contains(itemId)) {
            availableItemIds.add(itemId);
        }
    }
    
    public void removeItem(String itemId) {
        availableItemIds.remove(itemId);
    }
    
    public int getItemCount() {
        return availableItemIds.size();
    }
    
    public boolean hasPuzzle() {
        return puzzle != null;
    }
    
    public boolean hasDialogue() {
        return dialogue != null;
    }
    
    public boolean isPuzzleSolved() {
        return puzzle != null && puzzle.isSolved();
    }
    
    @Override
    public String toString() {
        return String.format(
            "Room: %s (ID: %s)\nDescription: %s\nMap: %s\nMusic: %s\nItems: %d\nPuzzle: %s\nDialogue: %s",
            name, roomId, description, mapFile, music, 
            availableItemIds.size(),
            (puzzle != null ? puzzle.getTitle() : "None"),
            (dialogue != null ? dialogue.getDialogueId() : "None")
        );
    }
}