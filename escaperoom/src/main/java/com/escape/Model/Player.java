package com.escape.Model;

import java.util.UUID;

public class Player {
	String username;
	String password;
	UUID UserID;
	String currentRoomdId;
	
	int currentLevel;
	 String currentRoomId;
	
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
	
	public void addLevel() {
		currentLevel++;
	}
	
	public void setLevel(int a) {
		currentLevel = a;
	}
	
	public void update() {
		
	}
	
	
}
