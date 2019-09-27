package com.example.adithya.tm_v3;

import java.util.Random;

public class UserActivityTracker {

    private int id;
    private String day_and_time_of_activity;
    private long checkInDate;
    private long checkOutDate;
    Random random = new Random();

    public int getId(){
        return this.id;
    }


    public void setDay_and_time_of_activity(String day_and_time_of_activity){
        this.day_and_time_of_activity = day_and_time_of_activity;
    }

    public String getDay_and_time_of_activity(){
        return this.day_and_time_of_activity;
    }

    public void setCheckInDate(long checkInDate){
        this.checkInDate = checkInDate;

    }


    public long getCheckInDate(){
        return this.checkInDate;
    }

    public long getCheckOutDate(){
        return this.checkOutDate;
    }

    public void setCheckOutDate(long checkOutDate){
        this.checkOutDate = checkOutDate;
    }

    public UserActivityTracker(String day_and_time_of_activity){
        this.id = random.nextInt(10000);
        this.day_and_time_of_activity = day_and_time_of_activity;
    }

    public UserActivityTracker(String day_and_time_of_activity,long checkInDate){
        this.id = random.nextInt(10000);
        this.day_and_time_of_activity = day_and_time_of_activity;
        this.checkOutDate = checkInDate;
    }

    public UserActivityTracker(String day_and_time_of_activity,long checkInDate,long checkOutDate){

        this.id = random.nextInt(10000);
        this.day_and_time_of_activity = day_and_time_of_activity;
        this.checkInDate = checkInDate;
        this.checkOutDate = checkOutDate;
    }
}
