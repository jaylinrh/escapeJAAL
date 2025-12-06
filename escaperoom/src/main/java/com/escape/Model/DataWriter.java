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

        if (userList.isEmpty()) {
            return;
        }

        JSONArray jsonUsers = new JSONArray();

        for(int i=0; i<userList.size(); i++) {
            jsonUsers.add(getUserJSON(userList.get(i)));
        }
    
        JSONObject wrapper = new JSONObject();
        wrapper.put("users", jsonUsers);

        try (FileWriter file = new FileWriter(USER_FILE_NAME)) {
            file.write(wrapper.toJSONString());
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
        userDetails.put(USER_LEVEL, player.getLevel());
        userDetails.put(USER_CURRENT_ROOM_ID, player.getCurrentRoomID());
        
        JSONArray savesArray = new JSONArray();
        for (GameSave save : player.getGameSaves()) {
            savesArray.add(getGameSaveJSON(save));
        }
        userDetails.put(GAME_SAVES, savesArray);

        if (player.getCurrentSaveId() != null) {
            userDetails.put(CURRENT_SAVE_ID, player.getCurrentSaveId().toString());
        } else {
            userDetails.put(CURRENT_SAVE_ID, "");
        }
        
        return userDetails;
    }

    private static JSONObject getGameSaveJSON(GameSave save) {
        JSONObject saveJSON = new JSONObject();
        
        saveJSON.put(SAVE_ID, save.getSaveId().toString());
        saveJSON.put(SAVE_NAME, save.getSaveName());
        saveJSON.put(DIFFICULTY, save.getDifficulty());
        saveJSON.put(CREATED_AT, save.getCreatedAt());
        saveJSON.put(LAST_PLAYED_AT, save.getLastPlayedAt());
        saveJSON.put(USER_CURRENT_ROOM_ID, save.getCurrentRoomId());
        saveJSON.put(PLAY_TIME_SECONDS, save.getPlayTimeSeconds());
        
        PlayerState ps = save.getPlayerState();
        JSONObject playerStateJSON = new JSONObject();
        playerStateJSON.put(WORLD_X, ps.getWorldX());
        playerStateJSON.put(WORLD_Y, ps.getWorldY());
        playerStateJSON.put(SPEED, ps.getSpeed());
        playerStateJSON.put(DIRECTION, ps.getDirection());
        playerStateJSON.put(COLLISION_ON, ps.getCollision());
        
        SolidArea sa = ps.getSolidArea();
        JSONObject solidAreaJSON = new JSONObject();
        solidAreaJSON.put(X, sa.getX());
        solidAreaJSON.put(Y, sa.getY());
        solidAreaJSON.put(WIDTH, sa.getWidth());
        solidAreaJSON.put(HEIGHT, sa.getHeight());
        playerStateJSON.put(SOLID_AREA, solidAreaJSON);
        
        JSONObject spritesJSON = new JSONObject();
        spritesJSON.put(U1, "/images/player.png");
        spritesJSON.put(U2, "/images/player.png");
        spritesJSON.put(D1, "/images/player.png");
        spritesJSON.put(D2, "/images/player.png");
        spritesJSON.put(L1, "/images/player.png");
        spritesJSON.put(L2, "/images/player.png");
        spritesJSON.put(R1, "/images/player.png");
        spritesJSON.put(R2, "/images/player.png");
        playerStateJSON.put(SPRITE_IMAGES, spritesJSON);
        
        saveJSON.put(USER_PLAYER_STATE, playerStateJSON);
        
        Inventory inv = save.getInventory();
        JSONObject inventoryJSON = new JSONObject();
        inventoryJSON.put(INVENTORY_ID, inv.getInventoryId());
        inventoryJSON.put(MAX_CAPACITY, inv.getMaxCapacity());
        
        JSONArray itemsArray = new JSONArray();
        for (Item item : inv.getItems()) {
            JSONObject itemJSON = new JSONObject();
            itemJSON.put(ITEM_ID, item.getItemId());
            itemJSON.put(NAME, item.getName());
            itemJSON.put(HINT, item.getHint());
            itemJSON.put(DESCRIPTION, item.getDescription());
            itemsArray.add(itemJSON);
        }
        inventoryJSON.put(ITEMS, itemsArray);
        saveJSON.put(USER_INVENTORY, inventoryJSON);
        
        JSONArray visitedRoomsArray = new JSONArray();
        for (String roomId : save.getVisitedRooms()) {
            visitedRoomsArray.add(roomId);
        }
        saveJSON.put(VISITED_ROOMS, visitedRoomsArray);

        JSONArray completedRoomsArray = new JSONArray();
        for (String roomId : save.getCompletedRooms()) {
            completedRoomsArray.add(roomId);
        }
        saveJSON.put(COMPLETED_ROOMS, completedRoomsArray);

        JSONArray solvedPuzzlesArray = new JSONArray();
        for (String puzzleId : save.getSolvedPuzzles()) {
            solvedPuzzlesArray.add(puzzleId);
        }
        saveJSON.put(SOLVED_PUZZLES, solvedPuzzlesArray);
        
        return saveJSON;
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
        display.put("maxScreenCol", config.getScreenCols());
        display.put("maxScreenRow", config.getScreenRows());
        display.put("screenWidth", config.getScreenWidth());
        display.put("screenHeight", config.getScreenHeight());
        JSONObject world = new JSONObject();
         world.put("maxWorldCol", config.getWorldCols());
        world.put("maxWorldRow", config.getWorldRows());
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