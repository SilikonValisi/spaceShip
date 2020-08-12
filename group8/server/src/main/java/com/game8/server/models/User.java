package com.game8.server.models;

import javax.persistence.*;


/**
 * An entity responsible for holding the user information.
 * This class is designed to hold the information of users.
 *
 * @author Group 8
 * @version 1.0
 * @since 2020-03-30
 */
@Entity
@Table (name= "Users")
public class User {

    @Id
    private String username;
    private  String password;

    /**
     * Constructor for the User class.
     * This method takes the username of the user, password of the
     * user and create an User object.
     * @param username A String containing the username of the User.
     * @param password A String containing the password of the User.
     */
    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    /**
     * Default constructor for the User class.
     */
    public User(){

    }


    /**
     * The public setter for password.
     * @param password New password for the User object.
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * The public getter for username.
     * @return String, username of the user.
     */
    public String getUsername() {
        return username;
    }

    /**
     * The public getter for password.
     * @return String, password of the user.
     */
    public String getPassword() {
        return password;
    }







}