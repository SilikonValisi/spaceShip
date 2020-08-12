package com.game8.client.model;

import javafx.geometry.Bounds;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Shooter extends Rectangle implements GameComponent{
    public boolean dead = false;
    public final String type;
    public int health;
    public int speed;


    public int id;
    public Shooter(int id){
        this.id=id;
        this.type="httpPlayer";
    }

    public Shooter(int x, int y, int w, int h,String type, Color color){
        super(w,h,color);
        this.type=type;
        setTranslateX(x);
        setTranslateY(y);
        this.health=100;
        this.speed=5;
    }

    public Shooter(int x, int y, int w, int h,int health,int speed, String type, Color color){
        super(w,h,color);
        this.health=health;
        this.type=type;
        this.speed=speed;
        setTranslateX(x);
        setTranslateY(y);
    }

    @Override
    public void moveLeft() {
        setTranslateX(getTranslateX()-speed);
    }

    @Override
    public void moveRight() {
        setTranslateX(getTranslateX()+speed);

    }

    @Override
    public void moveDown() {
        setTranslateY(getTranslateY()+speed);
    }

    @Override
    public void moveUp() {
        setTranslateY(getTranslateY()-speed);

    }

    @Override
    public String getType() {
        return this.type;
    }

    @Override
    public boolean isdead() {
        return dead;
    }

    @Override
    public int getCoordinateX() {
        return (int) getTranslateX();
    }

    @Override
    public int getCoordinateY() {
        return (int) getTranslateY();
    }

    @Override
    public Bounds getBoundaries(){
        return this.getBoundsInParent();
    }


    public Bullet shoot(int level) {
        int damage,speed;
        speed=2+level;
        damage=10;
        if(this.type.equals("player")){

        }
        else {
            damage+=level*5;
        }
        return new Bullet(this.getCoordinateX() + 20, this.getCoordinateY(), 5, 20,damage,speed, this.type + "bullet", Color.BLACK);
    }

    public int getHealth() {
        return health;
    }
}
