package com.escape.Model;

import java.util.ArrayList;
import java.util.HashMap;

public class Items {
    private static Items instance;
    private HashMap<String, Item> itemMap;
    

    public Items() {
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
        addItem("letter_l", "Book 'L'", "A heavy book with 'L' on the spine.", "Part of the library code.");
        addItem("letter_i", "Book 'I'", "A heavy book with 'I' on the spine.", "Part of the library code.");
        addItem("letter_b", "Book 'B'", "A heavy book with 'B' on the spine.", "Part of the library code.");
        addItem("letter_r_1", "Book 'R'", "A heavy book with'R' on the spine.", "Part of the library code.");
        addItem("letter_a", "Book 'A'", "A heavy book with 'A' on the spine.", "Part of the library code.");
        addItem("letter_r_2", "Book 'R'", "A heavy book with'R' on the spine.", "Part of the library code."); // 2nd R
        addItem("letter_y", "Book 'Y'", "A heavy book with 'Y' on the spine.", "Part of the library code.");
        addItem("lighter", "lighter", "An old, worn lighter. It looks like it is on its last life.", "Part of the library puzzle");
        addItem("record", "record", "The blackened record is marred with scratches and char marks...", "Part of the library puzzle");
        addItem("fragment_a", "fragment 'A'", "A torn piece of a photo.", "Part of the library puzzle");
        addItem("fragment_b", "fragment 'B'", "A torn piece of a photo.", "Part of the library puzzle");
        addItem("fragment_c", "fragment 'C'", "A torn piece of a photo.", "Part of the library puzzle");
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