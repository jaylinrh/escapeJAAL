package com.escape.Model;

import java.util.ArrayList;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;

public class LibraryItemsPuzzle extends AbstractPuzzle {
    private final int DOOR_COL = 25; 
    private final int DOOR_ROW = 1; 
    private boolean doorOpened = false;
    private ArrayList<InteractableObject> torches;
    private int litTorches = 0;
    private boolean hasLighter;
    private boolean hasRecord;
    private boolean hasfragA;
    private boolean hasfragB;
    private boolean hasfragC;

    public LibraryItemsPuzzle(GameApp gameApp) {
        super(gameApp, "puzzle_LibraryItems", "Library Puzzle", "Find the hidden cipher.");
        initialize();
    }
    private void openDoor() {
        System.out.println("An unseen door creaks open...");
        doorOpened = true;
        
        
        // Makes DOOR_COL and DOOR_ROW match where door is in foyer.txt
        gameApp.tileM.setMapTile(DOOR_COL, DOOR_ROW, 10);
        onComplete(Facade.getInstance());
    }

    private void createTorch(int gridX, int gridY) {
        int pixelX = gridX * gameApp.tileSize;
        int pixelY = gridY * gameApp.tileSize;
        
        InteractableObject torch = new InteractableObject(
            "torch", 
            "torch", 
            "An unlit torch", 
            pixelX, pixelY, 
            "/items/unlit.png", 
            InteractableObject.ObjectType.TORCH
        );
        torches.add(torch);
    }
    @Override
    public void initialize() {
        torches = new ArrayList<>();
        
        //  creates torches around table
        // interactableObjects of type TORCHES
        int x = 21;
        int y = 19;
        for (int i = 0; i <=6; i++) {
            createTorch(x+i,y);
        }
        for (int i = 1; i <= 4; i++) {
            createTorch(x,y+i);
        }
        x = 27;
        for (int i = 1; i<=4; i++) {
            createTorch(x,y+i);
        }
        x = 21;
        y = 23;
        for (int i = 1; i<=5; i++) {
            createTorch(x+i,y);
        }
        
        gameApp.gameObjects.addAll(torches);
        
        // spawn the cipher pieces.
        spawnCipher("record",   2, 50);
        spawnCipher("lighter",   2, 3); 
        spawnCipher("fragment_a",   10, 5);
        spawnCipher("fragment_b", 10, 17);
        spawnCipher("fragment_c",   23, 25);
    }
    
    private void spawnCipher(String itemId, int col, int row) {
        Item itemData = Items.getInstance().getItemById(itemId);
        if (itemData == null) return;
        
        InteractableObject cipherObj = new InteractableObject(
            itemId, 
            itemData.getName(), 
            itemData.getDescription(), 
            col * gameApp.tileSize, 
            row * gameApp.tileSize, 
            "/items/" + itemId + ".png", 
            InteractableObject.ObjectType.ITEM
        );
        cipherObj.setItem(itemData);
        gameApp.gameObjects.add(cipherObj);
    }    


    @Override
    public void update() {
        litTorches = 0;
        if (solved) return;
        for (int i = 0; i < torches.size(); i++) {
            InteractableObject torch = torches.get(i);
            
            if (torch.getLit() == true) {
                litTorches++;
            }
        }
        hasCipher(Facade.getInstance());
        if (litTorches == torches.size() && hasLighter && hasRecord && hasfragA && hasfragB && hasfragC) {
            System.out.println("Library puzzle solved!");
            markSolved();
            openGreenhouseDoor();
        }
    }

    private void hasCipher(Facade facade) {
        Inventory playerInv1 = facade.getCurrentUserInventory();
        for(Item i : playerInv1.getItems()) {
            if(i.getItemId().startsWith("lighter")) {
                hasLighter = true;
            } else if (i.getItemId().startsWith("record")) {
                hasRecord = true;
            } else if (i.getItemId().startsWith("fragment_a")) {
                hasfragA = true;
            } else if (i.getItemId().startsWith("fragment_b")) {
                hasfragB = true;
            } else if (i.getItemId().startsWith("fragment_c")) {
                hasfragC = true;
            }
        }
    }

    private void openGreenhouseDoor() {
        System.out.println("GREENHOUSE UNLOCKED!");
        // have a '10' tile image (open door) 
        gameApp.tileM.setMapTile(24, 0, 10);
        Facade facade = Facade.getInstance();
        facade.solvePuzzle(puzzleId);
        facade.saveUserProgress();
    }


       @Override
    public void draw(GraphicsContext gc) {
            //leave empty.
            }
        
    
    @Override public void handleKeyPress(KeyEvent e) {}
    @Override public void handleMouseClick(MouseEvent e) {}
    @Override public void reset() {}
}
