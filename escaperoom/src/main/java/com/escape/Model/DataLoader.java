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
                JSONArray usersJSON = (JSONArray) jsonObject().get(USER_ARRAY);

                for(int i = 0; i <usersJSON.size(); i++) {
                    JSONObject userJSON = (JSONObject) usersJSON.get(i);

                    UUID id = UUID.fromString((String) userJSON.get(USER_ID));
                    String username = (String) userJSON.get(USER_USERNAME);
                    String password = (String) usersJSON.get(USER_PASSWORD);
                    int level = ((Long) userJSON.get(USER_LEVEL)).intvalue();
                    String currentRoomId = (String) userJSON.get(USER_CURRENT_ROOM_ID);

                    users.add(new Player(id, username, password, level, currentRoomId));
                } 
                return users;

            } catch (Exception e) {
                e.printStackTrace();
            }

            return null;
        
    } 
}
