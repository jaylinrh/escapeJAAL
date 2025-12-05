package com.escape.Model;

import java.util.ArrayList;
import java.util.UUID;

public class Facade {
    private static Facade instance;
    
    UserList userList;
    private RoomList roomList;
    private Items items;
    private GameSave currentSave;
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
                this.currentSave = user.getCurrentSave();
                if (currentSave != null) {
                    this.progression = new Progression(currentUser);
                } else {
                    this.progression = null;
                }
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
            if (progression != null) {
                progression.syncToUser();
            }
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
    public ArrayList<String> getDialogueForRoom(String roomId) {
    Room room = roomList.getRoomById(roomId);
    if (room != null) {
        return room.getDialogues();
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
    
    public Player getCurrentPlayer() {
        return currentUser != null ? currentUser.getPlayer() : null;
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

    public double getVolume() {
        if (currentUser != null) {
            return currentUser.getVolume();
        }
        return 50.0;
    }

    public void setVolume(double volume) {
        if (currentUser != null) {
            currentUser.setVolume(volume);
        }
    }

    public double getSfx() {
        if (currentUser != null) {
            return currentUser.getSfx();
        }
        return 50.0;
    }

    public void setSfx(double sfx) {
        if (currentUser != null) {
            currentUser.setSfx(sfx);
        }
    }

    public GameSave createNewGame(String difficulty) {
        if (currentUser == null) return null;
        
        int saveNumber = currentUser.getGameSaves().size() + 1;
        String saveName = "Save " + saveNumber;
        
        GameSave newSave = new GameSave(saveName, difficulty);
        currentUser.addGameSave(newSave);
        currentUser.setCurrentSaveId(newSave.getSaveId());
        currentSave = newSave;
        
        progression = new Progression(currentUser);
        
        saveUserProgress();
        
        System.out.println("Created new game: " + saveName + " (" + difficulty + ")");
        return newSave;
    }

    public boolean loadGameSave(UUID saveId) {
        if (currentUser == null) return false;
        
        GameSave save = currentUser.getGameSaveById(saveId);
        if (save == null) {
            System.err.println("Save not found: " + saveId);
            return false;
        }
        
        currentUser.setCurrentSaveId(saveId);
        currentSave = save;
        save.setLastPlayedAt(System.currentTimeMillis());
        
        progression = new Progression(currentUser);
        
        System.out.println("Loaded game: " + save.getSaveName());
        return true;
    }

    public boolean deleteGameSave(UUID saveId) {
        if (currentUser == null) return false;
        
        currentUser.removeGameSave(saveId);
        
        if (currentSave != null && currentSave.getSaveId().equals(saveId)) {
            currentSave = null;
        }
        
        saveUserProgress();
        System.out.println("Deleted save: " + saveId);
        return true;
    }

    public GameSave getCurrentSave() {
        return currentSave;
    }

    public ArrayList<GameSave> getUserSaves() {
        if (currentUser == null) return new ArrayList<>();
        return currentUser.getGameSaves();
    }

    public boolean userHasSaves() {
        return currentUser != null && currentUser.hasSaves();
    }
    
    public void getLeaderboard() {
        ArrayList<User> users = userList.getUsers();

        users.sort((u1, u2) -> Integer.compare(u2.getLevel(), u1.getLevel()));
        
        int rank = 1;
        for (User u : users) {
            System.out.printf("%d. %s - Level %d%n", rank++, u.getUserName(), u.getLevel());
        }
    }
    public String getGameStats() {
        return String.format(
            "Total Users: %d, Total Rooms: %d, Total Items: %d, User Logged In: %s",
            getTotalUsers(), getTotalRooms(), getTotalItems(), 
            isUserLoggedIn() ? "Yes" : "No"
        );
    }
}
