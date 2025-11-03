package com.escape.Model;

import org.junit.Test;
import org.junit.Before;
import org.junit.After;
import static org.junit.Assert.*;
import java.util.ArrayList;


public class InventoryTest {
    private Inventory inventory;
    private Item testItem1;
    private Item testItem2;
    private Item testItem3;
    private static final String INVENTORY_ID = "test_inventory_123";
    private static final int MAX_CAPACITY = 5;
    

    @Before
    public void setUp() {
        inventory = new Inventory(INVENTORY_ID, MAX_CAPACITY);
        
        testItem1 = new Item("item1", "Test Item 1", "Hint 1", "Description 1");
        testItem2 = new Item("item2", "Test Item 2", "Hint 2", "Description 2");
        testItem3 = new Item("item3", "Test Item 3", "Hint 3", "Description 3");
    }

    @After
    public void tearDown() {
        inventory = null;
        testItem1 = null;
        testItem2 = null;
    }

    //Constructor Test

    @Test
    public void testConstructor_InventoryId() {
        assertEquals("Inventory ID should match", INVENTORY_ID, inventory.getInventoryId());
    }
    
    @Test
    public void testConstructor_MaxCapacity() {
        assertEquals("Max capacity should match", MAX_CAPACITY, inventory.getMaxCapacity());
    }
    
    @Test
    public void testConstructor_InitiallyEmpty() {
        ArrayList<Item> items = inventory.getItems();
        assertNotNull("Items list should not be null", items);
        assertTrue("Items list should be empty initially", items.isEmpty());
    }

    // Add Item test
    @Test
    public void testAddItem_SingleItem() {
        inventory.addItem(testItem1);
        
        ArrayList<Item> items = inventory.getItems();
        assertEquals("Inventory should contain 1 item", 1, items.size());
        assertTrue("Inventory should contain the added item", items.contains(testItem1));
    }
    
    @Test
    public void testAddItem_MultipleItems() {
        inventory.addItem(testItem1);
        inventory.addItem(testItem2);
        inventory.addItem(testItem3);
        
        ArrayList<Item> items = inventory.getItems();
        assertEquals("Inventory should contain 3 items", 3, items.size());
        assertTrue("Inventory should contain item1", items.contains(testItem1));
        assertTrue("Inventory should contain item2", items.contains(testItem2));
        assertTrue("Inventory should contain item3", items.contains(testItem3));
    }
    
    @Test
    public void testAddItem_AtCapacity() {
        // Add items up to capacity
        for (int i = 0; i < MAX_CAPACITY; i++) {
            Item item = new Item("item" + i, "Item " + i, "Hint", "Description");
            inventory.addItem(item);
        }
        
        assertEquals("Inventory should be at capacity", 
            MAX_CAPACITY, inventory.getItems().size());
    }
    
    @Test
    public void testAddItem_ExceedCapacity() {
        // Fill inventory to capacity
        for (int i = 0; i < MAX_CAPACITY; i++) {
            Item item = new Item("item" + i, "Item " + i, "Hint", "Description");
            inventory.addItem(item);
        }
        
        // Try to add one more
        Item extraItem = new Item("extra", "Extra Item", "Hint", "Description");
        inventory.addItem(extraItem);
        
        assertEquals("Inventory should not exceed capacity", 
            MAX_CAPACITY, inventory.getItems().size());
        assertFalse("Extra item should not be added", 
            inventory.getItems().contains(extraItem));
    }
    
    @Test
    public void testAddItem_NullItem() {
        inventory.addItem(testItem1);
        inventory.addItem(null);
        
        // Should handle null gracefully
        assertEquals("Valid item should still be in inventory", 
            1, inventory.getItems().size());
    }

    @Test
    public void testAddItem_DuplicateItem_DoesNotAddAgain() {
        inventory.addItem(testItem1);
        inventory.addItem(testItem1);
        
        assertEquals("Should only have one instance of the item", 
            1, inventory.getItems().size());
    }

    // Remove Item test
    @Test
    public void testRemoveItem_ExistingItem() {
        inventory.addItem(testItem1);
        inventory.addItem(testItem2);
        
        inventory.removeItem(testItem1);
        
        ArrayList<Item> items = inventory.getItems();
        assertEquals("Inventory should have 1 item", 1, items.size());
        assertFalse("Removed item should not be in inventory", items.contains(testItem1));
        assertTrue("Other item should still be in inventory", items.contains(testItem2));
    }
    
    @Test
    public void testRemoveItem_NonexistentItem() {
        inventory.addItem(testItem1);
        
        inventory.removeItem(testItem2);
        
        assertEquals("Inventory size should remain unchanged", 
            1, inventory.getItems().size());
        assertTrue("Original item should still be in inventory", 
            inventory.getItems().contains(testItem1));
    }
    
    @Test
    public void testRemoveItem_FromEmptyInventory() {
        inventory.removeItem(testItem1);
        
        assertTrue("Inventory should remain empty", inventory.getItems().isEmpty());
    }
    
