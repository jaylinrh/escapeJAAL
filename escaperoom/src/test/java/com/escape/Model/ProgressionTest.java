package com.escape.Model;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.UUID;

public class ProgressionTest {
    private Progression progression;
    private User testUser;

    @Before
    public void setUp() {
        SolidArea solidArea = new SolidArea(0, 0, 32, 32);
        SpriteImages sprites = new SpriteImages(
            "/images/player.png", "/images/player.png",
            "/images/player.png", "/images/player.png",
            "/images/player.png", "/images/player.png",
            "/images/player.png", "/images/player.png"
        );
        PlayerState playerState = new PlayerState(100, 100, 4, "down", solidArea, false, sprites);
        Inventory inventory = new Inventory(UUID.randomUUID().toString(), 10);
        
        testUser = new User(
            UUID.randomUUID(),
            "progressionTestUser",
            "password",
            1,
            "room_foyer",
            playerState,
            inventory
        );
        
        progression = new Progression(testUser);
    }
    
    //Room Visit test
    @Test
    public void testVisitRoom_FirstTime() {
        String roomId = "room_foyer";
        
        progression.visitRoom(roomId);
        
        assertTrue("Room should be marked as visited", progression.hasVisitedRoom(roomId));
        assertEquals("Visited rooms count should be 1", 1, progression.getRoomsVisited());
    }
    
    @Test
    public void testVisitRoom_MultipleTimes() {
        String roomId = "room_foyer";
        
        progression.visitRoom(roomId);
        progression.visitRoom(roomId);
        progression.visitRoom(roomId);
        
        assertEquals("Visited rooms count should still be 1", 1, progression.getRoomsVisited());
    }
    
    @Test
    public void testVisitRoom_MultipleRooms() {
        progression.visitRoom("room1");
        progression.visitRoom("room2");
        progression.visitRoom("room3");
        
        assertEquals("Visited rooms count should be 3", 3, progression.getRoomsVisited());
        assertTrue("Room1 should be visited", progression.hasVisitedRoom("room1"));
        assertTrue("Room2 should be visited", progression.hasVisitedRoom("room2"));
        assertTrue("Room3 should be visited", progression.hasVisitedRoom("room3"));
    }
    
    @Test
    public void testHasVisitedRoom_False() {
        assertFalse("Unvisited room should return false", 
            progression.hasVisitedRoom("unvisited_room"));
    }

    //Room completion test
    @Test
    public void testCompleteRoom_FirstTime() {
        String roomId = "room_parlor";
        
        progression.completeRoom(roomId);
        
        assertTrue("Room should be marked as completed", progression.hasCompletedRoom(roomId));
        assertTrue("Completed room should also be visited", progression.hasVisitedRoom(roomId));
        assertEquals("Completed rooms count should be 1", 1, progression.getRoomsCompleted());
    }
    
    @Test
    public void testCompleteRoom_MultipleTimes() {
        String roomId = "room_parlor";
        
        progression.completeRoom(roomId);
        progression.completeRoom(roomId);
        
        assertEquals("Completed rooms count should still be 1", 1, progression.getRoomsCompleted());
    }
    
    @Test
    public void testCompleteRoom_MultipleRooms() {
        progression.completeRoom("room1");
        progression.completeRoom("room2");
        progression.completeRoom("room3");
        
        assertEquals("Completed rooms count should be 3", 3, progression.getRoomsCompleted());
    }
    
    @Test
    public void testHasCompletedRoom_False() {
        assertFalse("Uncompleted room should return false", 
            progression.hasCompletedRoom("incomplete_room"));
    }
    
    @Test
    public void testGetRoomsVisited_InitiallyZero() {
        assertEquals("Initially visited rooms should be 0", 0, progression.getRoomsVisited());
    }
    
    @Test
    public void testGetRoomsCompleted_InitiallyZero() {
        assertEquals("Initially completed rooms should be 0", 0, progression.getRoomsCompleted());
    }
    
    // ========== Puzzle Tests ==========
    
    @Test
    public void testSolvePuzzle_FirstTime() {
        String puzzleId = "puzzle1";
        
        progression.solvePuzzle(puzzleId);
        
        assertTrue("Puzzle should be marked as solved", progression.hasSolvedPuzzle(puzzleId));
        assertEquals("Solved puzzles count should be 1", 1, progression.getPuzzlesSolved());
    }
    
    @Test
    public void testSolvePuzzle_MultipleTimes() {
        String puzzleId = "puzzle1";
        
        progression.solvePuzzle(puzzleId);
        progression.solvePuzzle(puzzleId);
        
        assertEquals("Solved puzzles count should still be 1", 1, progression.getPuzzlesSolved());
    }
    
