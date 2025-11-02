package com.escape.Model;

import java.util.ArrayList;

public class Room {
    private String roomId;
    private String name;
    private String description;
    private String mapFile;
    private String music;
    private Puzzle puzzle;
    private String puzzleId;
    private String puzzleTitle;
    private String puzzleDescription;
    private int puzzleLevel;
    private boolean isSolved;
    private String dialogueId;
    private String dialogueFile;

    private ArrayList<String> dialogues;
    private boolean hasPuzzle;
    private ArrayList<String> availableItemIds;


    public Room(String roomId, String name, String description, String mapFile, 
                String music, String[] dialogues, boolean hasPuzzle) {
        this.roomId = roomId;
        this.name = name;
        this.description = description;
        this.mapFile = mapFile;
        this.music = music;
        this.hasPuzzle = hasPuzzle;
        
        //Bug fix: initialized availableItemIds to prevent a NullPointerExceptionn,
        //when calling hasItem, addItem...ect.
        this.availableItemIds = new ArrayList<>();
        

         if (this.hasPuzzle) {
            this.puzzle = new Puzzle("a", "puzzle", "a puzzle", 3, false);
        } else {
            this.puzzle = null;
        }
    }


    public Room(String roomdId, String name, String description, String mapFile, String music,
                String puzzleId, String puzzleTitle, String puzzleDescription, int puzzleLevel,
                boolean isSolved, String dialogueId, String dialogueFile, ArrayList<String> dialogues,
                ArrayList<String> availableItems) {
           this.roomId = roomdId;
           this.name = name;
           this.description = description;
           this.mapFile = mapFile;
           this.music = music;
           this.puzzleId = puzzleId;
           this.puzzleTitle = puzzleTitle;
           this.puzzleDescription = puzzleDescription;
           this.puzzleLevel = puzzleLevel;
           this.isSolved = isSolved;
           this.dialogueId = dialogueId;
           this.dialogueFile = dialogueFile;
           this.dialogues = dialogues;
           this.availableItemIds = availableItems;
           this.hasPuzzle = true;

           this.puzzle = new Puzzle(puzzleId, puzzleTitle, puzzleDescription, puzzleLevel, isSolved);
        
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
    
 
    
    public ArrayList<String> getAvailableItemIds() {
        return availableItemIds;
    }

    public String getPuzzleId() {
        return puzzleId;
    }

    public String getPuzzleTitle() {
        return puzzleTitle;
    }

    public int getPuzzleLevel() {
        return puzzleLevel;
    }

    public String getPuzzleDescription() {
        return puzzleDescription;
    }

    public boolean getIsSolved() {
        return isSolved;
    }

    public String getDialogueFile() {
        return dialogueFile;
    }

    public ArrayList<String> getDialogues() {
        return dialogues;
    }
    
    public String getDialogueId() {
        return dialogueId;
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
    

    
    public boolean isPuzzleSolved() {
        return puzzle != null && puzzle.isSolved();
    }
    
    @Override
    public String toString() {
        return String.format(
            "Room: %s (ID: %s)\nDescription: %s\nMap: %s\nMusic: %s\nItems: %d\nPuzzle: %s\nDialogue: %s",
            name, roomId, description, mapFile, music, 
            availableItemIds.size(),
            (puzzle != null ? puzzle.getTitle() : "None")
        );
    }
}