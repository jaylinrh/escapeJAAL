package com.escape.Model;

import org.junit.Before;
import org.junit.After;
import org.junit.Test;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.io.File;

public class FacadeTest {
    private Facade facade;
    private static final String TEST_USERNAME = "testUser123";
    private static final String TEST_PASSWORD = "testPass456";

    @Before
    public void setUp() throws Exception{
        File jsonDir = new File("json");
        if (!jsonDir.exists()) {
            jsonDir.mkdirs();
        }
        facade = Facade.getInstance();
        UserList userList = UserList.getInstance();
        ArrayList<User> users = userList.getUsers();
        users.removeIf(u -> u.getUserName().equals(TEST_USERNAME));
    }

    @After
    public void tearDown() {
        if (facade.isUserLoggedIn()) {
            facade.logoutUser();
        }
        UserList userList = UserList.getInstance();
        ArrayList<User> users = userList.getUsers();
        users.removeIf(u -> u.getUserName().equals(TEST_USERNAME));
    }

    @Test
    public void testRegisterUser_Success() {
        boolean result = facade.registerUser(TEST_USERNAME, TEST_PASSWORD);
        assertTrue("User registration should succeed", result);
        assertTrue("User should be logged in after registration", facade.isUserLoggedIn());
        assertEquals("Current user should match registered username", TEST_USERNAME, facade.getCurrentUser().getUserName());
    }

    @Test
    public void testRegisterUser_DuplicateUsername() {
        facade.registerUser(TEST_USERNAME, TEST_PASSWORD);
        facade.logoutUser();

        boolean result = facade.registerUser(TEST_USERNAME, "differentPassword");
        assertFalse("registration should fail for duplicate username", false);
    }
    @Test
    public void testLoginUser_ValidCredentials() {
        facade.registerUser(TEST_USERNAME, TEST_PASSWORD);
        facade.logoutUser();

        boolean result = facade.loginUser(TEST_USERNAME, TEST_PASSWORD);
        assertTrue("Login should succeed with valide credentials", result);
        assertTrue("User should be logged in", facade.isUserLoggedIn());
    }
    @Test
    public void testLoginUser_InvalidPassword() {
        facade.registerUser(TEST_USERNAME, TEST_PASSWORD);
        facade.logoutUser();

        boolean result = facade.loginUser(TEST_USERNAME, "wrongPassword");
        assertFalse("Login should fail with invalid password", result);
        assertFalse("User should not be logged in", facade.isUserLoggedIn());
    }
    @Test
    public void testLoginUser_NonexistentUser() {
        boolean result = facade.loginUser(TEST_USERNAME, TEST_PASSWORD);
        assertFalse("Login should fail for nonexistent user", result);
        assertFalse("User should not be logged in", facade.isUserLoggedIn());
    }
    @Test
    public void testLogoutUser() {
        facade.registerUser(TEST_USERNAME, TEST_PASSWORD);
        assertTrue("User should be logged in initially", facade.isUserLoggedIn());

        facade.logoutUser();
        assertFalse("User should not be logged in after logout", facade.isUserLoggedIn());
        assertNull("Current User should be null after logout", facade.getCurrentUser());
    }
    @Test
    public void testIsUserLoggedIn_WhenLoggedOut() {
        assertFalse("No user should be logged in initially", facade.isUserLoggedIn());
    }
    
    @Test
    public void testGetCurrentUser_WhenLoggedOut() {
        assertNull("Current user should be null when not logged in", facade.getCurrentUser());
    }
    
    @Test
    public void testGetCurrentUser_WhenLoggedIn() {
        facade.registerUser(TEST_USERNAME, TEST_PASSWORD);
        User currentUser = facade.getCurrentUser();
        
        assertNotNull("Current user should not be null when logged in", currentUser);
        assertEquals("Username should match", TEST_USERNAME, currentUser.getUserName());
    }

    // Progression Test
    @Test
    public void testGetProgression_WhenLoggedOut() {
        assertNull("Progression should be null when not logged in", facade.getProgression());
    }
    
    @Test
    public void testGetProgression_WhenLoggedIn() {
        facade.registerUser(TEST_USERNAME, TEST_PASSWORD);
        Progression progression = facade.getProgression();
        
        assertNotNull("Progression should not be null when logged in", progression);
    }
    
    @Test
    public void testCompleteRoom() {
        facade.registerUser(TEST_USERNAME, TEST_PASSWORD);
        String roomId = "room_foyer";
        
        facade.completeRoom(roomId);
        
        Progression progression = facade.getProgression();
        assertTrue("Room should be marked as completed", 
            progression.hasCompletedRoom(roomId));
    }

    @Test
    public void testSolvePuzzle() {
        facade.registerUser(TEST_USERNAME, TEST_PASSWORD);
        String puzzleId = "puzzle1";
        
        facade.solvePuzzle(puzzleId);
        
        Progression progression = facade.getProgression();
        assertTrue("Puzzle should be marked as solved", 
            progression.hasSolvedPuzzle(puzzleId));
    }
    
    @Test
    public void testGetCompletionPercentage_InitiallyZero() {
        facade.registerUser(TEST_USERNAME, TEST_PASSWORD);
        
        double completion = facade.getCompletionPercentage();
        assertEquals("Initial completion should be 0", 0.0, completion, 0.01);
    }

