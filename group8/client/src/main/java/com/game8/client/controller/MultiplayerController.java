package com.game8.client.controller;

import com.game8.client.model.GameComponent;
import com.game8.client.model.Player;
import com.game8.client.view.MultiplayerGameplay;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

public class MultiplayerController {

    @FXML public Button waitPlayerButton;
    @FXML public Button exitButton;

    ControllerUtility controllerUtility = new ControllerUtility();

    private RestTemplate restTemplate=new RestTemplate();
    private String playerApiAddress;


    @FXML
    public void exitHandle(ActionEvent actionEvent){
        Stage primaryStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        primaryStage.close();
    }

    @FXML
    public void waitPlayerHandle(ActionEvent actionEvent){
        Stage primaryStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        playerApiAddress = controllerUtility.getPlayerApiAdress();
        String playerName = LoginRegisterController.username;
        HttpEntity<String> entity = ControllerUtility.createPlayer(playerName);
        ResponseEntity<String> postResponse = restTemplate.exchange(
                playerApiAddress + "/addPlayer",
                HttpMethod.POST,
                entity,
                new ParameterizedTypeReference<>() {}
        );

        System.out.printf("This players name is: %s %n",playerName);
        Player x = new Player("nap");
        while (x.getPlayerName().equals("nap")){
            ResponseEntity<Player> getResponse = restTemplate.exchange(
                    playerApiAddress + "/getOtherPlayer",
                    HttpMethod.POST,
                    entity,
                    new ParameterizedTypeReference<>() {}
            );
            x=getResponse.getBody();
            System.out.printf("Other player is: %s %n",x.getPlayerName());
            assert (getResponse.getStatusCode().equals(HttpStatus.OK));
            try {
                Thread.sleep(4000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
        System.out.printf("The other players name is %s %n",x.getPlayerName());
/*
        primaryStage.close();
*/


/*
        THREAD
*/
        MultiplayerGameplay game = new MultiplayerGameplay(x.getId(),primaryStage,playerName);
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }



    }

}
