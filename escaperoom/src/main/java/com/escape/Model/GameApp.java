package com.escape.Model;

import java.util.ArrayList;

import javafx.animation.AnimationTimer;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

public class GameApp extends Pane {
    
public PuzzleManager puzzleManager;

    private static GameApp instance;
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
    public int cutsceneState = 5;
    
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

        instance = this;

        // Create canvas
        canvas = new Canvas(screenWidth, screenHeight);
        gc = canvas.getGraphicsContext2D();
        
        // Add canvas to pane
        this.getChildren().add(canvas);
        this.setPrefSize(screenWidth, screenHeight);
        this.setMinSize(screenWidth, screenHeight);
        this.setMaxSize(screenWidth, screenHeight);
        
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

        this.setOnMouseClicked(e -> {
            this.requestFocus();

            if (gameState == pauseState) {
                if (ui.isQuitButtonClicked(e.getX(), e.getY())) {
                    System.out.println("Quitting to Main Menu");

                    SceneManager.getInstance().returnToMenu();
                }
            }
        });   
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
            //test user login for puzzle tests.
            System.out.println("Auto-logging in Test User");
            
            Facade facade = Facade.getInstance();
            
            //  Try to register a "Tester" account.
            //    If registerUser returns 'false', user already exists.
            if (!facade.registerUser("Tester", "password")) {
                //  If user exists, just log them in.
                facade.loginUser("Tester", "password");
            }
            loadRoom("room_exterior");
        }
    this.requestFocus();
    
}

     public void loadRoom(String roomId) {
        puzzleManager.exitPuzzle();
        gameObjects.clear();
        //  Get Room Data
        RoomList roomList = RoomList.getInstance();
        Room room = roomList.getRoomById(roomId);
    
        if (room == null) {
            System.err.println("ERROR: Room not found in RoomList: " + roomId);
            return;
        }
    
        //  load the Map File
        String mapPath = "/maps/" + room.getMapFile();
        tileM.loadMap(mapPath);
        player.setCurrentRoom(room);

        User user = Facade.getInstance().getCurrentUser();
        if (user != null) {
            user.setCurrentRoomID(roomId);
        }

        //  initialize the Specific Puzzle for this Room
        // This replaces the hardcoded "if (room_foyer)" checks
        switch (roomId) {
            case "room_foyer":
                // Activate it immediately
                if (!puzzleManager.puzzles.containsKey("room_foyer")) {
                    puzzleManager.puzzles.put("room_foyer", new FoyerPuzzle(this));    
                }
                puzzleManager.activatePuzzle("room_foyer"); 
                if (puzzleManager.puzzles.containsKey("room_foyer")) {
                    puzzleManager.puzzles.get("room_foyer").onRoomLoaded();
                }
                break;

            case "room_parlor":
                System.out.println(" Loading Parlor Book Puzzle");
                // same class, just activated in the Parlor now
                LibraryPuzzle bookPuzzle = new LibraryPuzzle(this);
                puzzleManager.puzzles.put("room_parlor", bookPuzzle);
                puzzleManager.activatePuzzle("room_parlor");
                bookPuzzle.onRoomLoaded();
                
                break;

            case "room_library": // Make sure this matches the ID in rooms.json
                System.out.println("LOADING LIBRARY PUZZLE");
                LibraryItemsPuzzle cipherPuzzle = new LibraryItemsPuzzle(this);
                puzzleManager.puzzles.put("room_library",cipherPuzzle);
                puzzleManager.activatePuzzle("room_library");
                cipherPuzzle.onRoomLoaded();
                break;

            case "room_cellar": 
                System.out.println("entering cellar - final room");
                break;
                
            default:
                break;
        }

        //  handle Room Visit Logic (Dialogues, etc.)
        Facade facade = Facade.getInstance();
        Progression progression = facade.getProgression();
        
        boolean hasVisitedBefore = false;
        if (progression != null) {
            hasVisitedBefore = progression.hasVisitedRoom(roomId);
        }
    
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
    
    //  load Objects
    // loadRoomObjects(room); // Uncomment when ready to use.
}

    private void loadRoomObjects(Room room) {
        gameObjects.clear();
        
        ArrayList<String> availableItems = room.getAvailableItemIds();
        Items itemManager = Items.getInstance();
        
        Facade facade = Facade.getInstance();
        User currentUser = facade.getCurrentUser();
        Progression progression = facade.getProgression();

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
        
        // ensure we are within map bounds
        if (playerCol < 0 || playerCol >= worldCols || 
            playerRow < 0 || playerRow >= worldRows) {
            return;
        }
        
        int tileNum = tileM.mapTileNum[playerCol][playerRow];
        
        // check if it's a special transition tile
        if (tileM.tile[tileNum] == null || !tileM.tile[tileNum].isSpecial) {
            return;
        }

        Room currentRoom = getCurrentRoom();
        if (currentRoom == null) return;
        
        String roomId = currentRoom.getRoomId();
        Facade facade = Facade.getInstance();
        
        switch (roomId) {
            case "room_exterior":
                // exterior to foyer
                if (tileNum == 10) {
                    System.out.println("Entering Foyer...");
                    facade.completeRoom(roomId);
                    loadRoom("room_foyer");
                    player.worldX = tileSize * 24;
                    player.worldY = tileSize * 45;
                    savePlayerPosition();
                }
                break;
                
            case "room_foyer":
                // foyer to parlor
                if (tileNum == 10) {
                    System.out.println("Entering Parlor...");
                    facade.completeRoom(roomId);
                    loadRoom("room_parlor");
                    player.worldX = tileSize * 2;
                    player.worldY = tileSize * 10;
                    savePlayerPosition();
                }
                break;
                
            case "room_parlor":
                // parlor → library
                if (tileNum == 10) {
                    System.out.println("Entering Library...");
                    facade.completeRoom(roomId);
                    loadRoom("room_library");
                    player.worldX = tileSize * 24;
                    player.worldY = tileSize * 45;
                    savePlayerPosition();
                }
                break;
                
            case "room_library":
                // library → cellar
                if (tileNum == 10) {
                    System.out.println("Entering Cellar...");
                    facade.completeRoom(roomId);
                    loadRoom("room_cellar");
                    player.worldX = tileSize * 24;
                    player.worldY = tileSize * 45;
                    savePlayerPosition();
                }
                break;
                
            case "room_cellar":
                // cellar to certificate
                if (tileNum == 10) {
                    System.out.println("GAME COMPLETE! Showing certificate...");
                    completeGame();
                }
                break;
        }
    }

    private void completeGame() {
        stopGameThread();
        
        Facade facade = Facade.getInstance();
        if (facade.getProgression() != null) {
            facade.getProgression().completeRoom("room_cellar");
        }
        facade.saveUserProgress();
        SceneManager.getInstance().showCertificate();
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
        
        if (nearestObject != null) {
            if (nearestObject.getType() == InteractableObject.ObjectType.SLOT) {
                if (nearestObject.isFilled()) {
                    ui.interactionPrompt = "Press E to Remove Book";
                } else {
                    ui.interactionPrompt = "Press E to Place Book";
                }
            } else if (nearestObject.getType() == InteractableObject.ObjectType.TORCH) {
                if (!nearestObject.getLit()) {
                    ui.interactionPrompt = "Press E to light the torch";
                } else {
                    ui.interactionPrompt = "The fire burns with an ethereal light";
                }
            } else {
            ui.interactionPrompt = "Press E to pick up " + nearestObject.getName();
            }
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

            Room currentRoom = player.getCurrentRoom();
            if (currentRoom != null) {
                currentUser.setCurrentRoomID(currentRoom.getRoomId());
            }
            facade.saveUserProgress();

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
    public static GameApp getInstance() {
    return instance;
    }
}
