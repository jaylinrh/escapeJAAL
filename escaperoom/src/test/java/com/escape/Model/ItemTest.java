package com.escape.Model;

import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;

/**
 * Unit tests for the Item model.
 *
 * Each test verifies one behavior:
 * - construction and getters
 * - toString formatting for normal values
 * - toString behavior when fields are null
 * - toString behavior with special characters
 * - toString consistency for identical items
 * - behavior with empty itemId
 */
public class ItemTest {

    private Item normalItem;
    private Item nullFieldsItem;
    private Item specialCharsItem;
    private Item emptyIdItem;

    @Before
    public void setUp() {
        normalItem = new Item("item_001", "Golden Key", "Look under the mat", "A small golden key with ornate teeth.");
        nullFieldsItem = new Item(null, null, null, null);
        specialCharsItem = new Item("it\"em\\002", "Name with \"quotes\" and \\backslashes\\", "Hint: use \"quotes\"", "Desc: newline\nand tabs\tend");
        emptyIdItem = new Item("", "Nameless", "No hint", "No description");
    }

    @Test
    public void constructorAndGetters_ShouldReturnValuesProvided() {
        assertEquals("item_001", normalItem.getItemId());
        assertEquals("Golden Key", normalItem.getName());
        assertEquals("Look under the mat", normalItem.getHint());
        assertEquals("A small golden key with ornate teeth.", normalItem.getDescription());
    }

    @Test
    public void toString_ShouldReturnJsonLikeString_WithAllFieldsInserted() {
        String expected = String.format("{\"itemID\":\"%s\",\"name\":\"%s\",\"hint\":\"%s\",\"description\":\"%s\"}",
                "item_001", "Golden Key", "Look under the mat", "A small golden key with ornate teeth.");
        assertEquals(expected, normalItem.toString());
    }

    @Test
    public void toString_NullFields_ShouldIncludeLiteralNullTextInPlaceOfValues() {
        // Note: String.format("%s", (Object) null) -> "null", and since the toString wraps values in quotes,
        // the result will contain the four-letter word null between quotes: "...\"itemID\":\"null\"..."
        String expected = String.format("{\"itemID\":\"%s\",\"name\":\"%s\",\"hint\":\"%s\",\"description\":\"%s\"}",
                null, null, null, null);
        assertEquals(expected, nullFieldsItem.toString());
    }

    @Test
    public void toString_SpecialCharacters_ShouldIncludeCharactersUnescaped() {
        // The Item.toString uses String.format without escaping JSON-special characters,
        // so double-quotes and backslashes will appear as-is in the produced string.
        String expected = String.format("{\"itemID\":\"%s\",\"name\":\"%s\",\"hint\":\"%s\",\"description\":\"%s\"}",
                "it\"em\\002", "Name with \"quotes\" and \\backslashes\\", "Hint: use \"quotes\"", "Desc: newline\nand tabs\tend");
        assertEquals(expected, specialCharsItem.toString());
    }

    @Test
    public void toString_TwoItemsWithSameFields_ShouldProduceIdenticalStrings() {
        Item a = new Item("same", "SameName", "SameHint", "SameDesc");
        Item b = new Item("same", "SameName", "SameHint", "SameDesc");
        assertEquals(a.toString(), b.toString());
    }

    @Test
    public void emptyItemId_ShouldBeAllowedAndReflectedByGetterAndToString() {
        assertEquals("", emptyIdItem.getItemId());
        String expected = String.format("{\"itemID\":\"%s\",\"name\":\"%s\",\"hint\":\"%s\",\"description\":\"%s\"}",
                "", "Nameless", "No hint", "No description");
        assertEquals(expected, emptyIdItem.toString());
    }
}
