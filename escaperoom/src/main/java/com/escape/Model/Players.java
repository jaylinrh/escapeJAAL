package com.escape.Model;
import java.util.ArrayList;

public class Players {
    private static Players players;
    private ArrayList<Player> playerList;

    private Players() {
        playerList = DataLoader.getUsers();
    }

    public static Players getInstance() {
        if(players == null) {
            players = new Players();
        }

        return players;
    }

    public boolean haveUser(String userName) {
        for(Player player : playerList) {
            if(player.getUserName().equals(userName)) {
                return true;
            }
        }
        return false;
    }

    public ArrayList<Player> getPlayers() {
        return playerList;
    }

    public boolean addUser(String userName, String password) {
        if(haveUser(userName)) {
            return false;
        }
        playerList.add(new Player(userName, password));
        return true;
    }

    public void saveUsers() {
        DataWriter.saveUsers();
    }
}
