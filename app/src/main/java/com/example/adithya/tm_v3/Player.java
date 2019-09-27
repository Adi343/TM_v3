package com.example.adithya.tm_v3;

public class Player {

    private  String name;
    private  int level;

    public void setName(String name){
        this.name = name;
    }

    public String getName(){
        return this.name;
    }

    public void setLevel(int level){
        this.level = level;

    }

    public int getLevel(){
        return this.level;
    }

    public Player(){

    }

    public Player(String name,int level){

        this.name = name;
        this.level = level;

    }

}
