package com.game8.server.services;

import com.game8.server.models.User;

import java.util.List;

/**
 * A Service Interface for User Entities.
 *
 * This interface defines the methods that are used
 * in API controller.
 *
 *  @author Group 8
 *  @version 1.0
 *  @since 2020-03-30
 */
public interface UserService {
    User addUser(User user);
    List<User> getAllUsers();
    String createNewUser(User user);
    String loginUser(User user);
}
