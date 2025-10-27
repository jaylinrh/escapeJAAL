package com.escape.Model;

import com.escape.speech.Speak;

public class Driver {
    public static void main(String[] args){
        String message = "Hello, how are you today?";
        Speak.speak(message);
    }
}