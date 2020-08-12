package com.game8.server.models;

public class Player {


    private String playerName;
    private int id;
    public double x,y;
    int score;

    public Player(String playerName){
        this.playerName =playerName;
        this.x=300;
        this.y=300;
    }

    public Player(){
        this.playerName ="";
    }

    public String getPlayerName(){
        return this.playerName;
    }

    public void setId(int id){
        this.id=id;
    }

    public int getId(){
        return this.id;
    }


}
