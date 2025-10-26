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
    public static JSONArray saveRooms() {
        RoomList games = RoomList.getInstance();
        ArrayList<Room> roomList = games.getRooms();

        JSONArray jsonRooms = new JSONArray();

        for(int i=0; i<roomList.size(); i++) {
            jsonRooms.add(getRoomsJSON(roomList.get(i)));
        }
        return jsonRooms;
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

    public static JSONObject saveGameConfig() {
        GameConfig config = GameApp.getGameConfig();
    
        JSONObject display = new JSONObject();
        display.put("originalTileSize", config.getOriginalTileSize());
        display.put("scale", config.getScale());
        display.put("tileSize", config.getTileSize());
        display.put("maxScreenCol", config.getMaxScreenCol());
        display.put("maxScreenRow", config.getMaxScreenRow());
        display.put("screenWidth", config.getScreenWidth());
        display.put("screenHeight", config.getScreenHeight());
        JSONObject world = new JSONObject();
         world.put("maxWorldCol", config.getMaxWorldCol());
        world.put("maxWorldRow", config.getMaxWorldRow());
        world.put("worldWidth", config.getWorldWidth());
        world.put("worldHeight", config.getWorldHeight());
        JSONObject gameStates = new JSONObject();
        gameStates.put("playState", config.getPlayState());
        gameStates.put("pauseState", config.getPauseState());
        gameStates.put("dialogueState", config.getDialogueState());
        gameStates.put("inventoryState", config.getInventoryState());
        JSONObject gameplay = new JSONObject();
        gameplay.put("fps", config.getFps());
        gameplay.put("gameStates", gameStates);

        JSONObject gameConfig = new JSONObject();
        gameConfig.put("display", display);
        gameConfig.put("world", world);
        gameConfig.put("gameplay", gameplay);
    
        return gameConfig;
    }

    public static JSONArray saveTiles(TileManager tileManager) {
        JSONArray tilesArray = new JSONArray();
    
        for (int i = 0; i< tileManager.tile.length;i++) {
            Tile tile = tileManager.tile[i];
            if (tile!= null) {
                tilesArray.add(getTilesJSON(tileManager.tile[i]));
            }
        }
        return tilesArray;
    }

    public static JSONObject getTilesJSON(Tile tile) {
        JSONObject tileDetails = new JSONObject();
        tileDetails.put("tileId", tile.tileId);
        tileDetails.put("name", tile.name);
        tileDetails.put("imagePath", tile.imagePath);
        tileDetails.put("collision", tile.collision);
        tileDetails.put("isSpecial", tile.isSpecial);
        return tileDetails;
    }

    public static void saveGame(GameApp gameApp) {
        try (FileWriter file = new FileWriter(ROOM_TEMP_FILE_NAME)) {
            JSONObject game = new JSONObject();
            game.put("game_config",saveGameConfig());
            game.put("tiles",saveTiles(gameApp.tileM));
            game.put("rooms", saveRooms());
            file.write(game.toString());
            file.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void main(String[] args) {
        DataWriter.saveUsers();
    }
}