    @Test
    public void testSolvePuzzle_MultiplePuzzles() {
        progression.solvePuzzle("puzzle1");
        progression.solvePuzzle("puzzle2");
        progression.solvePuzzle("puzzle3");
        
        assertEquals("Solved puzzles count should be 3", 3, progression.getPuzzlesSolved());
        assertTrue("Puzzle1 should be solved", progression.hasSolvedPuzzle("puzzle1"));
        assertTrue("Puzzle2 should be solved", progression.hasSolvedPuzzle("puzzle2"));
        assertTrue("Puzzle3 should be solved", progression.hasSolvedPuzzle("puzzle3"));
    }
    
    @Test
    public void testHasSolvedPuzzle_False() {
        assertFalse("Unsolved puzzle should return false", 
            progression.hasSolvedPuzzle("unsolved_puzzle"));
    }
    
    @Test
    public void testGetPuzzlesSolved_InitiallyZero() {
        assertEquals("Initially solved puzzles should be 0", 0, progression.getPuzzlesSolved());
    }

    //Item collection
    @Test
    public void testCollectItem_FirstTime() {
        String itemId = "item1";
        
        progression.collectItem(itemId);
        
        assertTrue("Item should be marked as collected", progression.hasCollectedItem(itemId));
        assertEquals("Collected items count should be 1", 1, progression.getItemsCollected());
    }
    
    @Test
    public void testCollectItem_MultipleTimes() {
        String itemId = "item1";
        
        progression.collectItem(itemId);
        progression.collectItem(itemId);
        
        assertEquals("Collected items count should still be 1", 1, progression.getItemsCollected());
    }
    
    @Test
    public void testCollectItem_MultipleItems() {
        progression.collectItem("item1");
        progression.collectItem("item2");
        progression.collectItem("item3");
        progression.collectItem("item4");
        progression.collectItem("item5");
        
        assertEquals("Collected items count should be 5", 5, progression.getItemsCollected());
        assertTrue("Item1 should be collected", progression.hasCollectedItem("item1"));
        assertTrue("Item5 should be collected", progression.hasCollectedItem("item5"));
    }
    
    @Test
    public void testHasCollectedItem_False() {
        assertFalse("Uncollected item should return false", 
            progression.hasCollectedItem("uncollected_item"));
    }
    
    @Test
    public void testGetItemsCollected_InitiallyZero() {
        assertEquals("Initially collected items should be 0", 0, progression.getItemsCollected());
    }

    //Level test
    @Test
    public void testGetCurrentLevel_Initial() {
        int level = progression.getCurrentLevel();
        assertEquals("Initial level should match user level", testUser.getLevel(), level);
    }
    
    @Test
    public void testLevelUp_Once() {
        int initialLevel = progression.getCurrentLevel();
        
        progression.levelUp();
        
        assertEquals("Level should increase by 1", initialLevel + 1, progression.getCurrentLevel());
    }
    
    @Test
    public void testLevelUp_Multiple() {
        int initialLevel = progression.getCurrentLevel();
        
        progression.levelUp();
        progression.levelUp();
        progression.levelUp();
        
        assertEquals("Level should increase by 3", initialLevel + 3, progression.getCurrentLevel());
    }

    // Completion percentage test
    @Test
    public void testGetCompletionPercentage_InitiallyZero() {
        double completion = progression.getCompletionPercentage();
        assertEquals("Initial completion should be 0", 0.0, completion, 0.01);
    }
    
    @Test
    public void testGetCompletionPercentage_WithProgress() {
        progression.completeRoom("room1");
        
        double completion = progression.getCompletionPercentage();
        assertTrue("Completion should be greater than 0", completion > 0);
        assertTrue("Completion should be less than 100", completion < 100);
    }
    
    @Test
    public void testGetRoomCompletionPercentage_Zero() {
        double completion = progression.getRoomCompletionPercentage();
        assertEquals("Initial room completion should be 0", 0.0, completion, 0.01);
    }
    
    @Test
    public void testGetRoomCompletionPercentage_Fifty() {
        progression.completeRoom("room1");
        progression.completeRoom("room2");
        progression.completeRoom("room3");
        
        double completion = progression.getRoomCompletionPercentage();
        assertEquals("Room completion should be 50%", 50.0, completion, 0.01);
    }
    
    @Test
    public void testGetPuzzleCompletionPercentage_Zero() {
        double completion = progression.getPuzzleCompletionPercentage();
        assertEquals("Initial puzzle completion should be 0", 0.0, completion, 0.01);
    }
    
    @Test
    public void testGetItemCollectionPercentage_Zero() {
        double completion = progression.getItemCollectionPercentage();
        assertEquals("Initial item collection should be 0", 0.0, completion, 0.01);
    }

    //Game completion test
    @Test
    public void testIsGameComplete_False_NoProgress() {
        assertFalse("Game should not be complete initially", progression.isGameComplete());
    }
    
    @Test
    public void testIsGameComplete_False_PartialProgress() {
        progression.completeRoom("room1");
        progression.solvePuzzle("puzzle1");
        
        assertFalse("Game should not be complete with partial progress", 
            progression.isGameComplete());
    }
    
