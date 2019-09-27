package com.example.adithya.tm_v3;

public class Project {
    private String name;
    private int id;
    private int priority;
    public String getName(){
        return name;
    }
    public void setName(String name){
        this.name = name;
    }

    public int getId(){
        return this.id;
    }
    public void setId(int id){
        this.id = id;

    }

    public int getPriority(){
        return this.priority;
    }

    public void setPriority(int priority){
        this.priority = priority;
    }

    public Project(){

    }
    public Project(int id){
        this.id = id;
    }
    public Project(int id,String name){
        this.name = name;
        this.id = id;
    }
    public Project(String name){
        this.name = name;
    }

    public Project(int id,String name,int priority){
        this.name = name;
        this.id = id;
        this.priority = priority;


    }
}

