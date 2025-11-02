package com.escape.Model;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import java.util.ArrayList;

public class RoomListTest {

    private RoomList roomList;
    
    @Before
    public void setUp() {
        roomList = RoomList.getInstance();
    }

    //Singleton Test

    @Test
    public void testGetInstance_NotNull() {
        assertNotNull("RoomList instance should not be null", roomList);
    }
    
    @Test
    public void testGetInstance_Singleton() {
        RoomList instance1 = RoomList.getInstance();
        RoomList instance2 = RoomList.getInstance();
        
        assertSame("Should return same instance", instance1, instance2);
    }

    // Get Room test

    @Test
    public void testGetRooms_NotNull() {
        ArrayList<Room> rooms = roomList.getRooms();
        assertNotNull("Room list should not be null", rooms);
    }
    
    @Test
    public void testGetRooms_NotEmpty() {
        ArrayList<Room> rooms = roomList.getRooms();
        assertFalse("Room list should not be empty (loaded from JSON)", rooms.isEmpty());
    }
    
    @Test
    public void testGetRooms_ContainsExpectedRooms() {
        ArrayList<Room> rooms = roomList.getRooms();
        
        boolean hasFoyer = false;
        boolean hasExterior = false;
        
        for (Room room : rooms) {
            if (room.getRoomId().equals("room_foyer")) {
                hasFoyer = true;
            }
            if (room.getRoomId().equals("room_exterior")) {
                hasExterior = true;
            }
        }
        
        assertTrue("Should contain foyer room", hasFoyer);
        assertTrue("Should contain exterior room", hasExterior);
    }

    // Get room by Id test
    @Test
    public void testGetRoomById_ExistingRoom() {
        Room room = roomList.getRoomById("room_foyer");
        
        assertNotNull("Should find existing room", room);
        assertEquals("Room ID should match", "room_foyer", room.getRoomId());
    }
    
    @Test
    public void testGetRoomById_NonexistentRoom() {
        Room room = roomList.getRoomById("nonexistent_room_999");
        
        assertNull("Should return null for nonexistent room", room);
    }
    
    @Test
    public void testGetRoomById_NullId() {
        Room room = roomList.getRoomById(null);
        
        assertNull("Should return null for null ID", room);
    }
    
    @Test
    public void testGetRoomById_EmptyId() {
        Room room = roomList.getRoomById("");
        
        assertNull("Should return null for empty ID", room);
    }
    
    @Test
    public void testGetRoomById_CaseSensitive() {
        Room room1 = roomList.getRoomById("room_foyer");
        Room room2 = roomList.getRoomById("ROOM_FOYER");
        
        assertNotNull("Should find room with correct case", room1);
        assertNull("Should not find room with wrong case", room2);
    }

    //Has Room test
    @Test
    public void testHasRoom_ExistingRoom() {
        boolean result = roomList.hasRoom("room_foyer");
        
        assertTrue("Should return true for existing room", result);
    }
    
    @Test
    public void testHasRoom_NonexistentRoom() {
        boolean result = roomList.hasRoom("nonexistent_room_999");
        
        assertFalse("Should return false for nonexistent room", result);
    }
    
    @Test
    public void testHasRoom_NullId() {
        boolean result = roomList.hasRoom(null);
        
        assertFalse("Should return false for null ID", result);
    }
    
    @Test
    public void testHasRoom_EmptyId() {
        boolean result = roomList.hasRoom("");
        
        assertFalse("Should return false for empty ID", result);
    }
    
    @Test
    public void testHasRoom_AfterGettingRoom() {
        Room room = roomList.getRoomById("room_foyer");
        boolean hasRoom = roomList.hasRoom("room_foyer");
        
        assertNotNull("Room should exist", room);
        assertTrue("hasRoom should return true", hasRoom);
    }
    
    //Add Room test
    @Test
    public void testAddRoom_NewRoom() {
        int initialCount = roomList.getRoomCount();
        
        ArrayList<String> dialogues = new ArrayList<>();
        dialogues.add("Test dialogue");
        ArrayList<String> items = new ArrayList<>();
        
        Room newRoom = new Room(
            "test_room_add",
            "Test Room",
            "Test Description",
            "test_map.txt",
            "test_music.mp3",
            "puzzle1",
            "Test Puzzle",
            "Puzzle description",
            1,
            false,
            "dialogue1",
            "dialogue.txt",
            dialogues,
            items
        );
        
        roomList.addRoom(newRoom);
        
        assertEquals("Room count should increase", initialCount + 1, roomList.getRoomCount());
        assertTrue("Room should be findable", roomList.hasRoom("test_room_add"));
        
        roomList.removeRoom("test_room_add");
    }
    
    @Test
    public void testAddRoom_DuplicateId() {
        Room existingRoom = roomList.getRoomById("room_foyer");
        assertNotNull("Room should exist", existingRoom);
        
        int initialCount = roomList.getRoomCount();
        
        ArrayList<String> dialogues = new ArrayList<>();
        ArrayList<String> items = new ArrayList<>();
        
        Room duplicateRoom = new Room(
            "room_foyer",
            "Duplicate Foyer",
            "Description",
            "map.txt",
            "music.mp3",
            "puzzle1",
            "Puzzle",
            "Description",
            1,
            false,
            "dialogue1",
            "dialogue.txt",
            dialogues,
            items
        );
        
        roomList.addRoom(duplicateRoom);
        
        assertEquals("Room count should not change", initialCount, roomList.getRoomCount());
    }

