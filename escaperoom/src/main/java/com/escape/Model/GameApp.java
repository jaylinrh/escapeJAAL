package com.escape.Model;

import java.util.ArrayList;

import javafx.animation.AnimationTimer;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

public class GameApp extends Pane {
    private static GameConfig config = DataLoader.getGameConfig();

    private static GameConfig loadConfig() {
        GameConfig cfg = DataLoader.getGameConfig();
        if (cfg == null) {
            throw new RuntimeException("Failed to load GameConfig from rooms.json! Check the game_config section.");
        }
        return cfg;
    }
    
    public static GameConfig getGameConfig() {
        return config;
    }

    final int originalTileSize = 16;
    final int scale = 3;


    public final int tileSize = config.getTileSize();
    public final int maxScreenCol = config.getMaxScreenCol();
    public final int maxScreenRow = config.getMaxScreenRow();
    final int screenWidth = config.getScreenWidth();
    final int screenHeight = config.getScreenHeight();
    
    // World settings from config
    public final int maxWorldCol = config.getMaxWorldCol();
    public final int maxWorldRow = config.getMaxWorldRow();
    public final int worldWidth = config.getWorldWidth();
    public final int worldHeight = config.getWorldHeight();
    
    // JavaFX Canvas for drawing
    private Canvas canvas;
    private GraphicsContext gc;
    
    // Game components
    public KeyHandler keyH;
    public CollisionHandler cHandler;
    public TileManager tileM;
    public Player player;
    public UI ui;
    
    // Game states
    public int gameState;
    public int playState = 1;
    public int pauseState = 2;
    public int dialogueState = 3;
    public int inventoryState = 4;
    
    // Animation timer for game loop
    private AnimationTimer gameTimer;
    private long lastUpdate = 0;
    private final long FRAME_TIME = 1_000_000_000 / config.getFps();

    public ArrayList<InteractableObject> gameObjects;
    private InteractableObject nearestObject;
    private final int INTERACTION_RANGE = 80;

    private long lastAutoSave = 0;
    private final long AUTO_SAVE_INTERVAL = 30_000;
    
    public GameApp() {
        // Create canvas
        canvas = new Canvas(screenWidth, screenHeight);
        gc = canvas.getGraphicsContext2D();
        
        // Add canvas to pane
        this.getChildren().add(canvas);
        
        // Initialize game components
        keyH = new KeyHandler(this);
        cHandler = new CollisionHandler(this);
        tileM = new TileManager(this);
        player = new Player(this, keyH);
        ui = new UI(this);
        gameObjects = new ArrayList<>();
        
        // Set up key listeners
        setupKeyHandlers();
    }
    
    private void setupKeyHandlers() {
        // Delegate all key handling to KeyHandler
        this.setOnKeyPressed(e -> keyH.handleKeyPressed(e));
        this.setOnKeyReleased(e -> keyH.handleKeyReleased(e));
        
        // Make sure pane can receive key events
        this.setFocusTraversable(true);

        this.setOnMouseClicked(e -> this.requestFocus());
    }

    public void setupGame() {
        User currentUser = Facade.getInstance().getCurrentUser();
        if (currentUser != null) {
            String startRoomId = currentUser.getCurrentRoomID();
            PlayerState savedState = currentUser.getPlayerState();
            if (savedState != null) {
                player.loadPlayerState(savedState);
            }
            loadRoom(startRoomId);
        } else {
            loadRoom("room_foyer");
        }
    this.requestFocus();
    
}

     public void loadRoom(String roomId) {
        RoomList roomList = RoomList.getInstance();
        Room room = roomList.getRoomById(roomId);
        
        if (room == null) {
            System.err.println("ERROR: Room not found: " + roomId);
            return;
        }
        
        String mapPath = "/maps/" + room.getMapFile();
        tileM.loadMap(mapPath);
        player.setCurrentRoom(room);
        loadRoomObjects(room);

        Facade facade = Facade.getInstance();
        Progression progression = facade.getProgression();
        
        boolean hasVisitedBefore = (progression != null && progression.hasVisitedRoom(roomId));
        
        if (hasVisitedBefore) {
            gameState = playState;
        } else {
            // Show dialogue for first visit
            if (room.getDialogues() != null && !room.getDialogues().isEmpty()) {
                ui.dialogues = room.getDialogues().toArray(new String[0]);
                ui.currentDialogueIndex = 0;
                ui.currentText = ui.dialogues[0];
                gameState = dialogueState;
            } else {
                gameState = playState;
            }
            
            // Mark room as visited
            if (progression != null) {
                progression.visitRoom(roomId);
                facade.saveUserProgress(); // Save immediately
            }
        }
    }

