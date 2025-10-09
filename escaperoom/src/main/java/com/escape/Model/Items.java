package com.escape.Model;

import java.util.ArrayList;
import java.util.HashMap;

public class Items {
    private static Items instance;
    private HashMap<String, Item> itemMap;
    
    private Items() {
        itemMap = new HashMap<>();
        createItems();
    }
    
    public static Items getInstance() {
        if (instance == null) {
            instance = new Items();
        }
        return instance;
    }
    
    private void createItems() {
    }
    
    private void addItem(String itemId, String name, String hint, String description) {
        Item item = new Item(itemId, name, hint, description);
        itemMap.put(itemId, item);
    }
    
    public boolean hasItem(String itemId) {
        return itemMap.containsKey(itemId);
    }
    
    public Item getItemById(String itemId) {
        return itemMap.get(itemId);
    }
    
    public ArrayList<Item> getAllItems() {
        return new ArrayList<>(itemMap.values());
    }
    
    public void addItem(Item item) {
        if (!hasItem(item.getItemId())) {
            itemMap.put(item.getItemId(), item);
        }
    }
    
    public void removeItem(String itemId) {
        itemMap.remove(itemId);
    }
    
    public int getItemCount() {
        return itemMap.size();
    }
    
    public ArrayList<Item> getItemsForRoom(Room room) {
        ArrayList<Item> roomItems = new ArrayList<>();
        for (String itemId : room.getAvailableItemIds()) {
            Item item = getItemById(itemId);
            if (item != null) {
                roomItems.add(item);
            }
        }
        return roomItems;
    }
}