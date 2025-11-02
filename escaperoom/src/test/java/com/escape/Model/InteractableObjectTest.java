package com.escape.Model;

import org.junit.Test;
import org.junit.Before;
import org.junit.After;
import static org.junit.Assert.*;

import com.escape.Model.InteractableObject.ObjectType;

public class InteractableObjectTest {
    private InteractableObject itemObject;
    private InteractableObject clueObject;
    private InteractableObject doorObject;
    private Item testItem;
    
    @Before
    public void setUp() {
        
        itemObject = new InteractableObject(
            "obj-001",
            "Golden Key",
            "A shiny golden key",
            100,
            150,
            null, 
            ObjectType.ITEM
        );
        
        clueObject = new InteractableObject(
            "obj-002",
            "Ancient Note",
            "A cryptic message written in old ink",
            200,
            250,
            null,
            ObjectType.CLUE
        );
        
        doorObject = new InteractableObject(
            "obj-003",
            "Wooden Door",
            "A sturdy wooden door",
            300,
            300,
            null,
            ObjectType.DOOR
        );
        
        // Create test item
        testItem = new Item("item-001", "Test Item", "Test Hint", "Test Description");
    }
    
    @After
    public void tearDown() {
        itemObject = null;
        clueObject = null;
        doorObject = null;
        testItem = null;
    }
    
    // Constructor Tests
    
    @Test
    public void testConstructor_InitializesAllFields() {
        assertEquals("Object ID should match", "obj-001", itemObject.getObjectId());
        assertEquals("Name should match", "Golden Key", itemObject.getName());
        assertEquals("Description should match", "A shiny golden key", itemObject.getDescription());
        assertEquals("World X should match", 100, itemObject.worldX);
        assertEquals("World Y should match", 150, itemObject.worldY);
        assertEquals("Type should match", ObjectType.ITEM, itemObject.getType());
    }
    
    @Test
    public void testConstructor_InitializesCollectedToFalse() {
        assertFalse("Object should not be collected initially", itemObject.isCollected());
    }
    
    @Test
    public void testConstructor_InitializesInteractableToTrue() {
        assertTrue("Object should be interactable initially", itemObject.isInteractable());
    }
    
    @Test
    public void testConstructor_CreatesEachObjectType() {
        assertEquals("Should create ITEM type", ObjectType.ITEM, itemObject.getType());
        assertEquals("Should create CLUE type", ObjectType.CLUE, clueObject.getType());
        assertEquals("Should create DOOR type", ObjectType.DOOR, doorObject.getType());
        
        InteractableObject puzzleObject = new InteractableObject(
            "obj-004", "Puzzle", "A puzzle piece", 0, 0, null, ObjectType.PUZZLE_PIECE
        );
        assertEquals("Should create PUZZLE_PIECE type", ObjectType.PUZZLE_PIECE, puzzleObject.getType());
        
        InteractableObject interactiveObject = new InteractableObject(
            "obj-005", "Lever", "A lever", 0, 0, null, ObjectType.INTERACTIVE
        );
        assertEquals("Should create INTERACTIVE type", ObjectType.INTERACTIVE, interactiveObject.getType());
    }
    
    // isPlayerNearby() Tests 
    
    @Test
    public void testIsPlayerNearby_PlayerAtExactLocation_ReturnsTrue() {
        assertTrue("Player at exact location should be nearby", 
            itemObject.isPlayerNearby(100, 150, 50));
    }
    
    @Test
    public void testIsPlayerNearby_PlayerWithinRange_ReturnsTrue() {
        
        assertTrue("Player within range should be nearby", 
            itemObject.isPlayerNearby(120, 160, 50));
    }
    
    @Test
    public void testIsPlayerNearby_PlayerAtExactRangeBoundary_ReturnsTrue() {
        
        assertTrue("Player at exact boundary should be nearby", 
            itemObject.isPlayerNearby(100, 200, 50));  
    }
    
