package com.escape.Model;

public class CollisionHandler {

    GameApp ga;

    public CollisionHandler(GameApp ga) {
        this.ga = ga;
    }

    public void checkTile(Entity entity) {

        int entityLeftWorldX = (int)(entity.worldX + entity.solidArea.getMinX());
        int entityRightWorldX = (int)(entity.worldX + entity.solidArea.getMinX() + entity.solidArea.getWidth());
        int entityTopWorldY = (int)(entity.worldY + entity.solidArea.getMinY());
        int entityBottomWorldY = (int)(entity.worldY + entity.solidArea.getMinY() + entity.solidArea.getHeight());
        
        int entityLeftCol = entityLeftWorldX / ga.tileSize;
        int entityRightCol = entityRightWorldX / ga.tileSize;
        int entityTopRow = entityTopWorldY / ga.tileSize;
        int entityBottomRow = entityBottomWorldY / ga.tileSize;
        
        int tileNum1, tileNum2;

        switch(entity.direction) {
            case "up":
                entityTopRow = (entityTopWorldY - entity.speed) / ga.tileSize;
                tileNum1 = ga.tileM.mapTileNum[entityLeftCol][entityTopRow];
                tileNum2 = ga.tileM.mapTileNum[entityRightCol][entityTopRow];
                if(ga.tileM.tile[tileNum1].collision || ga.tileM.tile[tileNum2].collision) {
                    entity.collisionOn = true;
                }
                break;
            case "down":
                entityBottomRow = (entityBottomWorldY + entity.speed) / ga.tileSize;
                tileNum1 = ga.tileM.mapTileNum[entityLeftCol][entityBottomRow];
                tileNum2 = ga.tileM.mapTileNum[entityRightCol][entityBottomRow];
                if(ga.tileM.tile[tileNum1].collision || ga.tileM.tile[tileNum2].collision) {
                    entity.collisionOn = true;
                }
                break;
            case "left":
                entityLeftCol = (entityLeftWorldX - entity.speed) / ga.tileSize;
                tileNum1 = ga.tileM.mapTileNum[entityLeftCol][entityTopRow];
                tileNum2 = ga.tileM.mapTileNum[entityLeftCol][entityBottomRow];
                if(ga.tileM.tile[tileNum1].collision || ga.tileM.tile[tileNum2].collision) {
                    entity.collisionOn = true;
                }
                break;
            case "right":
                entityRightCol = (entityRightWorldX + entity.speed) / ga.tileSize;
                tileNum1 = ga.tileM.mapTileNum[entityRightCol][entityTopRow];
                tileNum2 = ga.tileM.mapTileNum[entityRightCol][entityBottomRow];
                if(ga.tileM.tile[tileNum1].collision || ga.tileM.tile[tileNum2].collision) {
                    entity.collisionOn = true;
                }
                break;
        }
    }
}