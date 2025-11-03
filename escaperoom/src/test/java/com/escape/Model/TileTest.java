package com.escape.Model;

import org.junit.Test;
import org.junit.Before;
import org.junit.After;
import static org.junit.Assert.*;

public class TileTest {
    private Tile tile;
    
    @Before
    public void setUp() {
        tile = new Tile();
    }
    
    @After
    public void tearDown() {
        tile = null;
    }
    
    // === CONSTRUCTOR TESTS ===
    @Test
    public void testDefaultConstructor_InitializesDefaultValues() {
        assertEquals("Default tileId should be 0", 0, tile.tileId);
        assertEquals("Default name should be empty string", "", tile.name);
        assertFalse("Default collision should be false", tile.collision);
        assertFalse("Default isSpecial should be false", tile.isSpecial);
        assertNull("Default image should be null", tile.image);
        assertNull("Default imagePath should be null", tile.imagePath);
    }
    
    @Test
    public void testParameterizedConstructor_InitializesAllFields() {
        Tile customTile = new Tile(5, "Stone Wall", "/tiles/stone.png", true, false);
        assertEquals("Tile ID should match constructor parameter", 5, customTile.tileId);
        assertEquals("Tile name should match constructor parameter", "Stone Wall", customTile.name);
        assertTrue("Collision should be true as specified in constructor", customTile.collision);
        assertFalse("isSpecial should be false as specified in constructor", customTile.isSpecial);
        assertEquals("Image path should match constructor parameter", "/tiles/stone.png", customTile.imagePath);
    }
    
    // === FIELD ASSIGNMENT TESTS ===
    @Test
    public void testFieldAssignment_TileId() {
        tile.tileId = 7;
        assertEquals("Tile ID should be set to 7", 7, tile.tileId);
    }
    
    @Test
    public void testFieldAssignment_Name() {
        tile.name = "Grass";
        assertEquals("Tile name should be set to 'Grass'", "Grass", tile.name);
    }
    
    @Test
    public void testFieldAssignment_Collision() {
        tile.collision = true;
        assertTrue("Collision should be set to true", tile.collision);
    }
    
    @Test
    public void testFieldAssignment_IsSpecial() {
        tile.isSpecial = true;
        assertTrue("isSpecial should be set to true", tile.isSpecial);
    }
    
    // === EDGE CASE TESTS ===
    @Test
    public void testTileId_ExtremeValues() {
        tile.tileId = Integer.MAX_VALUE;
        assertEquals("Tile ID should accept maximum integer value", Integer.MAX_VALUE, tile.tileId);
    }
    
    @Test
    public void testName_NullAndEmpty() {
        tile.name = null;
        assertNull("Tile name should be null when set to null", tile.name);
        
        tile.name = "";
        assertEquals("Tile name should be empty string when set to empty", "", tile.name);
    }
    
    // === TOSTRING TESTS ===
    @Test
    public void testToString_DefaultTile() {
        String result = tile.toString();
        assertTrue("toString should contain tile ID", result.contains("ID=0"));
        assertTrue("toString should contain collision status", result.contains("Collision=false"));
        assertTrue("toString should contain special status", result.contains("Special=false"));
    }
    
    @Test
    public void testToString_CustomTile() {
        tile.tileId = 3;
        tile.name = "Wood Floor";
        tile.collision = false;
        tile.isSpecial = true;
        
        String result = tile.toString();
        assertTrue("toString should contain correct tile ID", result.contains("ID=3"));
        assertTrue("toString should contain correct tile name", result.contains("Name=Wood Floor"));
        assertTrue("toString should contain correct special status", result.contains("Special=true"));
    }
    
    // === INTEGRATION TESTS ===
    @Test
    public void testMultipleFieldChanges() {
        tile.tileId = 15;
        tile.name = "Lava";
        tile.collision = true;
        tile.isSpecial = true;
        
        assertEquals("Tile ID should be set correctly", 15, tile.tileId);
        assertEquals("Tile name should be set correctly", "Lava", tile.name);
        assertTrue("Collision should be set to true", tile.collision);
        assertTrue("isSpecial should be set to true", tile.isSpecial);
    }
}