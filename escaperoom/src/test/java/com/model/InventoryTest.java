package com.model;

import org.junit.Test;
import org.junit.Before;
import org.junit.After;
import static org.junit.Assert.*;

public class InventoryTest {
    private Inventory inventory;
    private Item testItem1;
    private Item testItem2;

    @Before
    public void setUp() {
        inventory = new Inventory("test-inv-001", 5);
        testItem1 = new Item("item1", "Test Item 1", "Hint 1", "Description 1");
        testItem2 = new Item("item2", "Test Item 2", "HInt 1", "Description 2");
    }

    @After
    public void tearDown() {
        inventory = null;
        testItem1 = null;
        testItem2 = null;
    }

    @Test
    public void testAddItem_Success() {
        inventory.addItem(testItem1);

        assertTrue("Inventory should contain the added Item", inventory.hasItem(testItem1));
        assertEquals("Inventory size should be 1 after adding one item", 1, inventory.getItems().size());
    }

    @Test
    public void testAddMultipleItems_Success() {
        inventory.addItem(testItem1);
        inventory.addItem(testItem2);

        assertEquals("Inventory should contain 2 items", 2, inventory.getItems().size());
        assertTrue("Should have item 1", inventory.hasItem(testItem1));
        assertTrue("Should have item 2", inventory.hasItem(testItem2))
    }

    @Test
    public void testRemoveItem_Success() {
        inventory.addItem(testItem1);
        invenotry.removeItem(testItem1);

        assertFalse("Inventory should not contain removed item", inventory.hasItem(testItem1));
        assertEquals("Inventory should be empty after removing the only item", 0, inventory.getItems().size());
    }
    
}
