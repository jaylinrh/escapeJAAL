package com.escape.Model;

import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

public class SolidAreaTest {
    private SolidArea solidArea;
    
    @Before
    public void setUp() {
        solidArea = new SolidArea(10, 20, 30, 40);
    }
    
    // === GETTER TESTS ===
    @Test
    public void testGetters_ReturnCorrectValues() {
        assertEquals("getX should return correct value", 10, solidArea.getX());
        assertEquals("getY should return correct value", 20, solidArea.getY());
        assertEquals("getWidth should return correct value", 30, solidArea.getWidth());
        assertEquals("getHeight should return correct value", 40, solidArea.getHeight());
    }
    
    // === SETTER TESTS ===
    @Test
    public void testSetters_UpdateValues() {
        solidArea.setX(100);
        solidArea.setY(200);
        solidArea.setWidth(300);
        solidArea.setHeight(400);
        
        assertEquals("setX should update x value", 100, solidArea.getX());
        assertEquals("setY should update y value", 200, solidArea.getY());
        assertEquals("setWidth should update width value", 300, solidArea.getWidth());
        assertEquals("setHeight should update height value", 400, solidArea.getHeight());
    }
    
    @Test
    public void testSetters_ZeroValues() {
        solidArea.setX(0);
        solidArea.setY(0);
        solidArea.setWidth(0);
        solidArea.setHeight(0);
        
        assertEquals("setX should handle zero", 0, solidArea.getX());
        assertEquals("setY should handle zero", 0, solidArea.getY());
        assertEquals("setWidth should handle zero", 0, solidArea.getWidth());
        assertEquals("setHeight should handle zero", 0, solidArea.getHeight());
    }
    
    @Test
    public void testSetters_NegativeValues() {
        solidArea.setX(-5);
        solidArea.setY(-10);
        solidArea.setWidth(-15);
        solidArea.setHeight(-20);
        
        assertEquals("setX should handle negative values", -5, solidArea.getX());
        assertEquals("setY should handle negative values", -10, solidArea.getY());
        assertEquals("setWidth should handle negative values", -15, solidArea.getWidth());
        assertEquals("setHeight should handle negative values", -20, solidArea.getHeight());
    }
    
    @Test
    public void testSetters_MaxValues() {
        solidArea.setX(Integer.MAX_VALUE);
        solidArea.setY(Integer.MAX_VALUE);
        solidArea.setWidth(Integer.MAX_VALUE);
        solidArea.setHeight(Integer.MAX_VALUE);
        
        assertEquals("setX should handle MAX_VALUE", Integer.MAX_VALUE, solidArea.getX());
        assertEquals("setY should handle MAX_VALUE", Integer.MAX_VALUE, solidArea.getY());
        assertEquals("setWidth should handle MAX_VALUE", Integer.MAX_VALUE, solidArea.getWidth());
        assertEquals("setHeight should handle MAX_VALUE", Integer.MAX_VALUE, solidArea.getHeight());
    }
    
    // === CONSTRUCTOR TESTS ===
    @Test
    public void testConstructor_InitializesAllFields() {
        assertEquals("Constructor should initialize x", 10, solidArea.getX());
        assertEquals("Constructor should initialize y", 20, solidArea.getY());
        assertEquals("Constructor should initialize width", 30, solidArea.getWidth());
        assertEquals("Constructor should initialize height", 40, solidArea.getHeight());
    }
    
    // === TOSTRING TESTS ===
    @Test
    public void testToString_ReflectsSetterChanges() {
        solidArea.setX(100);
        solidArea.setY(200);
        String result = solidArea.toString();
        
        assertTrue("toString should reflect updated x value", result.contains("\"x\":100"));
        assertTrue("toString should reflect updated y value", result.contains("\"y\":200"));
    }
    
    @Test
    public void testToString_DefaultFormat() {
        String result = solidArea.toString();
        assertEquals("toString should return correct JSON format", 
                    "{\"x\":10,\"y\":20,\"width\":30,\"height\":40}", result);
    }
    
    // === CONSISTENCY TESTS ===
    @Test
    public void testGetterSetter_Consistency() {
        solidArea.setX(50);
        assertEquals("Getter should return value set by setter", 50, solidArea.getX());
        
        solidArea.setY(60);
        assertEquals("Getter should return value set by setter", 60, solidArea.getY());
    }
    
    @Test
    public void testMultipleOperations() {
        // Test sequence of operations
        solidArea.setX(1);
        assertEquals("First setX", 1, solidArea.getX());
        
        solidArea.setY(2);
        assertEquals("Then setY", 2, solidArea.getY());
        
        solidArea.setWidth(3);
        assertEquals("Then setWidth", 3, solidArea.getWidth());
        
        solidArea.setHeight(4);
        assertEquals("Then setHeight", 4, solidArea.getHeight());
        
        String result = solidArea.toString();
        assertEquals("Final toString should reflect all changes",
                    "{\"x\":1,\"y\":2,\"width\":3,\"height\":4}", result);
    }
}