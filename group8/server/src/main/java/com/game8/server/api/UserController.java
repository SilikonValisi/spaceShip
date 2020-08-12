package com.game8.server.api;

import com.game8.server.models.User;
import com.game8.server.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * API Controller for User entities.
 *
 * This class is responsible for handling the API requests
 * regarding the User entities. The methods that responses
 * to the API requests are handled by this class.
 *
 *  @author Group 8
 *  @version 1.0
 *  @since 2020-03-30
 */
@RequestMapping("api/user")
@RestController
public class UserController {

    /**
     * The UserService interface describes the logic
     * used in this controller.
     */
    @Autowired
    private UserService userService;


    /**
     * This method is responsible for the requests to add
     * new users into the database.
     * @param user A User object that is requested by API
     * to be recorded into the database.
     * @return A ResponseEntity containing the User object.
     */
    @PostMapping(path="/addUser")
    public ResponseEntity<User> addUser(@RequestBody User user) {
        return ResponseEntity.ok().body(this.userService.addUser(user));
    }

    /**
     * This method is responsible for the GET requests for
     * all the users recorded in the database.
     * @return A ResponseEntity containing User objects.
     */
    @GetMapping("/getAllUsers")
    public ResponseEntity< List<User> > getAllUsers(){
        return ResponseEntity.ok().body(this.userService.getAllUsers());
    }

    /**
     * This method is responsible for the POST requests for
     * registering new unique user.
     * @param user A User candidate to record into the database.
     * @return A ResponseEntity containing a String, the
     * result of the registering process.
     */
    @PostMapping("/createUser")
    public ResponseEntity<String> createUser(@RequestBody User user){
        return ResponseEntity.ok().body(this.userService.createNewUser(user));
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody User user){
        return ResponseEntity.ok().body(this.userService.loginUser(user));
    }

}
