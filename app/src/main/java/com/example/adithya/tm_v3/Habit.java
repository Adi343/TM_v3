package com.example.adithya.tm_v3;

public class Habit {
    private String name;
    private int id;

    public String getName(){
        return this.name;
    }
    public void setName(String name){
        this.name = name;
    }
    public int getId(){
        return id;
    }

    public void setId(int id){
        this.id = id;
    }

    public Habit(){

    }
    public Habit(String name){
        this.name = name;
    }
    public Habit(int id,String name){
        this.id = id;
        this.name = name;
    }
}
