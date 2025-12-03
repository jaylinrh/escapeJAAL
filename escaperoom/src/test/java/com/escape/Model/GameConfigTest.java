package com.escape.Model;

import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

public class GameConfigTest {
    private GameConfig config;
    
    @Before
    public void setUp() {
        config = new GameConfig(
            16,    // originalTileSize
            3,     // scale
            48,    // tileSize
            16,    // maxScreenCol
            12,    // maxScreenRow
            768,   // screenWidth
            576,   // screenHeight
            50,    // maxWorldCol
            50,    // maxWorldRow
            2400,  // worldWidth
            2400,  // worldHeight
            60,    // fps
            1,     // playState
            2,     // pauseState
            3,     // dialogueState
            4      // inventoryState
        );
    }
    
    // === CONSTRUCTOR TESTS ===
    @Test
    public void testConstructor_InitializesAllFields() {
        assertEquals("Original tile size should be initialized", 16, config.getOriginalTileSize());
        assertEquals("Scale should be initialized", 3, config.getScale());
        assertEquals("Tile size should be initialized", 48, config.getTileSize());
        assertEquals("Max screen columns should be initialized", 16, config.getScreenCols());
        assertEquals("Max screen rows should be initialized", 12, config.getScreenRows());
        assertEquals("Screen width should be initialized", 768, config.getScreenWidth());
        assertEquals("Screen height should be initialized", 576, config.getScreenHeight());
        assertEquals("Max world columns should be initialized", 50, config.getWorldCols());
        assertEquals("Max world rows should be initialized", 50, config.getWorldRows());
        assertEquals("World width should be initialized", 2400, config.getWorldWidth());
        assertEquals("World height should be initialized", 2400, config.getWorldHeight());
        assertEquals("FPS should be initialized", 60, config.getFps());
        assertEquals("Play state should be initialized", 1, config.getPlayState());
        assertEquals("Pause state should be initialized", 2, config.getPauseState());
        assertEquals("Dialogue state should be initialized", 3, config.getDialogueState());
        assertEquals("Inventory state should be initialized", 4, config.getInventoryState());
    }
    
    // === DISPLAY GETTER TESTS ===
    @Test
    public void testDisplayGetters_OriginalTileSize() {
        assertEquals("getOriginalTileSize should return correct value", 16, config.getOriginalTileSize());
    }
    
    @Test
    public void testDisplayGetters_Scale() {
        assertEquals("getScale should return correct value", 3, config.getScale());
    }
    
    @Test
    public void testDisplayGetters_TileSize() {
        assertEquals("getTileSize should return correct value", 48, config.getTileSize());
    }
    
    @Test
    public void testDisplayGetters_MaxScreenCol() {
        assertEquals("getMaxScreenCol should return correct value", 16, config.getScreenCols());
    }
    
    @Test
    public void testDisplayGetters_MaxScreenRow() {
        assertEquals("getMaxScreenRow should return correct value", 12, config.getScreenRows());
    }
    
    @Test
    public void testDisplayGetters_ScreenWidth() {
        assertEquals("getScreenWidth should return correct value", 768, config.getScreenWidth());
    }
    
    @Test
    public void testDisplayGetters_ScreenHeight() {
        assertEquals("getScreenHeight should return correct value", 576, config.getScreenHeight());
    }
    
    // === WORLD GETTER TESTS ===
    @Test
    public void testWorldGetters_MaxWorldCol() {
        assertEquals("getMaxWorldCol should return correct value", 50, config.getWorldCols());
    }
    
    @Test
    public void testWorldGetters_MaxWorldRow() {
        assertEquals("getMaxWorldRow should return correct value", 50, config.getWorldRows());
    }
    
    @Test
    public void testWorldGetters_WorldWidth() {
        assertEquals("getWorldWidth should return correct value", 2400, config.getWorldWidth());
    }
    
    @Test
    public void testWorldGetters_WorldHeight() {
        assertEquals("getWorldHeight should return correct value", 2400, config.getWorldHeight());
    }
    
    // === GAMEPLAY GETTER TESTS ===
    @Test
    public void testGameplayGetters_Fps() {
        assertEquals("getFps should return correct value", 60, config.getFps());
    }
    
    @Test
    public void testGameplayGetters_PlayState() {
        assertEquals("getPlayState should return correct value", 1, config.getPlayState());
    }
    
    @Test
    public void testGameplayGetters_PauseState() {
        assertEquals("getPauseState should return correct value", 2, config.getPauseState());
    }
    
    @Test
    public void testGameplayGetters_DialogueState() {
        assertEquals("getDialogueState should return correct value", 3, config.getDialogueState());
    }
    
    @Test
    public void testGameplayGetters_InventoryState() {
        assertEquals("getInventoryState should return correct value", 4, config.getInventoryState());
    }
    
    // === EDGE CASE TESTS ===
    @Test
    public void testConstructor_ZeroValues() {
        GameConfig zeroConfig = new GameConfig(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0);
        
        assertEquals("Zero original tile size", 0, zeroConfig.getOriginalTileSize());
        assertEquals("Zero scale", 0, zeroConfig.getScale());
        assertEquals("Zero tile size", 0, zeroConfig.getTileSize());
        assertEquals("Zero FPS", 0, zeroConfig.getFps());
    }
    
