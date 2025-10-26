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

    private ObjectType type;

    private boolean collected = false;
    private boolean interactable = true;

    private Item item;

    public enum ObjectType{
        ITEM,
        CLUE,
        PUZZLE_PIECE,
        DOOR,
        INTERACTIVE
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
            e.printStackTrace();
        }
      }

      public void draw(GraphicsContext gc, int screenX, int screenY, int size) {
        if (image != null && !collected) {
            gc.drawImage(image, screenX, screenY, size, size);
        }
      }

      public boolean isPlayerNearby(int playerWorldX, int playerWorldY, int interactionRange) {
        int distance = Math.abs(playerWorldX - worldX) + Math.abs(playerWorldY - worldY);
        return distance <= interactionRange && !collected && interactable;
      }

      public void interact(Facade facade) {
        switch (type) {
            case ITEM:
            case PUZZLE_PIECE:
                if (item != null) {
                    facade.pickupItem(item.getItemId());

                    if (facade.getProgression() != null) {
                        facade.getProgression().collectItem(item.getItemId());
                    }
                    
                    collected = true;
                }
                break;
            case CLUE:
            System.out.println("Clue: " + description);
            break;
            case DOOR:
            System.out.println("Door: " + name);
            break;
            case INTERACTIVE:
            System.out.println("Interacted with: " + name);
            break;
        }
      }

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
    }

