package com.escape.Model;

import java.util.UUID;

public class User {
	private String username;
	private String password;
	private UUID UserID;
	private String currentRoomId;
	PlayerState playerState;
	Inventory inventory;

	
	int currentLevel;
	
	public User (String username, String password) {
		this.username = username;
		this.password = password;
		this.UserID = UUID.randomUUID();
	}
	public User(UUID id, String username, String password, int level, String currentRoomId, PlayerState playerState, Inventory inventory) {
		this.UserID = id;
		this.username = username;
		this.password = password;
		this.currentLevel = level;
		this.currentRoomId = currentRoomId;
		this.playerState = playerState;
		this.inventory = inventory;
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
		return currentRoomId;
	}

	public PlayerState getPlayerState() {
		return playerState;
	}

	public Inventory getInventory() {
		return inventory;
	}
	
	public void addLevel() {
		currentLevel++;
	}
	
	public void setLevel(int a) {
		currentLevel = a;
	}

	public void update() {
		
	}
	
	@Override
	public String toString() {
		return "Player: " + username + " (Level " +currentLevel + ", Room: " + currentRoomId + ")";
	}
	
}
