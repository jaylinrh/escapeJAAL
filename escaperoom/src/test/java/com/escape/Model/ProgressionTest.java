package com.escape.Model;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.UUID;

public class ProgressionTest {
    private Progression progression;
    private User testUser;

    @Before
    public void setUp() {
        SolidArea solidArea = new SolidArea(0, 0, 32, 32);
        SpriteImages sprites = new SpriteImages(
            "/images/player.png", "/images/player.png",
            "/images/player.png", "/images/player.png",
            "/images/player.png", "/images/player.png",
            "/images/player.png", "/images/player.png"
        );
        PlayerState playerState = new PlayerState(100, 100, 4, "down", solidArea, false, sprites);
        Inventory inventory = new Inventory(UUID.randomUUID().toString(), 10);
        
        testUser = new User(
            UUID.randomUUID(),
            "progressionTestUser",
            "password",
            1,
            "room_foyer",
            playerState,
            inventory
        );
        
        progression = new Progression(testUser);
    }
    
}
