package com.escape.Model;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.UUID;

public class Facade {
    private static Facade instance;
    
    private UserList userList;
    private RoomList roomList;
    private Items items;
    
    private User currentUser;
    private Progression progression;
    
    private Facade() {
        initialize();
    }
    
    public static Facade getInstance() {
        if (instance == null) {
            instance = new Facade();
        }
        return instance;
    }
    
    private void initialize() {
        this.userList = UserList.getInstance();
        this.roomList = RoomList.getInstance();
        this.items = Items.getInstance();
        this.currentUser = null;
        this.progression = null;
    }
    
    public boolean loginUser(String username, String password) {
        ArrayList<User> users = userList.getUsers();
        
        for (User user : users) {
            if (user.getUserName().equals(username) && 
                user.getPassword().equals(password)) {
                this.currentUser = user;
                this.progression = new Progression(user);
                return true;
            }
        }
        return false;
    }
    
    public boolean registerUser(String username, String password) {
        if (userList.haveUser(username)) {
            return false;
        }
        
        boolean success = userList.addUser(username, password);
        
        if (success) {
            loginUser(username, password);
        }
        
        return success;
    }
    
    public void logoutUser() {
        if (currentUser != null) {
            saveUserProgress();
            this.currentUser = null;
            this.progression = null;
        }
    }
    
    public boolean isUserLoggedIn() {
        return currentUser != null;
    }
    
    public User getCurrentUser() {
        return currentUser;
    }
    
    public Progression getProgression() {
        return progression;
    }
    
    public void completeRoom(String roomId) {
        if (progression != null) {
            progression.completeRoom(roomId);
        }
    }
    
    public void solvePuzzle(String puzzleId) {
        if (progression != null) {
            progression.solvePuzzle(puzzleId);
        }
    }
    
    public double getCompletionPercentage() {
        return progression != null ? progression.getCompletionPercentage() : 0.0;
    }
    
    public void saveUserProgress() {
        if (currentUser != null) {
            userList.saveUsers();
        }
    }
    
    public void loadGameData() {
        this.userList = UserList.getInstance();
        this.roomList = RoomList.getInstance();
        this.items = Items.getInstance();
    }
    
    public void saveGameData() {
        userList.saveUsers();
    }
    
    public Room getRoomById(String roomId) {
        return roomList.getRoomById(roomId);
    }
    
    public ArrayList<Room> getAllRooms() {
        return roomList.getRooms();
    }
    
    public Room getCurrentRoom() {
        if (currentUser != null) {
            String roomId = currentUser.getCurrentRoomID();
            return roomList.getRoomById(roomId);
        }
        return null;
    }
    
    public boolean moveToRoom(String roomId) {
        if (currentUser != null && roomList.hasRoom(roomId)) {
            return true;
        }
        return false;
    }
    
    public Item getItemById(String itemId) {
        return items.getItemById(itemId);
    }
    
    public ArrayList<Item> getAllItems() {
        return items.getAllItems();
    }
    
    public ArrayList<Item> getItemsInRoom(String roomId) {
        Room room = roomList.getRoomById(roomId);
        if (room != null) {
            return items.getItemsForRoom(room);
        }
        return new ArrayList<>();
    }
    
    public boolean pickupItem(String itemId) {
        if (currentUser == null) return false;
        
        Item item = items.getItemById(itemId);
        if (item != null) {
            currentUser.getInventory().addItem(item);
            if (progression != null) {
                progression.collectItem(itemId);
            }
            return true;
        }
        return false;
    }
    
    public boolean dropItem(String itemId) {
        if (currentUser == null) return false;
        
        Item item = items.getItemById(itemId);
        if (item != null) {
            currentUser.getInventory().removeItem(item);
            return true;
        }
        return false;
    }
    
    public boolean currentUserHasItem(String itemId) {
        if (currentUser == null) return false;
        
        Item item = items.getItemById(itemId);
        return item != null && currentUser.getInventory().hasItem(item);
    }
    
    public int getCurrentUserLevel() {
        return currentUser != null ? currentUser.getLevel() : 0;
    }
    
    public void levelUpCurrentUser() {
        if (currentUser != null) {
            currentUser.addLevel();
            if (progression != null) {
                progression.levelUp();
            }
        }
    }
    
    public Inventory getCurrentUserInventory() {
        return currentUser != null ? currentUser.getInventory() : null;
    }
    
    public PlayerState getCurrentUserPlayerState() {
        return currentUser != null ? currentUser.getPlayerState() : null;
    }
    
    public boolean usernameExists(String username) {
        return userList.haveUser(username);
    }
    
    public int getTotalUsers() {
        return userList.getUsers().size();
    }
    
    public int getTotalRooms() {
        return roomList.getRoomCount();
    }
    
    public int getTotalItems() {
        return items.getItemCount();
    }
    
    public void reset() {
        logoutUser();
        loadGameData();
    }
    
    public String getGameStats() {
        return String.format(
            "Total Users: %d, Total Rooms: %d, Total Items: %d, User Logged In: %s",
            getTotalUsers(), getTotalRooms(), getTotalItems(), 
            isUserLoggedIn() ? "Yes" : "No"
        );
    }
}

class FacadeTest {
    public static void main(String[] args) {
        Facade facade = Facade.getInstance();
        
        System.out.println("=== Testing Facade Login ===\n");
        
        System.out.println("Test 1: Login with Player1");
        boolean success1 = facade.loginUser("Player1", "hashed_password_123");
        System.out.println("Login successful: " + success1);
        
        if (success1) {
            User user = facade.getCurrentUser();
            System.out.println("Logged in as: " + user.getUserName());
            System.out.println("Current level: " + facade.getCurrentUserLevel());
            
            Room currentRoom = facade.getCurrentRoom();
            if (currentRoom != null) {
                System.out.println("Current room: " + currentRoom.getName());
            } else {
                System.out.println("Current room: Not loaded yet (RoomList is empty)");
            }
        }
        
        System.out.println("\n---\n");
        facade.logoutUser();
        System.out.println("Test 2: Login with wrong password");
        boolean success2 = facade.loginUser("Player1", "wrong_password");
        System.out.println("Login successful: " + success2);
        System.out.println("User logged in: " + facade.isUserLoggedIn());
        
        System.out.println("\n---\n");
        
        System.out.println("Test 3: Register new user");
        boolean success3 = facade.registerUser("TestPlayer", "test123");
        System.out.println("Registration successful: " + success3);
        
        if (success3) {
            System.out.println("Auto-logged in: " + facade.isUserLoggedIn());
            System.out.println("Logged in as: " + facade.getCurrentUser().getUserName());
        }
        
        System.out.println("\n---\n");

        facade.logoutUser();
        System.out.println("Test 4: Register duplicate username");
        boolean success4 = facade.registerUser("Player1", "newpass");
        System.out.println("Registration successful: " + success4);
        
        System.out.println("\n=== Tests Complete ===");
    }
}