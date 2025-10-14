package com.escape.Model;
import java.util.ArrayList;
import java.util.Scanner;

public class Login {
    public static void Login(String username, String password) {
        ArrayList<User> users = DataLoader.getUsers();
        boolean loggedIn = false;
        while(!loggedIn) {
            for(User user:users) {
            if(user.getUserName().equals(username) && user.getPassword().equals(password)) {
                loggedIn = true;
                System.out.println("Success");
            }
            }
        }
    } 


    public static void main(String[] args) {
        
        System.out.println("Test username: ");
        Scanner scanner = new Scanner(System.in);
        String username = scanner.nextLine();
        System.out.println("Test password: ");
        String password = scanner.nextLine();
        Login(username, password);
    }
    

}