    @Test
    public void testRemoveItem_NullItem() {
        inventory.addItem(testItem1);
        inventory.removeItem(null);
        
        assertEquals("Inventory should still contain the item", 
            1, inventory.getItems().size());
    }
    
    @Test
    public void testRemoveItem_AllItems() {
        inventory.addItem(testItem1);
        inventory.addItem(testItem2);
        inventory.addItem(testItem3);
        
        inventory.removeItem(testItem1);
        inventory.removeItem(testItem2);
        inventory.removeItem(testItem3);
        
        assertTrue("Inventory should be empty", inventory.getItems().isEmpty());
    }

    //Has Item test
    @Test
    public void testHasItem_True() {
        inventory.addItem(testItem1);
        
        assertTrue("Should return true for item in inventory", 
            inventory.hasItem(testItem1));
    }
    
    @Test
    public void testHasItem_False() {
        assertFalse("Should return false for item not in inventory", 
            inventory.hasItem(testItem1));
    }
    
    @Test
    public void testHasItem_AfterRemoval() {
        inventory.addItem(testItem1);
        inventory.removeItem(testItem1);
        
        assertFalse("Should return false after item is removed", 
            inventory.hasItem(testItem1));
    }
    
    @Test
    public void testHasItem_NullItem() {
        assertFalse("Should return false for null item", inventory.hasItem(null));
    }
    
    @Test
    public void testHasItem_MultipleItems() {
        inventory.addItem(testItem1);
        inventory.addItem(testItem2);
        
        assertTrue("Should find first item", inventory.hasItem(testItem1));
        assertTrue("Should find second item", inventory.hasItem(testItem2));
        assertFalse("Should not find unadded item", inventory.hasItem(testItem3));
    }

    // Get Method Test
    @Test
    public void testGetInventoryId() {
        assertEquals("Inventory ID should match", INVENTORY_ID, inventory.getInventoryId());
    }
    
    @Test
    public void testGetMaxCapacity() {
        assertEquals("Max capacity should match", MAX_CAPACITY, inventory.getMaxCapacity());
    }
    
    @Test
    public void testGetItems_NotNull() {
        assertNotNull("GetItems should never return null", inventory.getItems());
    }
    
    @Test
    public void testGetItems_ReturnsActualList() {
        inventory.addItem(testItem1);
        
        ArrayList<Item> items = inventory.getItems();
        items.add(testItem2);
        
        // Since we're returning the actual list, changes should reflect
        assertEquals("Changes to returned list should affect inventory", 
            2, inventory.getItems().size());
    }

    //ToString Test
    @Test
    public void testToString_Format() {
        String str = inventory.toString();
        assertNotNull("toString should not return null", str);
        assertTrue("toString should contain inventory ID", str.contains(INVENTORY_ID));
        assertTrue("toString should contain maxCapacity", str.contains("maxCapacity"));
    }
    
    @Test
    public void testToString_WithItems() {
        inventory.addItem(testItem1);
        
        String str = inventory.toString();
        assertNotNull("toString should not return null", str);
        assertTrue("toString should contain items info", str.contains("items"));
    }

    // Edge Case Test

    @Test
    public void testInventory_WithZeroCapacity() {
        Inventory zeroCapInv = new Inventory("zero_cap", 0);
        
        zeroCapInv.addItem(testItem1);
        
        assertTrue("Inventory with 0 capacity should not accept items", 
            zeroCapInv.getItems().isEmpty());
    }
    
    @Test
    public void testInventory_WithLargeCapacity() {
        Inventory largeInv = new Inventory("large", 1000);
        
        // Add many items
        for (int i = 0; i < 100; i++) {
            Item item = new Item("item" + i, "Item " + i, "Hint", "Desc");
            largeInv.addItem(item);
        }
        
        assertEquals("Should successfully add 100 items", 100, largeInv.getItems().size());
    }
    
    @Test
    public void testInventory_AddRemoveSequence() {
        inventory.addItem(testItem1);
        inventory.addItem(testItem2);
        inventory.removeItem(testItem1);
        inventory.addItem(testItem3);
        inventory.removeItem(testItem2);
        
        ArrayList<Item> items = inventory.getItems();
        assertEquals("Should have 1 item after sequence", 1, items.size());
        assertTrue("Should contain item3", items.contains(testItem3));
        assertFalse("Should not contain item1", items.contains(testItem1));
        assertFalse("Should not contain item2", items.contains(testItem2));
    }
    
    @Test
    public void testInventory_FillEmptyRefill() {
        // Fill
        for (int i = 0; i < MAX_CAPACITY; i++) {
            inventory.addItem(new Item("item" + i, "Item", "Hint", "Desc"));
        }
        assertEquals("Should be full", MAX_CAPACITY, inventory.getItems().size());
        
        // Empty
        ArrayList<Item> items = new ArrayList<>(inventory.getItems());
        for (Item item : items) {
            inventory.removeItem(item);
        }
        assertTrue("Should be empty", inventory.getItems().isEmpty());
        
        // Refill
        inventory.addItem(testItem1);
        assertEquals("Should accept items after emptying", 1, inventory.getItems().size());
    }
}