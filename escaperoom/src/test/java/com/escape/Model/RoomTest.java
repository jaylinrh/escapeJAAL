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
}