package com.game8.server.services;


import com.game8.server.models.User;
import com.game8.server.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;

/**
 * Implementation of the UserService Interface.
 *
 * This class implements the methods described in UserService
 * interface. The methods implemented here provides the logic
 * for the web service of the application.
 *
 *  @author Group 8
 *  @version 1.0
 *  @since 2020-03-30
 */
@Service
public class UserServiceImpl implements UserService{
    @Autowired
    private UserRepository userRepository;

    /**
     * This method adds a new User into the
     * database.
     * @param user New User object to be added.
     * @return User object added to the database.
     */
    @Override
    public User addUser(User user) {
        return userRepository.save(user);
    }

    /**
     * This method returns all the Users in the database.
     * @return A List of Users, where Users comprise the whole
     * Users database.
     */
    @Override
    public List<User> getAllUsers() {
        return this.userRepository.findAll();
    }

    /**
     * This method creates a new unique User.
     * The aim of this method is for using it in
     * registering process.
     * @param user a new User candidate.
     * @return a String, the result of the
     * CREATE operation.
     */
    @Override
    public String createNewUser(User user) {
        // If there is no User with user.username
        if (!this.userRepository.existsUserByUsername(user.getUsername())){
            // create the user with hashed password
            user.setPassword(Integer.toString(user.getPassword().hashCode()));
            // save it to the database
            this.userRepository.save(user);
            return "RegisterSuccess";
        }
        // if there exists a User with user.username
        else{
            // Fail.
            return "RegisterFail";
        }
    }

    @Override
    public String loginUser(User user) {
        User result = userRepository.findByUsername(user.getUsername());
        if(result == null){
            return "NoAuth";
        }
        else if(!result.getPassword().equals(Integer.toString(user.getPassword().hashCode()))){
            return "NoAuth";
        }
        else{
            return "Auth";
        }
    }

}
