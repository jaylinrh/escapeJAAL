package com.escape.Model;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import java.util.UUID;

public class UserTest {
    private User user;
    private UUID testUserId;
    private PlayerState testPlayerState;
    private Inventory testInventory;

    @Before
    public void setUp() {
        testUserId = UUID.randomUUID();
        
        SolidArea solidArea = new SolidArea(8, 16, 32, 32);
        SpriteImages sprites = new SpriteImages(
            "/images/player.png", "/images/player.png",
            "/images/player.png", "/images/player.png",
            "/images/player.png", "/images/player.png",
            "/images/player.png", "/images/player.png"
        );
        
        testPlayerState = new PlayerState(100, 200, 4, "down", solidArea, false, sprites);
        testInventory = new Inventory("inv_test", 10);
        
        user = new User(
            testUserId,
            "testUser",
            "testPassword",
            1,
            "room_foyer",
            testPlayerState,
            testInventory
        );
    }

    //Constructor test

    @Test
    public void testConstructor_Id() {
        assertEquals("User ID should match", testUserId, user.getId());
    }
    
    @Test
    public void testConstructor_Username() {
        assertEquals("Username should match", "testUser", user.getUserName());
    }
    
    @Test
    public void testConstructor_Password() {
        assertEquals("Password should match", "testPassword", user.getPassword());
    }
    
    @Test
    public void testConstructor_Level() {
        assertEquals("Level should be 1", 1, user.getLevel());
    }
    
    @Test
    public void testConstructor_CurrentRoomId() {
        assertEquals("Current room ID should match", "room_foyer", user.getCurrentRoomID());
    }
    
    @Test
    public void testConstructor_PlayerState() {
        assertNotNull("Player state should not be null", user.getPlayerState());
        assertSame("Player state should match", testPlayerState, user.getPlayerState());
    }
    
    @Test
    public void testConstructor_Inventory() {
        assertNotNull("Inventory should not be null", user.getInventory());
        assertSame("Inventory should match", testInventory, user.getInventory());
    }

    // Getter test
    @Test
    public void testGetId() {
        UUID id = user.getId();
        assertNotNull("ID should not be null", id);
        assertEquals("ID should match", testUserId, id);
    }
    
    @Test
    public void testGetUserName() {
        String username = user.getUserName();
        assertEquals("Username should match", "testUser", username);
    }
    
    @Test
    public void testGetPassword() {
        String password = user.getPassword();
        assertEquals("Password should match", "testPassword", password);
    }
    
    @Test
    public void testGetLevel() {
        int level = user.getLevel();
        assertEquals("Level should be 1", 1, level);
    }
    
    @Test
    public void testGetCurrentRoomID() {
        String roomId = user.getCurrentRoomID();
        assertEquals("Room ID should match", "room_foyer", roomId);
    }
    
    @Test
    public void testGetPlayerState() {
        PlayerState state = user.getPlayerState();
        assertNotNull("Player state should not be null", state);
        assertEquals("World X should match", 100, state.getWorldX());
        assertEquals("World Y should match", 200, state.getWorldY());
    }
    
    @Test
    public void testGetInventory() {
        Inventory inventory = user.getInventory();
        assertNotNull("Inventory should not be null", inventory);
        assertEquals("Inventory ID should match", "inv_test", inventory.getInventoryId());
    }
    
    @Test
    public void testGetPlayer() {
        Player player = user.getPlayer();
    }

    //Level management test
    @Test
    public void testAddLevel_IncreasesLevel() {
        int initialLevel = user.getLevel();
        
        user.addLevel();
        
        assertEquals("Level should increase by 1", initialLevel + 1, user.getLevel());
    }
    
    @Test
    public void testAddLevel_Multiple() {
        user.addLevel();
        user.addLevel();
        user.addLevel();
        
        assertEquals("Level should be 4", 4, user.getLevel());
    }
    
    @Test
    public void testSetLevel() {
        user.setLevel(5);
        
        assertEquals("Level should be set to 5", 5, user.getLevel());
    }
    
    @Test
    public void testSetLevel_Zero() {
        user.setLevel(0);
        
        assertEquals("Level should be 0", 0, user.getLevel());
    }
    
    @Test
    public void testSetLevel_Negative() {
        user.setLevel(-1);
        
        assertEquals("Level should be -1", -1, user.getLevel());
    }
    
