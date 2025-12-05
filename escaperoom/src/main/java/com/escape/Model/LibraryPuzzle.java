package com.escape.Model;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import java.util.ArrayList;

public class LibraryPuzzle extends AbstractPuzzle {

    private ArrayList<InteractableObject> slots;
    private boolean doorOpened = false;
    
    
    // WhEre the empty slots (Bookshelf) are located
    private final int SHELF_START_X = 18; 
    private final int SHELF_START_Y = 25;  
    
    //door location
    private final int DOOR_COL = 25;
    private final int DOOR_ROW = 1;

    public LibraryPuzzle(GameApp gameApp) {
        super(gameApp, "puzzle_library", "The Lost Archives", "Arrange the books to spell the password.");
        initialize();
    }

    @Override
    public void initialize() {
        slots = new ArrayList<>();
        
        //  create the slots (L-I-B-R-A-R-Y)
        // interactableObjects of type SLOT
        createSlot(0, "letter_l", SHELF_START_X);
        createSlot(1, "letter_i", SHELF_START_X + 1);
        createSlot(2, "letter_b", SHELF_START_X + 2);
        createSlot(3, "letter_r_1", SHELF_START_X + 3);
        createSlot(4, "letter_a", SHELF_START_X + 4);
        createSlot(5, "letter_r_2", SHELF_START_X + 5);
        createSlot(6, "letter_y", SHELF_START_X + 6);
        
        
        gameApp.gameObjects.addAll(slots);
        
        // spawn the books on the floor.
        spawnBook("letter_l",   15, 15); // On the rug
        spawnBook("letter_i",   18, 12); 
        spawnBook("letter_b",   12, 18); // on the rug
        spawnBook("letter_r_1", 22, 15);
        spawnBook("letter_a",   25, 10);
        spawnBook("letter_r_2", 15, 22); // Near bottom
        spawnBook("letter_y",   28, 14);

        Progression prog = Facade.getInstance().getProgression();
        if (prog != null && prog.hasSolvedPuzzle(puzzleId)) {
            System.out.println("Library puzzle already solved");
            this.solved = true;
        }
    }

    public void onRoomLoaded() {
        if (solved && gameApp.tileM != null) {
            System.out.println("Opening library door (puzzle was already solved)");
            gameApp.tileM.setMapTile(24, 0, 10);
        }
    }
    
    private void createSlot(int index, String requiredId, int gridX) {
        int pixelX = gridX * gameApp.tileSize;
        int pixelY = SHELF_START_Y * gameApp.tileSize;
        
        InteractableObject slot = new InteractableObject(
            "slot_" + index, 
            "Empty Shelf Slot", 
            "A dusty gap in the books.", 
            pixelX, pixelY, 
            "/items/slot_empty.png", 
            InteractableObject.ObjectType.SLOT
        );
        slot.setRequiredItemId(requiredId);
        slots.add(slot);
    }
    
    private void spawnBook(String itemId, int col, int row) {
        Item itemData = Items.getInstance().getItemById(itemId);
        if (itemData == null) return;
        
        InteractableObject bookObj = new InteractableObject(
            itemId, 
            itemData.getName(), 
            itemData.getDescription(), 
            col * gameApp.tileSize, 
            row * gameApp.tileSize, 
            "/items/" + itemId + ".png", 
            InteractableObject.ObjectType.ITEM
        );
        bookObj.setItem(itemData);
        gameApp.gameObjects.add(bookObj);
    }

    @Override
    public void update() {
        if (solved) return;
        
        String[] password = {"letter_l", "letter_i", "letter_b", "letter_r", "letter_a", "letter_r", "letter_y"};
        int correctCount = 0;
        

        for (int i = 0; i < slots.size(); i++) {
            InteractableObject slot = slots.get(i);
            Item itemInside = slot.getContainedItem();
            
            if (itemInside != null && itemInside.getItemId().startsWith(password[i])) {
                correctCount++;
            }
        }
        
        if (correctCount == 7) {
            System.out.println("!!! ALL 7 CORRECT - OPENING DOOR !!!");
            markSolved();
            openLibraryDoor();
        }
    }
    
    private void openLibraryDoor() {
        System.out.println("LIBRARY UNLOCKED!");
        // have a '10' tile image (open door) 
        gameApp.tileM.setMapTile(24, 0, 10);
    }

    @Override
    public void draw(GraphicsContext gc) {
            //leave empty.
            }
        
    

    @Override public void handleKeyPress(KeyEvent e) {}
    @Override public void handleMouseClick(MouseEvent e) {}
    @Override public void reset() {}
}