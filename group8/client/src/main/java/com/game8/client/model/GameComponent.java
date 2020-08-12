package com.game8.client.model;

import javafx.geometry.Bounds;

public interface GameComponent {

    int getCoordinateX();
    int getCoordinateY();

    void moveLeft();
    void moveRight();
    void moveDown();
    void moveUp();
    String getType();
    boolean isdead();


    Bounds getBoundaries();
}
