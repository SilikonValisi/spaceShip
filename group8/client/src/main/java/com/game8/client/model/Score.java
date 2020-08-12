package com.game8.client.model;

public class Score {
    public  int score;

    public Score(int score){
        this.score=score;
    }

    public Score(){
        this.score=0;
    }


    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }


}