    @Test
    public void testConstructor_NegativeValues() {
        GameConfig negativeConfig = new GameConfig(-1, -2, -3, -4, -5, -6, -7, -8, -9, -10, -11, -12, -13, -14, -15, -16);
        
        assertEquals("Negative original tile size", -1, negativeConfig.getOriginalTileSize());
        assertEquals("Negative scale", -2, negativeConfig.getScale());
        assertEquals("Negative tile size", -3, negativeConfig.getTileSize());
        assertEquals("Negative FPS", -12, negativeConfig.getFps());
    }
    
    @Test
    public void testConstructor_MaxValues() {
        GameConfig maxConfig = new GameConfig(
            Integer.MAX_VALUE, Integer.MAX_VALUE, Integer.MAX_VALUE,
            Integer.MAX_VALUE, Integer.MAX_VALUE, Integer.MAX_VALUE,
            Integer.MAX_VALUE, Integer.MAX_VALUE, Integer.MAX_VALUE,
            Integer.MAX_VALUE, Integer.MAX_VALUE, Integer.MAX_VALUE,
            Integer.MAX_VALUE, Integer.MAX_VALUE, Integer.MAX_VALUE, Integer.MAX_VALUE
        );
        
        assertEquals("MAX_VALUE for original tile size", Integer.MAX_VALUE, maxConfig.getOriginalTileSize());
        assertEquals("MAX_VALUE for scale", Integer.MAX_VALUE, maxConfig.getScale());
        assertEquals("MAX_VALUE for tile size", Integer.MAX_VALUE, maxConfig.getTileSize());
        assertEquals("MAX_VALUE for FPS", Integer.MAX_VALUE, maxConfig.getFps());
    }
    
    // === TOSTRING TESTS ===
    @Test
    public void testToString_Format() {
        String result = config.toString();
        
        assertTrue("Should contain tile size", result.contains("TileSize=48"));
        assertTrue("Should contain screen dimensions", result.contains("Screen=768x576"));
        assertTrue("Should contain world dimensions", result.contains("World=2400x2400"));
        assertTrue("Should contain FPS", result.contains("FPS=60"));
        assertTrue("Should start with GameConfig", result.startsWith("GameConfig["));
        assertTrue("Should end with ]", result.endsWith("]"));
    }
    
    @Test
    public void testToString_ZeroValues() {
        GameConfig zeroConfig = new GameConfig(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0);
        String result = zeroConfig.toString();
        
        assertTrue("Should contain zero tile size", result.contains("TileSize=0"));
        assertTrue("Should contain zero screen dimensions", result.contains("Screen=0x0"));
        assertTrue("Should contain zero world dimensions", result.contains("World=0x0"));
        assertTrue("Should contain zero FPS", result.contains("FPS=0"));
    }
    
    // === MULTIPLE INSTANCE TESTS ===
    @Test
    public void testMultipleInstances_Independent() {
        GameConfig config1 = new GameConfig(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16);
        GameConfig config2 = new GameConfig(100, 200, 300, 400, 500, 600, 700, 800, 900, 1000, 1100, 1200, 1300, 1400, 1500, 1600);
        
        assertNotEquals("Different instances should have different values", 
                       config1.getTileSize(), config2.getTileSize());
        assertNotEquals("Different instances should have different FPS", 
                       config1.getFps(), config2.getFps());
        
        assertEquals("Config1 should maintain its values", 3, config1.getTileSize());
        assertEquals("Config2 should maintain its values", 300, config2.getTileSize());
    }
    
    // === CONSISTENCY TESTS ===
    @Test
    public void testGetters_Consistency() {
        // Multiple calls to getters should return the same value
        assertEquals("getTileSize should be consistent", config.getTileSize(), config.getTileSize());
        assertEquals("getFps should be consistent", config.getFps(), config.getFps());
        assertEquals("getScreenWidth should be consistent", config.getScreenWidth(), config.getScreenWidth());
    }
    
    @Test
    public void testToString_Consistency() {
        String firstCall = config.toString();
        String secondCall = config.toString();
        assertEquals("toString should be consistent", firstCall, secondCall);
    }
    
    // === LOGICAL RELATIONSHIP TESTS ===
    @Test
    public void testTileSizeCalculation() {
        // Verify that tileSize = originalTileSize * scale
        int calculatedTileSize = config.getOriginalTileSize() * config.getScale();
        assertEquals("Tile size should equal originalTileSize * scale", 
                    calculatedTileSize, config.getTileSize());
    }
    
    @Test
    public void testScreenDimensionsCalculation() {
        // Verify that screenWidth = maxScreenCol * tileSize
        int calculatedScreenWidth = config.getScreenCols() * config.getTileSize();
        assertEquals("Screen width should equal maxScreenCol * tileSize", 
                    calculatedScreenWidth, config.getScreenWidth());
        
        // Verify that screenHeight = maxScreenRow * tileSize
        int calculatedScreenHeight = config.getScreenRows() * config.getTileSize();
        assertEquals("Screen height should equal maxScreenRow * tileSize", 
                    calculatedScreenHeight, config.getScreenHeight());
    }
    
    @Test
    public void testWorldDimensionsCalculation() {
        // Verify that worldWidth = maxWorldCol * tileSize
        int calculatedWorldWidth = config.getWorldCols() * config.getTileSize();
        assertEquals("World width should equal maxWorldCol * tileSize", 
                    calculatedWorldWidth, config.getWorldWidth());
        
        // Verify that worldHeight = maxWorldRow * tileSize
        int calculatedWorldHeight = config.getWorldRows() * config.getTileSize();
        assertEquals("World height should equal maxWorldRow * tileSize", 
                    calculatedWorldHeight, config.getWorldHeight());
    }
}