    //Remove room test
    @Test
    public void testRemoveRoom_ExistingRoom() {
        ArrayList<String> dialogues = new ArrayList<>();
        ArrayList<String> items = new ArrayList<>();
        
        Room testRoom = new Room(
            "test_room_remove",
            "Test Room",
            "Description",
            "map.txt",
            "music.mp3",
            "puzzle1",
            "Puzzle",
            "Description",
            1,
            false,
            "dialogue1",
            "dialogue.txt",
            dialogues,
            items
        );
        
        roomList.addRoom(testRoom);
        assertTrue("Room should be added", roomList.hasRoom("test_room_remove"));
        
        int countBefore = roomList.getRoomCount();
        roomList.removeRoom("test_room_remove");
        
        assertEquals("Room count should decrease", countBefore - 1, roomList.getRoomCount());
        assertFalse("Room should no longer exist", roomList.hasRoom("test_room_remove"));
    }
    
    @Test
    public void testRemoveRoom_NonexistentRoom() {
        int initialCount = roomList.getRoomCount();
        
        roomList.removeRoom("nonexistent_room_999");
        
        assertEquals("Room count should not change", initialCount, roomList.getRoomCount());
    }
    
    @Test
    public void testRemoveRoom_NullId() {
        int initialCount = roomList.getRoomCount();
        
        roomList.removeRoom(null);
        
        assertEquals("Room count should not change", initialCount, roomList.getRoomCount());
    }
    
    //Get Room count test
    @Test
    public void testGetRoomCount_Positive() {
        int count = roomList.getRoomCount();
        assertTrue("Room count should be positive", count > 0);
    }
    
    @Test
    public void testGetRoomCount_MatchesListSize() {
        int count = roomList.getRoomCount();
        int listSize = roomList.getRooms().size();
        
        assertEquals("Count should match list size", listSize, count);
    }
    
    @Test
    public void testGetRoomCount_AfterAdd() {
        int initialCount = roomList.getRoomCount();
        
        ArrayList<String> dialogues = new ArrayList<>();
        ArrayList<String> items = new ArrayList<>();
        
        Room newRoom = new Room(
            "test_room_count",
            "Test Room",
            "Description",
            "map.txt",
            "music.mp3",
            "puzzle1",
            "Puzzle",
            "Description",
            1,
            false,
            "dialogue1",
            "dialogue.txt",
            dialogues,
            items
        );
        
        roomList.addRoom(newRoom);
        
        assertEquals("Count should increase by 1", initialCount + 1, roomList.getRoomCount());
        
        roomList.removeRoom("test_room_count");
    }

    //Room properties test
    @Test
    public void testGetRoomById_HasValidProperties() {
        Room room = roomList.getRoomById("room_foyer");
        
        assertNotNull("Room should exist", room);
        assertNotNull("Room should have name", room.getName());
        assertNotNull("Room should have description", room.getDescription());
        assertNotNull("Room should have map file", room.getMapFile());
        assertNotNull("Room should have room ID", room.getRoomId());
    }
    
    @Test
    public void testGetRoomById_HasDialogues() {
        Room room = roomList.getRoomById("room_foyer");
        
        assertNotNull("Room should exist", room);
        assertNotNull("Room should have dialogues list", room.getDialogues());
    }
    
    @Test
    public void testGetRoomById_HasPuzzle() {
        Room room = roomList.getRoomById("room_foyer");
        
        assertNotNull("Room should exist", room);
        assertNotNull("Room should have puzzle", room.getPuzzle());
    }

    //Integration test
    @Test
    public void testRoomList_MultipleOperations() {
        int initialCount = roomList.getRoomCount();
        
        ArrayList<String> dialogues = new ArrayList<>();
        dialogues.add("Test");
        ArrayList<String> items = new ArrayList<>();
        items.add("item1");
        
        Room testRoom = new Room(
            "test_integration",
            "Integration Test Room",
            "Description",
            "map.txt",
            "music.mp3",
            "puzzle1",
            "Puzzle",
            "Description",
            1,
            false,
            "dialogue1",
            "dialogue.txt",
            dialogues,
            items
        );
        
        roomList.addRoom(testRoom);
        assertTrue("Room should be added", roomList.hasRoom("test_integration"));
        assertEquals("Count should increase", initialCount + 1, roomList.getRoomCount());
        
        Room retrieved = roomList.getRoomById("test_integration");
        assertNotNull("Room should be retrievable", retrieved);
        assertEquals("Room name should match", "Integration Test Room", retrieved.getName());
        
        roomList.removeRoom("test_integration");
        assertFalse("Room should be removed", roomList.hasRoom("test_integration"));
        assertEquals("Count should return to initial", initialCount, roomList.getRoomCount());
    }
}
