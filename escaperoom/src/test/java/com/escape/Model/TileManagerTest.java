package com.escape.Model;

import org.junit.Test;
import org.junit.Before;
import org.junit.After;
import static org.junit.Assert.*;

public class TileManagerTest {
    private TileManager tileManager;
    private GameApp gameApp;
    
    @Before
    public void setUp() {
        gameApp = new GameApp();
        tileManager = new TileManager(gameApp);
    }
    
    @After
    public void tearDown() {
        tileManager = null;
        gameApp = null;
    }
    
    @Test
    public void testTileManagerInitialization_CreatesTileArray() {
        assertNotNull("Tile array should be initialized", tileManager.tile);
        assertEquals("Should create 12 tiles", 12, tileManager.tile.length);
    }
    
    @Test
    public void testGetTileImage_InitializesTiles() {
        // Verify that tiles are properly initialized
        assertNotNull("Tile 0 should be initialized", tileManager.tile[0]);
        assertNotNull("Tile 0 should have an image", tileManager.tile[0].image);
        assertFalse("Tile 0 should not have collision", tileManager.tile[0].collision);
    }
    
    @Test
    public void testSpecialTiles_MarkedCorrectly() {
        // Check that special tiles are properly marked
        assertTrue("Tile 2 should be special (crime scene)", tileManager.tile[2].isSpecial);
        assertTrue("Tile 10 should be special (manor entrance)", tileManager.tile[10].isSpecial);
    }
    
    @Test
    public void testCollisionTiles_MarkedCorrectly() {
        // Check collision properties
        assertTrue("Tile 3 (wall) should have collision", tileManager.tile[3].collision);
        assertTrue("Tile 4 (bookshelf) should have collision", tileManager.tile[4].collision);
        assertFalse("Tile 0 (floor) should not have collision", tileManager.tile[0].collision);
    }
    
    @Test
    public void testMapLoading_DimensionsCorrect() {
        assertNotNull("Map tile numbers should be initialized", tileManager.mapTileNum);
        assertEquals("Map should have correct number of columns", 
                     gameApp.maxWorldCol, tileManager.mapTileNum.length);
        assertEquals("Map should have correct number of rows", 
                     gameApp.maxWorldRow, tileManager.mapTileNum[0].length);
    }
    
    @Test
    public void testTileNames_AssignedCorrectly() {
        assertEquals("Tile 10 should have name 'manor_entrance'", 
                     "manor_entrance", tileManager.tile[10].name);
    }
}