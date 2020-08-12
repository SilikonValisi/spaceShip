package com.game8.server.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.game8.server.models.User;

/**
 * An interface for handling the User Repository.
 *
 * @author Group 8
 * @version 1.0
 * @since 2020-03-30
 */
@Repository
public interface UserRepository extends JpaRepository<User, Integer>{
    /**
     * A method that checks the database to find whether a User object
     * with the given name is already recorded.
     * @param username A String that is queried in the Users table to
     * check whether it is unique.
     * @return Bool, a boolean value that represents the occurence of
     * a User object with given username.
     */
    boolean existsUserByUsername(String username);
    User findByUsername(String username);

}
