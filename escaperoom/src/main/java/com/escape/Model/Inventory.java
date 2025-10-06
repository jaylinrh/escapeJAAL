package com.escape.Model;
import java.util.ArrayList;


public class Inventory {
    private String inventoryId;
    private int maxCapacity;
    private ArrayList<Item> Items;

    public Inventory(String inventoryId, int maxCapacity) {
        this.inventoryId = inventoryId;
        this.maxCapacity = maxCapacity;
        this.Items = new ArrayList<>();
    }

    public boolean hasItem(Item item) {
        return Items.contains(item);
    }

    public void addItem(Item item) {
        if (Items.size()<maxCapacity) {
            Items.add(item);
        }
    }

    public void removeItem(Item item) {
        Items.remove(item);
    }
}
