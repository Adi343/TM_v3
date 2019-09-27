package com.example.adithya.tm_v3;

import android.graphics.Bitmap;
import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.util.Date;
import java.util.Random;

public class Challenge {

    String TAG = "TAG";

    int id;
    String challengeName;
    Bitmap bitmap;
    int streak;
    int total;
    int points ;



    int imageNo;
    String challengeType;


    public int getId(){
        return this.id;
    }
    public String getChallengeName() {
        return challengeName;
    }

    public void setChallengeName(String challengeName) {
        this.challengeName = challengeName;
    }

    public void setTotal(int total){
        this.total = total;

    }

    public int getTotal(){

        return this.total;
    }




    public int getStreak() {
        return streak;
    }

    public void setStreak(int streak) {
        this.streak = streak;
    }

    public int getImageNo() {
        return imageNo;
    }

    public void setImageNo(int imageNo) {
        this.imageNo = imageNo;
    }

    public String getChallengeType() {
        return challengeType;
    }

    public void setChallengeType(String challengeType) {
        this.challengeType = challengeType;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public Challenge(){

    }

    public Challenge(String challengeName,int total){
        this.challengeName = challengeName;
        this.total = total;
        Random random = new Random();
        this.id = random.nextInt(10000);
        Log.d(TAG,"Challenge Name: "+challengeName);
        Log.d(TAG,"Challenge total: "+Integer.toString(total));

    }

    public Challenge(String challengeName,int total,String challengeType){

        this.challengeName = challengeName;
        this.total = total;
        this.challengeType = challengeType;
        Random random = new Random();
        this.id = random.nextInt(10000);
        Log.d(TAG,"Challenge Name: "+challengeName);
        Log.d(TAG,"Challenge total: "+Integer.toString(total));

    }

    public Challenge(String challengeName,int total,String challengeType,int imageNo){

        this.challengeName = challengeName;
        this.total = total;
        this.challengeType = challengeType;
        this.imageNo = imageNo;
        Random random = new Random();
        this.id = random.nextInt(10000);
        Log.d(TAG,"Challenge Name: "+challengeName);
        Log.d(TAG,"Challenge total: "+Integer.toString(total));

    }

    public Challenge(String challengeName,int total,String challengeType,int imageNo,int points){

        this.challengeName = challengeName;
        this.total = total;
        this.challengeType = challengeType;
        this.imageNo = imageNo;
        Random random = new Random();
        this.id = random.nextInt(10000);
        this.points = points;
        Log.d(TAG,"Challenge Name: "+challengeName);
        Log.d(TAG,"Challenge total: "+Integer.toString(total));

    }

}
