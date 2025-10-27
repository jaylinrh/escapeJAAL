package com.escape.Model;

import java.util.ArrayList;
import java.util.Scanner;

import com.escape.speech.Speak;

public class EscapeGameTest {
    public static void main(String[] args) {
        Facade facade = Facade.getInstance();

        System.out.println("=== Escape Game ===");

        Scanner scanner = new Scanner(System.in); 

        System.out.print("Username: ");
        String testUsername = scanner.nextLine();
        System.out.print("Password: ");
        String testPassword = scanner.nextLine();

        boolean loggedIn = facade.loginUser(testUsername, testPassword);

        if (!loggedIn) {
            System.out.println("No existing user '" + testUsername + "' or wrong password. Creating account '" + testUsername + "'");
            boolean registered = facade.registerUser(testUsername, testPassword);
            System.out.println("Registered: " + registered);
            if (registered) {
                loggedIn = facade.loginUser(testUsername, testPassword);
            }
        }

        if (!loggedIn) {
            System.out.println("Could not log in or register user. Aborting test.");
            return;
        }

        System.out.println("\n--- Initial user/player info ---");
        User current = facade.getCurrentUser();
        if (current == null) {
            System.out.println("Logged in user is null (unexpected).");
            return;
        }

        System.out.println("Username: " + current.getUserName());
        System.out.println("User ID: " + current.getId());
        System.out.println("Level: " + current.getLevel());
        System.out.println("Current Room ID: " + (current.getCurrentRoomID() == null ? "(none)" : current.getCurrentRoomID()));
        System.out.println("Inventory: " + (current.getInventory() == null ? "(none)" : current.getInventory().toString()));

        PlayerState ps = facade.getCurrentUserPlayerState();
        if (ps != null) {
            System.out.println("PlayerState: worldX=" + ps.getWorldX() + ", worldY=" + ps.getWorldY() + ", speed=" + ps.getSpeed() + ", dir=" + ps.getDirection());
        } else {
            System.out.println("PlayerState: (none)");
        }

        ArrayList<String> dialogueLines = facade.getDialogueForRoom("room_exterior");
        for (String line : dialogueLines) {
            System.out.println(line);
            Speak.speak(line);
        }

        System.out.println("\n--- Progressing player: solving puzzle 'puzzle1' ---");
        // Mark puzzle solved in progression
        facade.solvePuzzle("puzzle1");


        System.out.println("Would you like to pick up the item? (Y/N)");
        Speak.speak("Would you like to pick up the item? (Y/N)");

        String choice = scanner.nextLine();
        if ("Y".equalsIgnoreCase(choice.trim())) {
            
        String candidateItemId = "test_item"; // an ID to attempt
        boolean pickedUp = false;

        Item existingItem = facade.getItemById(candidateItemId);

        if (existingItem != null) {
        // if item exists in repository
            pickedUp = facade.pickupItem(candidateItemId);
            System.out.println("Picked up existing item via Facade: " + pickedUp);
        } 
        else {
            // otherwise create a new one and manually register it in progression
            Item newItem = new Item(candidateItemId, "Test Item", "Hint for test item", "Description of test item");
            Inventory inv = current.getInventory();

            if (inv != null) {
                inv.addItem(newItem);
                pickedUp = true;
                System.out.println("New Item: " + newItem.getName());
                System.out.println("Item description: " + newItem.getDescription());
                System.out.println("You have received a hint from the item! Your hint is: ");
                System.out.print(newItem.getHint() + "\n");

                // tell progression about it
                Progression prog = facade.getProgression();
                if (prog != null) {
                prog.collectItem(candidateItemId);
                System.out.println("Recorded item collection in Progression.");
                }
            }   
        }
        facade.completeRoom("room1");
        facade.levelUpCurrentUser();
        facade.saveUserProgress();

        ArrayList<String> dialogueLines2 = facade.getDialogueForRoom("room_foyer");
        for (String line : dialogueLines2) {
            System.out.println(line);
            Speak.speak(line);
        }
        System.out.println("Would you like to pick up the item: (Y/N)");
        String choice2 = scanner.nextLine();

        if ("Y".equalsIgnoreCase(choice2.trim())) {
        String candidateItemId2 = "test_item2"; // an ID to attempt
        boolean pickedUp2 = false;
        Item existingItem2 = facade.getItemById(candidateItemId);

        if (existingItem2 != null) {
        // if item exists in repository
            pickedUp2 = facade.pickupItem(candidateItemId2);
            System.out.println("Picked up existing item via Facade: " + pickedUp2);
        } 
        else {
            // otherwise create a new one and manually register it in progression
            Item newItem = new Item(candidateItemId2, "Test Item", "Hint for test item", "Description of test item");
            Inventory inv = current.getInventory();

            if (inv != null) {
                inv.addItem(newItem);
                pickedUp = true;
                System.out.println("New Item: " + newItem.getName());
                System.out.println("Item description: " + newItem.getDescription());
                System.out.println("You have received a hint from the item! Your hint is: ");
                System.out.print(newItem.getHint() + "\n");

                // tell progression about it
                Progression prog = facade.getProgression();
                if (prog != null) {
                    prog.collectItem(candidateItemId2);
                    System.out.println("Recorded item collection in Progression.");
                    }
                }   
            }
        }
        facade.completeRoom("room2");
        facade.levelUpCurrentUser();
        facade.saveUserProgress();
        ArrayList<String> dialogueLines3 = facade.getDialogueForRoom("room_parlor");
        for (String line : dialogueLines3) {
            System.out.println(line);
            Speak.speak(line);
        }
        System.out.println("Would you like to pick up the item: (Y/N)");
        String choice3 = scanner.nextLine();

        if ("Y".equalsIgnoreCase(choice2.trim())) {
        String candidateItemId3 = "test_item3"; // an ID to attempt
        boolean pickedUp3 = false;
        Item existingItem3 = facade.getItemById(candidateItemId);

        if (existingItem3 != null) {
        // if item exists in repository
            pickedUp3 = facade.pickupItem(candidateItemId3);
            System.out.println("Picked up existing item via Facade: " + pickedUp3);
        } 
        else {
            // otherwise create a new one and manually register it in progression
            Item newItem = new Item(candidateItemId3, "Test Item", "Hint for test item", "Description of test item");
            Inventory inv = current.getInventory();

            if (inv != null) {
                inv.addItem(newItem);
                pickedUp = true;
                System.out.println("New Item: " + newItem.getName());
                System.out.println("Item description: " + newItem.getDescription());
                System.out.println("You have received a hint from the item! Your hint is: ");
                System.out.print(newItem.getHint() + "\n");

                // tell progression about it
                Progression prog = facade.getProgression();
                if (prog != null) {
                    prog.collectItem(candidateItemId3);
                    System.out.println("Recorded item collection in Progression.");
                    }
                }   
            }
        }
        facade.completeRoom("room3");
        facade.levelUpCurrentUser();
        facade.saveUserProgress();
    }   


       
        
        



        System.out.println("\n--- Post-progression status ---");
        System.out.println("Username: " + current.getUserName());
        System.out.println("User ID: " + current.getId());
        System.out.println("Level: " + current.getLevel());
        System.out.println("Inventory: " + (current.getInventory() == null ? "(none)" : current.getInventory().toString()));

        Progression prog = facade.getProgression();
        if (prog != null) {
            System.out.println("\nProgression summary:");
            System.out.println(prog.getProgressReport());
        } else {
            System.out.println("No progression object available.");
        }

        facade.getLeaderboard();
        
        System.out.println("=== TestGame complete ===");
    }
}
 