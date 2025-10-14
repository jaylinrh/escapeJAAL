package com.escape.Model;

import javafx.scene.image.Image;

public class Tile {
    public Image image;
    public boolean collision = false;
    public int tileId;
    public String name;
    public String imagePath;
    public boolean isSpecial = false; 
    
    public Tile() {
        this.tileId = 0;
        this.name = "";
        this.collision = false;
        this.isSpecial = false;
    }
    
    public Tile(int tileId, String name, String imagePath, boolean collision, boolean isSpecial) {
        this.tileId = tileId;
        this.name = name;
        this.collision = collision;
        this.isSpecial = isSpecial;
        loadImage();
    }
    private void loadImage() {
        try {
            if (imagePath != null && !imagePath.isEmpty()) {
                this.image = new Image(getClass().getResourceAsStream(imagePath));
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
    
    @Override
    public String toString() {
        return String.format("Tile[ID=%d, Name=%s, Collision=%b, Special=%b]", 
            tileId, name, collision, isSpecial);
    }
}