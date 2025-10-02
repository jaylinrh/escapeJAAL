package com.escape.Model;

import java.io.FileReader;
import java.util.ArrayList;

public class DataLoader extends DataConstants{
    
    public static ArrayList<Player> getUsers() {
        ArrayList<Player> users = new ArrayList<Player>();

            try {
                FileReader reader = new FileReader(USER_FILE_NAME);

                return users;

            } catch (Exception e) {
                e.printStackTrace();
            }

            return null;
        
    } 
}
