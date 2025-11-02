package com.escape.Model;

import org.junit.Test;

import com.escape.Model.Puzzle;
import com.escape.Model.Room;

import org.junit.Before;
import org.junit.After;
import static org.junit.Assert.*;

import java.util.ArrayList;

public class RoomTest {
    private Room roomWithPuzzle;
    private Room roomWithoutPuzzle;
    private Room roomFromFullConstructor;
    
    @Before
    public void setUp() {
        roomWithPuzzle = new Room(
            "room-001",
            "Library",
            "A dusty old library",
            "library_map.txt",
            "library_music.mp3",
            new String[]{"Welcome", "Look around"},
            true
        );
       
        roomWithoutPuzzle = new Room(
            "room-002",
            "Hallway",
            "A long dark hallway",
            "hallway_map.txt",
            "hallway_music.mp3",
            new String[]{"It's dark here"},
            false
        );

        ArrayList<String> dialogues = new ArrayList<>();
        dialogues.add("Dialogue 1");
        dialogues.add("Dialogue 2");

        ArrayList<String> items = new ArrayList<>();
        items.add("key-001");
        items.add("book-001");

        roomFromFullConstructor = new Room(
            "room-003",
            "Study",
            "A cozy study room",
            "study_map.txt",
            "study_music.mp3",
            "puzzle-003",
            "Code Breaker",
            "Crack the code",
            5,
            false,
            "dialogue-003",
            "study_dialogue.txt",
            dialogues,
            items
        );
    }

    @After
    public void tearDown() {
        roomWithPuzzle = null;
        roomWithoutPuzzle = null;
        roomFromFullConstructor = null;
    }

    @Test
    public void testSimpleConstructor_WithPuzzle_CreatesPuzzleObject() {
        assertNotNull("Room with puzzle should have a puzzle object", roomWithPuzzle.getPuzzle());
        assertTrue("Room should report having a puzzle", roomWithPuzzle.hasPuzzle());
    }

     @Test
    public void testSimpleConstructor_WithoutPuzzle_DoesNotCreatePuzzle() {
        assertNull("Room without puzzle should have null puzzle", roomWithoutPuzzle.getPuzzle());
        assertFalse("Room should report not having a puzzle", roomWithoutPuzzle.hasPuzzle());
}
@Test
    public void testFullConstructor_InitializesAllFields() {
        assertEquals("Room ID should match", "room-003", roomFromFullConstructor.getRoomId());
        assertEquals("Name should match", "Study", roomFromFullConstructor.getName());
        assertEquals("Description should match", "A cozy study room", roomFromFullConstructor.getDescription());
        assertEquals("Map file should match", "study_map.txt", roomFromFullConstructor.getMapFile());
        assertEquals("Music should match", "study_music.mp3", roomFromFullConstructor.getMusic());
    }
    
    @Test
    public void testFullConstructor_CreatesPuzzleWithCorrectProperties() {
        Puzzle puzzle = roomFromFullConstructor.getPuzzle();
        assertNotNull("Room should have a puzzle", puzzle);
        assertEquals("Puzzle ID should match", "puzzle-003", puzzle.getPuzzleId());
        assertEquals("Puzzle title should match", "Code Breaker", puzzle.getTitle());
        assertEquals("Puzzle description should match", "Crack the code", puzzle.getDescription());
        assertEquals("Puzzle level should match", 5, puzzle.getLevel());
        assertFalse("Puzzle should not be solved initially", puzzle.isSolved());
    }
    
    @Test
    public void testFullConstructor_InitializesItemList() {
        assertEquals("Room should have 2 items", 2, roomFromFullConstructor.getItemCount());
        assertTrue("Room should have key-001", roomFromFullConstructor.hasItem("key-001"));
        assertTrue("Room should have book-001", roomFromFullConstructor.hasItem("book-001"));
    }

    @Test
    public void testHasItem_WithExistingItem_ReturnsTrue() {
        assertTrue("Should return true for existing item", roomFromFullConstructor.hasItem("key-001"));
    }
    
    @Test
    public void testHasItem_WithNonExistentItem_ReturnsFalse() {
        assertFalse("Should return false for non-existent item", roomFromFullConstructor.hasItem("sword-999"));
    }
    
