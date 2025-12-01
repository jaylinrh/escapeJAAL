package com.escape.Model;

import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class Player extends Entity {
    GameApp ga;
    KeyHandler keyH;
    
    public final int screenX;
    public final int screenY;
    
    private SpriteImages spriteImages;

    // JavaFX Images instead of BufferedImage
    private Image u1, u2, d1, d2, l1, l2, r1, r2;

    private Room currentRoom;
    
    public Player(GameApp ga, KeyHandler keyH) {
        this.ga = ga;
        this.keyH = keyH;
        
        screenX = ga.screenWidth/2 - (ga.tileSize/2);
        screenY = ga.screenHeight/2 - (ga.tileSize/2);
        
        solidArea = new Rectangle2D(0, 0, ga.tileSize - 16, ga.tileSize - 16);
        
        setDefaultValues();
        loadSprites();
    }
    
    public void setDefaultValues() {
        worldX = ga.tileSize * 10;
        worldY = ga.tileSize * 10;
        speed = 4;
        direction = "down";
    }


    public void loadSprites() {
        try {
            // Load images using JavaFX Image class
            String imagePath = "/com/escape/images/player.png";
            u1 = new Image(getClass().getResourceAsStream(imagePath));
            u2 = new Image(getClass().getResourceAsStream(imagePath));
            d1 = new Image(getClass().getResourceAsStream(imagePath));
            d2 = new Image(getClass().getResourceAsStream(imagePath));
            l1 = new Image(getClass().getResourceAsStream(imagePath));
            l2 = new Image(getClass().getResourceAsStream(imagePath));
            r1 = new Image(getClass().getResourceAsStream(imagePath));
            r2 = new Image(getClass().getResourceAsStream(imagePath));
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
    
    public void update() {
        if(keyH.upPressed || keyH.downPressed || keyH.leftPressed || keyH.rightPressed) {

            if(keyH.upPressed) {
                direction = "up";
            } else if(keyH.downPressed) {
                direction = "down";
            } else if(keyH.leftPressed) {
                direction = "left";
            } else if(keyH.rightPressed) {
                direction = "right";
            }
        
            collisionOn = false;
            ga.cHandler.checkTile(this);
        
            if(!collisionOn) {
                switch(direction) {
                    case "up":
                        worldY -= speed;
                        break;
                    case "down":
                        worldY += speed;
                        break;
                    case "left":
                        worldX -= speed;
                        break;
                    case "right":
                        worldX += speed;
                        break;
                }
            } 
        }
    }
    
    public void draw(GraphicsContext gc) {
        Image image = null;
        
        switch(direction) {
            case "up":
                image = u1;
                break;
            case "down":
                image = d1;
                break;
            case "left":
                image = l1;
                break;
            case "right":
                image = r1;
                break;
            default:
                image = u1;
        }
        
        gc.drawImage(image, screenX, screenY, ga.tileSize, ga.tileSize);
    }

    public Room getCurrentRoom() {
        return currentRoom;
    }

    public void setCurrentRoom(Room room) {
        this.currentRoom = room;
    }
    
    public void loadPlayerState(PlayerState state) {
        if (state != null) {
            this.worldX = state.getWorldX();
            this.worldY = state.getWorldY();
            this.speed = state.getSpeed();
            this.direction = state.getDirection();
        } else {
            setDefaultValues();
        }
    }
}