package com.escape.Model;

import java.util.ArrayList;

public class Dialogue {
    private String dialogueId;
    private String dialogueFile;
    private ArrayList<String> dialogues;
    private int currentIndex;
    
    public Dialogue(String dialogueId, String dialogueFile, ArrayList<String> dialogues) {
        this.dialogueId = dialogueId;
        this.dialogueFile = dialogueFile;
        this.dialogues = dialogues;
        this.currentIndex = 0;
    }
    
    public String getCurrentDialogue() {
        if (currentIndex >= 0 && currentIndex < dialogues.size()) {
            return dialogues.get(currentIndex);
        }
        return null;
    }
    
    public boolean hasNext() {
        return currentIndex < dialogues.size() - 1;
    }
    
    public void nextDialogue() {
        if (hasNext()) {
            currentIndex++;
        }
    }
    
    public void resetDialogue() {
        currentIndex = 0;
    }
    
    public String getDialogueId() {
        return dialogueId;
    }
    
    public String getDialogueFile() {
        return dialogueFile;
    }
    
    public ArrayList<String> getDialogues() {
        return dialogues;
    }
    
    public int getCurrentIndex() {
        return currentIndex;
    }
    
    public void setDialogues(ArrayList<String> dialogues) {
        this.dialogues = dialogues;
        this.currentIndex = 0;
    }
    
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("{");
        sb.append("\"dialogueId\":\"").append(dialogueId).append("\",");
        sb.append("\"dialogueFile\":\"").append(dialogueFile).append("\",");
        sb.append("\"dialogues\":[");
        for (int i = 0; i < dialogues.size(); i++) {
            sb.append("\"").append(dialogues.get(i)).append("\"");
            if (i < dialogues.size() - 1) {
                sb.append(",");
            }
        }
        sb.append("]}");
        return sb.toString();
    }
}