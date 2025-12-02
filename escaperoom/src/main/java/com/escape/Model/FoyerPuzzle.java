package com.escape.Model;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

public class FoyerPuzzle extends AbstractPuzzle {
    
    private TileSteppingPuzzle tileSteppingPuzzle;
    private boolean doorOpened = false;

    // DOOR LOCATION!!!
    private final int DOOR_COL = 24; 
    private final int DOOR_ROW = 0; 
    
    public FoyerPuzzle(GameApp gameApp) {
        super(gameApp, "puzzle_foyer", "Foyer Puzzle", "Step on the 4 correct tiles.");
        initialize();
    }
    
    @Override
    public void initialize() {
        // Initialize with tile size only
        tileSteppingPuzzle = new TileSteppingPuzzle(gameApp.tileSize);

        // Setup: DEFINE 4 PUZZLE TILES HERE
        tileSteppingPuzzle.addTargetTile(19, 11);
        tileSteppingPuzzle.addTargetTile(11, 34);
        tileSteppingPuzzle.addTargetTile(38, 15);
        tileSteppingPuzzle.addTargetTile(30, 38);
        
        System.out.println("Foyer Puzzle Initialized.");
    }
    
    @Override
    public void update() {
        if (solved) return;
        
        // pass player coordinates to the logic
        boolean updateHappened = tileSteppingPuzzle.update(gameApp.player.worldX, gameApp.player.worldY);
        
        // check for win condition
        if (tileSteppingPuzzle.isPuzzleComplete()) {
            markSolved();
            openDoor();
        }
    }
    
    private void openDoor() {
        System.out.println("PUZZLE SOLVED: Opening Door!");
        doorOpened = true;
        
        
        // Makes DOOR_COL and DOOR_ROW match where door is in foyer.txt
        gameApp.tileM.setMapTile(DOOR_COL, DOOR_ROW, 10); 
    }

    @Override
    public void draw(GraphicsContext gc) {
        if (!solved) {
            //draw a helper to show where the triggers are for debugging.
            drawDebugOverlay(gc);
        }
    }

    private void drawDebugOverlay(GraphicsContext gc) {
        // this draws a faint yellow box over the trigger zones.
        int pX = gameApp.player.worldX;
        int pY = gameApp.player.worldY;
        int sX = gameApp.player.screenX;
        int sY = gameApp.player.screenY;

        // iterate a range around the player to check for overlays
        int range = 10; 
        int pCol = pX / gameApp.tileSize;
        int pRow = pY / gameApp.tileSize;

        for(int c = pCol - range; c < pCol + range; c++) {
            for(int r = pRow - range; r < pRow + range; r++) {
                if(tileSteppingPuzzle.isRequiredTile(c, r)) {
                    int screenX = c * gameApp.tileSize - pX + sX;
                    int screenY = r * gameApp.tileSize - pY + sY;
                    
                    if(tileSteppingPuzzle.isTileActivated(c, r)) {
                        gc.setFill(Color.rgb(0, 255, 0, 0.4)); // Green if stepped on
                    } else {
                        gc.setFill(Color.rgb(255, 255, 0, 0.4)); // Yellow if not stepped on 
                    }
                    gc.fillRect(screenX, screenY, gameApp.tileSize, gameApp.tileSize);
                }
            }
        }
    }

    @Override public void handleKeyPress(KeyEvent e) {}
    @Override public void handleMouseClick(MouseEvent e) {}
    @Override public void reset() { tileSteppingPuzzle.reset(); solved = false; }
}