    @Test
    public void testIsPlayerNearby_PlayerJustOutsideRange_ReturnsFalse() {
        
        assertFalse("Player just outside range should not be nearby", 
            itemObject.isPlayerNearby(100, 201, 50));
    }
    
    @Test
    public void testIsPlayerNearby_PlayerFarAway_ReturnsFalse() {
        assertFalse("Player far away should not be nearby", 
            itemObject.isPlayerNearby(500, 500, 50));
    }
    
    @Test
    public void testIsPlayerNearby_WithZeroRange_OnlyTrueAtExactLocation() {
        assertTrue("Zero range should work at exact location", 
            itemObject.isPlayerNearby(100, 150, 0));
        assertFalse("Zero range should not work for any offset", 
            itemObject.isPlayerNearby(101, 150, 0));
    }
    
    @Test
    public void testIsPlayerNearby_WithLargeRange_CoversBigArea() {
        assertTrue("Large range should cover distant player", 
            itemObject.isPlayerNearby(200, 250, 200));
    }
    
    @Test
    public void testIsPlayerNearby_CollectedObject_ReturnsFalse() {
        itemObject.setCollected(true);
        assertFalse("Collected object should not be nearby even if player is close", 
            itemObject.isPlayerNearby(100, 150, 50));
    }
    
    @Test
    public void testIsPlayerNearby_NonInteractableObject_ReturnsFalse() {
        itemObject.setInteractable(false);
        assertFalse("Non-interactable object should not be nearby even if player is close", 
            itemObject.isPlayerNearby(100, 150, 50));
    }
    
    @Test
    public void testIsPlayerNearby_CollectedAndNonInteractable_ReturnsFalse() {
        itemObject.setCollected(true);
        itemObject.setInteractable(false);
        assertFalse("Collected and non-interactable object should not be nearby", 
            itemObject.isPlayerNearby(100, 150, 50));
    }
    
    @Test
    public void testIsPlayerNearby_ManhattanDistanceCalculation() {
        
        assertTrue("Should use Manhattan distance (25 <= 50)", 
            itemObject.isPlayerNearby(110, 165, 50));
        
        
        assertFalse("Should use Manhattan distance (60 > 50)", 
            itemObject.isPlayerNearby(130, 180, 50));
    }
    
    @Test
    public void testIsPlayerNearby_NegativeCoordinates_WorksCorrectly() {
        InteractableObject negativeObject = new InteractableObject(
            "obj-neg", "Negative", "Desc", -50, -50, null, ObjectType.ITEM
        );
        
        assertTrue("Should work with negative coordinates", 
            negativeObject.isPlayerNearby(-45, -45, 20));
        assertFalse("Should calculate distance correctly with negatives", 
            negativeObject.isPlayerNearby(0, 0, 50));
    }
    
    @Test
    public void testIsPlayerNearby_DiagonalMovement() {
        
        assertTrue("Diagonal movement should count as Manhattan distance", 
            itemObject.isPlayerNearby(110, 160, 25));
    }
    
    // isCollected() and setCollected() Tests
    
    @Test
    public void testSetCollected_ChangesCollectedState() {
        assertFalse("Should start not collected", itemObject.isCollected());
        
        itemObject.setCollected(true);
        assertTrue("Should be collected after setting", itemObject.isCollected());
        
        itemObject.setCollected(false);
        assertFalse("Should be not collected after setting back", itemObject.isCollected());
    }
    
    @Test
    public void testIsCollected_ReflectsCurrentState() {
        itemObject.setCollected(true);
        assertTrue("isCollected should return current state", itemObject.isCollected());
    }
    
    // isInteractable() and setInteractable() Tests
    
    @Test
    public void testSetInteractable_ChangesInteractableState() {
        assertTrue("Should start interactable", itemObject.isInteractable());
        
        itemObject.setInteractable(false);
        assertFalse("Should be non-interactable after setting", itemObject.isInteractable());
        
        itemObject.setInteractable(true);
        assertTrue("Should be interactable after setting back", itemObject.isInteractable());
    }
    
