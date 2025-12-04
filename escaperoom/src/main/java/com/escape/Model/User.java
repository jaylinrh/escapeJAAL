package com.escape.Model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.UUID;

public class User {
	private String username;
	private String password;
	private UUID UserID;
	private String currentRoomId;
	Player Player;
	PlayerState playerState;
	Inventory inventory;
	GameApp ga;
	KeyHandler kh;
	private HashSet<String> visitedRooms;
	private HashSet<String> completedRooms;
	private HashSet<String> solvedPuzzles;
	private double volume;
	private double sfx;

	
	int currentLevel;
	
	//public User (String username, String password) {
	//	this.username = username;
	//	this.password = password;
	//	this.UserID = UUID.randomUUID();
	//}
	public User(UUID id, String username, String password, int level, String currentRoomId, PlayerState playerState, Inventory inventory, HashSet<String> visitedRooms, HashSet<String> completedRooms, HashSet<String> solvedPuzzles, double volume, double sfx) {
		this.UserID = id;
		this.username = username;
		this.password = password;
		this.currentLevel = level;
		this.currentRoomId = currentRoomId;
		this.inventory = inventory;
		this.playerState = playerState;
		this.visitedRooms = visitedRooms != null ? visitedRooms : new HashSet<>();
		this.completedRooms = completedRooms != null ? completedRooms : new HashSet<>();
		this.solvedPuzzles = solvedPuzzles != null ? solvedPuzzles : new HashSet<>();
		//Player = new Player(ga, kh);
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
		return currentRoomId;
	}

	public void setCurrentRoomID(String roomId) {
		this.currentRoomId = roomId;
	}

	public Player getPlayer() {
		return Player;
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

	public HashSet<String> getVisitedRooms() {
    return visitedRooms;
	}

	public void setVisitedRooms(HashSet<String> visitedRooms) {
		this.visitedRooms = visitedRooms;
	}

	public HashSet<String> getCompletedRooms() {
		return completedRooms;
	}

	public void setCompletedRooms(HashSet<String> completedRooms) {
		this.completedRooms = completedRooms;
	}

	public HashSet<String> getSolvedPuzzles() {
		return solvedPuzzles;
	}

	public void setSolvedPuzzles(HashSet<String> solvedPuzzles) {
		this.solvedPuzzles = solvedPuzzles;
	}

	public void addVisitedRoom(String roomId) {
		visitedRooms.add(roomId);
	}

	public void addCompletedRoom(String roomId) {
		completedRooms.add(roomId);
	}

	public void addSolvedPuzzle(String puzzleId) {
		solvedPuzzles.add(puzzleId);
	}

	public boolean hasVisitedRoom(String roomId) {
		return visitedRooms.contains(roomId);
	}

	public boolean hasSolvedPuzzle(String puzzleId) {
		return solvedPuzzles.contains(puzzleId);
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
		return "Player: " + username + " (Level " +currentLevel + ", Room: " + currentRoomId + ")";
	}
	
}
