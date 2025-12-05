package com.escape.Model;

import java.io.InputStream;
import java.util.ArrayList;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.shape.StrokeLineJoin;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class UI {
    GameApp ga;
    Font arial_40, arial_80B;
    public boolean messageOn = false;
    public String message = "";
    public boolean gameFinished = false;
    public String currentText = "Welcome to the Escape Room";
    public String[] dialogues = new String[20];
    public int currentDialogueIndex = 0;
    public boolean showInteractionPrompt = false;
    public String interactionPrompt = "";
    public UI(GameApp ga) {
        this.ga = ga;
    }

    public void draw(GraphicsContext gc) {

        gc.setFont(arial_40);
        gc.setFill(Color.WHITE);
        
        if(ga.gameState == ga.playState) {
            if (showInteractionPrompt) {
                drawInteractionPrompt(gc);
            }
        }
        if(ga.gameState == ga.dialogueState) {
            drawDialogueScreen(gc);
        }
        if(ga.gameState == ga.pauseState) {
            drawPauseScreen(gc);
        }
        if(ga.gameState == ga.inventoryState) {
            drawInventoryScreen(gc);
        }
        if(ga.gameState == ga.cutsceneState) {
            drawcutsceneScene(gc);
        }
    }

    private void drawInteractionPrompt(GraphicsContext gc) {
        gc.setFont(Font.font("Arial", FontWeight.BOLD, 20));
        gc.setFill(Color.web("#FFD700")); // Gold color
        
        int x = ga.screenWidth / 2 - 100;
        int y = ga.screenHeight - 100;
        
        gc.fillText(interactionPrompt, x, y);
    }
    
    public void setDialogue() {
        dialogues[0] = "Welcome to the Escape Room";
        dialogues[1] = "Make sure to have fun";
        dialogues[2] = "Now go and escape!";
        dialogues[3] = "Ciao!";
        currentDialogueIndex = 0;
        currentText = dialogues[currentDialogueIndex];
    }
    
    public String getLevel() {
        return "1";
    }
    
    public void drawDialogueScreen(GraphicsContext gc) {
        int x = ga.tileSize * 2;
        int y = ga.tileSize / 2;
        int width = ga.screenWidth - (ga.tileSize * 4);
        int height = ga.tileSize * 4;
        
        drawSubWindow(gc, x, y, width, height);
        
        gc.setFont(Font.font("Arial", FontWeight.NORMAL, 32));
        x += ga.tileSize;
        y += ga.tileSize;
        
        gc.setFill(Color.WHITE);
        gc.fillText(currentText, x, y);
    }
    
    public void drawPauseScreen(GraphicsContext gc) {
        int x = ga.tileSize * 4;
        int y = ga.tileSize / 2;
        
        drawSubWindow(gc, x, y, 350, 500);
        
        x += ga.tileSize;
        y += ga.tileSize;
        
        gc.setFont(arial_40);
        gc.setFill(Color.WHITE);
        gc.fillText("Pause Menu", x, y);
        gc.fillText("Current Level: " + getLevel(), x, y * 4);
    }

    public void drawInventoryScreen(GraphicsContext gc) {
        int x = ga.tileSize * 2;
        int y = ga.tileSize;
        int width = ga.screenWidth - (ga.tileSize * 4);
        int height = ga.screenHeight - (ga.tileSize * 2);
    
        drawSubWindow(gc, x, y, width, height);
    
        gc.setFont(Font.font("Arial", FontWeight.BOLD, 32));
        gc.setFill(Color.WHITE);
        gc.fillText("Inventory", x + ga.tileSize, y + ga.tileSize);
    
    User currentUser = Facade.getInstance().getCurrentUser();
    if (currentUser != null) {
        Inventory inv = currentUser.getInventory();
        ArrayList<Item> items = inv.getItems();
        
        int itemX = x + ga.tileSize;
        int itemY = y + (ga.tileSize * 2);
        
        gc.setFont(Font.font("Arial", FontWeight.NORMAL, 24));
        for (int i = 0; i < items.size(); i++) {
            Item item = items.get(i);
            gc.fillText((i + 1) + ". " + item.getName(), itemX, itemY + (i * 40));
        }
        
        if (items.isEmpty()) {
            gc.fillText("No items collected yet", itemX, itemY);
        }
        
        gc.setFont(Font.font("Arial", FontWeight.NORMAL, 20));
        String capacityText = "Capacity: " + items.size() + " / " + inv.getMaxCapacity();
        gc.fillText(capacityText, itemX, itemY + (items.size() * 40) + 60);
    }
    
        gc.setFont(Font.font("Arial", FontWeight.NORMAL, 20));
        gc.fillText("Press I to close", x + ga.tileSize, y + height - ga.tileSize);
    }



    
    private void drawSubWindow(GraphicsContext gc, int x, int y, int width, int height) {
    gc.setFill(Color.BLACK);
    gc.fillRoundRect(x, y, width, height, 35, 35);
    
    gc.setStroke(Color.WHITE);
    gc.setLineWidth(5);
    gc.setLineJoin(StrokeLineJoin.ROUND);
    gc.strokeRoundRect(x, y, width, height, 35, 35);
    }

        // add these two helper methods to set the cutscene image
    private Image cutsceneImage = null; 
    
    public void setCutsceneImage(Image img) {
        this.cutsceneImage = img;
    }

    /**
     * convenience: load an image from a resource or file path
     * example usage: ui.setCutsceneImageFromPath("/images/scene1.png");
     * or a file path: ui.setCutsceneImageFromPath("file:/C:/myimages/scene1.png");
     */
    public void setCutsceneImageFromPath(String path) {
        try {
        InputStream is = getClass().getResourceAsStream(path);
        if (is == null) {
            System.err.println("Resource not found: " + path);
            return;
        }
        this.cutsceneImage = new Image(is, ga.screenWidth, ga.screenHeight, false, true);
    } catch (Exception e) {
        System.err.println("Failed to load cutscene image: " + e);
    }
    }

    // replace the empty implementation with this:
    public void drawcutsceneScene(GraphicsContext gc) {
        // 1) draw background image stretched to screen (or a fallback)
        if (cutsceneImage != null && !cutsceneImage.isError()) {
            // drawImage(Image img, double x, double y, double w, double h)
            gc.drawImage(cutsceneImage, 0, 0, ga.screenWidth, ga.screenHeight);
        } else {
            // fallback background if no image available
            gc.setFill(Color.BLACK);
            gc.fillRect(0, 0, ga.screenWidth, ga.screenHeight);
        }

        // 2) draw a semi-transparent overlay to ensure dialogue is readable
        double overlayHeight = ga.tileSize * 4; // same height you use elsewhere
        double overlayY = ga.screenHeight - overlayHeight - ga.tileSize / 2;
        gc.setFill(new Color(0, 0, 0, 0.45)); // translucent black overlay
        gc.fillRoundRect(ga.tileSize, overlayY, ga.screenWidth - (ga.tileSize * 2), overlayHeight, 20, 20);

        // 3) draw dialogue sub-window using your helper so style matches other windows
        int x = ga.tileSize;
        int y = (int) overlayY;
        int width = ga.screenWidth - (ga.tileSize * 2);
        int height = (int) overlayHeight;
        drawSubWindow(gc, x, y, width, height);

        // 4) draw the current dialogue text inside the subwindow
        gc.setFont(Font.font("Arial", FontWeight.NORMAL, 28));
        gc.setFill(Color.WHITE);

        // text padding
        int textX = x + ga.tileSize / 2;
        int textY = y + ga.tileSize;
        // handle multi-line text (split on newline if you set currentText with '\n')
        String[] lines = currentText.split("\n");
        int lineHeight = 36; // adjust to your font size
        for (int i = 0; i < lines.length; i++) {
            gc.fillText(lines[i], textX, textY + (i * lineHeight));
        }

        // 5) draw a small "press key to continue" hint in the bottom-right of the dialogue box
        gc.setFont(Font.font("Arial", FontWeight.BOLD, 18));
        gc.setFill(Color.web("#FFD700")); // gold hint color
        String hint = "Press SPACE to continue";
        double hintX = x + width - ga.tileSize - gc.getFont().getSize() * hint.length() * 0.25;
        double hintY = y + height - (ga.tileSize / 2);
        gc.fillText(hint, hintX, hintY);
    }

}