package com.escape.Model;

import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class KeyHandler {
    GameApp ga;
    public boolean upPressed, downPressed, leftPressed, rightPressed;

    public boolean interactPressed = false;
    public boolean inventoryPressed = false;
    
    public KeyHandler(GameApp ga) {
        this.ga = ga;
    }
    
    
    public void handleKeyPressed(KeyEvent e) {
        KeyCode code = e.getCode();
        
        if (ga.gameState == ga.playState) {
            if (code == KeyCode.W) {
                upPressed = true;
            }
            if (code == KeyCode.S) {
                downPressed = true;
            }
            if (code == KeyCode.A) {
                leftPressed = true;
            }
            if (code == KeyCode.D) {
                rightPressed = true;
            }
            if (code == KeyCode.P) {
                ga.gameState = ga.pauseState;
            }
            if (code == KeyCode.E) {
                interactPressed = true;
                ga.interactWithNearestObject();
            }
            if (code == KeyCode.I) {
                ga.gameState = ga.inventoryState;
            }
        }
        
        else if (ga.gameState == ga.dialogueState) {
            if (code == KeyCode.ENTER) {
                ga.ui.currentDialogueIndex++;
                
                if (ga.ui.currentDialogueIndex < ga.ui.dialogues.length && 
                    ga.ui.dialogues[ga.ui.currentDialogueIndex] != null) {
                    ga.ui.currentText = ga.ui.dialogues[ga.ui.currentDialogueIndex];
                } else {
                    ga.gameState = ga.playState;
                    ga.requestFocus();
                }
            }
        }
        
        else if (ga.gameState == ga.pauseState) {
            if (code == KeyCode.P) {
                ga.gameState = ga.playState;
                ga.requestFocus();
                System.out.println("pause");
            }
        }
         else if (ga.gameState == ga.inventoryState) {
            if (code == KeyCode.I || code == KeyCode.ESCAPE) {
                ga.gameState = ga.playState;
            }
        }
    }
    

    public void handleKeyReleased(KeyEvent e) {
        KeyCode code = e.getCode();
        
        if (code == KeyCode.W) {
            upPressed = false;
        }
        if (code == KeyCode.S) {
            downPressed = false;
        }
        if (code == KeyCode.A) {
            leftPressed = false;
        }
        if (code == KeyCode.D) {
            rightPressed = false;
        }
         if (code == KeyCode.E) {
            interactPressed = false;
        }
    }
}