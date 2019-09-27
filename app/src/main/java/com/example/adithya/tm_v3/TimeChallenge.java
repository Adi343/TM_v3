package com.example.adithya.tm_v3;

import android.graphics.Bitmap;

import java.io.ByteArrayOutputStream;
import java.util.Date;
import java.util.Random;

public class TimeChallenge  {


    //Time challenges includes challenges such as use taskM for 5 days,
    // 10 days,20 days...........

    private int id;
    private String challengeName;
    private int streak;
    private int total;
    byte[] imageArray;
    Bitmap bitmap;

    public int getId() {
        return id;
    }

    /*
    public void setId(int id) {
        this.id = id;
    }
    */

    public String getChallengeName() {
        return challengeName;
    }

    public void setChallengeName(String challengeName) {
        this.challengeName = challengeName;
    }

    public int getStreak() {
        return streak;
    }

    public void setStreak(int streak) {
        this.streak = streak;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public byte[] getImageArray(){
        return this.imageArray;
    }







    public TimeChallenge(String challengeName,int streak,int total){
        Random random = new Random();

        this.id = random.nextInt(10000);
        this.challengeName = challengeName;
        this.streak = streak;
        this.total = total;

    }






    public void incrementStreak(){
        streak++;
    }



}
