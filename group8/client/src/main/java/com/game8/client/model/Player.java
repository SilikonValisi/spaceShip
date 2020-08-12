package com.game8.client.model;

public class Player {


    private String playerName;
    private int id;
    public double x,y;

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
