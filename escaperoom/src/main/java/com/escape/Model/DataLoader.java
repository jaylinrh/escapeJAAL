package com.escape.Model;

import java.io.FileReader;
import java.util.ArrayList;
import java.util.UUID;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class DataLoader extends DataConstants {
    
    public static ArrayList<User> getUsers() {
        ArrayList<User> users = new ArrayList<User>();

            try {
                FileReader reader = new FileReader(USER_FILE_NAME);
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
                return users;

            } catch (Exception e) {
                e.printStackTrace();
            }

            return users;
        
    } 

    public static ArrayList<Tile> getTiles() {
        ArrayList<Tile> tiles = new ArrayList<Tile>();

        try {
            FileReader reader = new FileReader(ROOM_FILE_NAME);
            JSONObject jsonObject = (JSONObject) new JSONParser().parse(reader);
            JSONArray tilesJSON = (JSONArray) jsonObject.get(TILES_ARRAY);

            for(int i = 0; i < tiles.JSON.size(); i++) {
                JSONObject tileJSON = (JSONObject) tilesJSON.get(i);

                int tileId = ((Long) tileJSON.get(TILE_ID)).intValue();
                String tileName = (String) tileJSON.get(TILE_NAME);
                String imagePath = (String) tileJSON.get(IMAGE_PATH);
                boolean collision = (Boolean) tileJSON.get(COLLISION);
                boolean isSpecial = (Boolean) tileJSON.get(IS_SPECIAL);

                Tile tile = new Tile(tileId, tileName, imagePath, collision, isSpecial);
                tile.add(tile);
            }
            return tiles;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return tiles;
    }

    public static ArrayList<Room> getRooms() {
        ArrayList<Room> rooms = new ArrayList<Room>();

        try {
            FileReader reader = new FileReader(ROOM_FILE_NAME);
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
        return rooms;
    } catch(Exception e) {
        e.printStackTrace();
    }

    return rooms;
    }
    







        /**
         * This is a Tester method so I can see if the dataLoader is working for the current constants.
         * @param args
         */

    
    public static void main(String[] args) {
        System.out.println("Testing dataLoader with current constants \n");
        ArrayList<User> users = DataLoader.getUsers();

        if(users == null || users.isEmpty()) {
            System.out.println("error");
            return;
        }  
                System.out.println(" IT WORKS! " + users.size() + " users:\n");

                for (int i = 0; i < users.size(); i++) {
                    User player = users.get(i);
                    System.out.println(" User #" + (i+1) + ":");
                    System.out.println(" ID: " + player.getId());
                    System.out.println(" Username: " + player.getUserName());
                    System.out.println(" Password: " + player.getPassword());
                    System.out.println(" Level: " + player.getLevel());
                    System.out.println(" Current Room: " + player.getCurrentRoomID());

                    if(player.getPlayerState() != null) {
                        PlayerState ps = player.getPlayerState();
                        System.out.println("\n --Player state-- ");
                        System.out.println("Position:" + ps.getWorldX() + ", " + ps.getWorldY());
                        System.out.println("Speed: " + ps.getSpeed());
                        System.out.println("Direction: " + ps.getDirection());
                        System.out.println("Collision: " + ps.getCollision());

                        if(ps.getSolidArea() != null) {
                            System.out.println("Solid Area: Present");
                        }

                        if(ps.getSpriteImages() != null) {
                            System.out.println("Sprite Images: present");
                        }

                        if(player.getInventory() != null) {
                            System.out.println("\n --Inventory--");
                            System.out.println("Inventory loaded!");
                        }
                    }
                }
            }
    
    }
