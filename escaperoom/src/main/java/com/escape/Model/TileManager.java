package com.escape.Model;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

public class TileManager {
    public Tile[] tile;
    public int mapTileNum[][];

    public TileManager(GameApp ga) {
        tile = new Tile[12];
        mapTileNum = new int[ga.WorldColLimit][ga.WorldRowLimit];   
        getTileImage();
        loadMap("/maps/exterior.txt");
    }

    public void getTileImage() {
        try {
            tile[0] = new Tile();
            tile[0].image = new Image(getClass().getResourceAsStream("/tiles/tile_worn_floor.png"));
            tile[0].collision = false;
            
            tile[1] = new Tile();
            tile[1].image = new Image(getClass().getResourceAsStream("/tiles/tile_tattered_rug.png"));
            tile[1].collision = false;
            
            tile[2] = new Tile();
            tile[2].image = new Image(getClass().getResourceAsStream("/tiles/tile_crime_scene.png"));
            tile[2].collision = false;
            tile[2].isSpecial = true;
            
            tile[3] = new Tile();
            tile[3].image = new Image(getClass().getResourceAsStream("/tiles/tile_manor_wall.png"));
            tile[3].collision = true;

            tile[4] = new Tile();
            tile[4].image = new Image(getClass().getResourceAsStream("/tiles/tile_bookshelf.png"));
            tile[4].collision = true;

            tile[5] = new Tile();
            tile[5].image = new Image(getClass().getResourceAsStream("/tiles/tile_wooden_table.png"));
            tile[5].collision = true;

            tile[6] = new Tile();
            tile[6].image = new Image(getClass().getResourceAsStream("/tiles/tile_portrait_wall.png"));
            tile[6].collision = true;

            tile[7] = new Tile();
            tile[7].image = new Image(getClass().getResourceAsStream("/tiles/tile_overgrown_floor.png"));
            tile[7].collision = false;

            tile[8] = new Tile();
            tile[8].image = new Image(getClass().getResourceAsStream("/tiles/tile_broken_glass.png"));
            tile[8].collision = true;

            tile[9] = new Tile();
            tile[9].image = new Image(getClass().getResourceAsStream("/tiles/tile_stage_floor.png"));
            tile[9].collision = false;

            tile[10] = new Tile();
            tile[10].image = new Image(getClass().getResourceAsStream("/tiles/tile_manor_entrance.png"));
            tile[10].collision = false;
            tile[10].isSpecial = true; // ← Mark as special
            tile[10].name = "manor_entrance"; // ← Add this for identification

            tile[11] = new Tile();
            tile[11].image = new Image(getClass().getResourceAsStream("/tiles/tile_muddy_path.png"));
            tile[11].collision = false;
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

}