    private void loadRoomObjects(Room room) {
        gameObjects.clear();
        
        ArrayList<String> availableItems = room.getAvailableItemIds();
        Items itemManager = Items.getInstance();
        
        Facade facade = Facade.getInstance();
        User currentUser = facade.getCurrentUser();
        Progression progression = facade.getProgression();

        // For now, place items at specific locations
        // TODO: Later, load positions from JSON or room data
        int startX = tileSize * 10;
        int startY = tileSize * 10;
        
        for (int i = 0; i < availableItems.size(); i++) {
            String itemId = availableItems.get(i);
            if (progression != null && progression.hasCollectedItem(itemId)) {
            continue;
            }
            Item item = itemManager.getItemById(itemId);
            
            if (item != null) {
                // Create interactable object for this item
                InteractableObject obj = new InteractableObject(
                    itemId,
                    item.getName(),
                    item.getDescription(),
                    startX + (i * tileSize * 2),
                    startY,
                    "/items/" + itemId + ".png", // Default item image
                    InteractableObject.ObjectType.ITEM
                );
                obj.setItem(item);
                gameObjects.add(obj);
            }
        }
    }

    public Room getCurrentRoom() {
        return player.getCurrentRoom();
    }
    
    public void startGameThread() {
        gameTimer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                if (lastUpdate == 0) {
                    lastUpdate = now;
                    return;
                }
                
                long elapsed = now - lastUpdate;
                if (elapsed >= FRAME_TIME) {
                    update();
                    render();
                    lastUpdate = now;
                }
            }
        };
        gameTimer.start();
    }
    
    public void stopGameThread() {
        if (gameTimer != null) {
            gameTimer.stop();
        }
    }
    
    private void update() {
        if (gameState == playState) {
            player.update();
            checkNearbyObjects();
            checkRoomTransition();
            long currentTime = System.currentTimeMillis();
            if (currentTime - lastAutoSave > AUTO_SAVE_INTERVAL) {
                savePlayerPosition();
                lastAutoSave = currentTime;
            }
        }
    }

    private void checkRoomTransition() {
    int playerCol = player.worldX / tileSize;
    int playerRow = player.worldY / tileSize;
    
    if (playerCol >= 0 && playerCol < maxWorldCol && 
        playerRow >= 0 && playerRow < maxWorldRow) {
        
        int tileNum = tileM.mapTileNum[playerCol][playerRow];
        
        // Check if it's a special transition tile
        if (tileM.tile[tileNum] != null && tileM.tile[tileNum].isSpecial) {
            Room currentRoom = getCurrentRoom();
            
            if (currentRoom != null && currentRoom.getRoomId().equals("room_exterior")) {
                // Transition from exterior to foyer
                loadRoom("room_foyer");
                User currentUser = Facade.getInstance().getCurrentUser();
                if (currentUser != null) {
                    currentUser.setCurrentRoomID("room_foyer");
                }
            }
        }
    }
}

    private void savePlayerPosition() {
    Facade facade = Facade.getInstance();
    User currentUser = facade.getCurrentUser();
    
    if (currentUser != null && currentUser.getPlayerState() != null) {
        PlayerState state = currentUser.getPlayerState();

        state.setWorldX(player.worldX);
        state.setWorldY(player.worldY);
        state.setDirection(player.direction);
        facade.saveUserProgress();
    }
}

    private void checkNearbyObjects() {
        nearestObject = null;
        double closestDistance = INTERACTION_RANGE;
        
        for (InteractableObject obj : gameObjects) {
            if (obj.isPlayerNearby(player.worldX, player.worldY, INTERACTION_RANGE)) {
                double distance = Math.sqrt(
                    Math.pow(player.worldX - obj.worldX, 2) + 
                    Math.pow(player.worldY - obj.worldY, 2)
                );
                
                if (distance < closestDistance) {
                    closestDistance = distance;
                    nearestObject = obj;
                }
            }
        }
        
        // Update UI with interaction prompt
        if (nearestObject != null) {
            ui.interactionPrompt = "Press E to pick up " + nearestObject.getName();
            ui.showInteractionPrompt = true;
        } else {
            ui.showInteractionPrompt = false;
        }
    }

    public void interactWithNearestObject() {
        if (nearestObject != null) {
            nearestObject.interact(Facade.getInstance());
            
            // Show pickup message
            ui.message = "Picked up: " + nearestObject.getName();
            ui.messageOn = true;
            
            // Save progress after picking up item
            Facade.getInstance().saveUserProgress();
        }
    }

    public void savePlayerPositionOnExit() {
        Facade facade = Facade.getInstance();
        User currentUser = facade.getCurrentUser();
        
        if (currentUser != null && currentUser.getPlayerState() != null && player != null) {
            PlayerState state = currentUser.getPlayerState();
                        
            state.setWorldX(player.worldX);
            state.setWorldY(player.worldY);
            state.setDirection(player.direction);
        }
    }
    
    private void render() {
        // Clear canvas
        gc.setFill(Color.BLACK);
        gc.fillRect(0, 0, screenWidth, screenHeight);
        
        // Draw game elements
        tileM.draw(gc);
        drawGameObjects(gc);
        player.draw(gc);
        ui.draw(gc);
    }
    private void drawGameObjects(GraphicsContext gc) {
        for (InteractableObject obj : gameObjects) {
            if (!obj.isCollected()) {
                int screenX = obj.worldX - player.worldX + player.screenX;
                int screenY = obj.worldY - player.worldY + player.screenY;
                
                // Only draw if on screen
                if (screenX > -tileSize && screenX < screenWidth &&
                    screenY > -tileSize && screenY < screenHeight) {
                    obj.draw(gc, screenX, screenY, tileSize);
                }
            }
        }
    }
}