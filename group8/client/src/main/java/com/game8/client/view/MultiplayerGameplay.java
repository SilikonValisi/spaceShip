package com.game8.client.view;

import com.game8.client.controller.ControllerUtility;
import com.game8.client.model.*;
import javafx.animation.AnimationTimer;
import javafx.beans.binding.BooleanBinding;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import com.game8.client.model.Bullet;
import com.game8.client.model.GameComponent;
import com.game8.client.model.Shooter;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;

public class MultiplayerGameplay {



    private Stage stage;
    private Pane root;
    private AnimationTimer timer;
    private double t = 0;
    private int shotCount=0;
    public Shooter player;
    public Shooter otherPlayer;
    private String playerName;
    private RestTemplate restTemplate=new RestTemplate();

    private ControllerUtility controllerUtility = new ControllerUtility();
    public boolean cheatEnabled=false;
    BooleanProperty ctrlPressed = new SimpleBooleanProperty(false);
    BooleanProperty shiftPressed = new SimpleBooleanProperty(false);
    BooleanProperty ninepressed = new SimpleBooleanProperty(false);
    BooleanBinding ctrlShiftNinePressed = ctrlPressed.and(shiftPressed).and(ninepressed);
    private int score=0;
    private int id=0;

    private int currentLevel=5;

    private String playerApiAddress;





    private List<GameComponent> changedElements ;

    public MultiplayerGameplay(int id,Stage stage,String playerName){
        this.id=id;
        System.out.printf("the id is %d %n",id);
        this.stage = stage;
        this.root=new Pane();
        this.playerName=playerName;
        timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                update();
            }
        };
        this.playerApiAddress = controllerUtility.getPlayerApiAdress() ;

        this.root.setPrefSize(1000, 800);
        this.player = new Shooter(200+(id%2)*40, 750, 40, 40, "player", Color.BLUE);
        this.root.getChildren().add(player);
        this.otherPlayer = new Shooter(200+((id+1)%2)*40, 750, 40, 40, "player", Color.RED);
        this.root.getChildren().add(otherPlayer);


        Scene scene = new Scene(createContent());

        EventHandler<MouseEvent> enteredShipEventHandler = mouseEvent -> {
            scene.setOnMouseMoved(e->{
                player.setTranslateX(e.getX()-20);
                player.setTranslateY(e.getY()-20);
            });
            scene.setOnMouseClicked(e->{
                Bullet bullet = player.shoot(5);
                root.getChildren().add(bullet);
            });
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

        this.stage.setScene(scene);
        this.stage.show();


    }


    public void updateOtherPlayer(){

    }

    private Pane createContent() {


        this.timer.start();

        createEnemies();

        return this.root;
    }





    private void createEnemies() {
        int health = currentLevel*10;
        //different size and colors for different enemies
        Color[] enemyColor = {Color.GRAY,Color.GREEN,Color.PURPLE,Color.RED,Color.GOLD};
        Color currentEnemyColor = enemyColor[currentLevel-1];
        int currentEnemySize=50-currentLevel*7;
        for (int i = 0; i < 3+currentLevel; i++) {
            Shooter enemyShooter = new Shooter(90 + i*100, 150, currentEnemySize, currentEnemySize,health,5, "enemy", currentEnemyColor);
            root.getChildren().add(enemyShooter);
        }
    }

    private void update() {
        HttpEntity<String> ourPlayer = ControllerUtility.createPlayerWithPos(playerName,player.getTranslateX(),player.getTranslateY());

        ResponseEntity<Player> otherPlayerInfo = restTemplate.exchange(
                playerApiAddress + "/otherPlayerMovement",
                HttpMethod.POST,
                ourPlayer,
                new ParameterizedTypeReference<>() {});
        double xValue = Objects.requireNonNull(otherPlayerInfo.getBody()).x;
        double yValue = otherPlayerInfo.getBody().y;

        otherPlayer.setTranslateX(xValue);
        otherPlayer.setTranslateY(yValue);
        System.out.printf("other player activity: %f,%f %n",xValue,yValue);

        t += 0.016;
        shotCount++;
        if(shotCount>10){
            Bullet bullet = player.shoot(currentLevel);
            Bullet otherPlayerbullet = otherPlayer.shoot(currentLevel);

            root.getChildren().add(bullet);
            root.getChildren().add(otherPlayerbullet);

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


                            Button tryAgainButton = new Button("EXIT");
                            tryAgainButton.setTranslateX(100);
                            tryAgainButton.setTranslateY(100);
                            levelRoot.getChildren().add(tryAgainButton);
                            tryAgainButton.setOnAction(e->{
                                Stage m = (Stage) (levelDisplayScene.getWindow());
                                m.close();
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
            score+=currentLevel*100-player.getHealth()+id;

            Stage levelDisplayStage = new Stage();
            Pane levelRoot = new Pane();
            Scene levelDisplayScene = new Scene(levelRoot);
            levelRoot.setPrefSize(400, 150);
            HttpEntity<String> ourScore = ControllerUtility.createPlayerScore(score);
            int otherPlayersScore=0;

            if(cheatEnabled){
                String msg = "Congratulations you CHEATED, you get all the points"+(score+otherPlayersScore);
                String msg2 = "Your score is "+score;
                String msg3 = "Opponents score is "+otherPlayersScore;

                Text t = new Text();
                Text t2 = new Text();
                Text t3 = new Text();

                t.setText(msg);
                t.setX(50);
                t.setY(50);
                levelRoot.getChildren().add(t);
                t2.setText(msg2);
                t2.setX(50);
                t2.setY(70);
                levelRoot.getChildren().add(t2);
                t3.setText(msg3);
                t3.setX(50);
                t3.setY(90);
                levelRoot.getChildren().add(t3);
            }
            else {
                while (otherPlayersScore==0){
                    ResponseEntity<Score> otherPlayerScore = restTemplate.exchange(
                            playerApiAddress + "/otherPlayerScore",
                            HttpMethod.POST,
                            ourScore,
                            new ParameterizedTypeReference<>() {});

                    otherPlayersScore=otherPlayerScore.getBody().getScore();
                    System.out.printf("Our score is %d, others score is %d %n",score,otherPlayersScore);
                    try {
                        Thread.sleep(4000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

                String msg = "Congratulations you won total score is "+(score+otherPlayersScore);
                String msg2 = "Your score is "+score;
                String msg3 = "Opponents score is "+otherPlayersScore;

                Text t = new Text();
                Text t2 = new Text();
                Text t3 = new Text();

                t.setText(msg);
                t.setX(50);
                t.setY(50);
                levelRoot.getChildren().add(t);
                t2.setText(msg2);
                t2.setX(50);
                t2.setY(70);
                levelRoot.getChildren().add(t2);
                t3.setText(msg3);
                t3.setX(50);
                t3.setY(90);
                levelRoot.getChildren().add(t3);
            }

            cheatEnabled=false;

            Button exitBtn = new Button("Exit");
            exitBtn.setTranslateX(100);
            exitBtn.setTranslateY(100);
            levelRoot.getChildren().add(exitBtn);
            exitBtn.setOnAction(e->{
                
                Stage s = (Stage) (levelDisplayScene.getWindow());
                s.close();
            });
            levelDisplayStage.setScene(levelDisplayScene);
            levelDisplayStage.show();
            timer.stop();

        }

        if (t > 2) {
            t = 0;
        }
    }


    public List<GameComponent> GameComponent() {
        return root.getChildren().stream().map(n -> (GameComponent)n).collect(Collectors.toList());
    }

}
