package com.example.adithya.tm_v3;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Random;


public class ProjectHelper extends SQLiteOpenHelper {

    private static final String TAG = "TAG";
    private static final String TAGC = "TAGC";

    //Database version.
    //Note: Increase the database version every-time you make changes to your table structure.
    private static final int DATABASE_VERSION = 30;

    //Database Name
    private static final String DATABASE_NAME = "projectDetails";

    //You will declare all your table names here.
    private static final String TABLE_PROJECTS = "projects";
    private static final String TABLE_TASKS = "tasks";
    private static final String TABLE_ARCHIVED_TASKS = "archived_tasks";
    private static final String TABLE_HABITS = "habits";
    private static final String TABLE_CALENDAR = "calendar";
    private static final String TABLE_TIME_CHALLENGES = "timeChallenges";
    private static final String TABLE_TASK_CHALLENGES = "taskChallenges";
    private static final String TABLE_USER_ACTIVITY = "userActivity";
    private static final String TABLE_CHALLENGES = "challenges";
    private static final String TABLE_COMPLETED_CHALLENGES = "completedChallenges";
    private static final String TABLE_TEST = "testTable";
    private static final String TABLE_IMAGES = "images";

    // Students Table Columns names
    private static final String _id = "_id";

    private static final String KEY_PROJECT_NAME = "projectName";
    private static final String KEY_TASK_NAME = "taskName";
    private static final String KEY_HABIT_NAME = "habitName";
    private static final String KEY_DUE_DATE = "dueDate";
    private static final String KEY_DUE_DATE_2 = "dueDate2";
    private static final String KEY_DAT = "Date";
    //KEY_DATE_MS used to indicate date in milliseconds
    private static final String KEY_EVENT_NAME = "eventName";
    private static final String KEY_START_DATE = "startDate";
    private static final String KEY_CHALLENGE_NAME = "challengeName";
    private static final String KEY_END_DATE = "endDate";
    private static final String KEY_PRIORITY = "priority";
    private static final String KEY_STREAK = "streak";
    private static final String KEY_DATE_IN_MS = "date_in_ms";
    private static final String KEY_DATE_COMPLETED = "dateCompleted";
    //KEY_DAY used to indicate day/date of user checkin
    private static final String KEY_DAY = "details";

    private static final String KEY_IMAGE_NAME = "imageName";

    private static final String KEY_IMAGE_NO = "imageNo";
    private static final String KEY_POINTS = "points";

    private static final String KEY_CHALLENGE_TYPE = "challengeType";
    private static final String KEY_IMAGE = "keyImage";
    //check in time of user
    private static final String KEY_CHECK_IN_TIME = "checkInTime";

    //check out time of user
    private static final String KEY_CHECK_OUT_TIME = "checkOutTime";
    //total is total number of check ins required to complete the challenge
    private static final String KEY_TOTAL = "total";
    //date variable to indicate day's date
    private static final String KEY_DATE = "date";
    //data variable to see if challenges are completed
    private static final String KEY_STATUS = "status";
    private SQLiteDatabase db = this.getWritableDatabase();


