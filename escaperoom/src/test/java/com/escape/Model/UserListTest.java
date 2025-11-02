package com.escape.Model;

import org.junit.Before;
import org.junit.After;
import org.junit.Test;
import static org.junit.Assert.*;
import java.util.ArrayList;

public class UserListTest {
    private UserList userList;
    private static final String TEST_USERNAME = "testUserList123";
    private static final String TEST_PASSWORD = "testPassword456";
    
    @Before
    public void setUp() {
        userList = UserList.getInstance();
        ArrayList<User> users = userList.getUsers();
        users.removeIf(u -> u.getUserName() != null && u.getUserName().equals(TEST_USERNAME));
    }

    @After
    public void tearDown() {
        ArrayList<User> users = userList.getUsers();
        users.removeIf(u -> u.getUserName() != null && u.getUserName().equals(TEST_USERNAME));
    }

    //Singleton Test
    @Test
    public void testGetInstance_NotNull() {
        assertNotNull("UserList instance should not be null", userList);
    }
    
    @Test
    public void testGetInstance_Singleton() {
        UserList instance1 = UserList.getInstance();
        UserList instance2 = UserList.getInstance();
        
        assertSame("Should return same instance", instance1, instance2);
    }

    //Add User Test
    @Test
    public void testAddUser_Success() {
        boolean result = userList.addUser(TEST_USERNAME, TEST_PASSWORD);
        
        assertTrue("Adding new user should succeed", result);
        assertTrue("User should exist after adding", userList.haveUser(TEST_USERNAME));
    }
    
    @Test
    public void testAddUser_DuplicateUsername() {
        userList.addUser(TEST_USERNAME, TEST_PASSWORD);
        
        boolean result = userList.addUser(TEST_USERNAME, "differentPassword");
        
        assertFalse("Adding duplicate username should fail", result);
    }
    
    @Test
    public void testAddUser_UserInList() {
        userList.addUser(TEST_USERNAME, TEST_PASSWORD);
        
        ArrayList<User> users = userList.getUsers();
        boolean found = false;
        for (User user : users) {
            if (user.getUserName().equals(TEST_USERNAME)) {
                found = true;
                break;
            }
        }
        
        assertTrue("Added user should be in the user list", found);
    }
    
    @Test
    public void testAddUser_CorrectPassword() {
        userList.addUser(TEST_USERNAME, TEST_PASSWORD);
        
        User addedUser = null;
        for (User user : userList.getUsers()) {
            if (user.getUserName().equals(TEST_USERNAME)) {
                addedUser = user;
                break;
            }
        }
        
        assertNotNull("User should be found", addedUser);
        assertEquals("Password should match", TEST_PASSWORD, addedUser.getPassword());
    }
    
    @Test
    public void testAddUser_InitialLevel() {
        userList.addUser(TEST_USERNAME, TEST_PASSWORD);
        
        User addedUser = null;
        for (User user : userList.getUsers()) {
            if (user.getUserName().equals(TEST_USERNAME)) {
                addedUser = user;
                break;
            }
        }
        
        assertNotNull("User should be found", addedUser);
        assertEquals("Initial level should be 1", 1, addedUser.getLevel());
    }
    
    @Test
    public void testAddUser_InitialRoomId() {
        userList.addUser(TEST_USERNAME, TEST_PASSWORD);
        
        User addedUser = null;
        for (User user : userList.getUsers()) {
            if (user.getUserName().equals(TEST_USERNAME)) {
                addedUser = user;
                break;
            }
        }
        
        assertNotNull("User should be found", addedUser);
        assertEquals("Initial room should be room_exterior", 
            "room_exterior", addedUser.getCurrentRoomID());
    }
    
    @Test
    public void testAddUser_HasInventory() {
        userList.addUser(TEST_USERNAME, TEST_PASSWORD);
        
        User addedUser = null;
        for (User user : userList.getUsers()) {
            if (user.getUserName().equals(TEST_USERNAME)) {
                addedUser = user;
                break;
            }
        }
        
        assertNotNull("User should be found", addedUser);
        assertNotNull("User should have inventory", addedUser.getInventory());
        assertEquals("Inventory should have capacity 10", 
            10, addedUser.getInventory().getMaxCapacity());
    }
    
    @Test
    public void testAddUser_HasPlayerState() {
        userList.addUser(TEST_USERNAME, TEST_PASSWORD);
        
        User addedUser = null;
        for (User user : userList.getUsers()) {
            if (user.getUserName().equals(TEST_USERNAME)) {
                addedUser = user;
                break;
            }
        }
        
        assertNotNull("User should be found", addedUser);
        assertNotNull("User should have player state", addedUser.getPlayerState());
    }
    
    @Test
    public void testAddUser_UniqueId() {
        userList.addUser(TEST_USERNAME + "1", TEST_PASSWORD);
        userList.addUser(TEST_USERNAME + "2", TEST_PASSWORD);
        
        User user1 = null;
        User user2 = null;
        
        for (User user : userList.getUsers()) {
            if (user.getUserName().equals(TEST_USERNAME + "1")) {
                user1 = user;
            } else if (user.getUserName().equals(TEST_USERNAME + "2")) {
                user2 = user;
            }
        }
        
        assertNotNull("First user should exist", user1);
        assertNotNull("Second user should exist", user2);
        assertNotEquals("User IDs should be unique", user1.getId(), user2.getId());
        
        ArrayList<User> users = userList.getUsers();
        users.removeIf(u -> u.getUserName().equals(TEST_USERNAME + "1"));
        users.removeIf(u -> u.getUserName().equals(TEST_USERNAME + "2"));
    }

