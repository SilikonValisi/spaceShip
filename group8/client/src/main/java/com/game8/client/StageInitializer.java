package com.game8.client;


import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import org.springframework.core.io.Resource;
import java.io.IOException;

@Component
public class StageInitializer implements ApplicationListener<UiApplication.StageReadyEvent> {

    @Value("classpath:/welcomePage.fxml") private Resource uiResuorce;
    private String applicationTitle;
    private ApplicationContext applicationContext;

    public StageInitializer(@Value("${spring.application.ui.title}") String applicationTitle,
                            ApplicationContext applicationContext) {
        this.applicationTitle = applicationTitle;
        this.applicationContext = applicationContext;
    }

    @Override
    public void onApplicationEvent(UiApplication.StageReadyEvent stageReadyEvent) {
        Stage stage = stageReadyEvent.getStage();
        FXMLLoader loader = null;
        try {
            loader = new FXMLLoader(uiResuorce.getURL());
        } catch (IOException e) {
            e.printStackTrace();
        }
        assert loader != null;
        Parent root = null;
        try {
            root = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        assert root != null;
        Scene scene = new Scene(root);
        stage.setTitle(this.applicationTitle);
        stage.setScene(scene);
        stage.show();



    }
}
