package com.example.adithya.tm_v3;

import android.util.Log;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;

public class Task {

    private static final String TAG = "TAG";
    private String name;
    private int id;
    //Returns number of milliseconds till due date...
    private long getTime;
    private String projectName;
    private int date;
    private int priority;

    private Date dueDate;
    //dat is for example 2019 3 19(This is string made out of datepicker dialog box).
    private String dueDate2;
    private String dAt;
    //DAT is for example Fri,Apr 19
    private String DAT;



    //DATE is for date of task in milliseconds
    private String dateCompleted;
    private long DATE;

    public int getDate() {
        return date;
    }

    public void setDate(int date) {
        this.date = date;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId(){
        return id;
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

    public String getProjectName(){
        return this.projectName;
    }

    public void setProjectName(String projectName){
        this.projectName = projectName;
    }

    public String getdAt() {
        return dAt;
    }

    public void setdAt(String dAt) {
        this.dAt = dAt;
    }

    public String getDAT(){
        return DAT;
    }

    public void setDAT(String DAT){
        this.DAT = DAT;
    }

    public long getGetTime(){

        return this.dueDate.getTime();
    }
    public void setGetTime(long getTime){
        this.getTime = getTime;
    }

    public String getDateCompleted() {
        return dateCompleted;
    }

    public void setDateCompleted(String dateCompleted) {
        this.dateCompleted = dateCompleted;
    }

    public String getDueDate2() {
        return dueDate2;
    }

    public void setDueDate2(String dueDate2) {
        this.dueDate2 = dueDate2;
    }


    public long getDATE(){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        try {
            date = sdf.parse(dAt);
            //All your parse Operations
        } catch (ParseException e) {
            //Handle exception here, most of the time you will just log it.
            e.printStackTrace();
        }

        long millis = date.getTime();

        return millis;
    }

    public void setDATE(long DATE){
        this.DATE = DATE;
    }

    //
    public String generateDate(){
        if(dAt!=""){

            String[] stringArray = dAt.split(" ");
            Log.v(TAG,"Split array date*** "+stringArray[0]);
            Log.v(TAG,"Split array date*** "+stringArray[1]);
            Log.v(TAG,"Split array date*** "+stringArray[2]);

            int year = Integer.parseInt(stringArray[0]);
            int month = Integer.parseInt(stringArray[1]);
            int day = Integer.parseInt(stringArray[2]);

            this.dueDate = new GregorianCalendar(year, month, day).getTime();
            SimpleDateFormat sdf = new SimpleDateFormat("EEE, MMM d");
            this.DAT = sdf.format(dueDate);
            DATE = dueDate.getTime();
            Log.v(TAG,"Task Constructor ***"+Integer.toString(year));
            Log.d(TAG,"Task Constructor "+DAT);




        }

        //*optional code
        else {
            this.DAT = "";
        }
        return DAT;
    }
    public Task(){

        createDate();

    }

    public Task(int id,String name){
        this.name = name;
        this.id = id;

        createDate();
    }

    public Task(String name){


        this.name = name;
        createDate();
    }


    public Task(String name,String projectName){
        this.name = name;
        this.projectName = projectName;
        createDate();

    }

    public Task(String name,String projectName,int priority){
        this.name = name;
        this.projectName = projectName;
        createDate();
        this.priority = priority;

    }
    public Task(int id,String name,String projectName){
        this.id = id;
        this.name = name;
        this.projectName = projectName;

        createDate();
    }

    public Task(int id,String name,String projectName,int priority){
        this.id = id;
        this.name = name;
        this.projectName = projectName;

        createDate();
        this.priority = priority;
    }



    /*

    public Task(int id,String name,String projectName,int date){
        this.id = id;
        this.name = name;
        this.projectName = projectName;
        this.date = date;

        createDate();

    }



    public Task(int id,String name,String projectName,String dAt){
        this.id = id;
        this.name = name;
        this.projectName = projectName;
        this.dAt = dAt;

        if(dAt==""){
            this.dAt="";
        }
        else if(dAt!=""){

            String[] stringArray = dAt.split(" ");
            Log.v(TAG,"Split array date*** "+stringArray[0]);
            Log.v(TAG,"Split array date*** "+stringArray[1]);
            Log.v(TAG,"Split array date*** "+stringArray[2]);

            int year = Integer.parseInt(stringArray[0]);
            int month = Integer.parseInt(stringArray[1]);
            int day = Integer.parseInt(stringArray[2]);

            this.dueDate = new GregorianCalendar(year, month, day).getTime();
            SimpleDateFormat sdf = new SimpleDateFormat("EEE, MMM d");
            this.DAT = sdf.format(dueDate);

            Log.v(TAG,"Task Constructor ***"+Integer.toString(year));


        }


        //*optional code
        else {
            this.DAT = "";
        }
        Log.v(TAG,"Inside Task Constructor* "+this.name);
        Log.v(TAG,"Inside Task Constructor* "+this.projectName);
        Log.v(TAG,"Inside Task Constructor* "+this.dAt);
        Log.v(TAG,"Inside Task Constructor* "+this.DAT);


    }
    */
//Task Constructor
    public Task(int id,String name,String projectName,String dAt,String DAT){
        this.id = id;
        this.name = name;
        this.projectName = projectName;
        this.dAt = dAt;
        this.DAT = DAT;



        Log.v(TAG,"Inside Task Constructor* "+this.name);
        Log.v(TAG,"Inside Task Constructor* "+this.projectName);
        Log.v(TAG,"Inside Task Constructor* "+this.dAt);
        Log.v(TAG,"Inside Task Constructor* "+this.DAT);


    }

    public Task(int id,String name,String projectName,String dAt,String DAT,int priority){
        this.id = id;
        this.name = name;
        this.projectName = projectName;
        this.dAt = dAt;
        this.DAT = DAT;
        this.priority = priority;


        Log.v(TAG,"Inside Task Constructor* "+this.name);
        Log.v(TAG,"Inside Task Constructor* "+this.projectName);
        Log.v(TAG,"Inside Task Constructor* "+this.dAt);
        Log.v(TAG,"Inside Task Constructor* "+this.DAT);


    }

    public Task(int id,String name,String projectName,String dAt,String DAT,int priority,String dateCompleted){
        this.id = id;
        this.name = name;
        this.projectName = projectName;
        this.dAt = dAt;
        this.DAT = DAT;
        this.priority = priority;
        this.dateCompleted = dateCompleted;


        Log.v(TAG,"Inside Task Constructor* "+this.name);
        Log.v(TAG,"Inside Task Constructor* "+this.projectName);
        Log.v(TAG,"Inside Task Constructor* "+this.dAt);
        Log.v(TAG,"Inside Task Constructor* "+this.DAT);


    }




    //Method used to convert Date obkect to integer date
    private void createDate(){

        Date date = new Date();
        SimpleDateFormat ft =
                new SimpleDateFormat ("E yyyy.MM.dd 'at' hh:mm:ss a zzz");
        Log.v(TAG,"Create Date function called "+ft.format(date));
    }



}