    @Test(expected = NullPointerException.class)
    public void testHasItem_WithNullItemId_ThrowsException() {
        roomFromFullConstructor.hasItem(null);
    }
    
    //previosly tested for nullPointerException on empty list behavior.
    // now tests to see the list is properly initialized.
    @Test
public void testHasItem_OnRoomWithEmptyItemList_ReturnsFalse() {
    assertFalse("Should return false when item list is empty", 
        roomWithPuzzle.hasItem("any-item"));
}

    @Test
    public void testAddItem_NewItem_AddsSuccessfully() {
        int initialCount = roomFromFullConstructor.getItemCount();
        roomFromFullConstructor.addItem("potion-001");
        
        assertEquals("Item count should increase by 1", initialCount + 1, roomFromFullConstructor.getItemCount());
        assertTrue("Room should now contain the new item", roomFromFullConstructor.hasItem("potion-001"));
    }
    
    @Test
    public void testAddItem_DuplicateItem_DoesNotAddAgain() {
        roomFromFullConstructor.addItem("key-001"); 
        
        assertEquals("Item count should remain 2", 2, roomFromFullConstructor.getItemCount());
        
        
        int keyCount = 0;
        for (String itemId : roomFromFullConstructor.getAvailableItemIds()) {
            if (itemId.equals("key-001")) {
                keyCount++;
            }
        }
        assertEquals("Should only have one instance of key-001", 1, keyCount);
    }
    
    @Test
    public void testAddItem_MultipleUniqueItems_AddsAll() {
        roomFromFullConstructor.addItem("sword-001");
        roomFromFullConstructor.addItem("shield-001");
        roomFromFullConstructor.addItem("potion-001");
        
        assertEquals("Room should have 5 items total", 5, roomFromFullConstructor.getItemCount());
        assertTrue("Should have sword", roomFromFullConstructor.hasItem("sword-001"));
        assertTrue("Should have shield", roomFromFullConstructor.hasItem("shield-001"));
        assertTrue("Should have potion", roomFromFullConstructor.hasItem("potion-001"));
    }
    
    //previously tested for nullPointerException on empty list behavior.
    //now tests if items can be added to initialized empty list
    @Test
public void testAddItem_OnRoomWithEmptyItemList_AddsSuccessfully() {
    roomWithPuzzle.addItem("item-001");
    assertTrue("Should add item to initially empty list", 
        roomWithPuzzle.hasItem("item-001"));
    assertEquals("Should have 1 item", 1, roomWithPuzzle.getItemCount());
}

 @Test
    public void testRemoveItem_ExistingItem_RemovesSuccessfully() {
        assertTrue("Item should exist before removal", roomFromFullConstructor.hasItem("key-001"));
        
        roomFromFullConstructor.removeItem("key-001");
        
        assertFalse("Item should no longer exist", roomFromFullConstructor.hasItem("key-001"));
        assertEquals("Item count should decrease by 1", 1, roomFromFullConstructor.getItemCount());
    }
    
    @Test
    public void testRemoveItem_NonExistentItem_DoesNothing() {
        int initialCount = roomFromFullConstructor.getItemCount();
        
        roomFromFullConstructor.removeItem("non-existent-item");
        
        assertEquals("Item count should remain unchanged", initialCount, roomFromFullConstructor.getItemCount());
    }
    
    @Test
    public void testRemoveItem_AllItems_LeavesEmptyList() {
        roomFromFullConstructor.removeItem("key-001");
        roomFromFullConstructor.removeItem("book-001");
        
        assertEquals("Room should have no items", 0, roomFromFullConstructor.getItemCount());
        assertFalse("Should not have key-001", roomFromFullConstructor.hasItem("key-001"));
        assertFalse("Should not have book-001", roomFromFullConstructor.hasItem("book-001"));
    }
    
    @Test
public void testRemoveItem_OnRoomWithEmptyItemList_DoesNothing() {
    roomWithPuzzle.removeItem("non-existent-item");
    assertEquals("Should still have 0 items", 0, roomWithPuzzle.getItemCount());
    }
    
    // getItemCount() Tests
    
    @Test
    public void testGetItemCount_WithMultipleItems_ReturnsCorrectCount() {
        assertEquals("Should return correct item count", 2, roomFromFullConstructor.getItemCount());
    }
    
