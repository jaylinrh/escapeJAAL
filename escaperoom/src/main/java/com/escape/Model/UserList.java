package com.escape.Model;
import java.util.ArrayList;

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

    public boolean addUser(String userName, String password) {
        if(haveUser(userName)) {
            return false;
        }
        userList.add(new User(userName, password));
        return true;
    }

    public void saveUsers() {
        DataWriter.saveUsers();
    }
}
