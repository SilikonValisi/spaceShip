package com.game8.client.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.net.URL;
import org.springframework.http.*;

import java.util.Objects;
import java.util.ResourceBundle;

/**
 * Login and Register screen controller
 *
 * This class is responsible for handling the interactions
 * between the user and the Login and Register screens.
 *
 * @author Group 8
 * @version 1.0
 * @since 2020-04-27
 */
@Component
public class LoginRegisterController implements Initializable {

    private RestTemplate restTemplate;
    @Value("${spring.application.userApiAddress}") private String userApiAddress;
    private ControllerUtility controllerUtility = new ControllerUtility();

    @FXML public Button loginButton;
    @FXML public PasswordField passwordBox;
    @FXML public TextField usernameBox;
    @FXML public Hyperlink signInLink;

    public static String username;

    @FXML
    public void handleLoginEnter(ActionEvent actionEvent){
        Stage primaryStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();

        String password = passwordBox.getText();
        String username = usernameBox.getText();

        if(password.isEmpty()){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Password");
            alert.setContentText("Password is missing");
            alert.showAndWait();
            return;
        }
        if(username.isEmpty()){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Username");
            alert.setContentText("Username is missing");
            alert.showAndWait();

            return;
        }

        // User for testing purposes.
        // username: clientTest
        // password: clientTest

        // This is a backdoor to avoid database connection in testing.
        // If username = local and password = local the system just logs in
        if(password.equals("local") && username.equals("local")){
            try {
                Parent mainMenu = FXMLLoader.load(getClass().getResource("/mainPage.fxml"));
                Scene mainMenuScene = new Scene(mainMenu, 1200, 720);
                primaryStage.setScene(mainMenuScene);
                primaryStage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return;
        }

        // Create an entity and send it to backend server.
        System.out.println("api address: " + userApiAddress);
        HttpEntity<String> entity = ControllerUtility.createUserEntity(username, password);
        ResponseEntity<String> response = restTemplate.exchange(
                userApiAddress + "/login",
                    HttpMethod.POST,
                    entity,
                    new ParameterizedTypeReference<>() {});

        if(Objects.equals(response.getBody(), "Auth")){
            // If user is authorized, the system redirects the user to main page
            try {
                this.username=username;
                Parent mainMenu = FXMLLoader.load(getClass().getResource("/mainPage.fxml"));
                Scene mainMenuScene = new Scene(mainMenu, 1200, 720);
                primaryStage.setScene(mainMenuScene);
                primaryStage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else
        {
            // Else, user gets a warning
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Invalid Creds");
            alert.setContentText("Incorrect username or password");
            alert.showAndWait();

        }
    }

    @FXML
    public void handleSignInPressed() {
        // Creates a popup for register
        Stage popupwindow = new Stage();
        popupwindow.initModality(Modality.APPLICATION_MODAL);
        Label label = new Label("Create new Account");
        Label userLabel = new Label("Username");
        TextField usernameBox = new TextField();
        Label passLabel1 = new Label("Password");
        PasswordField passwordBox1 = new PasswordField();
        Label passLabel2 = new Label("Password again");
        PasswordField passwordBox2 = new PasswordField();

        Button button = new Button("Register");

        // Button is tasked to send the user data to backend server
        button.setOnAction(e -> {
            if(!passwordBox1.getText().equals(passwordBox2.getText())){
                // If passwords do not match, alert is shown
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("FAIL");
                alert.setHeaderText("Passwords are not matching.");
                alert.setContentText("Passwords are not matching.");
                alert.showAndWait();
            }
            String password = passwordBox1.getText();
            String username = usernameBox.getText();


            HttpEntity<String> entity = ControllerUtility.createUserEntity(username, password);

            ResponseEntity<String> response = restTemplate.exchange(
                    userApiAddress + "/createUser",
                    HttpMethod.POST,
                    entity,
                    new ParameterizedTypeReference<>() {});

            if(Objects.equals(response.getBody(), "RegisterSuccess")){
                // User is created
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Success");
                alert.setHeaderText("Register successful");
                alert.setContentText("New account created.");
                alert.showAndWait();
            }
            else if(Objects.equals(response.getBody(), "RegisterFail")){
                // User is not created. It already exists.
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Fail");
                alert.setHeaderText("Register failed");
                alert.setContentText("Account already exists.");
                alert.showAndWait();
            }
            else{
                // If something goes wrong.
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Fail");
                alert.setHeaderText("Register failed");
                alert.setContentText("Some error has occured.");
                alert.showAndWait();
            }

            popupwindow.close();
        });
        VBox layout = new VBox(10);
        layout.getChildren().addAll(label, userLabel, usernameBox, passLabel1, passwordBox1, passLabel2, passwordBox2, button);
        layout.setAlignment(Pos.CENTER);
        Scene scene1 = new Scene(layout, 500, 500);
        popupwindow.setScene(scene1);
        popupwindow.showAndWait();

    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        restTemplate = new RestTemplate();
        userApiAddress = controllerUtility.getUserApiAddress();
    }
}