    @Test
    public void testGetItemCount_AfterAddingItems_ReturnsUpdatedCount() {
        roomFromFullConstructor.addItem("new-item");
        assertEquals("Count should reflect added item", 3, roomFromFullConstructor.getItemCount());
    }
    
    @Test
    public void testGetItemCount_AfterRemovingItems_ReturnsUpdatedCount() {
        roomFromFullConstructor.removeItem("key-001");
        assertEquals("Count should reflect removed item", 1, roomFromFullConstructor.getItemCount());
    }
    
    @Test
public void testGetItemCount_OnRoomWithEmptyItemList_ReturnsZero() {
    assertEquals("Empty room should have 0 items", 0, roomWithPuzzle.getItemCount());
}
    
    // hasPuzzle() Tests
    
    @Test
    public void testHasPuzzle_WithPuzzle_ReturnsTrue() {
        assertTrue("Room with puzzle should return true", roomWithPuzzle.hasPuzzle());
    }
    
    @Test
    public void testHasPuzzle_WithoutPuzzle_ReturnsFalse() {
        assertFalse("Room without puzzle should return false", roomWithoutPuzzle.hasPuzzle());
    }
    
    @Test
    public void testHasPuzzle_AfterSettingPuzzleToNull_ReturnsFalse() {
        roomWithPuzzle.setPuzzle(null);
        assertFalse("Room should return false after puzzle set to null", roomWithPuzzle.hasPuzzle());
    }
    
    @Test
    public void testHasPuzzle_AfterSettingPuzzle_ReturnsTrue() {
        Puzzle newPuzzle = new Puzzle("p1", "New Puzzle", "Desc", 1, false);
        roomWithoutPuzzle.setPuzzle(newPuzzle);
        assertTrue("Room should return true after setting puzzle", roomWithoutPuzzle.hasPuzzle());
    }
    
    // isPuzzleSolved() Tests
    
    @Test
    public void testIsPuzzleSolved_WithUnsolvedPuzzle_ReturnsFalse() {
        assertFalse("Unsolved puzzle should return false", roomFromFullConstructor.isPuzzleSolved());
    }
    
    @Test
    public void testIsPuzzleSolved_WithSolvedPuzzle_ReturnsTrue() {
        roomFromFullConstructor.getPuzzle().setSolved(true);
        assertTrue("Solved puzzle should return true", roomFromFullConstructor.isPuzzleSolved());
    }
    
    @Test
    public void testIsPuzzleSolved_WithNoPuzzle_ReturnsFalse() {
        assertFalse("Room without puzzle should return false", roomWithoutPuzzle.isPuzzleSolved());
    }
    
    @Test
    public void testIsPuzzleSolved_AfterPuzzleSetToNull_ReturnsFalse() {
        roomWithPuzzle.setPuzzle(null);
        assertFalse("Room with null puzzle should return false", roomWithPuzzle.isPuzzleSolved());
    }
    
    // toString() Tests
    
    @Test
    public void testToString_ContainsRoomName() {
        String result = roomFromFullConstructor.toString();
        assertTrue("toString should contain room name", result.contains("Study"));
    }
    
    @Test
    public void testToString_ContainsRoomId() {
        String result = roomFromFullConstructor.toString();
        assertTrue("toString should contain room ID", result.contains("room-003"));
    }
    
    @Test
    public void testToString_WithPuzzle_ContainsPuzzleTitle() {
        String result = roomFromFullConstructor.toString();
        assertTrue("toString should contain puzzle title", result.contains("Code Breaker"));
    }
    
    @Test
    public void testToString_WithoutPuzzle_ContainsNone() {
        String result = roomWithoutPuzzle.toString();
        assertTrue("toString should contain 'None' for puzzle", result.contains("None"));
    }
    
    @Test
    public void testToString_ContainsItemCount() {
        String result = roomFromFullConstructor.toString();
        assertTrue("toString should contain item count", result.contains("Items: 2"));
    }
    
    @Test
public void testToString_OnRoomWithEmptyItemList_ContainsZeroItems() {
    String result = roomWithPuzzle.toString();
    assertTrue("toString should indicate 0 items", result.contains("Items: 0"));
}
}