    @Test
    public void testIsInteractable_ReflectsCurrentState() {
        itemObject.setInteractable(false);
        assertFalse("isInteractable should return current state", itemObject.isInteractable());
    }
    
    // getItem() and setItem() Tests
    
    @Test
    public void testGetItem_InitiallyNull() {
        assertNull("Item should be null initially", itemObject.getItem());
    }
    
    @Test
    public void testSetItem_AssignsItem() {
        itemObject.setItem(testItem);
        assertNotNull("Item should not be null after setting", itemObject.getItem());
        assertEquals("Should return the set item", testItem, itemObject.getItem());
    }
    
    @Test
    public void testSetItem_CanChangeItem() {
        Item firstItem = new Item("item-001", "First", "Hint", "Desc");
        Item secondItem = new Item("item-002", "Second", "Hint", "Desc");
        
        itemObject.setItem(firstItem);
        assertEquals("Should have first item", firstItem, itemObject.getItem());
        
        itemObject.setItem(secondItem);
        assertEquals("Should have second item", secondItem, itemObject.getItem());
    }
    
    @Test
    public void testSetItem_CanSetToNull() {
        itemObject.setItem(testItem);
        assertNotNull("Item should be set", itemObject.getItem());
        
        itemObject.setItem(null);
        assertNull("Item should be null after setting to null", itemObject.getItem());
    }
    
    // Getter Tests
    
    @Test
    public void testGetObjectId_ReturnsCorrectId() {
        assertEquals("Should return correct object ID", "obj-001", itemObject.getObjectId());
    }
    
    @Test
    public void testGetName_ReturnsCorrectName() {
        assertEquals("Should return correct name", "Golden Key", itemObject.getName());
    }
    
    @Test
    public void testGetDescription_ReturnsCorrectDescription() {
        assertEquals("Should return correct description", 
            "A shiny golden key", itemObject.getDescription());
    }
    
    @Test
    public void testGetType_ReturnsCorrectType() {
        assertEquals("Should return correct type", ObjectType.ITEM, itemObject.getType());
    }
    
    // State Combination Tests
    
    @Test
    public void testObjectStates_IndependentFromEachOther() {
       
        itemObject.setCollected(true);
        assertTrue("Should still be interactable", itemObject.isInteractable());
        
        
        itemObject.setInteractable(false);
        assertTrue("Should still be collected", itemObject.isCollected());
    }
    
    @Test
    public void testMultipleObjects_IndependentStates() {
        itemObject.setCollected(true);
        clueObject.setCollected(false);
        
        assertTrue("First object should be collected", itemObject.isCollected());
        assertFalse("Second object should not be collected", clueObject.isCollected());
    }
    
    // Integration Tests
    
    @Test
    public void testObjectLifecycle_FromSpawnToCollection() {
        
        assertFalse("Should start uncollected", itemObject.isCollected());
        assertTrue("Should start interactable", itemObject.isInteractable());
        
       
        assertTrue("Player should detect nearby object", 
            itemObject.isPlayerNearby(110, 160, 50));
        
        
        itemObject.setCollected(true);
        
        
        assertFalse("Collected object should not be detected as nearby", 
            itemObject.isPlayerNearby(110, 160, 50));
    }
    
    @Test
    public void testObjectWithItem_CompleteSetup() {
        itemObject.setItem(testItem);
        
        assertNotNull("Should have an item", itemObject.getItem());
        assertEquals("Item should match", testItem, itemObject.getItem());
        assertFalse("Should not be collected yet", itemObject.isCollected());
        assertTrue("Should be interactable", itemObject.isInteractable());
        
        
        itemObject.setCollected(true);
        assertTrue("Should be collected now", itemObject.isCollected());
        assertNotNull("Item should still be attached", itemObject.getItem());
    }
}