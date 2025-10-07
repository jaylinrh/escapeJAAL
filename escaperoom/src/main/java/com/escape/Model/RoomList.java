package com.escape.Model;
import java.util.ArrayList;

public class RoomList {
    private static RoomList instance;
    private ArrayList<Room> roomList;
    
    private RoomList() {
        roomList = new ArrayList<>();
        createRooms();
    }
    
    public static RoomList getInstance() {
        if (instance == null) {
            instance = new RoomList();
        }
        return instance;
    }
    
    private void createRooms() {
        
    }
    
    public boolean hasRoom(String roomId) {
        for (Room room : roomList) {
            if (room.getRoomId().equals(roomId)) {
                return true;
            }
        }
        return false;
    }
    
    public Room getRoomById(String roomId) {
        for (Room room : roomList) {
            if (room.getRoomId().equals(roomId)) {
                return room;
            }
        }
        return null;
    }
    
    public ArrayList<Room> getRooms() {
        return roomList;
    }
    
    public void addRoom(Room room) {
        if (!hasRoom(room.getRoomId())) {
            roomList.add(room);
        }
    }
    
    public void removeRoom(String roomId) {
        roomList.removeIf(room -> room.getRoomId().equals(roomId));
    }
    
    public int getRoomCount() {
        return roomList.size();
    }
}