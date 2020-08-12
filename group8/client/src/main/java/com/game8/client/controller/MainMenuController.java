package com.game8.client.controller;

import com.game8.client.view.Gameplay;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Main Menu screen controller
 *
 * This class is responsible for handling the interactions
 * between the user and the Main Menu screen.
 *
 * @author Group 8
 * @version 1.0
 * @since 2020-04-27
 */
public class MainMenuController implements Initializable {
    @FXML public Button newGameButton;
    @FXML public Button highScoresButton;
    @FXML public Button creditsButton;
    @FXML public Button exitButton;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }

    @FXML
    public void handleNewGameButtonPressed(){
        Gameplay game = new Gameplay("game");
        game.display();
    }

    @FXML
    public void handleHighScoresButtonPressed(ActionEvent actionEvent){
        Stage primaryStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();

        try {
            Parent highScores = FXMLLoader.load(getClass().getResource("/highScoresPage.fxml"));
            Scene highScoresScene = new Scene(highScores, 1200, 720);
            primaryStage.setScene(highScoresScene);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void handleCreditsButtonPressed(ActionEvent actionEvent){
        Stage primaryStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();

        try {
            Parent creditsMenu = FXMLLoader.load(getClass().getResource("/creditsPage.fxml"));
            Scene creditsMenuScene = new Scene(creditsMenu, 1200, 720);
            primaryStage.setScene(creditsMenuScene);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void handleExitButtonPressed(ActionEvent actionEvent){
        Stage primaryStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();

        Stage popupwindow = new Stage();
        popupwindow.initModality(Modality.APPLICATION_MODAL);
        Label label1 = new Label("Are you sure you want to exit the game?");
        Button button1 = new Button("Yes");
        Button button2 = new Button("No");
        button1.setOnAction(e -> {popupwindow.close(); primaryStage.close();});
        button2.setOnAction(e -> popupwindow.close());
        VBox layout = new VBox(10);
        layout.getChildren().addAll(label1, button1, button2);
        layout.setAlignment(Pos.CENTER);
        Scene scene1 = new Scene(layout, 300, 250);
        popupwindow.setScene(scene1);
        popupwindow.showAndWait();

    }


}
