package com.game8.client.view;

import com.game8.client.model.Bullet;
import com.game8.client.model.GameComponent;
import com.game8.client.model.Shooter;
import javafx.animation.AnimationTimer;
import javafx.beans.binding.BooleanBinding;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;

public class Gameplay{
    private String applicationTitle;
    private Stage stage;
    private int score=0;
    @Value("classpath:/level5.fxml") private Resource uiResuorce;

    private int currentLevel=1;
    private int firedShotsCount;
    private AnimationTimer timer;

    public boolean cheatEnabled=false;
     BooleanProperty ctrlPressed = new SimpleBooleanProperty(false);
     BooleanProperty shiftPressed = new SimpleBooleanProperty(false);
     BooleanProperty ninepressed = new SimpleBooleanProperty(false);
     BooleanBinding ctrlShiftNinePressed = ctrlPressed.and(shiftPressed).and(ninepressed);

    private Pane root;
    private double t = 0;
    private int shotCount=0;

    private Shooter player;

    public Gameplay(String applicationTitle) {

        this.stage = new Stage();
        this.applicationTitle = applicationTitle;
        timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                update();
            }
        };

    }

    public int getCurrentLevel() {
        return currentLevel;
    }
    public void setCurrentLevel(int level) {
        this.currentLevel=level;
    }

    public void display(){
        this.root = new Pane();
        root.setPrefSize(1000, 800);
        this.player = new Shooter(300, 750, 40, 40, "player", Color.BLUE);
        root.getChildren().add(player);

        Scene scene = new Scene(createContent());
        EventHandler<MouseEvent> enteredShipEventHandler = new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                scene.setOnMouseMoved(e->{
                    player.setTranslateX(e.getX()-20);
                    player.setTranslateY(e.getY()-20);
                });
                scene.setOnMouseClicked(e->{
                    firedShotsCount++;
                    Bullet bullet = player.shoot(currentLevel);
                    root.getChildren().add(bullet);
                });
            }
        };
        player.addEventFilter(MouseEvent.MOUSE_ENTERED,enteredShipEventHandler);

        ctrlShiftNinePressed.addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observableValue, Boolean aBoolean, Boolean t1) {
                cheatEnabled=true;
            }
        });

        scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent ke) {
                if (ke.getCode() == KeyCode.CONTROL) {
                    ctrlPressed.set(true);
                } if (ke.getCode() == KeyCode.SHIFT) {
                    shiftPressed.set(true);

                } if (ke.getCode() == KeyCode.DIGIT9) {
                    ninepressed.set(true);
                }
            }
        });

        scene.setOnKeyReleased(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent ke) {
                if (ke.getCode() == KeyCode.CONTROL) {
                    ctrlPressed.set(false);
                } if (ke.getCode() == KeyCode.SHIFT) {
                    shiftPressed.set(false);
                } if (ke.getCode() == KeyCode.DIGIT9) {
                    ninepressed.set(false);
                }
            }
        });


        stage.setScene(scene);



        stage.show();

    }




    private Parent createContent() {


        timer.start();

        createEnemies();

        return root;
    }



    private void createEnemies() {

        int health = currentLevel*10;
        //different size and colors for different enemies
        Color[] enemyColor = {Color.GRAY,Color.GREEN,Color.PURPLE,Color.RED};
        Color currentEnemyColor = enemyColor[currentLevel-1];
        int currentEnemySize=50-currentLevel*7;
        for (int i = 0; i < 3+currentLevel; i++) {
            Shooter enemyShooter = new Shooter(90 + i*100, 150, currentEnemySize, currentEnemySize,health,5, "enemy", currentEnemyColor);
            root.getChildren().add(enemyShooter);
        }
    }

    public List<GameComponent> GameComponent() {
        return root.getChildren().stream().map(n -> (GameComponent)n).collect(Collectors.toList());
    }

    private void update() {
        t += 0.016;
        shotCount++;
        if(shotCount>10){
            Bullet bullet = player.shoot(currentLevel);
            root.getChildren().add(bullet);
            shotCount=0;
        }
        GameComponent().forEach(s -> {
            switch (s.getType()) {

                case "enemybullet":
                    s.moveDown();
                    if (s.getBoundaries().intersects(player.getBoundaries())) {
                        player.health-=((Bullet)s).damage;
                        if(player.health<=0){
                            player.dead = true;

                            Stage levelDisplayStage = new Stage();
                            Pane levelRoot = new Pane();
                            Scene levelDisplayScene = new Scene(levelRoot);
                            levelRoot.setPrefSize(400, 150);
                            String msg = "GAME OVER";
                            Text t = new Text();

                            t.setText(msg);
                            t.setX(50);
                            t.setY(50);
                            levelRoot.getChildren().add(t);


                            Button tryAgainButton = new Button("Try again");
                            tryAgainButton.setTranslateX(100);
                            tryAgainButton.setTranslateY(100);
                            levelRoot.getChildren().add(tryAgainButton);
                            tryAgainButton.setOnAction(e->{
                                Stage m = (Stage) (levelDisplayScene.getWindow());
                                m.close();
                                currentLevel=1;
                                display();
                            });
                            levelDisplayStage.setScene(levelDisplayScene);
                            levelDisplayStage.show();
                            timer.stop();
                        }
                        ((Bullet)s).dead = true;
                    }
                    break;

                case "playerbullet":
                    s.moveUp();
                    GameComponent().stream().filter(e -> e.getType().equals("enemy")).forEach(enemy -> {
                        if (s.getBoundaries().intersects(enemy.getBoundaries())) {
                            ((Shooter) enemy).health-=((Bullet)s).damage;
                            if(((Shooter) enemy).health<=0){
                                ((Shooter) enemy).dead=true;
                            }
                            ((Bullet)s).dead = true;
                        }
                    });

                    GameComponent().stream().filter(e -> e.getType().equals("enemybullet")).forEach(enemybullet -> {
                        if (s.getBoundaries().intersects(enemybullet.getBoundaries())) {
                            ((Bullet)enemybullet).dead = true;
                            ((Bullet)s).dead = true;
                        }
                    });
                    break;

                case "enemy":
                    if (t > 2) {
                        if (Math.random() < 0.3) {
                            Bullet newBullet = ((Shooter)s).shoot(currentLevel);
                            root.getChildren().add(newBullet);
                        }
                    }

                    break;
            }
        });

        root.getChildren().removeIf(n -> {
            GameComponent s = (GameComponent) n;
            return s.isdead();
        });

        AtomicBoolean canFinish = new AtomicBoolean(true);
        GameComponent().forEach(s -> {
            if(s.getType().equals("enemy")){
                canFinish.set(false);
            }
        });

        if(canFinish.get() || cheatEnabled){
            cheatEnabled=false;
            score+=player.getHealth();
            currentLevel++;
            if(currentLevel==5){

                try {
/*                    Parent level5 = FXMLLoader.load(getClass().getResource("/level5.fxml"));
                    Scene level5Scene = new Scene(level5, 1200, 720);*/
                    root = FXMLLoader.load(getClass().getResource("/level5.fxml"));
                    Scene level5Scene = new Scene(root, 1200, 720);
                    stage.setScene(level5Scene);
                    stage.show();

                } catch (IOException e) {
                    e.printStackTrace();
                }
/*
                canFinish.set(false);
*/
                timer.stop();
/*                stage.close();*/

/*
                Stage levelDisplayStage = new Stage();
                Pane levelRoot = new Pane();
                Scene levelDisplayScene = new Scene(levelRoot);
                levelRoot.setPrefSize(400, 150);
                String msg = "Congratulations you have finished level "+(currentLevel-1);
                String msg2 = "Now we wait for another player ";
                Text t = new Text();
                Text t2 = new Text();

                t.setText(msg);
                t.setX(50);
                t.setY(50);
                levelRoot.getChildren().add(t);
                t2.setText(msg2);
                t2.setX(50);
                t2.setY(70);
                levelRoot.getChildren().add(t2);

                Button exitButton = new Button("Exit");
                exitButton.setTranslateX(100);
                exitButton.setTranslateY(100);
                levelRoot.getChildren().add(exitButton);
                Button waitButton = new Button("Wait for other player");
                waitButton.setTranslateX(200);
                waitButton.setTranslateY(100);
                levelRoot.getChildren().add(waitButton);

                exitButton.setOnAction(e->{
                    Stage s = (Stage) (levelDisplayScene.getWindow());
                    s.close();
                    stage.close();

                });
                waitButton.setOnAction(e->{
*/
/*
                    How can we direct it to controller
*//*


                });

                levelDisplayStage.setScene(levelDisplayScene);
                levelDisplayStage.show();
                canFinish.set(false);
                timer.stop();
*/


            }
            else {
                Stage levelDisplayStage = new Stage();
                Pane levelRoot = new Pane();
                Scene levelDisplayScene = new Scene(levelRoot);
                levelRoot.setPrefSize(400, 150);
                String msg = "Congratulations you have finished level "+(currentLevel-1);
                String msg2 = "Your score is "+score;
                Text t = new Text();
                Text t2 = new Text();

                t.setText(msg);
                t.setX(50);
                t.setY(50);
                levelRoot.getChildren().add(t);
                t2.setText(msg2);
                t2.setX(50);
                t2.setY(70);
                levelRoot.getChildren().add(t2);


                Button nextLevelButton = new Button("Next Level");
                nextLevelButton.setTranslateX(100);
                nextLevelButton.setTranslateY(100);
                levelRoot.getChildren().add(nextLevelButton);
                nextLevelButton.setOnAction(e->{
                    Stage s = (Stage) (levelDisplayScene.getWindow());
                    s.close();
                    display();
                });
                levelDisplayStage.setScene(levelDisplayScene);
                levelDisplayStage.show();
                canFinish.set(false);
                timer.stop();
            }
        }

        if (t > 2) {
            t = 0;
        }
    }





}
