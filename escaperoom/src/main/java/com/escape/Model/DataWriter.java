package com.escape.Model;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

@SuppressWarnings("unchecked")
public class DataWriter extends DataConstants {

    public static void saveUsers() {
        UserList players = UserList.getInstance();
        ArrayList<User> userList = players.getUsers();

        JSONArray jsonUsers = new JSONArray();

        for(int i=0; i<userList.size(); i++) {
            jsonUsers.add(getUserJSON(userList.get(i)));
        }
    

        try (FileWriter file = new FileWriter(USER_TEMP_FILE_NAME)) {
            file.write(jsonUsers.toJSONString());
            file.flush();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static JSONObject getUserJSON(User player) {
        JSONObject userDetails = new JSONObject();
        userDetails.put(USER_ID, player.getId().toString());
        userDetails.put(USER_USERNAME, player.getUserName());
        userDetails.put(USER_PASSWORD, player.getPassword());
        userDetails.put(USER_LEVEL, Integer.toString(player.getLevel()));
        userDetails.put(USER_CURRENT_ROOM_ID, player.getCurrentRoomID());
        userDetails.put(USER_PLAYER_STATE, player.getPlayerState());
        userDetails.put(USER_INVENTORY, player.getInventory());
        return userDetails;
    }
    public static void saveGame() {
        RoomList games = RoomList.getInstance();
        ArrayList<Room> roomList = games.getRooms();

        JSONArray jsonRooms = new JSONArray();

        for(int i=0; i<roomList.size(); i++) {
            jsonRooms.add(getRoomsJSON(roomList.get(i)));
        }

        try (FileWriter file = new FileWriter(ROOM_TEMP_FILE_NAME)) {
            file.write(jsonRooms.toJSONString());
            file.flush();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

public static JSONObject getRoomsJSON(Room game) {
    JSONObject roomDetails = new JSONObject();
    roomDetails.put(ROOM_ID, game.getRoomId().toString());
    roomDetails.put(ROOM_NAME, game.getName());
    roomDetails.put(ROOM_DESCRIPTION, game.getDescription());
    roomDetails.put(MAP_FILE, game.getMapFile());
    roomDetails.put(MUSIC, game.getMusic());
    roomDetails.put(PUZZLE, game.getPuzzle());
    JSONObject dialogueObj = new JSONObject();
    dialogueObj.put(DIALOGUE_ID, game.getDialogueId());
    dialogueObj.put(DIALOGUE_FILE, game.getDialogueFile());
    dialogueObj.put(DIALOGUES, game.getDialogues());
    roomDetails.put(DIALOGUE, dialogueObj);
    roomDetails.put(AVAILABLE_ITEMS, game.getAvailableItemIds());
    
    return roomDetails;
}

    public static void main(String[] args) {
        DataWriter.saveUsers();
        DataWriter.saveGame();
    }
}