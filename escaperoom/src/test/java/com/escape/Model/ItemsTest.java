package com.escape.Model;

import org.junit.Test;
import org.junit.Before;
import org.junit.After;
import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import com.escape.Model.Item;
import com.escape.Model.Room;


public class ItemsTest {

    private Items itemsManager; 
    private Item testItem1;
    private Item testItem2;

    
    @Before
    public void setUp() {
       
        itemsManager = new Items();

       
        testItem1 = new Item("key", "A shiny brass key", "Hint for key", "Desc for key");
        testItem2 = new Item("lamp", "An old oil lamp", "Hint for lamp", "Desc for lamp");
    }

    @After
    public void tearDown() {
        itemsManager = null;
        testItem1 = null;
        testItem2 = null;
    }

   //addItem() Tests
    @Test
    public void testAddItem_NewItem_AddsSuccessfully() {
       
        int initialCount = itemsManager.getItemCount();

        
        itemsManager.addItem(testItem1);

       
        assertEquals("Item count should increase by 1", initialCount + 1, itemsManager.getItemCount());
        assertTrue("Manager should now have the item", itemsManager.hasItem("key"));
        assertEquals("Manager should return the correct item", testItem1, itemsManager.getItemById("key"));
    }

    @Test
    public void testAddItem_DuplicateItem_DoesNotAdd() {
        
        itemsManager.addItem(testItem1);
        int countAfterFirstAdd = itemsManager.getItemCount();

        
        itemsManager.addItem(testItem1); 
        int countAfterSecondAdd = itemsManager.getItemCount();

       
        assertEquals("Count should be 1 after first add", 1, countAfterFirstAdd);
        assertEquals("Count should remain 1 after duplicate add", 1, countAfterSecondAdd);
    }

    @Test(expected = NullPointerException.class)
    public void testAddItem_NullItem_ThrowsException() {
       
        itemsManager.addItem(null);
    }

    //removeItem() Tests
    @Test
    public void testRemoveItem_ExistingItem_RemovesSuccessfully() {
      
        itemsManager.addItem(testItem1);
        assertTrue("Item should exist before removal", itemsManager.hasItem("key"));

        itemsManager.removeItem("key");

        assertFalse("Item should not exist after removal", itemsManager.hasItem("key"));
        assertEquals("Item count should be 0", 0, itemsManager.getItemCount());
    }

    @Test
    public void testRemoveItem_NonExistentItem_DoesNothing() {

        itemsManager.addItem(testItem1); 
        int initialCount = itemsManager.getItemCount();

        
        itemsManager.removeItem("fake_id"); 

        
        assertEquals("Count should not change", initialCount, itemsManager.getItemCount());
    }
    //hasItem() Tests
    @Test
    public void testHasItem_WithExistingAndNonExistingItems_ReturnsCorrectBoolean() {
       
        itemsManager.addItem(testItem1);

       
        assertTrue("Should return true for existing item", itemsManager.hasItem("key"));
        assertFalse("Should return false for non-existing item", itemsManager.hasItem("lamp"));
        assertFalse("Should return false for null", itemsManager.hasItem(null));
    }
    //getItemById() Tests
    @Test
    public void testGetItemById_ExistingItem_ReturnsCorrectItem() {
        
        itemsManager.addItem(testItem1);
        itemsManager.addItem(testItem2);

        
        Item retrievedItem = itemsManager.getItemById("lamp");

       
        assertNotNull("Retrieved item should not be null", retrievedItem);
        assertEquals("Item ID should match", "lamp", retrievedItem.getItemId());
        assertEquals("Item name should match", "An old oil lamp", retrievedItem.getName());
    }

    @Test
    public void testGetItemById_NonExistentItem_ReturnsNull() {
       

        
        Item retrievedItem = itemsManager.getItemById("fake_id");

       
        assertNull("Retrieved item should be null", retrievedItem);
    }
    //getItemCount() Tests
    @Test
    public void testGetItemCount_AfterAddingItems_ReturnsUpdatedCount() {
       

        
        assertEquals("Count should be 0 for new manager", 0, itemsManager.getItemCount());

        
        itemsManager.addItem(testItem1);

        
        assertEquals("Count should be 1 after adding one item", 1, itemsManager.getItemCount());

        
        itemsManager.addItem(testItem2);

       
        assertEquals("Count should be 2 after adding two items", 2, itemsManager.getItemCount());
    }

    //getItemsForRoom() Tests
    @Test
    public void testGetItemsForRoom_WithValidAndInvalidIds_ReturnsCorrectList() {
       
        itemsManager.addItem(testItem1); 
        itemsManager.addItem(testItem2); 

        
        Room testRoom = new Room("room-test-1", "Test Room", "A room for testing", 
                                "test.txt", "test.mp3", new String[]{}, false);
        
      
        testRoom.getAvailableItemIds().add("key");
        testRoom.getAvailableItemIds().add("fake_id"); 

       
        ArrayList<Item> roomItems = itemsManager.getItemsForRoom(testRoom);

        
        assertNotNull("List should not be null", roomItems);
        assertEquals("List should only contain 1 item", 1, roomItems.size());
        assertEquals("The item should be the key", "key", roomItems.get(0).getItemId());
    }

    @Test
    public void testGetItemsForRoom_OnRoomWithEmptyItemList_ReturnsEmptyList() {

        itemsManager.addItem(testItem1);
        itemsManager.addItem(testItem2);

        Room emptyRoom = new Room("room-test-2", "Empty Room", "A room for testing", 
                                "test.txt", "test.mp3", new String[]{}, false);

        ArrayList<Item> roomItems = itemsManager.getItemsForRoom(emptyRoom);

        assertNotNull("List should not be null", roomItems);
        assertTrue("List should be empty", roomItems.isEmpty());
    }

    @Test(expected = NullPointerException.class)
    public void testGetItemsForRoom_WithNullRoom_ThrowsException() {
        
        itemsManager.getItemsForRoom(null);
    }
}

