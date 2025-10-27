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
        String candidateItemId = "test_item_2"; // an ID to attempt
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

        ArrayList<String> dialogueLines2 = facade.getDialogueForRoom("room_parlor");
        for (String line : dialogueLines2) {
            System.out.println(line);
            Speak.speak(line);
        }
        existingItem = facade.getItemById(candidateItemId);

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
    }


       
        
        

        
        // Optionally mark a room complete (if you want)
        facade.completeRoom("room1");

        // Level up the user once to reflect progress
        facade.levelUpCurrentUser();

        // Save progress to make it persistent (calls DataWriter via UserList.saveUsers())
        facade.saveUserProgress();

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
 