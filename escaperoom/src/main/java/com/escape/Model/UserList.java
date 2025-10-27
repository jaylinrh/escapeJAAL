package com.escape.Model;
import java.util.ArrayList;
import java.util.UUID;

public class UserList {
    private static UserList users;
    private ArrayList<User> userList;

    private UserList() {
        userList = DataLoader.getUsers();
    }

    public static UserList getInstance() {
        if(users == null) {
            users = new UserList();
        }

        return users;
    }

    public boolean haveUser(String userName) {
        for(User user : userList) {
            if(user.getUserName().equals(userName)) {
                return true;
            }
        }
        return false;
    }

    public ArrayList<User> getUsers() {
        return userList;
    }

    public boolean addUser(String username, String password) {
        if(haveUser(username)) {
            return false;
        }
        SolidArea solidArea = new SolidArea(8, 16, 32, 32);
        SpriteImages sprites = new SpriteImages(
            "/images/player.png", "/images/player.png",
            "/images/player.png", "/images/player.png",
            "/images/player.png", "/images/player.png",
            "/images/player.png", "/images/player.png"
        );
        
        PlayerState playerState = new PlayerState(
            1200, 2160, 4, "down", solidArea, false, sprites
        );

        Inventory inventory = new Inventory(UUID.randomUUID().toString(), 10);

        UUID id = UUID.randomUUID();
                User newUser = new User(
            UUID.randomUUID(),
            username,
            password,
            1,
            "room_exterior",
            playerState,
            inventory
        );
        userList.add(newUser);
        return true;
    }

    public void saveUsers() {
        DataWriter.saveUsers();
    }
}
