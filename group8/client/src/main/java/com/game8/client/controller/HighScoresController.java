package com.game8.client.controller;

import com.game8.client.model.Record;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import javafx.scene.control.Button;

import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;


import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

/**
 * HighScore screen controller
 *
 * This class is responsible for handling the interactions
 * between the user and the HighScore screen.
 *
 * @author Group 8
 * @version 1.0
 * @since 2020-04-27
 */
@Component
public class HighScoresController implements Initializable {

    @Value("${spring.application.recordApiAddress}") private String recordApiAddress;
    private RestTemplate restTemplate = new RestTemplate();
    private ControllerUtility controllerUtility = new ControllerUtility();
    @FXML Button returnButton;

    @FXML TableView<Record> weeklyTable;
    @FXML TableColumn<Record, String> weeklyRank;
    @FXML TableColumn<Record, String> weeklyUser;
    @FXML TableColumn<Record, String> weeklyScore;
    @FXML TableColumn<Record, String> weeklyDate;

    @FXML TableView<Record> monthlyTable;
    @FXML TableColumn<Record, String> monthlyRank;
    @FXML TableColumn<Record, String> monthlyUser;
    @FXML TableColumn<Record, String> monthlyScore;
    @FXML TableColumn<Record, String> monthlyDate;

    @FXML TableView<Record> allTimeTable;
    @FXML TableColumn<Record, String> allTimeRank;
    @FXML TableColumn<Record, String> allTimeUser;
    @FXML TableColumn<Record, String> allTimeScore;
    @FXML TableColumn<Record, String> allTimeDate;


    /**
     * This method initalizes the TableView and populates it with
     * Record objects.
     *
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        recordApiAddress = controllerUtility.getRecordApiAddress();

        weeklyUser.setCellValueFactory(new PropertyValueFactory<>("username"));
        weeklyScore.setCellValueFactory(new PropertyValueFactory<>("score"));
        weeklyDate.setCellValueFactory(new PropertyValueFactory<>("recordDate"));

        monthlyUser.setCellValueFactory(new PropertyValueFactory<>("username"));
        monthlyScore.setCellValueFactory(new PropertyValueFactory<>("score"));
        monthlyDate.setCellValueFactory(new PropertyValueFactory<>("recordDate"));

        allTimeUser.setCellValueFactory(new PropertyValueFactory<>("username"));
        allTimeScore.setCellValueFactory(new PropertyValueFactory<>("score"));
        allTimeDate.setCellValueFactory(new PropertyValueFactory<>("recordDate"));

        weeklyTable.getItems().setAll(getWeeklyRecords());
        monthlyTable.getItems().setAll(getMonthlyRecords());
        allTimeTable.getItems().setAll(getAllTimeRecords());

    }

    /**
     * This method fetches the weekly records from the backend server.
     **
     * @return List of records.
     */
    private List<Record> getWeeklyRecords() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> entity = new HttpEntity<>(headers);
        ResponseEntity<List<Record>> response = restTemplate.exchange(
                recordApiAddress + "/getWeeklyRecords",
                HttpMethod.GET,
                entity,
                new ParameterizedTypeReference<>() {});



        return response.getBody();
    }

    /**
     * This method fetches the monthly records from the backend server.
     **
     * @return List of records.
     */
    private List<Record> getMonthlyRecords() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> entity = new HttpEntity<>(headers);
        ResponseEntity<List<Record>> response = restTemplate.exchange(
                recordApiAddress + "/getMonthlyRecords",
                HttpMethod.GET,
                entity,
                new ParameterizedTypeReference<>() {});



        return response.getBody();
    }

    /**
     * This method fetches the all time records from the backend server.
     **
     * @return List of records.
     */
    private List<Record> getAllTimeRecords() {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> entity = new HttpEntity<>(headers);
        ResponseEntity<List<Record>> response = restTemplate.exchange(
                recordApiAddress + "/getAllRecords",
                HttpMethod.GET,
                entity,
                new ParameterizedTypeReference<>() {});


        
        return response.getBody();
    }

    /**
     * This method handles the return button on the Highscore screen.
     **
     * @param actionEvent Action event handling the click button
     */
    @FXML
    public void handleReturnButtonPressed(ActionEvent actionEvent){
        controllerUtility.getMainPage(actionEvent);
    }
}