    //Have User Test
    @Test
    public void testHaveUser_True() {
        userList.addUser(TEST_USERNAME, TEST_PASSWORD);
        
        assertTrue("Should return true for existing user", 
            userList.haveUser(TEST_USERNAME));
    }
    
    @Test
    public void testHaveUser_False() {
        assertFalse("Should return false for nonexistent user", 
            userList.haveUser("nonexistentUser999"));
    }
    
    @Test
    public void testHaveUser_CaseSensitive() {
        userList.addUser(TEST_USERNAME.toLowerCase(), TEST_PASSWORD);
        
        assertFalse("Should be case sensitive", 
            userList.haveUser(TEST_USERNAME.toUpperCase()));
        
        ArrayList<User> users = userList.getUsers();
        users.removeIf(u -> u.getUserName().equals(TEST_USERNAME.toLowerCase()));
    }
    
    @Test
    public void testHaveUser_AfterMultipleAdds() {
        userList.addUser(TEST_USERNAME + "1", TEST_PASSWORD);
        userList.addUser(TEST_USERNAME + "2", TEST_PASSWORD);
        userList.addUser(TEST_USERNAME + "3", TEST_PASSWORD);
        
        assertTrue("Should find first user", userList.haveUser(TEST_USERNAME + "1"));
        assertTrue("Should find second user", userList.haveUser(TEST_USERNAME + "2"));
        assertTrue("Should find third user", userList.haveUser(TEST_USERNAME + "3"));
        
        ArrayList<User> users = userList.getUsers();
        users.removeIf(u -> u.getUserName().startsWith(TEST_USERNAME));
    }

    //Get User test
    @Test
    public void testGetUsers_NotNull() {
        ArrayList<User> users = userList.getUsers();
        assertNotNull("User list should not be null", users);
    }
    
    @Test
    public void testGetUsers_SizeIncreasesAfterAdd() {
        int initialSize = userList.getUsers().size();
        
        userList.addUser(TEST_USERNAME, TEST_PASSWORD);
        
        int newSize = userList.getUsers().size();
        assertEquals("Size should increase by 1", initialSize + 1, newSize);
    }
    
    @Test
    public void testGetUsers_ContainsAddedUser() {
        userList.addUser(TEST_USERNAME, TEST_PASSWORD);
        
        ArrayList<User> users = userList.getUsers();
        boolean found = false;
        for (User user : users) {
            if (user.getUserName().equals(TEST_USERNAME)) {
                found = true;
                break;
            }
        }
        
        assertTrue("User list should contain added user", found);
    }

    // Edge Case Test

    @Test
    public void testAddUser_EmptyUsername_ShouldFail() {
        boolean result = userList.addUser("", TEST_PASSWORD);
        
        assertFalse("Should reject empty username", result);
        assertFalse("Empty username should not be in list", userList.haveUser(""));
    }

    @Test
    public void testAddUser_NullUsername_ShouldFail() {
        boolean result = userList.addUser(null, TEST_PASSWORD);
        
        assertFalse("Should reject null username", result);
    }

    @Test
    public void testAddUser_EmptyPassword_ShouldFail() {
        String username = TEST_USERNAME + "_empty_pass";
        boolean result = userList.addUser(username, "");
        
        assertFalse("Should reject empty password", result);
        assertFalse("User with empty password should not exist", 
            userList.haveUser(username));
    }

    @Test
    public void testAddUser_NullPassword_ShouldFail() {
        boolean result = userList.addUser(TEST_USERNAME, null);
        
        assertFalse("Should reject null password", result);
    }

    @Test
    public void testAddUser_SpecialCharactersUsername() {
        String specialUsername = "test@example.com";
        boolean result = userList.addUser(specialUsername, TEST_PASSWORD);
        
        assertTrue("Should allow valid special characters in username", result);
        assertTrue("Should find user with special characters", 
            userList.haveUser(specialUsername));
        
        ArrayList<User> users = userList.getUsers();
        users.removeIf(u -> u.getUserName().equals(specialUsername));
    }

    @Test
    public void testAddUser_WhitespaceUsername_ShouldFail() {
        boolean result = userList.addUser("   ", TEST_PASSWORD);
        
        assertFalse("Should reject whitespace-only username", result);
    }

    @Test
    public void testAddUser_VeryLongUsername() {
        StringBuilder longUsername = new StringBuilder();
        for (int i = 0; i < 256; i++) {
            longUsername.append("a");
        }
        
        // Might want to set max length limit
        boolean result = userList.addUser(longUsername.toString(), TEST_PASSWORD);
        
        assertTrue("Should handle long usernames up to reasonable limit", result);
        
        if (result) {
            ArrayList<User> users = userList.getUsers();
            users.removeIf(u -> u.getUserName().equals(longUsername.toString()));
        }
    }
    @Test
    public void testAddUser_MultipleUsers_AllPersist() {
        String[] usernames = {
            TEST_USERNAME + "_1",
            TEST_USERNAME + "_2",
            TEST_USERNAME + "_3",
            TEST_USERNAME + "_4",
            TEST_USERNAME + "_5"
        };
        
        for (String username : usernames) {
            boolean result = userList.addUser(username, TEST_PASSWORD);
            assertTrue("Adding user " + username + " should succeed", result);
        }
        
        for (String username : usernames) {
            assertTrue("User " + username + " should exist after batch add", 
                userList.haveUser(username));
        }
        
        int expectedMinSize = usernames.length;
        assertTrue("User list should contain at least " + expectedMinSize + " users",
            userList.getUsers().size() >= expectedMinSize);
        
        ArrayList<User> users = userList.getUsers();
        for (String username : usernames) {
            users.removeIf(u -> u.getUserName().equals(username));
        }
    }
}
