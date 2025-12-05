package com.escape.Model;

import java.util.ArrayList;
import java.util.HashSet;

public class Progression {
    private User player;
    
    private HashSet<String> visitedRooms;
    private HashSet<String> completedRooms;
    private HashSet<String> solvedPuzzles;
    private HashSet<String> collectedItems;
    private int levelsCompleted;
    
    private int totalRooms;
    private int totalPuzzles;
    private int totalItems;
    private long startTime;
    private long totalPlayTime;
    
    public Progression(User player) {
        this.player = player;
        this.visitedRooms = new HashSet<>(player.getVisitedRooms());
        this.completedRooms = new HashSet<>(player.getCompletedRooms());
        this.solvedPuzzles = new HashSet<>(player.getSolvedPuzzles());
        this.collectedItems = new HashSet<>();
        this.levelsCompleted = player.getLevel();
        
        this.totalRooms = 6;
        this.totalPuzzles = 6;
        this.totalItems = 27;
        
        this.startTime = System.currentTimeMillis();
        this.totalPlayTime = 0;
    }
    
    public void syncToUser() {
        player.setVisitedRooms(new HashSet<>(visitedRooms));
        player.setCompletedRooms(new HashSet<>(completedRooms));
        player.setSolvedPuzzles(new HashSet<>(solvedPuzzles));
    }
    
    public void visitRoom(String roomId) {
        visitedRooms.add(roomId);
    }
    
    
    public void completeRoom(String roomId) {
        visitedRooms.add(roomId);
        completedRooms.add(roomId);
    }
    
    
    public boolean hasVisitedRoom(String roomId) {
        return visitedRooms.contains(roomId);
    }
    
    
    public boolean hasCompletedRoom(String roomId) {
        return completedRooms.contains(roomId);
    }
    
    
    public int getRoomsVisited() {
        return visitedRooms.size();
    }
    
    
    public int getRoomsCompleted() {
        return completedRooms.size();
    }
    
    public void solvePuzzle(String puzzleId) {
        solvedPuzzles.add(puzzleId);
    }
    
    public boolean hasSolvedPuzzle(String puzzleId) {
        return solvedPuzzles.contains(puzzleId);
    }
    
    public int getPuzzlesSolved() {
        return solvedPuzzles.size();
    }
    
    public void collectItem(String itemId) {
        collectedItems.add(itemId);
    }
    
    public boolean hasCollectedItem(String itemId) {
        return collectedItems.contains(itemId);
    }
    
    public int getItemsCollected() {
        return collectedItems.size();
    }
    
    public void levelUp() {
        levelsCompleted++;
    }
    
    public int getCurrentLevel() {
        return levelsCompleted;
    }
    
    public double getCompletionPercentage() {
        double roomProgress = (double) completedRooms.size() / totalRooms * 40;
        double puzzleProgress = (double) solvedPuzzles.size() / totalPuzzles * 40;
        double itemProgress = (double) collectedItems.size() / totalItems * 20;
        
        return roomProgress + puzzleProgress + itemProgress;
    }
    
    public double getRoomCompletionPercentage() {
        return (double) completedRooms.size() / totalRooms * 100;
    }
    
    public double getPuzzleCompletionPercentage() {
        return (double) solvedPuzzles.size() / totalPuzzles * 100;
    }
    
    public double getItemCollectionPercentage() {
        return (double) collectedItems.size() / totalItems * 100;
    }
    
    public boolean isGameComplete() {
        return completedRooms.size() >= totalRooms && 
               solvedPuzzles.size() >= totalPuzzles;
    }
    
    
    public void updatePlayTime() {
        long currentTime = System.currentTimeMillis();
        totalPlayTime += (currentTime - startTime);
        startTime = currentTime;
    }
    
    public long getTotalPlayTime() {
        updatePlayTime();
        return totalPlayTime;
    }
    
    public long getPlayTimeMinutes() {
        return getTotalPlayTime() / 60000;
    }
    
    public String getFormattedPlayTime() {
        long totalSeconds = getTotalPlayTime() / 1000;
        long hours = totalSeconds / 3600;
        long minutes = (totalSeconds % 3600) / 60;
        long seconds = totalSeconds % 60;
        
        return String.format("%02d:%02d:%02d", hours, minutes, seconds);
    }
    
    
    public ArrayList<String> getVisitedRoomsList() {
        return new ArrayList<>(visitedRooms);
    }
    
    public ArrayList<String> getCompletedRoomsList() {
        return new ArrayList<>(completedRooms);
    }
    
    public ArrayList<String> getSolvedPuzzlesList() {
        return new ArrayList<>(solvedPuzzles);
    }
    
    public ArrayList<String> getCollectedItemsList() {
        return new ArrayList<>(collectedItems);
    }
    
    public String getProgressReport() {
        StringBuilder report = new StringBuilder();
        report.append("=== PROGRESS REPORT ===\n");
        report.append(String.format("Player: %s\n", player.getUserName()));
        report.append(String.format("Level: %d\n", levelsCompleted));
        report.append(String.format("Overall Completion: %.1f%%\n\n", getCompletionPercentage()));
        
        report.append(String.format("Rooms Visited: %d/%d (%.1f%%)\n", 
            visitedRooms.size(), totalRooms, getRoomCompletionPercentage()));
        report.append(String.format("Rooms Completed: %d/%d\n", 
            completedRooms.size(), totalRooms));
        report.append(String.format("Puzzles Solved: %d/%d (%.1f%%)\n", 
            solvedPuzzles.size(), totalPuzzles, getPuzzleCompletionPercentage()));
        report.append(String.format("Items Collected: %d/%d (%.1f%%)\n\n", 
            collectedItems.size(), totalItems, getItemCollectionPercentage()));
        
        report.append(String.format("Play Time: %s\n", getFormattedPlayTime()));
        report.append(String.format("Game Complete: %s\n", isGameComplete() ? "Yes" : "No"));
        
        return report.toString();
    }
    
    @Override
    public String toString() {
        return String.format(
            "Progression[Rooms: %d/%d, Puzzles: %d/%d, Items: %d/%d, Completion: %.1f%%]",
            completedRooms.size(), totalRooms,
            solvedPuzzles.size(), totalPuzzles,
            collectedItems.size(), totalItems,
            getCompletionPercentage()
        );
    }
}