    @Test
    public void testGetCompletionPercentage_AfterProgress() {
        facade.registerUser(TEST_USERNAME, TEST_PASSWORD);
        
        facade.completeRoom("room1");
        facade.solvePuzzle("puzzle1");
        
        double completion = facade.getCompletionPercentage();
        assertTrue("Completion should be greater than 0", completion > 0);
    }

    //Room managment
    @Test
    public void testGetRoomById_ValidId() {
        Room room = facade.getRoomById("room_foyer");
        assertNotNull("Room should exist", room);
    }
    
    @Test
    public void testGetRoomById_InvalidId() {
        Room room = facade.getRoomById("nonexistent_room_999");
        assertNull("Room should not exist", room);
    }
    
    @Test
    public void testGetAllRooms() {
        ArrayList<Room> rooms = facade.getAllRooms();
        assertNotNull("Room list should not be null", rooms);
        assertTrue("Room list should not be empty", rooms.size() > 0);
    }
    
    @Test
    public void testGetCurrentRoom_WhenLoggedOut() {
        Room room = facade.getCurrentRoom();
        assertNull("Current room should be null when not logged in", room);
    }
    
    @Test
    public void testMoveToRoom_ValidRoom() {
        facade.registerUser(TEST_USERNAME, TEST_PASSWORD);
        boolean result = facade.moveToRoom("room_foyer");
        assertTrue("Move should succeed for valid room", result);
    }
    
    @Test
    public void testMoveToRoom_InvalidRoom() {
        facade.registerUser(TEST_USERNAME, TEST_PASSWORD);
        boolean result = facade.moveToRoom("invalid_room_999");
        assertFalse("Move should fail for invalid room", result);
    }
    
    @Test
    public void testMoveToRoom_WhenLoggedOut() {
        boolean result = facade.moveToRoom("room_foyer");
        assertFalse("Move should fail when not logged in", result);
    }

    // Item management
    @Test
    public void testGetItemById_ValidId() {
        Items items = Items.getInstance();
        Item testItem = new Item("test_item_facade", "Test Item", "Test hint", "Test description");
        items.addItem(testItem);
        
        Item retrieved = facade.getItemById("test_item_facade");
        assertNotNull("Item should be retrieved", retrieved);
        assertEquals("Item names should match", "Test Item", retrieved.getName());
    }
    
    @Test
    public void testGetItemById_InvalidId() {
        Item item = facade.getItemById("nonexistent_item_999");
        assertNull("Item should not exist", item);
    }
    
    @Test
    public void testGetAllItems() {
        ArrayList<Item> items = facade.getAllItems();
        assertNotNull("Item list should not be null", items);
    }
    
    @Test
    public void testPickupItem_Success() {
        facade.registerUser(TEST_USERNAME, TEST_PASSWORD);
        
        Items items = Items.getInstance();
        Item testItem = new Item("pickup_test_item", "Pickup Test", "Hint", "Description");
        items.addItem(testItem);
        
        boolean result = facade.pickupItem("pickup_test_item");
        assertTrue("Pickup should succeed", result);
        assertTrue("User should have item", facade.currentUserHasItem("pickup_test_item"));
    }
    
    @Test
    public void testPickupItem_NonexistentItem() {
        facade.registerUser(TEST_USERNAME, TEST_PASSWORD);
        
        boolean result = facade.pickupItem("nonexistent_item_999");
        assertFalse("Pickup should fail for nonexistent item", result);
    }
    
    @Test
    public void testPickupItem_WhenLoggedOut() {
        boolean result = facade.pickupItem("any_item");
        assertFalse("Pickup should fail when not logged in", result);
    }
    
    @Test
    public void testDropItem_Success() {
        facade.registerUser(TEST_USERNAME, TEST_PASSWORD);
        
        Items items = Items.getInstance();
        Item testItem = new Item("drop_test_item", "Drop Test", "Hint", "Description");
        items.addItem(testItem);
        facade.pickupItem("drop_test_item");
        
        boolean result = facade.dropItem("drop_test_item");
        assertTrue("Drop should succeed", result);
        assertFalse("User should not have item", facade.currentUserHasItem("drop_test_item"));
    }
    
    @Test
    public void testDropItem_NonexistentItem() {
        facade.registerUser(TEST_USERNAME, TEST_PASSWORD);
        
        boolean result = facade.dropItem("nonexistent_item_999");
        assertFalse("Drop should fail for nonexistent item", result);
    }
    
    @Test
    public void testCurrentUserHasItem_True() {
        facade.registerUser(TEST_USERNAME, TEST_PASSWORD);
        
        Items items = Items.getInstance();
        Item testItem = new Item("has_test_item", "Has Test", "Hint", "Description");
        items.addItem(testItem);
        facade.pickupItem("has_test_item");
        
        assertTrue("Should return true when user has item", 
            facade.currentUserHasItem("has_test_item"));
    }
    
    @Test
    public void testCurrentUserHasItem_False() {
        facade.registerUser(TEST_USERNAME, TEST_PASSWORD);
        
        assertFalse("Should return false when user doesn't have item", 
            facade.currentUserHasItem("nonexistent_item"));
    }
    
    @Test
    public void testCurrentUserHasItem_WhenLoggedOut() {
        assertFalse("Should return false when not logged in", 
            facade.currentUserHasItem("any_item"));
    }
}