    @Test
    public void testSetLevel_Large() {
        user.setLevel(999);
        
        assertEquals("Level should be 999", 999, user.getLevel());
    }
    
    @Test
    public void testAddLevel_AfterSetLevel() {
        user.setLevel(10);
        user.addLevel();
        
        assertEquals("Level should be 11", 11, user.getLevel());
    }

    //ToString test
    @Test
    public void testToString_NotNull() {
        String str = user.toString();
        assertNotNull("toString should not return null", str);
    }
    
    @Test
    public void testToString_ContainsUsername() {
        String str = user.toString();
        assertTrue("Should contain username", str.contains("testUser"));
    }
    
    @Test
    public void testToString_ContainsLevel() {
        String str = user.toString();
        assertTrue("Should contain level", str.contains("Level"));
    }
    
    @Test
    public void testToString_ContainsRoomId() {
        String str = user.toString();
        assertTrue("Should contain room ID", str.contains("room_foyer"));
    }
    
    @Test
    public void testToString_Format() {
        String str = user.toString();
        assertTrue("Should contain 'Player:'", str.contains("Player:"));
        assertTrue("Should contain 'Room:'", str.contains("Room:"));
    }

    //Edge Case Test
    @Test
    public void testUser_WithSpecialCharactersUsername() {
        String specialUsername = "user@email.com";
        User specialUser = new User(
            UUID.randomUUID(),
            specialUsername,
            "password",
            1,
            "room1",
            testPlayerState,
            testInventory
        );
        
        assertEquals("Special characters should be allowed in valid usernames", 
            specialUsername, specialUser.getUserName());
    }

    @Test
    public void testUser_WithLongUsername() {
        StringBuilder longUsername = new StringBuilder();
        for (int i = 0; i < 100; i++) {
            longUsername.append("a");
        }
        
        User longUser = new User(
            UUID.randomUUID(),
            longUsername.toString(),
            "password",
            1,
            "room1",
            testPlayerState,
            testInventory
        );
        
        assertEquals("Long username should be allowed", 
            longUsername.toString(), longUser.getUserName());
    }

    @Test
    public void testUser_WithHighLevel() {
        User highLevelUser = new User(
            UUID.randomUUID(),
            "username",
            "password",
            999,
            "room1",
            testPlayerState,
            testInventory
        );
        
        assertEquals("High level should be allowed", 999, highLevelUser.getLevel());
    }
    
    @Test
    public void testUser_LevelProgression() {
        assertEquals("Start at level 1", 1, user.getLevel());
        
        user.addLevel();
        assertEquals("Level 2", 2, user.getLevel());
        
        user.addLevel();
        assertEquals("Level 3", 3, user.getLevel());
        
        user.setLevel(10);
        assertEquals("Jump to level 10", 10, user.getLevel());
        
        user.addLevel();
        assertEquals("Level 11", 11, user.getLevel());
    }

    @Test
    public void testUser_StateConsistency() {
        PlayerState state1 = user.getPlayerState();
        PlayerState state2 = user.getPlayerState();
        
        assertSame("Should return same player state instance", state1, state2);
        
        Inventory inv1 = user.getInventory();
        Inventory inv2 = user.getInventory();
        
        assertSame("Should return same inventory instance", inv1, inv2);
    }
    
    @Test
    public void testUser_InventoryOperations() {
        Inventory inv = user.getInventory();
        
        Item testItem = new Item("item1", "Test Item", "Hint", "Description");
        inv.addItem(testItem);
        
        assertTrue("Inventory should contain added item", inv.hasItem(testItem));
        assertEquals("Inventory should have 1 item", 1, inv.getItems().size());
    }

    @Test
    public void testUser_PlayerStateProperties() {
        PlayerState state = user.getPlayerState();
        
        assertEquals("World X should be 100", 100, state.getWorldX());
        assertEquals("World Y should be 200", 200, state.getWorldY());
        assertEquals("Speed should be 4", 4, state.getSpeed());
        assertEquals("Direction should be down", "down", state.getDirection());
    }
    
    @Test
    public void testUser_UniqueIds() {
        User user2 = new User(
            UUID.randomUUID(),
            "user2",
            "password",
            1,
            "room1",
            testPlayerState,
            testInventory
        );
        
        assertNotEquals("User IDs should be unique", user.getId(), user2.getId());
    }
}
