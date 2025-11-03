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
    
    // === CONSTRUCTOR TESTS ===
    @Test
    public void testConstructor_InitializesAllFields() {
        assertEquals("X coordinate should be initialized", 10, solidArea.x);
        assertEquals("Y coordinate should be initialized", 20, solidArea.y);
        assertEquals("Width should be initialized", 30, solidArea.width);
        assertEquals("Height should be initialized", 40, solidArea.height);
    }
    
    @Test
    public void testConstructor_ZeroValues() {
        SolidArea zeroArea = new SolidArea(0, 0, 0, 0);
        assertEquals("X should be 0", 0, zeroArea.x);
        assertEquals("Y should be 0", 0, zeroArea.y);
        assertEquals("Width should be 0", 0, zeroArea.width);
        assertEquals("Height should be 0", 0, zeroArea.height);
    }
    
    @Test
    public void testConstructor_NegativeValues() {
        SolidArea negativeArea = new SolidArea(-5, -10, -15, -20);
        assertEquals("X should be -5", -5, negativeArea.x);
        assertEquals("Y should be -10", -10, negativeArea.y);
        assertEquals("Width should be -15", -15, negativeArea.width);
        assertEquals("Height should be -20", -20, negativeArea.height);
    }
    
    @Test
    public void testConstructor_MaxValues() {
        SolidArea maxArea = new SolidArea(Integer.MAX_VALUE, Integer.MAX_VALUE, 
                                         Integer.MAX_VALUE, Integer.MAX_VALUE);
        assertEquals("X should be MAX_VALUE", Integer.MAX_VALUE, maxArea.x);
        assertEquals("Y should be MAX_VALUE", Integer.MAX_VALUE, maxArea.y);
        assertEquals("Width should be MAX_VALUE", Integer.MAX_VALUE, maxArea.width);
        assertEquals("Height should be MAX_VALUE", Integer.MAX_VALUE, maxArea.height);
    }
    
    // === FIELD ACCESS TESTS ===
    @Test
    public void testFieldAccess_X() {
        solidArea.x = 100;
        assertEquals("X should be settable", 100, solidArea.x);
    }
    
    @Test
    public void testFieldAccess_Y() {
        solidArea.y = 200;
        assertEquals("Y should be settable", 200, solidArea.y);
    }
    
    @Test
    public void testFieldAccess_Width() {
        solidArea.width = 300;
        assertEquals("Width should be settable", 300, solidArea.width);
    }
    
    @Test
    public void testFieldAccess_Height() {
        solidArea.height = 400;
        assertEquals("Height should be settable", 400, solidArea.height);
    }
    
    // === TOSTRING TESTS ===
    @Test
    public void testToString_DefaultFormat() {
        String result = solidArea.toString();
        String expected = "{\"x\":10,\"y\":20,\"width\":30,\"height\":40}";
        assertEquals("toString should return correct JSON format", expected, result);
    }
    
    @Test
    public void testToString_ZeroValues() {
        SolidArea zeroArea = new SolidArea(0, 0, 0, 0);
        String result = zeroArea.toString();
        String expected = "{\"x\":0,\"y\":0,\"width\":0,\"height\":0}";
        assertEquals("toString should handle zero values", expected, result);
    }
    
    @Test
    public void testToString_NegativeValues() {
        SolidArea negativeArea = new SolidArea(-1, -2, -3, -4);
        String result = negativeArea.toString();
        String expected = "{\"x\":-1,\"y\":-2,\"width\":-3,\"height\":-4}";
        assertEquals("toString should handle negative values", expected, result);
    }
    
    @Test
    public void testToString_LargeValues() {
        SolidArea largeArea = new SolidArea(1000, 2000, 3000, 4000);
        String result = largeArea.toString();
        String expected = "{\"x\":1000,\"y\":2000,\"width\":3000,\"height\":4000}";
        assertEquals("toString should handle large values", expected, result);
    }
    
    // === EDGE CASE TESTS ===
    @Test
    public void testFieldAssignment_Sequence() {
        solidArea.x = 1;
        solidArea.y = 2;
        solidArea.width = 3;
        solidArea.height = 4;
        
        assertEquals("X should be 1", 1, solidArea.x);
        assertEquals("Y should be 2", 2, solidArea.y);
        assertEquals("Width should be 3", 3, solidArea.width);
        assertEquals("Height should be 4", 4, solidArea.height);
    }
    
    @Test
    public void testMultipleInstances() {
        SolidArea area1 = new SolidArea(1, 2, 3, 4);
        SolidArea area2 = new SolidArea(5, 6, 7, 8);
        
        assertEquals("Area1 X should be independent", 1, area1.x);
        assertEquals("Area2 X should be independent", 5, area2.x);
        assertNotEquals("Instances should have different values", area1.toString(), area2.toString());
    }
    
    // === JSON FORMAT VALIDATION ===
    @Test
    public void testToString_JsonFormat() {
        String result = solidArea.toString();
        
        // Verify JSON structure
        assertTrue("Should contain x field", result.contains("\"x\":10"));
        assertTrue("Should contain y field", result.contains("\"y\":20"));
        assertTrue("Should contain width field", result.contains("\"width\":30"));
        assertTrue("Should contain height field", result.contains("\"height\":40"));
        assertTrue("Should start with {", result.startsWith("{"));
        assertTrue("Should end with }", result.endsWith("}"));
    }
    
    // === BOUNDARY VALUE TESTS ===
    @Test
    public void testBoundaryValues_MinInteger() {
        SolidArea minArea = new SolidArea(Integer.MIN_VALUE, Integer.MIN_VALUE, 
                                         Integer.MIN_VALUE, Integer.MIN_VALUE);
        String result = minArea.toString();
        assertTrue("Should handle MIN_VALUE in toString", result.contains(String.valueOf(Integer.MIN_VALUE)));
    }
    
    @Test
    public void testBoundaryValues_MixedPositiveNegative() {
        SolidArea mixedArea = new SolidArea(-10, 20, -30, 40);
        String result = mixedArea.toString();
        String expected = "{\"x\":-10,\"y\":20,\"width\":-30,\"height\":40}";
        assertEquals("Should handle mixed positive/negative values", expected, result);
    }
}