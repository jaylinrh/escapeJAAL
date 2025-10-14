package com.escape.Model;


public class TestGame {
    
	
	public static void main(String[] args) {

		String dl[] = {"hi", "b"};
		
		Room Room = new Room("roomID", "room", "description", "mapFIle", "musicFile", dl, true);
		Puzzle Puzzle = Room.getPuzzle();
		System.out.println(Puzzle.getLevel());

		System.out.println("Welcome to the Escape Game!");
		System.out.println("Good luck escpaing!");

		System.out.println("Enter Room 1");
		boolean t = false;
		(while t == false） {
			System.out.println("");
		}


	}

}

