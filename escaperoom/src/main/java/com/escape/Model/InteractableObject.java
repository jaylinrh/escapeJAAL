package com.escape.Model;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class InteractableObject {
    
    public int worldX;
    public int worldY;

    private String objectId;
    private String name;
    private String description;
    private String imagePath;
    private Image image;

    private Item containedItem = null;
    private Image containedImage = null;
    private boolean lit = false;

    private ObjectType type;

    private boolean collected = false;
    private boolean interactable = true;

    private Item item;

    private String requiredItemId; //ID of the item this slot wants.
   

    public enum ObjectType{
        ITEM,
        CLUE,
        PUZZLE_PIECE,
        DOOR,
        INTERACTIVE,
        SLOT,
        TORCH
    }
    
    public InteractableObject(String objectId, String name, String description,
                                int worldX, int worldY, String imagePath, ObjectType type) {
        this.objectId = objectId;
        this.name = name;
        this.description = description;
        this.worldX = worldX;
        this.worldY = worldY;
        this.imagePath = imagePath;
        this.type = type;
        loadImage();
    }

    private void loadImage() {
        try {
            if (imagePath != null && !imagePath.isEmpty()) {
                this.image = new Image(getClass().getResourceAsStream(imagePath));
            }
        } catch (Exception e) {
            System.err.println("Failed to load image: " + imagePath);
        }
      }

      public void draw(GraphicsContext gc, int screenX, int screenY, int size) {
        if (collected) return; // Don't draw if collected (picked up items)

        //  draw base object
        if (image != null) {
            gc.drawImage(image, screenX, screenY, size, size);
        }

        // if the slot has a book, draw the bookk on top.
        if (type == ObjectType.SLOT && containedImage != null) {
            // Draw slightly smaller to look like it's "in" the slot
            int padding = 4;
            gc.drawImage(containedImage, screenX + padding, screenY + padding, size - (padding*2), size - (padding*2));
        }
    }

      public boolean isPlayerNearby(int playerWorldX, int playerWorldY, int interactionRange) {
        int distance = Math.abs(playerWorldX - worldX) + Math.abs(playerWorldY - worldY);
        return distance <= interactionRange && !collected && interactable;
      }

      public void interact(Facade facade) {
        switch (type) {
            case SLOT:
                Inventory playerInv = facade.getCurrentUser().getInventory();
                // SLOT IS FULL -> REMOVE BOOK 
                if (containedItem != null) {
                    System.out.println("Taking back " + containedItem.getName());
                    // Give book back to player
                    playerInv.addItem(containedItem);
                    // Clear slot
                    this.containedItem = null;
                    this.containedImage = null;
                    return;
                }

                // SLOT IS EMPTY -> PLACE BOOK
                Item bookToPlace = null;

                // Find the first "letter_" book in player's inventory
                for(Item i : playerInv.getItems()) {
                    if(i.getItemId().startsWith("letter_")) {
                        bookToPlace = i;
                        break;
                    }
                }

                if (bookToPlace != null) {
                    System.out.println("Placed " + bookToPlace.getName());
                    // Remove from player
                    playerInv.removeItem(bookToPlace);
                    // Add to slot
                    this.containedItem = bookToPlace;
                    // Load image for the book so we can draw it
                    try {
                        this.containedImage = new Image(getClass().getResourceAsStream("/items/" + bookToPlace.getItemId() + ".png"));
                    } catch (Exception e) { e.printStackTrace(); }
                    
                } else {
                    System.out.println("You don't have any books to place.");
                }
                break;

            case ITEM:
            case PUZZLE_PIECE:
                if (item != null) {
                    facade.pickupItem(item.getItemId());
                    if (facade.getProgression() != null) {
                        facade.getProgression().collectItem(item.getItemId());
                    }
                    collected = true; // Visually remove from floor
                }
                break;
            
            case CLUE:

            case DOOR:
            case INTERACTIVE:
                System.out.println(name + ": " + description);
                break;
            case TORCH:
                Inventory playerInv1 = facade.getCurrentUser().getInventory();
                boolean hasLighter = false;
                for(Item i : playerInv1.getItems()) {
                    if(i.getItemId().startsWith("lighter")) {
                        hasLighter = true;
                        break;
                    }
                }
                if (hasLighter == true && this.containedImage.equals(new Image(getClass().getResourceAsStream("/items/unlit.png")))) {
                    this.containedImage = new Image(getClass().getResourceAsStream("/items/lit.png"));
                    this.lit = true;
                } else {
                    System.out.println("the torchlight burns with an ethereal light");
                }
        }
    }

    public void setRequiredItemId(String id) { this.requiredItemId = id; }
    public String getRequiredItemId() { return requiredItemId; }

    public boolean isCorrectlyFilled() {
        return containedItem != null && containedItem.getItemId().equals(requiredItemId);
    }
      
    public boolean isFilled() { return containedItem != null; }

    public String getObjectId() { return objectId; }
    public String getName() { return name; }
    public String getDescription() { return description; }
    public ObjectType getType() { return type; }
    public boolean isCollected() { return collected; }
    public void setCollected(boolean collected) {this.collected = collected; }
    public boolean isInteractable() { return interactable; }
    public void setInteractable(boolean interactable) { this.interactable = interactable; }
    public Item getItem() { return item; }
    public void setItem(Item item) { this.item = item; }
    public Item getContainedItem() { return containedItem;}
    public boolean getLit() {return lit;}
}


