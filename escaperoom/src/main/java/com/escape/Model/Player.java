package com.escape.Model;

import java.util.UUID;

public class Player {
	private String username;
	private String password;
	private UUID UserID;
	private String currentRoomId;

	
	int currentLevel;
	
	public Player (String username, String password) {
		this.username = username;
		this.password = password;
		this.UserID = UUID.randomUUID();
	}
	public Player(UUID id, String username, String password, int level, String currentRoomId) {
		this.UserID = id;
		this.username = username;
		this.password = password;
		this.currentLevel = level;
		this.currentRoomId = currentRoomId;
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
	
	public void addLevel() {
		currentLevel++;
	}
	
	public void setLevel(int a) {
		currentLevel = a;
	}

	public void update() {
		
	}
	
	
}
