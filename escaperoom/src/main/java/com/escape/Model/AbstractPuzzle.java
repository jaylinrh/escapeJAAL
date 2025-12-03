package com.escape.Model;

import com.escape.Model.Facade;
import com.escape.Model.GameApp;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;


public abstract class AbstractPuzzle implements PuzzleBase {
    protected GameApp gameApp;
    protected String puzzleId;
    protected boolean solved;
    protected String title;
    protected String description;
    protected String successMessage;
    
    protected int screenWidth;
    protected int screenHeight;
    protected int tileSize;
    
    protected Font titleFont;
    protected Font textFont;
    protected Font smallFont;
    
    public AbstractPuzzle(GameApp gameApp, String puzzleId, String title, String description) {
        this.gameApp = gameApp;
        this.puzzleId = puzzleId;
        this.title = title;
        this.description = description;
        this.solved = false;
        this.successMessage = "Puzzle Solved!";
        
        this.screenWidth = gameApp.screenWidth;
        this.screenHeight = gameApp.screenHeight;
        this.tileSize = gameApp.tileSize;
        
        this.titleFont = Font.font("Arial", FontWeight.BOLD, 40);
        this.textFont = Font.font("Arial", FontWeight.NORMAL, 24);
        this.smallFont = Font.font("Arial", FontWeight.NORMAL, 18);
    }
    
    @Override
    public boolean isSolved() {
        return solved;
    }
    
    @Override
    public String getPuzzleId() {
        return puzzleId;
    }
    
    protected void markSolved() {
        this.solved = true;
    }
    
    @Override
    public void onComplete(Facade facade) {
        facade.solvePuzzle(puzzleId);
        facade.saveUserProgress();
    }
    
    protected void drawPuzzleWindow(GraphicsContext gc, int x, int y, int width, int height) {
        gc.setFill(Color.rgb(20, 20, 30, 0.95));
        gc.fillRoundRect(x, y, width, height, 20, 20);
        
        gc.setStroke(Color.web("#FFD700"));
        gc.setLineWidth(3);
        gc.strokeRoundRect(x, y, width, height, 20, 20);
    }
    
    protected void drawTitle(GraphicsContext gc, int x, int y) {
        gc.setFont(titleFont);
        gc.setFill(Color.web("#FFD700"));
        gc.fillText(title, x, y);
    }
    
    protected void drawDescription(GraphicsContext gc, int x, int y, int maxWidth) {
        gc.setFont(textFont);
        gc.setFill(Color.WHITE);
        
        String[] words = description.split(" ");
        StringBuilder line = new StringBuilder();
        int lineY = y;
        
        for (String word : words) {
            String testLine = line + word + " ";
            double textWidth = gc.getFont().getSize() * testLine.length() * 0.6; // Approximate
            
            if (textWidth > maxWidth && line.length() > 0) {
                gc.fillText(line.toString().trim(), x, lineY);
                line = new StringBuilder(word + " ");
                lineY += 30;
            } else {
                line.append(word).append(" ");
            }
        }
        if (line.length() > 0) {
            gc.fillText(line.toString().trim(), x, lineY);
        }
    }
    
    protected void drawSuccessMessage(GraphicsContext gc) {
        gc.setFont(titleFont);
        gc.setFill(Color.web("#00FF00"));
        
        int x = screenWidth / 2 - 150;
        int y = screenHeight / 2;
        
        gc.fillText(successMessage, x, y);
        
        gc.setFont(textFont);
        gc.setFill(Color.WHITE);
        gc.fillText("Press ENTER to continue", x + 10, y + 50);
    }
    
    protected void drawButton(GraphicsContext gc, String text, int x, int y, int width, int height, boolean hover) {
        if (hover) {
            gc.setFill(Color.web("#FFD700"));
        } else {
            gc.setFill(Color.web("#555555"));
        }
        gc.fillRoundRect(x, y, width, height, 10, 10);
        
        gc.setStroke(Color.WHITE);
        gc.setLineWidth(2);
        gc.strokeRoundRect(x, y, width, height, 10, 10);
        
        gc.setFont(textFont);
        gc.setFill(hover ? Color.BLACK : Color.WHITE);
        
        double textWidth = text.length() * 12; // Approximate
        double textX = x + (width - textWidth) / 2;
        double textY = y + (height / 2) + 8;
        
        gc.fillText(text, textX, textY);
    }
}