package com.escape.Model;

import java.util.UUID;

public class Player {
	String username;
	String password;
	UUID UserID;
	
	int currentLevel;
	
	public Player (String username, String password) {
		this.username = username;
		this.password = password;
		this.UserID = UUID.randomUUID();
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
