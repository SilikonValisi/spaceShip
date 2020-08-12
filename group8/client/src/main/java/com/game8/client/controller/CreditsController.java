package com.game8.client.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import javafx.fxml.Initializable;

import javafx.scene.control.Button;

import org.springframework.stereotype.Component;

import java.net.URL;
import java.util.ResourceBundle;

/**
 *  This class is responsible for controlling the
 *  Credits page
 *
 * @author group8
 * @since 27.04.2020
 */
@Component
public class CreditsController implements Initializable {

    @FXML
    Button returnButton;

    ControllerUtility controllerUtility;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }


    @FXML
    public void handleReturnButtonPressed(ActionEvent actionEvent){
        controllerUtility.getMainPage(actionEvent);

    }
}
