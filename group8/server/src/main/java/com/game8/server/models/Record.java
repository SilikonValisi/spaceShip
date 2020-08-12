package com.game8.server.models;

import javax.persistence.*;
import java.time.LocalDate;


/**
 * An entity for the game records of users.
 *
 * The Record class is designed to handle the gaming records of
 * users in the database.
 *
 * @author Group 8
 * @version 1.0
 * @since 2020-03-30
 */
@Entity
@Table (name= "Records")
public class Record {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Integer id;

    private LocalDate recordDate;
    private String username;
    private  Integer score;

    /**
     * Constructor for the Record class.
     * This method takes a score value and the username of the user who
     * received the score. Then, it fetches the current time and creates
     * a Record object.
     * @param score an int, representing the score of the User.
     * @param username A String, representing the username of the User.
     */
    public Record(Integer score, String username) {
        this.score = score;
        this.username = username;
        this.recordDate = LocalDate.now();
    }

    /**
     * Default constructor for the Record class.
     */
    public Record(){

    }


    /**
     * The public setter for id attribute.
     * @param id new ID for Record object.
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * The public setter for Score attribute.
     * @param score new score value for Record object.
     */
    public void setScore(Integer score) {
        this.score = score;
    }

    /**
     * The public setter for username attribute
     * @param username new username for Record object.
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * The public setter for recorddate.
     * @param recordDate new recorddate for Record object.
     */
    public void setRecordDate(LocalDate recordDate) {
        this.recordDate = recordDate;
    }

    /**
     * The ublic getter for id.
     * @return an int value, the value of id.
     */
    public Integer getId() {
        return id;
    }

    /**
     * The public getter for score.
     * @return an int value, the value of score.
     */
    public Integer getScore() {
        return score;
    }

    /**
     * The public getter for username.
     * @return a String, the username of user
     */
    public String getUsername() {
        return username;
    }

    /**
     * The public getter for recorddate.
     * @return LocalDate date of the Record.
     */
    public LocalDate getRecordDate() {
        return recordDate;
    }






}