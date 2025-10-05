package com.escape.Model;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class DataWriter extends DataConstants {

    public static void saveUsers() {
        Players players = Players.getInstance();
        ArrayList<Player> playerList = players.getPlayers();

        JSONArray jsonUsers = new JSONArray();

        for(int i=0; i<playerList.size(); i++) {
            jsonUsers.add(getUserJSON(playerList.get(i)));
        }
    

        try (FileWriter file = new FileWriter(USER_TEMP_FILE_NAME)) {
            file.write(jsonUsers.toJSONString());
            file.flush();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static JSONObject getUserJSON(Player player) {
        JSONObject userDetails = new JSONObject();
        userDetails.put(USER_ID, player.getId().toString());
        userDetails.put(USER_USERNAME, player.getUserName());
        userDetails.put(USER_PASSWORD, player.getPassword());
        userDetails.put(USER_LEVEL, Integer.toString(player.getLevel()));
        userDetails.put(USER_CURRENT_ROOM_ID, player.getCurrentRoomID());

        return userDetails;
    }
    public static void saveGame() {
        
    }
    public static void main(String[] args) {
        DataWriter.saveUsers();
        DataWriter.saveGame();
        
    }
}