package com.game8.client.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import java.io.IOException;

/**
 * Utility methods for Controller classes
 *
 * This class contains the utility functions used in
 * Controller classes.
 *
 * @author Group 8
 * @version 1.0
 * @since 2020-04-27
 */

public class ControllerUtility {

    private final String userApiAddress = "http://localhost:8080/api/user";
    private final String recordApiAddress = "http://localhost:8080/api/record";
    private final String playerApiAdress = "http://localhost:8080/api/multiplay";
    /**
     * This method creates an HTTPEntity with a JSON object
     * containing username and password of a given user.
     **
     * @param username username string
     * @param password password string
     * @return List of records.
     */
    public static HttpEntity<String> createUserEntity(String username, String password){
    JSONObject json = new JSONObject();
            try{
        json.put("password", password);
        json.put("username", username);
    } catch (
    JSONException ex){
        ex.printStackTrace();
    }
    HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
    return new HttpEntity<>(json.toString(), headers);
    }

    public static HttpEntity<String> createPlayer(String playerName){
        JSONObject json = new JSONObject();
        try{
            json.put("playerName", playerName);
        } catch (
                JSONException ex){
            ex.printStackTrace();
        }
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        return new HttpEntity<>(json.toString(), headers);
    }

    public static HttpEntity<String> createPlayerWithPos(String playerName,double x,double y){
        JSONObject json = new JSONObject();
        try{
            json.put("playerName", playerName);
            json.put("x", x);
            json.put("y", y);

        } catch (
                JSONException ex){
            ex.printStackTrace();
        }
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        return new HttpEntity<>(json.toString(), headers);
    }

    public static HttpEntity<String> createPlayerScore(int score){
        JSONObject json = new JSONObject();
        try{
            json.put("score", score);

        } catch (
                JSONException ex){
            ex.printStackTrace();
        }
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        return new HttpEntity<>(json.toString(), headers);
    }


    public void getMainPage(ActionEvent actionEvent){
        Stage primaryStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        try {
            Parent mainMenu = FXMLLoader.load(getClass().getResource("/mainPage.fxml"));
            Scene mainMenuScene = new Scene(mainMenu, 1200, 720);
            primaryStage.setScene(mainMenuScene);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getRecordApiAddress() {
        return recordApiAddress;
    }

    public String getUserApiAddress() {
        return userApiAddress;
    }


    public String getPlayerApiAdress() {
        return playerApiAdress;
    }
}
