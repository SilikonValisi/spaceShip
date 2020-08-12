package com.game8.server.services;


import com.game8.server.models.User;
import com.game8.server.repositories.UserRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UserServiceImplTest{

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Test
    public void addUserTest(){
        User newUser1 = new User("test"+Math.random(),"test"+Math.random());
        // Check old size
        int oldSize = userService.getAllUsers().size();
        // Add new user
        userService.addUser(newUser1);
        // Check new size
        int newSize = userService.getAllUsers().size();
        // Assert that oldSize +1 = newSize
        assertEquals(oldSize+1, newSize);
        // Assert that new user is in database
        assertTrue(userRepository.existsUserByUsername(newUser1.getUsername()));
    }

    @Test
    public void getAllUsersTest() {
        // We create a new user
        User newUser1 = new User("test"+Math.random(),"test"+Math.random());
        User newUser2 = new User("test"+Math.random(),"test"+Math.random());
        User newUser3 = new User("test"+Math.random(),"test"+Math.random());
        int oldSize = userService.getAllUsers().size();
        userService.addUser(newUser1);
        userService.addUser(newUser2);
        userService.addUser(newUser3);
        int newSize = userService.getAllUsers().size();
        assertEquals(newSize, oldSize+3);
    }

    @Test
    public void createUserTest(){
        User newUser1 = new User("test"+Math.random(),"test"+Math.random());
        // Check old size
        int oldSize = userService.getAllUsers().size();
        // Add new user
        String createResult1 = userService.createNewUser(newUser1);
        // Check new size
        int newSize = userService.getAllUsers().size();
        // Assert that oldSize +1 = newSize
        assertEquals(oldSize+1, newSize);
        // Assert that new user is in database
        assertTrue(userRepository.existsUserByUsername(newUser1.getUsername()));
        // Assert that the right message is returned.
        assertEquals(createResult1, "New user created.");
        // create new user
        String createResult2 = userService.createNewUser(newUser1);
        // Check the newer size
        int newerSize = userService.getAllUsers().size();
        // assert that the size did not change
        assertEquals(newSize, newerSize);
        // assert that the right message is returned.
        assertEquals(createResult2, "User already exists.");
    }

}