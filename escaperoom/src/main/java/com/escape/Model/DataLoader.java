package com.escape.Model;

import java.io.FileReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.UUID;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class DataLoader extends DataConstants {
    
    /**
     * Gets a reader for a resource file, trying classpath first (for tests),
     * then falling back to filesystem
     */
    private static Reader getReader(String fileName) throws Exception {
        // First try to load from classpath (for tests)
        InputStream is = DataLoader.class.getClassLoader().getResourceAsStream(fileName);
        if (is != null) {
            return new InputStreamReader(is);
        }
        
        // Fall back to file system (for production)
        return new FileReader(fileName);
    }
    
    public static ArrayList<User> getUsers() {
        ArrayList<User> users = new ArrayList<User>();

            try {
                Reader reader = getReader(USER_FILE_NAME);
                JSONObject jsonObject = (JSONObject) new JSONParser().parse(reader);
                JSONArray usersJSON = (JSONArray) jsonObject.get(USERS_ARRAY);

                for(int i = 0; i <usersJSON.size(); i++) {
                    JSONObject userJSON = (JSONObject) usersJSON.get(i);

                    UUID id = UUID.fromString((String) userJSON.get(USER_ID));
                    String username = (String) userJSON.get(USER_USERNAME);
                    String password = (String) userJSON.get(USER_PASSWORD);
                    int level = ((Long) userJSON.get(USER_LEVEL)).intValue();
                    String currentRoomId = (String) userJSON.get(USER_CURRENT_ROOM_ID);

                    JSONObject playerStateJSON = (JSONObject) userJSON.get(USER_PLAYER_STATE);

                    int worldX = ((Long) playerStateJSON.get(WORLD_X)).intValue();
                    int worldY = ((Long) playerStateJSON.get(WORLD_Y)).intValue();
                    int speed = ((Long) playerStateJSON.get(SPEED)).intValue();
                    String direction = (String) playerStateJSON.get(DIRECTION);
                    boolean collisionOn = (Boolean) playerStateJSON.get(COLLISION_ON);

                    JSONObject solidAreaJSON = (JSONObject) playerStateJSON.get(SOLID_AREA);

                    int solidX = ((Long) solidAreaJSON.get(X)).intValue();
                    int solidY = ((Long) solidAreaJSON.get(Y)).intValue();
                    int solidWidth = ((Long) solidAreaJSON.get(WIDTH)).intValue();
                    int solidHeight = ((Long) solidAreaJSON.get(HEIGHT)).intValue();
                    SolidArea solidArea = new SolidArea(solidX, solidY, solidWidth, solidHeight);

                    JSONObject spriteImagesJSON = (JSONObject) playerStateJSON.get(SPRITE_IMAGES);

                    String u1 = (String) spriteImagesJSON.get(U1);
                    String u2 = (String) spriteImagesJSON.get(U2);
                    String d1 = (String) spriteImagesJSON.get(D1);
                    String d2 = (String) spriteImagesJSON.get(D2);
                    String l1 = (String) spriteImagesJSON.get(L1);
                    String l2 = (String) spriteImagesJSON.get(L2);
                    String r1 = (String) spriteImagesJSON.get(R1);
                    String r2 = (String) spriteImagesJSON.get(R2);

                    SpriteImages spriteImages = new SpriteImages(u1, u2, d1, d2, l1, l2, r1, r2);

                   PlayerState playerState = new PlayerState(worldX, worldY, speed, direction, solidArea, collisionOn, spriteImages);

                    JSONObject inventoryJSON = (JSONObject) userJSON.get(USER_INVENTORY);
                    String inventoryId = (String) inventoryJSON.get(INVENTORY_ID);
                    int maxCapacity = ((Long) inventoryJSON.get(MAX_CAPACITY)).intValue();

                    Inventory inventory = new Inventory(inventoryId, maxCapacity);

                   
                    JSONArray itemsJSON = (JSONArray) inventoryJSON.get(ITEMS);
                    

                    for (int j = 0; j < itemsJSON.size(); j++) {
                        JSONObject itemJSON = (JSONObject) itemsJSON.get(j);
                        String itemId = (String) itemJSON.get(ITEM_ID);
                        String itemName = (String) itemJSON.get(NAME);
                        String itemHint = (String) itemJSON.get(HINT);
                        String itemDesc = (String) itemJSON.get(DESCRIPTION);

                       Item item = new Item(itemId, itemName, itemHint, itemDesc);
                       inventory.addItem(item);
                    }


                    User player = new User(id, username, password, level, currentRoomId, playerState, inventory);
                    users.add(player);
                }
                reader.close();
                return users;

            } catch (Exception e) {
                e.printStackTrace();
            }

            return users;
        
    } 

    public static ArrayList<Tile> getTiles() {
        ArrayList<Tile> tiles = new ArrayList<Tile>();

        try {
            Reader reader = getReader(ROOM_FILE_NAME);
            JSONObject jsonObject = (JSONObject) new JSONParser().parse(reader);
            JSONArray tilesJSON = (JSONArray) jsonObject.get(TILE_ARRAY);

            for(int i = 0; i < tilesJSON.size(); i++) {
                JSONObject tileJSON = (JSONObject) tilesJSON.get(i);

                int tileId = ((Long) tileJSON.get(TILE_ID)).intValue();
                String tileName = (String) tileJSON.get(TILE_NAME);
                String imagePath = (String) tileJSON.get(IMAGE_PATH);
                boolean collision = (Boolean) tileJSON.get(COLLISION);
                boolean isSpecial = (Boolean) tileJSON.get(IS_SPECIAL);

                Tile tile = new Tile(tileId, tileName, imagePath, collision, isSpecial);
                tiles.add(tile);
            }
            reader.close();
            return tiles;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return tiles;
    }

    public static ArrayList<Room> getRooms() {
        ArrayList<Room> rooms = new ArrayList<Room>();

        try {
            Reader reader = getReader(ROOM_FILE_NAME);
            JSONObject jsonObject = (JSONObject) new JSONParser().parse(reader);
            JSONArray roomsJSON = (JSONArray) jsonObject.get(ROOMS_ARRAY);

            for(int i = 0; i < roomsJSON.size(); i++) {
                JSONObject roomJSON = (JSONObject) roomsJSON.get(i);

                String roomId = (String) roomJSON.get(ROOM_ID);
                String roomName = (String) roomJSON.get(ROOM_NAME);
                String roomDescription = (String) roomJSON.get(ROOM_DESCRIPTION);
                String mapFile = (String) roomJSON.get(MAP_FILE);
                String music = (String) roomJSON.get(MUSIC);

                JSONObject puzzleJSON = (JSONObject) roomJSON.get(PUZZLE);
                String puzzleId = (String) puzzleJSON.get(PUZZLE_ID);
                String puzzleTitle = (String) puzzleJSON.get(PUZZLE_TITLE);
                String puzzleDescription = (String) puzzleJSON.get(PUZZLE_DESCRIPTION);
                int puzzleLevel = ((Long) puzzleJSON.get(PUZZLE_LEVEL)).intValue();
                boolean isSolved = (Boolean) puzzleJSON.get(IS_SOLVED);

                JSONObject dialogueJSON = (JSONObject) roomJSON.get(DIALOGUE);
                String dialogueId = (String) dialogueJSON.get(DIALOGUE_ID);
                String dialogueFile = (String) dialogueJSON.get(DIALOGUE_FILE);
                JSONArray dialoguesArray = (JSONArray) dialogueJSON.get(DIALOGUES);
                ArrayList<String> dialogues = new ArrayList<String>();
                for (int j = 0; j < dialoguesArray.size(); j++) {
                    dialogues.add((String) dialoguesArray.get(j));
                }

                JSONArray availableItemsJSON = (JSONArray) roomJSON.get(AVAILABLE_ITEMS);
                ArrayList<String> availableItems = new ArrayList<String>();
                for (int j = 0; j < availableItemsJSON.size(); j++) {
                    availableItems.add((String) availableItemsJSON.get(j));
                }

                Room room = new Room(roomId, roomName, roomDescription, mapFile, music,
                                    puzzleId, puzzleTitle, puzzleDescription, puzzleLevel, isSolved,
                                    dialogueId, dialogueFile, dialogues, availableItems);
                rooms.add(room);
        }
        reader.close();
        return rooms;
    } catch(Exception e) {
        e.printStackTrace();
    }

    return rooms;
    }

    public static GameConfig getGameConfig() {

        try {
            Reader reader = getReader(ROOM_FILE_NAME);
            JSONObject jsonObject = (JSONObject) new JSONParser().parse(reader);
            JSONObject configJSON = (JSONObject) jsonObject.get(GAME_CONFIG);

            JSONObject displayJSON = (JSONObject) configJSON.get(DISPLAY);
            int originalTileSize = ((Long) displayJSON.get(ORIGINAL_TILE_SIZE)).intValue();
            int scale = ((Long) displayJSON.get(SCALE)).intValue();
            int tileSize = ((Long) displayJSON.get(TILE_SIZE)).intValue();
            int maxScreenCol = ((Long) displayJSON.get(MAX_SCREEN_COL)).intValue();
            int maxScreenRow = ((Long) displayJSON.get(MAX_SCREEN_ROW)).intValue();
            int screenWidth = ((Long) displayJSON.get(SCREEN_WIDTH)).intValue();
            int screenHeight = ((Long) displayJSON.get(SCREEN_HEIGHT)).intValue();

            JSONObject worldJSON = (JSONObject) configJSON.get(WORLD);
            int maxWorldCol =((Long) worldJSON.get(MAX_WORLD_COL)).intValue();
            int maxWorldRow = ((Long) worldJSON.get(MAX_WORLD_ROW)).intValue();
            int worldWidth = ((Long) worldJSON.get(WORLD_WIDTH)).intValue();
            int worldHeight = ((Long) worldJSON.get(WORLD_HEIGHT)).intValue();

            JSONObject gameplayJSON = (JSONObject) configJSON.get(GAMEPLAY);
            int fps = ((Long) gameplayJSON.get(FPS)).intValue();
            JSONObject gameStatesJSON = (JSONObject) gameplayJSON.get(GAME_STATES);
            int playState = ((Long) gameStatesJSON.get(PLAY_STATE)).intValue();
            int pauseState = ((Long) gameStatesJSON.get(PAUSE_STATE)).intValue();
            int dialogueState = ((Long) gameStatesJSON.get(DIALOGUE_STATE)).intValue();
            int inventoryState = ((Long) gameStatesJSON.get(INVENTORY_STATE)).intValue();

            GameConfig config = new GameConfig(
                originalTileSize, scale, tileSize, maxScreenCol, maxScreenRow,
                screenWidth, screenHeight, maxWorldCol, maxWorldRow, worldWidth,
                worldHeight, fps, playState, pauseState, dialogueState, inventoryState
            );
            
            reader.close();
            return config;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
}