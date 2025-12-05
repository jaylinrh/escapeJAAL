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
    private int quitBtnX, quitBtnY, quitBtnW, quitBtnH;
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
        int width = 350;
        int height = 500;
        
        drawSubWindow(gc, x, y, 350, 500);
        
        x += ga.tileSize;
        y += ga.tileSize;
        
        gc.setFont(arial_40);
        gc.setFill(Color.WHITE);
        gc.fillText("Pause Menu", x, y);
        gc.fillText("Current Level: " + getLevel(), x, y * 80);

        String roomName = "Unknown";
        if (Facade.getInstance().getCurrentUser() != null) {
            String rawId = Facade.getInstance().getCurrentUser().getCurrentRoomID();
            roomName = getTheRoomName(rawId);
        }
        gc.fillText("Location: " + roomName, x, y + 110);

        quitBtnW = 200;
        quitBtnH = 50;

        quitBtnX = (ga.tileSize * 4) + (350 - quitBtnW) / 2;
        quitBtnY = (ga.tileSize / 2) + 400;

        gc.setFill(Color.web("#d8890aff"));
        gc.fillRoundRect(quitBtnX, quitBtnY, quitBtnW, quitBtnH, 10, 10);

        gc.setStroke(Color.WHITE);
        gc.setLineWidth(2);
        gc.strokeRoundRect(quitBtnX, quitBtnY, quitBtnW, quitBtnH, 10, 10);

        gc.setFill(Color.WHITE);
        gc.setFont(Font.font("Arial", FontWeight.BOLD, 24));
        gc.fillText("Quit to Menu", quitBtnX + 25, quitBtnY + 33);
    }

    private String getTheRoomName(String roomId) {
        if (roomId == null) return "Unknown";
        switch (roomId) {
            case "room_exterior": return "Exterior";
            case "room_foyer": return "Foyer";
            case "room_parlor": return "Parlor";
            case "room_library": return "Library";

            default: return roomId;
        }
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


    private Image cutsceneImage = null; 
    
    public void setCutsceneImage(Image img) {
        this.cutsceneImage = img;
    }

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

 
    public void drawcutsceneScene(GraphicsContext gc) {

        if (cutsceneImage != null && !cutsceneImage.isError()) {
 
            gc.drawImage(cutsceneImage, 0, 0, ga.screenWidth, ga.screenHeight);
        } else {
            // fallback background if no image available
            gc.setFill(Color.BLACK);
            gc.fillRect(0, 0, ga.screenWidth, ga.screenHeight);
        }

        //semi-transparent overlay to ensure dialogue is readable
        double overlayHeight = ga.tileSize * 4; // same height you use elsewhere
        double overlayY = ga.screenHeight - overlayHeight - ga.tileSize / 2;
        gc.setFill(new Color(0, 0, 0, 0.45)); // translucent black overlay
        gc.fillRoundRect(ga.tileSize, overlayY, ga.screenWidth - (ga.tileSize * 2), overlayHeight, 20, 20);

        //dialogue sub-window using drawsubwindow method
        int x = ga.tileSize;
        int y = (int) overlayY;
        int width = ga.screenWidth - (ga.tileSize * 2);
        int height = (int) overlayHeight;
        drawSubWindow(gc, x, y, width, height);

        // draws  the current dialogue text inside the subwindow
        gc.setFont(Font.font("Arial", FontWeight.NORMAL, 28));
        gc.setFill(Color.WHITE);

        // text padding
        int textX = x + ga.tileSize / 2;
        int textY = y + ga.tileSize;
        // handle multi-line text
        String[] lines = currentText.split("\n");
        int lineHeight = 36;
        for (int i = 0; i < lines.length; i++) {
            gc.fillText(lines[i], textX, textY + (i * lineHeight));
        }

       
        gc.setFont(Font.font("Arial", FontWeight.BOLD, 18));
        gc.setFill(Color.web("#FFD700")); // gold hint color
        String hint = "Press SPACE to continue";
        double hintX = x + width - ga.tileSize - gc.getFont().getSize() * hint.length() * 0.25;
        double hintY = y + height - (ga.tileSize / 2);
        gc.fillText(hint, hintX, hintY);
    }

    public boolean isQuitButtonClicked(double mouseX, double mouseY) {
        return mouseX >= quitBtnX && mouseX <= quitBtnX + quitBtnW &&
               mouseY >= quitBtnY && mouseY <= quitBtnY + quitBtnH;
    }

}