    @Test
    public void testIsGameComplete_True() {
        for (int i = 1; i <= 6; i++) {
            progression.completeRoom("room" + i);
            progression.solvePuzzle("puzzle" + i);
        }
        
        assertTrue("Game should be complete", progression.isGameComplete());
    }

    //Play time test
    @Test
    public void testGetTotalPlayTime_NonNegative() {
        long playTime = progression.getTotalPlayTime();
        assertTrue("Play time should be non-negative", playTime >= 0);
    }
    
    @Test
    public void testGetPlayTimeMinutes_NonNegative() {
        long minutes = progression.getPlayTimeMinutes();
        assertTrue("Play time minutes should be non-negative", minutes >= 0);
    }
    
    @Test
    public void testGetFormattedPlayTime_Format() {
        String formatted = progression.getFormattedPlayTime();
        assertNotNull("Formatted play time should not be null", formatted);
        assertTrue("Formatted play time should contain colons", formatted.contains(":"));
    }
    
    @Test
    public void testUpdatePlayTime() throws InterruptedException {
        long initialTime = progression.getTotalPlayTime();
        
        Thread.sleep(100);
        
        progression.updatePlayTime();
        long newTime = progression.getTotalPlayTime();
        
        assertTrue("Play time should increase", newTime > initialTime);
    }

    //List getter test
    @Test
    public void testGetVisitedRoomsList_InitiallyEmpty() {
        ArrayList<String> visited = progression.getVisitedRoomsList();
        assertNotNull("Visited rooms list should not be null", visited);
        assertTrue("Visited rooms list should be empty initially", visited.isEmpty());
    }
    
    @Test
    public void testGetVisitedRoomsList_WithRooms() {
        progression.visitRoom("room1");
        progression.visitRoom("room2");
        
        ArrayList<String> visited = progression.getVisitedRoomsList();
        assertEquals("Visited rooms list size should be 2", 2, visited.size());
        assertTrue("List should contain room1", visited.contains("room1"));
        assertTrue("List should contain room2", visited.contains("room2"));
    }
    
    @Test
    public void testGetCompletedRoomsList_InitiallyEmpty() {
        ArrayList<String> completed = progression.getCompletedRoomsList();
        assertNotNull("Completed rooms list should not be null", completed);
        assertTrue("Completed rooms list should be empty initially", completed.isEmpty());
    }
    
    @Test
    public void testGetCompletedRoomsList_WithRooms() {
        progression.completeRoom("room1");
        progression.completeRoom("room2");
        
        ArrayList<String> completed = progression.getCompletedRoomsList();
        assertEquals("Completed rooms list size should be 2", 2, completed.size());
    }
    
    @Test
    public void testGetSolvedPuzzlesList_InitiallyEmpty() {
        ArrayList<String> solved = progression.getSolvedPuzzlesList();
        assertNotNull("Solved puzzles list should not be null", solved);
        assertTrue("Solved puzzles list should be empty initially", solved.isEmpty());
    }
    
    @Test
    public void testGetSolvedPuzzlesList_WithPuzzles() {
        progression.solvePuzzle("puzzle1");
        progression.solvePuzzle("puzzle2");
        
        ArrayList<String> solved = progression.getSolvedPuzzlesList();
        assertEquals("Solved puzzles list size should be 2", 2, solved.size());
    }
    
    @Test
    public void testGetCollectedItemsList_InitiallyEmpty() {
        ArrayList<String> collected = progression.getCollectedItemsList();
        assertNotNull("Collected items list should not be null", collected);
        assertTrue("Collected items list should be empty initially", collected.isEmpty());
    }
    
    @Test
    public void testGetCollectedItemsList_WithItems() {
        progression.collectItem("item1");
        progression.collectItem("item2");
        progression.collectItem("item3");
        
        ArrayList<String> collected = progression.getCollectedItemsList();
        assertEquals("Collected items list size should be 3", 3, collected.size());
    }
    
    // Progress report test
    @Test
    public void testGetProgressReport_NotNull() {
        String report = progression.getProgressReport();
        assertNotNull("Progress report should not be null", report);
    }
    
    @Test
    public void testGetProgressReport_ContainsExpectedInfo() {
        progression.completeRoom("room1");
        progression.solvePuzzle("puzzle1");
        progression.collectItem("item1");
        
        String report = progression.getProgressReport();
        assertTrue("Report should contain player name", 
            report.contains(testUser.getUserName()));
        assertTrue("Report should contain level info", report.contains("Level"));
        assertTrue("Report should contain rooms info", report.contains("Rooms"));
        assertTrue("Report should contain puzzles info", report.contains("Puzzles"));
        assertTrue("Report should contain items info", report.contains("Items"));
    }
    
    @Test
    public void testToString_Format() {
        String str = progression.toString();
        assertNotNull("toString should not return null", str);
        assertTrue("toString should contain 'Progression'", str.contains("Progression"));
        assertTrue("toString should contain 'Rooms'", str.contains("Rooms"));
    }
}
