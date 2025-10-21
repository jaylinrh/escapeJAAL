package com.escape.Model;

public class PlayerState {
    private int worldX;
    private int worldY;
    private int speed;
    private String direction;
    private SolidArea solidArea;
    private boolean collisionOn;
    private SpriteImages spriteImages;

    public PlayerState(int worldX, int worldY, int speed, String direction, SolidArea solidArea, boolean collisionOn, SpriteImages spriteImages) {
        this.worldX = worldX;
        this.worldY = worldY;
        this.speed = speed;
        this.direction = direction;
        this.solidArea = solidArea;
        this.collisionOn = collisionOn;
        this.spriteImages = spriteImages;
    }

    public int getWorldX() {
        return worldX;
    }

    public void setWorldX(int worldX) {
        this.worldX = worldX;
    }

    public int getWorldY() {
        return worldY;
    }

    public void setWorldY(int worldY) {
        this.worldY = worldY;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    public SolidArea getSolidArea() {
        return solidArea;
    }

    public void setSolidArea(SolidArea solidArea) {
        this.solidArea = solidArea;
    }

    public boolean getCollision() {
        return collisionOn;
    }

    public void setCollision(boolean collision) {
        this.collisionOn = collision;
    }

    public SpriteImages getSpriteImages() {
        return spriteImages;
    }

    public void setSpriteImages(SpriteImages spriteImages) {
        this.spriteImages = spriteImages;
    }

   @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("{");
        sb.append("\"worldx\":").append(worldX).append(",");
        sb.append("\"worldy\":").append(worldY).append(",");
        sb.append("\"speed\":").append(speed).append(",");
        sb.append("\"direction\":\"").append(direction).append("\",");
        sb.append("\"collisionOn\":").append(collisionOn).append(",");
        sb.append("\"solidArea\":").append(solidArea.toString()).append(",");
        sb.append("\"spriteImages\":").append(spriteImages.toString());
        sb.append("}");
        return sb.toString();
    }
}