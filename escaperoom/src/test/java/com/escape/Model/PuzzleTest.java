package com.escape.Model;

import org.junit.After;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.Test;

/**
 * Unit tests for Puzzle class.
 *
 * Coverage:
 *  • Constructor and getters
 *  • Setters modifying state
 *  • toString() formatting and correctness
 *  • Edge and error-like cases (nulls, extremes)
 */
public class PuzzleTest {

    private Puzzle puzzle;

    @Before
    public void setUp() {
        puzzle = new Puzzle("p001", "Riddle of Sphinx", "Solve the ancient riddle.", 3, false);
    }

    @After
    public void tearDown() {
        puzzle = null;
    }

    // === CONSTRUCTOR & GETTERS ===
    @Test
    public void testConstructor_SetsAllFieldsCorrectly() {
        assertEquals("Puzzle ID should match constructor input", "p001", puzzle.getPuzzleId());
        assertEquals("Title should match constructor input", "Riddle of Sphinx", puzzle.getTitle());
        assertEquals("Description should match constructor input", "Solve the ancient riddle.", puzzle.getDescription());
        assertEquals("Level should match constructor input", 3, puzzle.getLevel());
        assertFalse("Puzzle should initially be unsolved", puzzle.isSolved());
    }

    // === SETTERS ===
    @Test
    public void testSetters_UpdateValuesCorrectly() {
        puzzle.setPuzzleId("p002");
        puzzle.setTitle("Mirror Maze");
        puzzle.setDescription("Find your way out of the maze of mirrors.");
        puzzle.setLevel(5);
        puzzle.setSolved(true);

        assertEquals("Puzzle ID should update", "p002", puzzle.getPuzzleId());
        assertEquals("Title should update", "Mirror Maze", puzzle.getTitle());
        assertEquals("Description should update", "Find your way out of the maze of mirrors.", puzzle.getDescription());
        assertEquals("Level should update", 5, puzzle.getLevel());
        assertTrue("Solved status should update", puzzle.isSolved());
    }

    // === TOSTRING ===
    @Test
    public void testToString_ContainsAllFieldsInJsonFormat() {
        String expected = "{\"puzzleId\":\"p001\",\"title\":\"Riddle of Sphinx\",\"description\":\"Solve the ancient riddle.\",\"level\":3,\"isSolved\":false}";
        assertEquals("toString() should produce correct JSON-like format", expected, puzzle.toString());
    }

    @Test
    public void testToString_ReflectsUpdatedValues() {
        puzzle.setPuzzleId("p999");
        puzzle.setTitle("Shadow Cipher");
        puzzle.setDescription("Decode the symbols hidden in the shadows.");
        puzzle.setLevel(7);
        puzzle.setSolved(true);

        String expected = "{\"puzzleId\":\"p999\",\"title\":\"Shadow Cipher\",\"description\":\"Decode the symbols hidden in the shadows.\",\"level\":7,\"isSolved\":true}";
        assertEquals("toString() should include updated field values", expected, puzzle.toString());
    }

    // === EDGE CASES ===
    @Test
    public void testNullAndEmptyStringFields() {
        puzzle.setPuzzleId(null);
        puzzle.setTitle("");
        puzzle.setDescription(null);

        assertNull("Puzzle ID can be set to null", puzzle.getPuzzleId());
        assertEquals("Empty title should be allowed", "", puzzle.getTitle());
        assertNull("Description can be set to null", puzzle.getDescription());
    }

    @Test
    public void testLevelExtremeValues() {
        puzzle.setLevel(Integer.MAX_VALUE);
        assertEquals("Level should accept maximum int", Integer.MAX_VALUE, puzzle.getLevel());

        puzzle.setLevel(Integer.MIN_VALUE);
        assertEquals("Level should accept minimum int", Integer.MIN_VALUE, puzzle.getLevel());
    }

    @Test
    public void testIsSolvedToggle() {
        assertFalse("Initially unsolved", puzzle.isSolved());
        puzzle.setSolved(true);
        assertTrue("Should mark as solved", puzzle.isSolved());
        puzzle.setSolved(false);
        assertFalse("Should toggle back to unsolved", puzzle.isSolved());
    }

    // === CONSISTENCY TEST ===
    @Test
    public void testTwoIdenticalPuzzles_HaveSameToStringOutput() {
        Puzzle another = new Puzzle("p001", "Riddle of Sphinx", "Solve the ancient riddle.", 3, false);
        assertEquals("toString outputs should match for identical puzzles", puzzle.toString(), another.toString());
    }
}
