package com.escape.Model;

public class Puzzle {
    private String puzzleId;
    private String title;
    private String description;
    private int level;
    private boolean isSolved;
    
    public Puzzle(String puzzleId, String title, String description, int level, boolean isSolved) {
        this.puzzleId = puzzleId;
        this.title = title;
        this.description = description;
        this.level = level;
        this.isSolved = isSolved;
        
        System.out.println("Puzzle generated");
    }
    
    
    public String getPuzzleId() {
        return puzzleId;
    }
    
    public String getTitle() {
        return title;
    }
    
    public String getDescription() {
        return description;
    }
    
    public int getLevel() {
        return level;
    }
    
    public boolean isSolved() {
        return isSolved;
    }
    
    public void setPuzzleId(String puzzleId) {
        this.puzzleId = puzzleId;
    }
    
    public void setTitle(String title) {
        this.title = title;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    
    public void setLevel(int level) {
        this.level = level;
    }
    
    public void setSolved(boolean solved) {
        this.isSolved = solved;
    }
    
    @Override
    public String toString() {
        return String.format(
            "{\"puzzleId\":\"%s\",\"title\":\"%s\",\"description\":\"%s\",\"level\":%d,\"isSolved\":%b}",
            puzzleId, title, description, level, isSolved
        );
    }
}