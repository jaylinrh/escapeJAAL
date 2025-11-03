package com.escape.Model;

import org.junit.Test;
import org.junit.Before;
import org.junit.After;
import static org.junit.Assert.*;

public class PlayerStateTest {
    private PlayerState playerState;
    private SolidArea solidArea;
    private SpriteImages spriteImages;
    
    //initialization
    @Before
    public void setUp() {
        solidArea = new SolidArea(0, 0, 32, 32);
        spriteImages = new SpriteImages(
            "/images/player.png", "/images/player.png",
            "/images/player.png", "/images/player.png",
            "/images/player.png", "/images/player.png",
            "/images/player.png", "/images/player.png"
        );
        
        playerState = new PlayerState(
            1104, 1104, 4, "down", solidArea, false, spriteImages
        );
    }
    
    @After
    public void tearDown() {
        playerState = null;
        solidArea = null;
        spriteImages = null;
    }
    //Constructor test
      @Test
    public void testConstructor_InitializesAllFields() {
        assertEquals("X coordinate should match",1104, playerState.getWorldX());
        assertEquals("Y coordinate should match",1104, playerState.getWorldY());
        assertEquals("speed should match",4, playerState.getSpeed());
        assertEquals("direction should match","down", playerState.getDirection());
        assertEquals("hitbox should match",solidArea, playerState.getSolidArea());
        assertEquals("collision state should match",false, playerState.getCollision());
        assertEquals("sprite images should match",spriteImages, playerState.getSpriteImages());
        assertNotNull("SolidArea should not be null",solidArea);
        assertNotNull("spriteImages should not be null",spriteImages);
    }

    @Test
    public void testWorldCoordinates_EdgeCases() {
        // Test minimum values
        playerState.setWorldX(0);
        playerState.setWorldY(0);
        assertEquals(0, playerState.getWorldX());
        assertEquals(0, playerState.getWorldY());
        
        // Test maximum values
        playerState.setWorldX(2400);
        playerState.setWorldY(2400);
        assertEquals(2400, playerState.getWorldX());
        assertEquals(2400, playerState.getWorldY());
    }
    // speed test
    @Test
    public void testSpeed_EdgeCases() {
        playerState.setSpeed(0);
        assertEquals("speed should match",0, playerState.getSpeed());
        
        playerState.setSpeed(10);
        assertEquals("speed should match",10, playerState.getSpeed());
    }
    // direction test
    @Test
    public void testDirection_ValidValues() {
        String[] validDirections = {"up", "down", "left", "right"};
        
        for (String direction : validDirections) {
            playerState.setDirection(direction);
            assertEquals("direction should match",direction, playerState.getDirection());
        }
    }
//Negative coordinate/speed test
    @Test
public void testWorldCoordinates_NegativeValues() {
    playerState.setWorldX(-100);
    playerState.setWorldY(-50);
    assertEquals("X should match",-100, playerState.getWorldX());
    assertEquals("Y should match",-50, playerState.getWorldY());
}

@Test
public void testSpeed_NegativeValue() {
    playerState.setSpeed(-5);
    assertEquals("speed should match",-5, playerState.getSpeed());
}

//Null solidArea and spriteImages tests
    @Test
public void testConstructor_WithNullSpriteImages() {
    try {
        PlayerState state = new PlayerState(100, 100, 4, "down", solidArea, false, null);
        assertNull(state.getSpriteImages());
    } catch (NullPointerException e) {
        assertTrue(true);
    }
}

@Test
public void testConstructor_WithNullSolidArea() {
    try {
        PlayerState state = new PlayerState(100, 100, 4, "down", null, false, spriteImages);
        assertNull(state.getSolidArea());
    } catch (NullPointerException e) {
        assertTrue(true);
    }
}

    // toString test
    @Test
    public void testToString_ContainsAllFields() {
        String result = playerState.toString();
        
        assertTrue("Should contain worldX", result.contains("\"worldx\":1104"));
        assertTrue("Should contain worldY", result.contains("\"worldy\":1104"));
        assertTrue("Should contain speed", result.contains("\"speed\":4"));
        assertTrue("Should contain direction", result.contains("\"direction\":\"down\""));
        assertTrue("Should contain collision", result.contains("\"collisionOn\":false"));
        assertTrue("Should contain solidArea", result.contains("\"solidArea\":"));
        assertTrue("Should contain spriteImages", result.contains("\"spriteImages\":"));
    }
    
    @Test
    public void testToString_ValidJSONFormat() {
        String result = playerState.toString();
    
        assertTrue("Should start with {", result.startsWith("{"));
        assertTrue("Should end with }", result.endsWith("}"));
        assertTrue("Should be valid JSON format", result.matches("^\\{.*\\}$"));
    }
    
    @Test
    public void testToString_WithDifferentValues() {
        playerState.setWorldX(2400);
        playerState.setWorldY(1200);
        playerState.setDirection("up");
        playerState.setCollision(true);
        
        String result = playerState.toString();
        
        assertTrue("Should contain updated worldX", result.contains("\"worldx\":2400"));
        assertTrue("Should contain updated worldY", result.contains("\"worldy\":1200"));
        assertTrue("Should contain updated direction", result.contains("\"direction\":\"up\""));
        assertTrue("Should contain updated collision", result.contains("\"collisionOn\":true"));
    }
}