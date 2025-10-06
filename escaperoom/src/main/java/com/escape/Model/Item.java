package com.escape.Model;

public class Item {
    private String itemId;
    private String name;
    private String hint;
    private String description;

    public Item(String itemId, String name, String hint, String description) {
        this.itemId = itemId;
        this.name = name;
        this.hint = hint;
        this.description = description;
    }
}
