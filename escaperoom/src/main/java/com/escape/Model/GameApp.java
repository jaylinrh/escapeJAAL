package com.escape.Model;

import java.util.ArrayList;

import javafx.animation.AnimationTimer;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

public class GameApp extends Pane {
    
public PuzzleManager puzzleManager;

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

    public void saveGame() {
        DataWriter.saveGame(this);
    }

    final int originalTileSize = 16;
    final int scale = 3;


    public final int tileSize = config.getTileSize();
    public final int screenCols = config.getScreenCols();
    public final int screenRows = config.getScreenRows();
    final int screenWidth = config.getScreenWidth();
    final int screenHeight = config.getScreenHeight();
    
    // World settings from config
    public final int worldCols = config.getWorldCols();
    public final int worldRows = config.getWorldRows();
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
    public ArrayList<InteractableObject> gameObjects;
    private InteractableObject nearestObject;
    
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

    //public ArrayList<InteractableObject> gameObjects;
    //private InteractableObject nearestObject;
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
        puzzleManager = new PuzzleManager(this);
        keyH = new KeyHandler(this);
        cHandler = new CollisionHandler(this);
        tileM = new TileManager(this);
        player = new Player(this, keyH);
        ui = new UI(this);
        this.gameObjects = new ArrayList<>();
        
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
        puzzleManager.exitPuzzle();
// 1. Get Room Data
    RoomList roomList = RoomList.getInstance();
    Room room = roomList.getRoomById(roomId);
    
    if (room == null) {
        System.err.println("ERROR: Room not found in RoomList: " + roomId);
        return;
    }
    
    // 2. load the Map File
    // ensure you have "parlor.txt" inside your /resources/maps/ folder!
    String mapPath = "/maps/" + room.getMapFile();
    tileM.loadMap(mapPath);
    player.setCurrentRoom(room);

    // 3. initialize the Specific Puzzle for this Room
    // This replaces the hardcoded "if (room_foyer)" checks
    switch (roomId) {
        case "room_foyer":
            // Activate it immediately
            puzzleManager.activatePuzzle("room_foyer"); 
            break;

        case "room_parlor":
            System.out.println("=== LOADING PARLOR (No Puzzle Yet) ===");
            
            break;
            
        default:
            break;
    }

    // 4. handle Room Visit Logic (Dialogues, etc.)
    Facade facade = Facade.getInstance();
    Progression progression = facade.getProgression();
    
    boolean hasVisitedBefore = (progression != null && progression.hasVisitedRoom(roomId));
    
    if (hasVisitedBefore) {
        gameState = playState;
    } else {
        // Show dialogue for first visit if available
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
            facade.saveUserProgress();
        }
    }
    
    // 5. load Objects
    // loadRoomObjects(room); // Uncomment when you are ready to use this
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
            puzzleManager.update();
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
        // calculate center of player to avoid accidental triggers
        int playerCenterX = player.worldX + (tileSize / 2);
        int playerCenterY = player.worldY + (tileSize / 2);
        
        int playerCol = playerCenterX / tileSize;
        int playerRow = playerCenterY / tileSize;
        
        // ensure we are within map bounds to avoid crash
        if (playerCol >= 0 && playerCol < worldCols && 
            playerRow >= 0 && playerRow < worldRows) {
            
            int tileNum = tileM.mapTileNum[playerCol][playerRow];
            
            // Check if it's a special transition tile
            if (tileM.tile[tileNum] != null && tileM.tile[tileNum].isSpecial) {
                Room currentRoom = getCurrentRoom();
                
                // 1. Logic for Foyer to Parlor 
                if (currentRoom != null && currentRoom.getRoomId().equals("room_foyer")) {
                    // If we stepped on the newly opened door (Tile 10)
                    if (tileNum == 10) {
                        System.out.println("Entering Parlor...");
                        loadRoom("room_parlor"); 
                        
                        
                        // change these numbers to where you want them to spawn in the Parlor.
                        player.worldX = tileSize * 2; 
                        player.worldY = tileSize * 10;
                    }
                }
                
                // 2. Logic for Exterior -> Foyer (Existing logic)
                else if (currentRoom != null && currentRoom.getRoomId().equals("room_exterior")) {
                    loadRoom("room_foyer");
                    // Move player to Foyer entrance
                    player.worldX = tileSize * 24; 
                    player.worldY = tileSize * 45;
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
        puzzleManager.draw(gc);
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
