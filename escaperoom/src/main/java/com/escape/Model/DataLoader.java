package com.escape.Model;

import java.io.FileReader;
import java.util.ArrayList;
import java.util.UUID;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class DataLoader extends DataConstants{
    
    public static ArrayList<Player> getUsers() {
        ArrayList<Player> users = new ArrayList<Player>();

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

                    int worldX = ((Long) playerStateJSON.get(PlayerState.WORLD_X)).intValue();
                    int worldY = ((Long) playerStateJSON.get(PlayerState.WORLD_Y)).intValue();
                    int speed = ((Long) playerStateJSON.get(PlayerState.SPEED)).intValue();
                    String direction = (String) playerStateJSON.get(PlayerState.DIRECTION);
                    boolean collisionOn = (Boolean) playerStateJSON.get(PlayerState.COLLISION_ON);

                    JSONObject solidAreaJSON = (JSONObject) playerStateJSON.get(PlayerState.SOLID_AREA);

                    int solidX = ((Long) solidAreaJSON.get(PlayerState.SolidArea.X)).intValue();
                    int SolidY = ((Long) solidAreaJSON.get(PlayerState.SolidArea.Y)).intValue();
                    int solidWidth = ((Long) solidAreaJSON.get(PlayerState.SolidArea.WIDTH)).intValue();
                    int solidHeight = ((Long) solidAreaJSON.get(PlayerState.SolidArea.HEIGHT)).intValue();

                    JSONObject spriteImagesJSON = (JSONObject) playerStateJSON.get(PlayerState.SPRITE_IMAGES);

                    String u1 = (String) spriteImagesJSON.get(PlayerState.SpriteImages.U1);
                    String u2 = (String) spriteImagesJSON.get(PlayerState.SpriteImages.U2);
                    String d1 = (String) spriteImagesJSON.get(PlayerState.SpriteImages.D1);
                    String d2 = (String) spriteImagesJSON.get(PlayerState.SpriteImages.D2);
                    String l1 = (String) spriteImagesJSON.get(PlayerState.SpriteImages.L1);
                    String l2 = (String) spriteImagesJSON.get(PlayerState.SpriteImages.L2);
                    String r1 = (String) spriteImagesJSON.get(PlayerState.SpriteImages.R1);
                    String r2 = (String) spriteImagesJSON.get(PlayerState.SpriteImages.R2);

                    JSONObject inventoryJSON = (JSONObject) userJSON.get(USER_INVENTORY);
                    String inventoryId = (String) inventoryJSON.get(Inventory.INVENTORY_ID);
                    int maxCapacity = ((Long) inventoryJSON.get(Inventory.MAX_CAPACITY)).intValue();

                   
                    JSONArray itemsJSON = (JSONArray) inventoryJSON.get(Inventory.ITEMS);
                    ArrayList<Item> items = new ArrayList<Item>();

                    for (int j = 0; j < itemsJSON.size(); j++) {
                        JSONObject itemJSON = (JSONObject) itemsJSON.get(j);
                        String itemId = (String) itemsJSON.get(Inventory.Item.ITEM_ID);
                        String itemName = (String) itemsJSON.get(Inventory.Item.NAME);
                        String itemHint = (String) itemsJSON.get(Inventory.Item.HINT);
                        String itemDesc = (String) itemJSON.get(Inventory.Item.DESCRIPTION);

                        items.add(new Item(itemId, itemName, itemHint, itemDesc));
                    }


                    users.add(new Player(id, username, password, level, currentRoomId));
                } 
                return users;

            } catch (Exception e) {
                e.printStackTrace();
            }

            return users;
        
    } 
        /**
         * This is a Tester method so I can see if the dataLoader is working for the current constants.
         * @param args
         */
    public static void main(String[] args) {
        System.out.println("Testing dataLoader with current constants \n");
        ArrayList<Player> users = DataLoader.getUsers();

        if(users == null || users.isEmpty()) {
            System.out.println("error");
        }  else {
                System.out.println(" IT WORKS! " + users.size() + " users:\n");

                for (int i = 0; i < users.size(); i++) {
                    Player player = users.get(i);
                    System.out.println(" User #" + (i+1) + ":");
                    System.out.println(" ID: " + player.getId());
                    System.out.println(" Username: " + player.getUserName());
                    System.out.println(" Password: " + player.getPassword());
                    System.out.println(" Level: " + player.getLevel());
                    System.out.println(" Current Room: " + player.getCurrentRoomID());
                }
            }
    
    }
}