package com.game8.client.model;

import javafx.geometry.Bounds;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Bullet extends Rectangle implements GameComponent{
    public boolean dead = false;
    public final String type;
    public int damage;
    public int speed;

    //changing speed and damage
    public Bullet(int x, int y, int w, int h,int damage,int speed, String type, Color color){
        super(w,h,color);
        this.damage=damage;
        this.type=type;
        this.speed=speed;
        setTranslateX(x);
        setTranslateY(y);
    }

    //default bullet

    public Bullet(int x, int y, int w, int h, String type, Color color){
        super(w,h,color);
        this.damage=10;
        this.type=type;
        this.speed=5;
        setTranslateX(x);
        setTranslateY(y);
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
    public Bounds getBoundaries(){
        return this.getBoundsInParent();
    }



}
