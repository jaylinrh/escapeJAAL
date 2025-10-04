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