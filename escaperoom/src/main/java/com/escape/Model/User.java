package com.escape.Model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.UUID;

public class User {
	private String username;
	private String password;
	private UUID UserID;
	Player Player;
	GameApp ga;
	KeyHandler kh;
	private ArrayList<GameSave> gameSaves;
    private UUID currentSaveId;
	private double volume;
	private double sfx;

	
	int currentLevel;
	
	//public User (String username, String password) {
	//	this.username = username;
	//	this.password = password;
	//	this.UserID = UUID.randomUUID();
	//}
	public User(UUID id, String username, String password, int level, double volume, double sfx, ArrayList<GameSave> gameSaves, UUID currentSaveId) {
		this.UserID = id;
		this.username = username;
		this.password = password;
		this.currentLevel = level;
		this.volume = volume;
		this.sfx = sfx;
		this.gameSaves = gameSaves != null ? gameSaves : new ArrayList<>();
		this.currentSaveId = currentSaveId;
		//Player = new Player(ga, kh);
	}

	public ArrayList<GameSave> getGameSaves() { 
        return gameSaves; 
    }
    
    public void addGameSave(GameSave save) {
        gameSaves.add(save);
    }
    
    public void removeGameSave(UUID saveId) {
        gameSaves.removeIf(save -> save.getSaveId().equals(saveId));
        if (currentSaveId != null && currentSaveId.equals(saveId)) {
            currentSaveId = null;
        }
    }
    
    public GameSave getGameSaveById(UUID saveId) {
        for (GameSave save : gameSaves) {
            if (save.getSaveId().equals(saveId)) {
                return save;
            }
        }
        return null;
    }
    
    public GameSave getCurrentSave() {
        if (currentSaveId == null) return null;
        return getGameSaveById(currentSaveId);
    }
    
    public void setCurrentSaveId(UUID saveId) {
        this.currentSaveId = saveId;
    }
    
    public UUID getCurrentSaveId() {
        return currentSaveId;
    }
    
    public boolean hasSaves() {
        return !gameSaves.isEmpty();
    }
	
	public void getData() {
		ArrayList<User> users = DataLoader.getUsers();
		if (users.isEmpty()) {
			System.out.println("No data found");

		}

		var userData = users.get(0);
		

	}
	public int getLevel () {
		return currentLevel;
	}

	public UUID getId() {
		return UserID;
	}

	public String getUserName() {
		return username;
	}

	public String getPassword() {
		return password;
	}

	public String getCurrentRoomID() {
		GameSave save = getCurrentSave();
		return save != null ? save.getCurrentRoomId() : "room_exterior";
	}

	public void setCurrentRoomID(String roomId) {
		GameSave save = getCurrentSave();
		if (save != null) {
			save.setCurrentRoomId(roomId);
		}
	}

	public Player getPlayer() {
		return Player;
	}

	public PlayerState getPlayerState() {
		GameSave save = getCurrentSave();
		return save != null ? save.getPlayerState() : null;
	}

	public Inventory getInventory() {
		GameSave save = getCurrentSave();
		return save != null ? save.getInventory() : null;
	}
	
	public void addLevel() {
		currentLevel++;
	}
	
	public void setLevel(int a) {
		currentLevel = a;
	}

	public void update() {
		
	}

	public HashSet<String> getVisitedRooms() {
		GameSave save = getCurrentSave();
		return save != null ? save.getVisitedRooms() : new HashSet<>();
	}

	public void setVisitedRooms(HashSet<String> visitedRooms) {
		GameSave save = getCurrentSave();
		if (save != null) {
			save.setVisitedRooms(visitedRooms);
		}
	}

	public HashSet<String> getCompletedRooms() {
		GameSave save = getCurrentSave();
		return save != null ? save.getCompletedRooms() : new HashSet<>();
	}

	public void setCompletedRooms(HashSet<String> completedRooms) {
		GameSave save = getCurrentSave();
		if (save != null) {
			save.setCompletedRooms(completedRooms);
		}
	}

	public HashSet<String> getSolvedPuzzles() {
		GameSave save = getCurrentSave();
		return save != null ? save.getSolvedPuzzles() : new HashSet<>();
	}

	public void setSolvedPuzzles(HashSet<String> solvedPuzzles) {
		GameSave save = getCurrentSave();
		if (save != null) {
			save.setSolvedPuzzles(solvedPuzzles);
		}
	}

	public void addVisitedRoom(String roomId) {
		GameSave save = getCurrentSave();
		if (save != null) {
			save.getVisitedRooms().add(roomId);
		}
	}

	public void addCompletedRoom(String roomId) {
		GameSave save = getCurrentSave();
		if (save != null) {
			save.getCompletedRooms().add(roomId);
		}
	}

	public void addSolvedPuzzle(String puzzleId) {
		GameSave save = getCurrentSave();
		if (save != null) {
			save.getSolvedPuzzles().add(puzzleId);
		}
	}

	public boolean hasVisitedRoom(String roomId) {
		GameSave save = getCurrentSave();
		return save != null && save.getVisitedRooms().contains(roomId);
	}

	public boolean hasSolvedPuzzle(String puzzleId) {
		GameSave save = getCurrentSave();
		return save != null && save.getSolvedPuzzles().contains(puzzleId);
	}

	public double getVolume() {
    return volume;
	}

	public void setVolume(double volume) {
		this.volume = volume;
	}

	public double getSfx() {
		return sfx;
	}

	public void setSfx(double sfx) {
		this.sfx = sfx;
	}
	
	@Override
	public String toString() {
		return "Player: " + username + " (Level " +currentLevel + ", Saves: " + gameSaves.size() + ")";
	}
	
}
