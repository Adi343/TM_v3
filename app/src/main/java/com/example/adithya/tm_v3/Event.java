package com.example.adithya.tm_v3;

import java.util.Date;

public class Event {
    private int id;
    private String name;
    private String startDate;
    private String endDate;

    public void setName(String name){
        this.name = name;
    }

    public String getName(){
        return this.name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public Event(int id,String name){
        this.id = id;
        this.name = name;
    }

    public Event(int id,String name,String endDate){
        this.id = id;
        this.name = name;
        this.endDate = endDate;
    }

    public Event(int id,String name,String startDate,String endDate){
        this.id = id;
        this.name = name;
        this.startDate = startDate;
        this.endDate = endDate;
    }

}