    public ProjectHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    //This method will be called every-time the file is called.
    @Override
    public void onCreate(SQLiteDatabase db) {
        //Query to create table
        String CREATE_PROJECTS_TABLE = "CREATE TABLE IF NOT EXISTS "
                + TABLE_PROJECTS + "("
                + _id + " INTEGER (10) PRIMARY KEY, "
                + KEY_PROJECT_NAME + " TEXT " + ")";

        String CREATE_TASKS_TABLE = "CREATE TABLE IF NOT EXISTS "+TABLE_TASKS + "(" +_id+" INTEGER (10) PRIMARY KEY, "
                + KEY_TASK_NAME + " TEXT, " + KEY_PROJECT_NAME + " TEXT, "+ KEY_DUE_DATE + " TEXT, "+KEY_DUE_DATE_2+" TEXT, "+KEY_DAT+ " TEXT, "+KEY_DATE_COMPLETED+" TEXT, " + KEY_PRIORITY+" INTEGER (10), "+KEY_DATE_IN_MS+" INTEGER (10)"+ ")";

        String CREATE_ARCHIVED_TASKS_TABLE = "CREATE TABLE IF NOT EXISTS "+TABLE_ARCHIVED_TASKS + "(" +_id+" INTEGER (10) PRIMARY KEY, "
                + KEY_TASK_NAME + " TEXT, " + KEY_PROJECT_NAME + " TEXT, "+ KEY_DUE_DATE + " TEXT, "+KEY_DUE_DATE_2+" TEXT, "+KEY_DAT+" TEXT, "+KEY_DATE_COMPLETED+" TEXT"+")";

        String CREATE_TABLE_HABITS = "CREATE TABLE IF NOT EXISTS "+TABLE_HABITS+"("+_id+" INTEGER (10) PRIMARY KEY, "+ KEY_HABIT_NAME + " TEXT " + ")";

        String CREATE_CALENDAR_TABLE = "CREATE TABLE IF NOT EXISTS "+TABLE_CALENDAR+"("+_id+" INTEGER (10) PRIMARY KEY, "+KEY_EVENT_NAME+" TEXT, "+KEY_START_DATE+" TEXT, "+KEY_END_DATE+" TEXT "+")";

        String CREATE_TIME_CHALLENGES_TABLE="CREATE TABLE IF NOT EXISTS "+TABLE_TIME_CHALLENGES+"("+_id+" INTEGER (10) PRIMARY KEY, "+KEY_CHALLENGE_NAME+ " TEXT, " +KEY_DATE+" TEXT, "+KEY_STREAK + " INTEGER (10), "+ KEY_TOTAL+" INTEGER (10), "+KEY_STATUS+" TEXT, "+KEY_IMAGE+" BLOB"+  ")";

        String CREATE_USER_ACTIVITY_TABLE ="CREATE TABLE IF NOT EXISTS "+TABLE_USER_ACTIVITY +"(" +_id+" INTEGER (10) PRIMARY KEY, "+KEY_DAY + " TEXT, "+KEY_CHECK_IN_TIME + " INTEGER, "+KEY_CHECK_OUT_TIME + " INTEGER (10)" + ")";

        String CREATE_TASK_CHALLENGES_TABLE = "CREATE TABLE IF NOT EXISTS "+TABLE_TASK_CHALLENGES+"("+_id+" INTEGER (10) PRIMARY KEY, "+KEY_CHALLENGE_NAME+ " TEXT, " +KEY_DATE+" TEXT, "+KEY_STREAK + " INTEGER (10), "+ KEY_TOTAL+" INTEGER (10), "+KEY_STATUS+" TEXT, "+KEY_IMAGE+" BLOB"+  ")";

        String CREATE_TABLE_CHALLENGES = "CREATE TABLE IF NOT EXISTS "+TABLE_CHALLENGES+"("+_id+" INTEGER (10) PRIMARY KEY, "+KEY_CHALLENGE_NAME+ " TEXT, " +KEY_DATE+" TEXT, "+KEY_STREAK + " INTEGER (10), "+ KEY_TOTAL+" INTEGER (10), "+KEY_STATUS+" TEXT, "+KEY_CHALLENGE_TYPE+" TEXT, "+KEY_IMAGE_NO+" INTEGER (10), "+KEY_POINTS+" INTEGER (10)"  +")";

        String CREATE_TABLE_COMPLETED_CHALLENGES = "CREATE TABLE IF NOT EXISTS "+TABLE_COMPLETED_CHALLENGES+"("+_id+" INTEGER (10) PRIMARY KEY, "+KEY_CHALLENGE_NAME+ " TEXT, " +KEY_DATE+" TEXT, "+KEY_STREAK + " INTEGER (10), "+ KEY_TOTAL+" INTEGER (10), "+KEY_STATUS+" TEXT, "+KEY_CHALLENGE_TYPE+" TEXT, "+KEY_IMAGE_NO+" INTEGER (10), "+KEY_POINTS+" INTEGER (10)"  +")";

        String CREATE_IMAGES_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_IMAGES + "("+
        KEY_IMAGE_NAME + " TEXT," +
                KEY_IMAGE + " BLOB);";







        //Create table query executed in sqlite
        db.execSQL(CREATE_PROJECTS_TABLE);
        db.execSQL(CREATE_TASKS_TABLE);
        db.execSQL(CREATE_ARCHIVED_TASKS_TABLE);
        db.execSQL(CREATE_TABLE_HABITS);
        db.execSQL(CREATE_CALENDAR_TABLE);
        db.execSQL(CREATE_TIME_CHALLENGES_TABLE);
        db.execSQL(CREATE_USER_ACTIVITY_TABLE);
        db.execSQL(CREATE_TASK_CHALLENGES_TABLE);
        db.execSQL(CREATE_TABLE_CHALLENGES);
        db.execSQL(CREATE_TABLE_COMPLETED_CHALLENGES);
        db.execSQL(CREATE_IMAGES_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //This method will be called only if there is change in DATABASE_VERSION.

        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PROJECTS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TASKS);
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_ARCHIVED_TASKS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_HABITS);
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_CALENDAR);
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_TIME_CHALLENGES);
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_USER_ACTIVITY);
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_TASK_CHALLENGES);
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_CHALLENGES);
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_COMPLETED_CHALLENGES);
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_IMAGES);
        // Create tables again
        onCreate(db);
    }
    // Add New Project
    public void addProject(Project project) {
        //SQLiteDatabase db = this.getWritableDatabase();

        //Content values use KEY-VALUE pair concept
        ContentValues values = new ContentValues();
        values.put(_id, project.getId());
        values.put(KEY_PROJECT_NAME, project.getName());
        db.insert(TABLE_PROJECTS, null, values);
        Log.d(TAG,"Project Helper Project Added.");
    }

    // Add New Task
    public void addTask(Task task) {
        //SQLiteDatabase db = this.getWritableDatabase();
        //Content values use KEY-VALUE pair concept
        ContentValues values = new ContentValues();
        long date_in_ms = task.getDATE();
        values.put(_id, task.getId());
        values.put(KEY_TASK_NAME, task.getName());
        values.put(KEY_PROJECT_NAME,task.getProjectName());
        values.put(KEY_DUE_DATE,task.getdAt());
        values.put(KEY_DAT,task.getDAT());
        values.put(KEY_PRIORITY,task.getPriority());
        values.put(KEY_DATE_IN_MS,date_in_ms);
        values.put(KEY_DUE_DATE_2,task.getDueDate2());
        db.insert(TABLE_TASKS, null, values);
        Log.d(TAG,values.toString());
        //db.close();
        Log.d(TAG,"***Project Helper Task Added");

    }

    // Archive Task
    public void archiveTask(Task task) {
        //SQLiteDatabase db = this.getWritableDatabase();
        //Content values use KEY-VALUE pair concept
        ContentValues values = new ContentValues();
        values.put(_id, task.getId());
        values.put(KEY_TASK_NAME, task.getName());
        values.put(KEY_PROJECT_NAME,task.getProjectName());
        values.put(KEY_DUE_DATE,task.getdAt());
        values.put(KEY_DAT,task.getDAT());
        values.put(KEY_DUE_DATE_2,task.getDueDate2());
        values.put(KEY_DATE_COMPLETED,task.getDateCompleted());
        db.insert(TABLE_ARCHIVED_TASKS, null, values);
        Log.d(TAG,values.toString());
        //db.close();
        Log.d(TAG,"***Project Helper Task Archived");

    }

    // Add New Task
    public void addHabit(Habit habit) {
        //SQLiteDatabase db = this.getWritableDatabase();
        //Content values use KEY-VALUE pair concept
        ContentValues values = new ContentValues();
        values.put(_id, habit.getId());
        values.put(KEY_HABIT_NAME, habit.getName());
        db.insert(TABLE_HABITS, null, values);
        Log.d(TAG,values.toString());
        //db.close();
        Log.d(TAG,"***Project Helper Habit Added");
    }

    // Add New Task
    public void addEvent(Event event) {
        //SQLiteDatabase db = this.getWritableDatabase();
        //Content values use KEY-VALUE pair concept
        ContentValues values = new ContentValues();
        values.put(_id, event.getId());
        values.put(KEY_EVENT_NAME, event.getName());
        values.put(KEY_START_DATE,event.getStartDate());
        values.put(KEY_END_DATE,event.getEndDate());
        db.insert(TABLE_CALENDAR, null, values);
        Log.d(TAG,values.toString());
        //db.close();
        Log.d(TAG,"***Project Helper Event Added");
    }

    public void addUserActivity(UserActivityTracker tracker){

        ContentValues values = new ContentValues();
        values.put(_id,tracker.getId());
        values.put(KEY_DAY,tracker.getDay_and_time_of_activity());
        values.put(KEY_CHECK_IN_TIME,tracker.getCheckInDate());
        values.put(KEY_CHECK_OUT_TIME,tracker.getCheckOutDate());
        db.insert(TABLE_USER_ACTIVITY,null,values);
        Log.d(TAG,"UserActivity added!");
    }

    public void addChallenge(TimeChallenge challenge){

        ContentValues values = new ContentValues();
        values.put(_id,challenge.getId());
        values.put(KEY_CHALLENGE_NAME,challenge.getChallengeName());
        //requires fixing
        values.put(KEY_DATE,"null for now");
        values.put(KEY_STREAK,challenge.getStreak());
        values.put(KEY_TOTAL,challenge.getTotal());
        values.put(KEY_IMAGE,challenge.getImageArray());
        //requires fixing
        values.put(KEY_STATUS,"null for now");

        db.insert(TABLE_TIME_CHALLENGES,null,values);
        Log.d(TAGC,"TimeChallenge inserted");
    }

    public void addChallenge(Challenge challenge){

        ContentValues values = new ContentValues();
        values.put(_id,challenge.getId());
        values.put(KEY_CHALLENGE_NAME,challenge.getChallengeName());
        //requires fixing
        values.put(KEY_DATE,"null for now");
        values.put(KEY_STREAK,challenge.getStreak());
        values.put(KEY_TOTAL,challenge.getTotal());
        //requies fixing
        values.put(KEY_STATUS,"null for now");

        db.insert(TABLE_TIME_CHALLENGES,null,values);
        Log.d(TAGC,"Task Challenge inserted");
    }

    public void addTaskChallenge(Challenge challenge){

        ContentValues values = new ContentValues();
        values.put(_id,challenge.getId());
        values.put(KEY_CHALLENGE_NAME,challenge.getChallengeName());
        //requires fixing
        values.put(KEY_DATE,"null for now");
        values.put(KEY_STREAK,challenge.getStreak());
        values.put(KEY_TOTAL,challenge.getTotal());
        //requies fixing
        values.put(KEY_STATUS,"null for now");

        db.insert(TABLE_TASK_CHALLENGES,null,values);
        Log.d(TAGC,"Task Challenge inserted");



    }

    public void addNewChallenge(Challenge challenge){

        ContentValues values = new ContentValues();
        values.put(_id,challenge.getId());
        values.put(KEY_CHALLENGE_NAME,challenge.getChallengeName());
        //requires fixing
        values.put(KEY_DATE,"null for now");
        values.put(KEY_STREAK,challenge.getStreak());
        values.put(KEY_TOTAL,challenge.getTotal());
        //requies fixing
        values.put(KEY_STATUS,"null for now");
        values.put(KEY_CHALLENGE_TYPE,challenge.getChallengeType());
        values.put(KEY_IMAGE_NO,challenge.getImageNo());
        values.put(KEY_POINTS,challenge.getPoints());



        db.insert(TABLE_CHALLENGES,null,values);
        Log.d(TAGC,"Task Challenge inserted");

    }




    /*
    public void addChallenge(Challenge challenge){
        ContentValues values = new ContentValues();
        values.put(KEY_STREAK,challenge.getStreak());
        db.insert(TABLE_CHALLENGES,null,values);
        Log.v(TAG,values.toString());
        Log.d(TAG,"Project Helper Challenge added!");
    }
    */



    // Getting single student details through ID
    public Project getProject(int id) {

        //SQLiteDatabase db = this.getReadableDatabase();


        //You can browse to the query method to know more about the arguments.
        Cursor cursor = db.query(TABLE_PROJECTS,
                new String[] { _id, KEY_PROJECT_NAME},
                _id + "=?",
                new String[] { String.valueOf(id) },
                null,
                null,
                null,
                null);

        if (cursor != null)
            cursor.moveToFirst();

        Project project = new Project(
                Integer.parseInt(cursor.getString(0)),
                cursor.getString(1));

        Log.d(TAG,"Project Helper getProject.");
        //Return Student
        return project;
    }

    // Getting single task details through ID
    public Task getTask(int id) {

        //SQLiteDatabase db = this.getReadableDatabase();


        //You can browse to the query method to know more about the arguments.
        Cursor cursor = db.query(TABLE_TASKS,
                new String[] { _id, KEY_TASK_NAME},
                _id + "=?",
                new String[] { String.valueOf(id) },
                null,
                null,
                null,
                null);

        if (cursor != null)
            cursor.moveToFirst();

        Task task = new Task(
                Integer.parseInt(cursor.getString(0)),
                cursor.getString(1));
        Log.d(TAG,"Project Helper getTask.");
        //Return Student
        return task;
    }

    // Getting All Students
    public ArrayList<Project> getAllProjects() {
        ArrayList<Project> projectsList = new ArrayList<Project>();

        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_PROJECTS;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // Looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Project project = new Project(
                        cursor.getInt(0),
                        cursor.getString(1));

                projectsList.add(project);
            } while (cursor.moveToNext());
        }
        Log.d(TAG,"Project Helper getAllProjects.");
        // return student list
        return projectsList;
    }

    // Getting All Tasks
    public ArrayList<Task> getAllTasks() {
        ArrayList<Task> tasksList = new ArrayList<Task>();

        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_TASKS;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // Looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Task task = new Task(
                        cursor.getInt(0),
                        cursor.getString(1));

                tasksList.add(task);
            } while (cursor.moveToNext());
        }
        Log.d(TAG,"Project Helper getAllTasks.");

        // return student list
        return tasksList;
    }

    public ArrayList<Task> getTasks(String pName) {
        //SQLiteDatabase db = this.getReadableDatabase();

        ArrayList<Task> taskList = new ArrayList<Task>();
        Cursor cursor = db.query(TABLE_TASKS,
                new String[]{_id, KEY_TASK_NAME, KEY_PROJECT_NAME},
                KEY_PROJECT_NAME + "=?",
                new String[]{pName},
                null,
                null,
                null,
                null);
        //Return Student

        if(cursor.getCount()>0) {
            if (cursor != null) {
                cursor.moveToFirst();
                do {
                    // do what you need with the cursor here
                    Task task = new Task(cursor.getInt(0),
                            cursor.getString(1),
                            cursor.getString(2));
                    taskList.add(task);
                } while (cursor.moveToNext());

            }
        }
        else if(cursor.getCount()==0){
            Log.v(TAG,"Empty Cursor in tasks");
        }
        return taskList;
    }

    public String getProjectName(String taskName){

        String selectQuery = "SELECT  * FROM " + TABLE_TASKS+" WHERE "+KEY_TASK_NAME+" = ?";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, new String[]{taskName});

        String projectName = cursor.getString(1);
        return  projectName;


    }
    // Deleting single student
    public void deleteProject(int id) {

        //SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_PROJECTS, _id + " = ?",
                new String[] { String.valueOf(id) });
        //db.close();
        Log.v(TAG,"Project Helper deleteProject.");
    }

    public void deleteTasks(String projectName){
        String[] array = new String[]{projectName};
        Log.v(TAG,"Project Helper deleteTasks called! ---@!#3");
        db.delete(TABLE_TASKS,KEY_PROJECT_NAME + " = ?",array);

    }
    // Deleting single task
    public void deleteTask(int id) {

        //SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_TASKS, _id + " = ?",
                new String[] { String.valueOf(id) });
        //db.close();
        Log.v(TAG,"Project Helper deleteTask.");
    }

    public void deleteArchivedTask(int id){
        //SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_ARCHIVED_TASKS, _id + " = ?",
                new String[] { String.valueOf(id) });
        //db.close();
        Log.v(TAG,"Project Helper deleteArchivedTask.");
    }

    // Deleting single task
    public void deleteEvent(int id) {

        //SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_CALENDAR, _id + " = ?",
                new String[] { String.valueOf(id) });
        //db.close();
        Log.v(TAG,"Project Helper deleteEvent.");
    }

    public void deleteChallenge(String challengeName){

        db.delete(TABLE_CHALLENGES,KEY_CHALLENGE_NAME+" = ?",new String[]{challengeName});

        Log.v(TAGC,"Challenge deleted");


    }

    public void addChallengeWhenCompleted(String challengeName){

        deleteChallenge(challengeName);

        Cursor cursor = db.rawQuery("SELECT * FROM "+TABLE_CHALLENGES+" WHERE "+KEY_CHALLENGE_NAME+" = ?",new String[]{challengeName});
        ContentValues contentValues = new ContentValues();
        contentValues.put(_id,cursor.getInt(0));
        contentValues.put(KEY_CHALLENGE_NAME,cursor.getString(1));
        contentValues.put(KEY_DATE,cursor.getString(2));
        contentValues.put(KEY_STREAK,cursor.getInt(3));
        contentValues.put(KEY_TOTAL,cursor.getInt(4));
        contentValues.put(KEY_STATUS,cursor.getString(5));
        contentValues.put(KEY_CHALLENGE_TYPE,cursor.getString(6));
        contentValues.put(KEY_IMAGE_NO,cursor.getInt(7));
        contentValues.put(KEY_POINTS,cursor.getInt(8));

        db.insert(TABLE_COMPLETED_CHALLENGES,null,contentValues);
        Log.d(TAGC,"Task Challenge inserted");


    }



    public void updateTask(int id,Task task){
        //SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(_id,id);
        values.put(KEY_TASK_NAME,task.getName());
        values.put(KEY_PROJECT_NAME,task.getProjectName());
        values.put(KEY_DUE_DATE,task.getdAt());
        values.put(KEY_DAT,task.generateDate());
        values.put(KEY_PRIORITY,task.getPriority());
        Log.d(TAG,values.toString());
        Log.d(TAG,"***Project Helper Task Updated");
        db.update(TABLE_TASKS,values,_id +  "="+id,null);
        Log.d(TAG,"Project helper update teask called!");
        // updating student row
    }

    public void updateTask(int id){
        //SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(_id,id);
        values.put(KEY_TASK_NAME,"Updated Task");
        values.put(KEY_PROJECT_NAME,"Updated Project Name");
        values.put(KEY_DUE_DATE,"UPDATED DATE");
        values.put(KEY_DAT,"UPDATED DAT");
        Log.d(TAG,values.toString());
        Log.d(TAG,"***Project Helper Task Updated");
        db.update(TABLE_TASKS,values,_id +  "="+id,null);
        Log.d(TAG,"Project helper update teask called!");
        // updating student row
    }

    public void updateTaskOnComplete(String taskName,String taskDateCompleted){

        ContentValues values = new ContentValues();
        values.put(KEY_DATE_COMPLETED,taskDateCompleted);
        db.update(TABLE_TASKS,values,KEY_TASK_NAME+"= ?",new String[]{taskName});
        Log.v(TAG,"Task Updated! on completion!");
    }

    public void updateProject(Project project){
        //SQLiteDatabase db = this.getWritableDatabase();
        int id = project.getId();

        ContentValues values = new ContentValues();
        values.put(_id,project.getId());
        values.put(KEY_PROJECT_NAME,project.getName().toString());
        Log.d(TAG,values.toString());
        Log.d(TAG,"***Project Helper Task Updated");
        db.update(TABLE_PROJECTS,values,_id +  "="+id,null);
        Log.d(TAG,"Project helper update teask called!");
        // updating student row
    }

    public void updateTimeChallenge(int countStreak){
        Log.d(TAGC,"Inside updateTimeChallenge method body");
        String temp = "null for now";
        ContentValues values = new ContentValues();
        values.put(KEY_STREAK,countStreak);
        db.update(TABLE_TIME_CHALLENGES,values,null,null);
        Log.d(TAGC,"Project Helper updateTimeChallenge called");
    }

    public void updateTimeChallenges(int countStreak){


        Log.d(TAGC,"updateTimeChallenges called!");
        ContentValues values = new ContentValues();
        values.put(KEY_STREAK,countStreak);
        db.update(TABLE_CHALLENGES,values,KEY_CHALLENGE_TYPE +"= ?",new String[]{"timeChallenge"});
        Log.d(TAGC,"Project helper updateTimeChallenges called");


    }

    public void updateTaskChallenge(){
        int streak = getCompletedTaskCount();
        ContentValues values = new ContentValues();
        values.put(KEY_STREAK,streak);
        db.update(TABLE_TASK_CHALLENGES,values,null,null);
        Log.d(TAGC,"Project Helper updateTaskChallenge called");
    }



    public void updateTaskChallenges(){

        Log.d(TAGC,"Update task challenges called!");
        int streak = getCompletedTaskCount();

        ContentValues contentValues = new ContentValues();
        contentValues.put(KEY_STREAK,streak);
        db.update(TABLE_CHALLENGES,contentValues,KEY_CHALLENGE_TYPE +"= ?",new String[]{"taskChallenge"});
        Log.d(TAGC,"Task Challenge updated!");



    }

    public void updateTaskChallenges2(){


        int streak = getCompletedTaskCount();
        Log.d(TAGC,"updateTimeChallenges2 called!");
        ContentValues values = new ContentValues();
        values.put(KEY_STREAK,streak);
        db.update(TABLE_CHALLENGES,values,KEY_CHALLENGE_TYPE +"= ?",new String[]{"taskChallenge"});
        Log.d(TAGC,"Project helper updateTimeChallenges2 called");


    }

    public void updateDueDateChallenges(){

        int streak = getTasksCompletedBeforeDueDate1();
        ContentValues values = new ContentValues();
        values.put(KEY_STREAK,streak);
        db.update(TABLE_CHALLENGES,values,KEY_CHALLENGE_TYPE +"= ?",new String[]{"dueDateChallenge"});

    }

    public void updateTimeChallenges(){

        int streak = 48;



        Log.d(TAGC,"updateTimeChallenges called!");
        ContentValues values = new ContentValues();
        values.put(KEY_STREAK,streak);
        db.update(TABLE_CHALLENGES,values,KEY_CHALLENGE_TYPE+"= ?",new String[]{"timeChallenge"});
    }


    public int getTaskCount(){

        String countQuery = "SELECT  * FROM " + TABLE_ARCHIVED_TASKS;
        Cursor cursor = db.rawQuery(countQuery, null);
        int count1 = cursor.getCount();
        Log.d(TAG,"TABLE_TASKS "+Integer.toString(count1));

        cursor.close();
        return count1;

    }

    public int getCompletedTaskCount(){

        String countQuery = "SELECT  * FROM " + TABLE_ARCHIVED_TASKS;
        Cursor cursor = db.rawQuery(countQuery, null);
        int count = cursor.getCount();
        cursor.close();
        Log.d(TAGC,"Task Count "+count);
        return count;

    }

    public void getTasksCompletedBeforeDueDate() {


        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_ARCHIVED_TASKS, null);
        int streak = 0;
        int total = cursor.getCount();

        if (total > 0) {

            cursor.moveToFirst();


            int cmp = 0;


            do {

                String dateText1 = cursor.getString(3);
                String dateText2 = cursor.getString(6);

                if (dateText1.isEmpty()) {


                } else if (dateText2.isEmpty()) {


                } else {

                    LocalDate date1 = LocalDate.parse(dateText1);
                    LocalDate date2 = LocalDate.parse(dateText2);


                    cmp = date1.compareTo(date2);

                    Log.d(TAGC, "Compare value Brfore is " + cmp);
                    if (cmp > 0) {

                        Log.v(TAGC, "Date1 is greater than Date2");
                        //streak++;

                    } else if (cmp < 0) {

                        Log.v(TAGC, "Date1 is smaller than Date2");

                    } else if (cmp == 0) {

                        Log.v(TAGC, "Date1 is equal to Date2");
                        streak++;


                    }

                }

            }
                    while (cursor.moveToNext()) ;



                //Log.d(TAGC, "Compare value after is " + cmp);
                Log.d(TAGC, "Streak is" + streak);





        }

    }

    public int getTasksCompletedBeforeDueDate1() {


        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_ARCHIVED_TASKS, null);
        int streak = 0;
        int total = cursor.getCount();

        if (total > 0) {

            cursor.moveToFirst();


            int cmp = 0;


            do {

                String dateText1 = cursor.getString(3);
                String dateText2 = cursor.getString(6);

                if (dateText1.isEmpty()) {


                } else if (dateText2.isEmpty()) {


                } else {

                    LocalDate date1 = LocalDate.parse(dateText1);
                    LocalDate date2 = LocalDate.parse(dateText2);


                    cmp = date1.compareTo(date2);

                    Log.d(TAGC, "Compare value Brfore is " + cmp);
                    if (cmp > 0) {

                        Log.v(TAGC, "Date1 is greater than Date2");
                        streak++;

                    } else if (cmp < 0) {

                        Log.v(TAGC, "Date1 is smaller than Date2");

                    } else if (cmp == 0) {

                        Log.v(TAGC, "Date1 is equal to Date2");


                    }

                }

            }
            while (cursor.moveToNext()) ;



            //Log.d(TAGC, "Compare value after is " + cmp);
            Log.d(TAGC, "Streak is" + streak);




        }

        return streak;

    }












    //checks for challenges that are done and adds them to completed challenges table
    public void checkForChallengeComplete(){

        Cursor cursor = db.rawQuery("SELECT * FROM "+TABLE_CHALLENGES +" WHERE "+KEY_STREAK+" > "+KEY_TOTAL,null);
        Cursor cursor1 = db.rawQuery("SELECT * FROM "+TABLE_CHALLENGES,null);
        int count = cursor.getCount();
        int count1 = cursor1.getCount();

        if(count>0){


            cursor.moveToFirst();
            cursor1.moveToFirst();

            int  nameIndex = cursor.getColumnIndex(KEY_CHALLENGE_NAME);
            int streakIndex = cursor.getColumnIndex(KEY_STREAK);

            do{

                Log.d(TAGC,"***Challenge name is "+cursor.getString(nameIndex));
                Log.d(TAGC,"***Streak value is "+cursor.getInt(streakIndex));





            }while (cursor1.moveToNext());


            ContentValues values = new ContentValues();
            values.put(KEY_STATUS,"complete");
            db.update(TABLE_CHALLENGES,values,KEY_STREAK +" > "+KEY_TOTAL,null);
            Log.d(TAGC,"Project helper updateTimeChallenges2 called");


            Log.d(TAGC,"***checkForChallengeComplete count " +count);

        }


    }

    public void createCompletedChallenges(){

        Cursor cursor = db.rawQuery("SELECT * FROM "+TABLE_CHALLENGES+" WHERE "+KEY_STATUS+" = ?",new String[]{"complete"});
        cursor.moveToFirst();

        if(cursor.getCount()>0){

            ContentValues contentValues = new ContentValues();

            do{


                contentValues.put(_id,cursor.getInt(0));
                contentValues.put(KEY_CHALLENGE_NAME,cursor.getString(1));
                contentValues.put(KEY_DATE,cursor.getString(2));
                contentValues.put(KEY_STREAK,cursor.getInt(3));
                contentValues.put(KEY_TOTAL,cursor.getInt(4));
                contentValues.put(KEY_STATUS,cursor.getString(5));

                contentValues.put(KEY_CHALLENGE_TYPE,cursor.getString(6));
                contentValues.put(KEY_IMAGE_NO,cursor.getInt(7));
                contentValues.put(KEY_POINTS,cursor.getInt(8));

                //db.update(TABLE_COMPLETED_CHALLENGES,contentValues,null,null);

                db.insert(TABLE_COMPLETED_CHALLENGES,null,contentValues);

                Log.d(TAGC,"Challenge Inserted!");



            }while (cursor.moveToNext());


        }


    }

    public int getCompletedChallengesCount(){

        Cursor cursor = db.rawQuery("SELECT * FROM "+TABLE_COMPLETED_CHALLENGES,null);
        return  cursor.getCount();



    }
