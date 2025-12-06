package com.escape.Model;
import java.util.ArrayList;
import java.util.HashSet;
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
        if(username == null || username.trim().isEmpty()) {
            return false;
        }
        
        if(password == null || password.trim().isEmpty()) {
            return false;
        }

        if(haveUser(username)) {
            return false;
        }
        
        UUID id = UUID.randomUUID();
        User newUser = new User(
            id,
            username,
            password,
            1,
            50.0,
            50.0,
            new ArrayList<>(),
            null
        );
        userList.add(newUser);
        return true;
    }

    public void saveUsers() {
        DataWriter.saveUsers();
    }
}
