package com.escape.Model;
import java.util.Scanner;

public class TestGame {
    
	
	public static void main(String[] args) {
		Scanner key = new Scanner(System.in);

		String dl[] = {"hi", "b"};
		
		/*
		 * These are all test methods, will not be in the final test game
		 */
		Room Room1 = new Room("roomID", "room", "description", "mapFIle", "musicFile", dl, true);
		Puzzle Puzzle = Room1.getPuzzle();
		System.out.println(Puzzle.getLevel());

		System.out.println("Welcome to the Escape Game!");
		System.out.println("Good luck escpaing!");

		System.out.println("Enter Room 1");
		System.out.println("Puzzle Lvl: " + Puzzle.getLevel() + " Puzzle Title: " + Puzzle.getTitle());

		Item item = new Item("id", "name", "no hint", "description");
		
		boolean t = false;
		while (t == false) {
			System.out.println("Get item 1?");
			int choice = key.nextInt();

			t = true;

		}


	}

}