/*
    public void setTaskChallengeStreak(){
        int streak = getCompletedTaskCount();
        ContentValues values = new ContentValues();
        values.put(KEY_STREAK,streak);
        db.update(TABLE_TIME_CHALLENGES,values,null,null);
        Log.d(TAGC,"Project Helper updateTimeChallenge called");

    }
    */

    public int getProjectTaskCount(String projectName){


        String countQuery = "SELECT * FROM tasks WHERE projectName = "+projectName;
        String query = "SELECT *  FROM " + TABLE_TASKS + " WHERE " + KEY_PROJECT_NAME + " = " + projectName;

        Cursor cursor =

                db.rawQuery("select * from tasks where projectName = ?",new String[]{projectName});

            int count = cursor.getCount();
            Log.d(TAG,"Count "+Integer.toString(count));
            return  count;

    }

    public void insertImg(int id ,String name ,Bitmap img ) {


        byte[] data = getBitmapAsByteArray(img); // this is a function

        ContentValues cv = new  ContentValues();
        cv.put(KEY_IMAGE_NAME,    name);
        cv.put(KEY_IMAGE,   data);
        db.insert( TABLE_IMAGES, null,cv  );


    }

    public static byte[] getBitmapAsByteArray(Bitmap bitmap) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 0, outputStream);
        return outputStream.toByteArray();
    }

    public Bitmap getImage(int position){

        Cursor cursor = db.rawQuery("SELECT *"+" FROM "+TABLE_IMAGES,null);
        cursor.moveToPosition(position);
        byte[] img = cursor.getBlob(1);

        Bitmap bitmap = processImage(img);

        return bitmap;


    }

    public static Bitmap processImage(byte[] image) {
        return BitmapFactory.decodeByteArray(image, 0, image.length);
    }

    public int getImagesCount(){

        Cursor cursor = db.rawQuery("SELECT *"+" FROM "+TABLE_IMAGES,null);

        return cursor.getCount();


    }

    public int getTotalPoints(){

        int sum=0;
        String column_name = KEY_POINTS;
        String table_name = TABLE_CHALLENGES;
        db=this.getReadableDatabase();
        String sumQuery=String.format("SELECT SUM(%s) as Total FROM %s",column_name,table_name);
        Cursor cursor=db.rawQuery(sumQuery,null);
        if(cursor.moveToFirst())
            sum= cursor.getInt(cursor.getColumnIndex("Total"));

        Log.d(TAGC,"Sum of points are "+Integer.toString(sum));
        return sum;


    }

    public int getChallengePoints(){

        int sum = 0;
        String column_name = KEY_POINTS;
        String table_name = TABLE_COMPLETED_CHALLENGES;
        db=this.getReadableDatabase();
        String sumQuery=String.format("SELECT SUM(%s) as Total FROM %s",column_name,table_name);
        Cursor cursor=db.rawQuery(sumQuery,null);
        if(cursor.moveToFirst())
            sum= cursor.getInt(cursor.getColumnIndex("Total"));

        Log.d(TAGC,"Sum of points are "+Integer.toString(sum));
        return sum;


    }



}
