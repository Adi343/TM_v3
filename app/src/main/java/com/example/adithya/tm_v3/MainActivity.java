package com.example.adithya.tm_v3;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import androidx.annotation.NonNull;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;

import androidx.core.app.ActivityCompat;
import androidx.core.content.res.ResourcesCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;

import androidx.appcompat.widget.Toolbar;

import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {


    DrawerLayout mDrawerLayout;
    FloatingActionButton fab;
    Button addProjectButton;
    ProjectHelper handler;
    ListView listView;
    LinearLayout emptyView;

    private static final String TAG = "TAG";
    private static final String TAGC = "TAGC";
    private static final String TAGD = "TAGD";
    private int id = 0;
    //ImageButton extra_button;
    String projectName;

    SQLiteDatabase db;
    Cursor cursor;
    ProjectCursorAdapter adapter;
    ProjectArrayAdapter Adapter;
    ArrayList<Project> list_of_projects;
    CoordinatorLayout coordinatorLayout;
    UserActivityTracker tracker;
    String checkTaskChallenges;
    private static final String ChallengePreferences = "ChallengePreferences";
    private static final String challenges_created_1 ="challenges_created_1";
    private static final String player_level = "player_level";
    private static final String player_points ="player_points";
    private static final String level1_challenges = "level1_challenges";
    private static final String level2_challenges = "level2_challenges";
    private static final String level3_challenges = "level3_challenges";
    Date date = new Date();
    long check_in_date;
    private static final String AppPreferences = "AppPreferences";
    private static final String dateCheck = "date";
    private static final String streak = "streak";
    SharedPreferences sharedPreferences,sharedPreferences1;
    SharedPreferences.Editor editor;
    //streakCount denotes number of days user has used the app
    int streakCount;
    LocalDate today;
    Player player;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
        setContentView(R.layout.activity_main);


        today = LocalDate.now();
        Log.d(TAGC, "Local date :" + today);
//        Typeface typeface = Typeface.createFromAsset(getApplicationContext().getAssets(),"fonts/Roboto-Regular.ttf");
        handler = new ProjectHelper(this);
        coordinatorLayout = (CoordinatorLayout) findViewById(R.id.coordinatorLayout);
        //extra_button = (ImageButton) findViewById(R.id.extra_button);
        //FAB code
        //fab = (FloatingActionButton)findViewById(R.id.fab);
        addProjectButton = (Button) findViewById(R.id.fab);
        //      addProjectButton.setTypeface(typeface);
        emptyView = (LinearLayout) findViewById(R.id.emptyView);
        addProjectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createProjectDialog();
            }
        });


        //ToolBar Code
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionbar = getSupportActionBar();
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setHomeAsUpIndicator(R.drawable.ic_menu);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("TaskM");

        //DrawerLayout Code
        mDrawerLayout = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        //SharedPreferences
        sharedPreferences = getSharedPreferences(AppPreferences, Context.MODE_PRIVATE);
        sharedPreferences1 = getSharedPreferences(ChallengePreferences,Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        //new code
        editor.putString(challenges_created_1,"false");

        checkLevel();
        //checkPoints();
        //editor.putString(dateCheck, today.toString());
        streakCount = sharedPreferences.getInt(streak, 0);

        //check() checks for user using the app on the same day or different dau.
        check();

        checkTaskChallenges = sharedPreferences.getString(challenges_created_1,null);

        Log.d(TAGC,"Value of checkTaskChallenges is "+checkTaskChallenges);
        if(checkTaskChallenges.contentEquals("true")){
            Log.d(TAGC,"Task Challenges  already created!");

        }

        else {
            Log.d(TAGC,"Task Challenges  not created");
            //createTaskChallenges_1();
            createLevel1Challenges();

        }
        //updateTimeChallengeStreak();
        //UserActicityTracker initialization

        check_in_date = date.getTime();
        //tracker = new UserActivityTracker("test");
        Log.d(TAG, "OnCreate() checkInDate =" + Long.toString(check_in_date));
        //ListView Code
        listView = (ListView) findViewById(R.id.list_of_projects);
        db = handler.getWritableDatabase();
        cursor = db.rawQuery("SELECT  * FROM projects", null);
        adapter = new ProjectCursorAdapter(this, cursor);
        //getListData();
        //Adapter modification ***
        listView.setAdapter(adapter);
        listView.setEmptyView(emptyView);
        handler.updateTaskChallenges2();
        handler.getTasksCompletedBeforeDueDate();
        handler.updateTimeChallenge(streakCount);
        handler.updateDueDateChallenges();
        handler.checkForChallengeComplete();
        handler.createCompletedChallenges();
        //handler.updateTimeChallenges();
        //Log.v(TAG,"DueDate Streak"+handler.getTasksCompletedBeforeDueDate());

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //Toast.makeText(getApplicationContext(),position+"Clicked",Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(MainActivity.this, TaskViewActivity.class);
                Cursor cursor1 = adapter.getCursor();
                cursor1.moveToPosition(position);

                projectName = cursor1.getString(cursor1.getColumnIndexOrThrow("projectName"));

                //Adapter modification ***
                intent.putExtra("position", position);
                intent.putExtra("project_name", projectName);
                Toast.makeText(getApplicationContext(), projectName, Toast.LENGTH_SHORT).show();

                startActivity(intent);
            }
        });
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Cursor cursor1 = adapter.getCursor();
                cursor1.moveToPosition(position);
                int id1 = cursor1.getInt(cursor1.getColumnIndexOrThrow("_id"));
                String projectName = cursor1.getString(cursor1.getColumnIndexOrThrow("projectName"));
                Toast.makeText(getApplicationContext(), "Long press" + position, Toast.LENGTH_SHORT).show();
                Project project = new Project(id1, projectName);
                EditprojectDialog(position, id1, projectName, project);
                return true;
            }
        });

        Log.d(TAG, "onCreate() MainActivity called!");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "onStop() MainActivity called");
        /*
        Log.d(TAG, "Check in Date =" + Long.toString(tracker.getCheckInDate()));
        Log.d(TAG, "Check Out Date =" + Long.toString(tracker.getCheckOutDate()));
        Log.d(TAG, "Tracker id =" + Long.toString(tracker.getId()));

        */
        ProjectHelper projectHelper = new ProjectHelper(getApplicationContext());



        Log.d(TAG, "Project helper " + projectHelper.toString());

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy() MainActivity called");/*
        tracker.setCheckInDate(check_in_date);
        tracker.setCheckOutDate(date.getTime());
        Log.d(TAG,tracker.getDay_and_time_of_activity());

        Log.d(TAG,"Check in Date ="+Long.toString(tracker.getCheckInDate()));
        Log.d(TAG,"Check Out Date ="+Long.toString(tracker.getCheckOutDate()));
        Log.d(TAG,"Tracker id ="+Long.toString(tracker.getId()));
        ProjectHelper projectHelper = new ProjectHelper(getApplicationContext());
        if(tracker!=null){
            projectHelper.addUserActivity(tracker);
        }

        else{
            Log.d(TAG,"tracker is null");
        }


        Log.d(TAG,"Project helper "+projectHelper.toString());

        */

    }


    //Navigation Drawer Button Code


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // Handle navigation view item clicks here.
        switch (item.getItemId()) {

            case R.id.you: {
                Intent intent = new Intent(getApplicationContext(), AccountActivity.class);
                startActivity(intent);
                Toast.makeText(getApplicationContext(), "Profile Menu Item", Toast.LENGTH_SHORT).show();
                break;
            }

            case R.id.today:{
                Intent intent = new Intent(getApplicationContext(), TodayActivity.class);
                startActivity(intent);
                Toast.makeText(MainActivity.this,"In Progress! stay patient",Toast.LENGTH_SHORT).show();
                break;
            }


            case R.id.inbox:{

                Intent intent = new Intent(getApplicationContext(),InboxActivity.class);
                startActivity(intent);
                Toast.makeText(MainActivity.this,"",Toast.LENGTH_SHORT).show();

                break;
            }



            case R.id.projects: {
                Toast.makeText(getApplicationContext(), "Profile Menu Item", Toast.LENGTH_SHORT).show();
                break;
            }

            case R.id.challenges: {
                Intent intent = new Intent(getApplicationContext(), ChallengesActivity.class);
                Log.v(TAG, "Challenges Activity Launched!");
                startActivity(intent);
                Toast.makeText(getApplicationContext(), "Challenges Menu Item", Toast.LENGTH_SHORT).show();

                break;
            }


            case R.id.settings: {
                Intent intent = new Intent(getApplicationContext(), SettingsActivity.class);
                Log.v(TAG, "Settings Activity Launched!");
                startActivity(intent);
                Toast.makeText(getApplicationContext(), "Settings Menu Item", Toast.LENGTH_SHORT).show();
                break;
            }



        }
        //close navigation drawer
        mDrawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }


    private void createProjectDialog() {
        AlertDialog.Builder alertbox = new AlertDialog.Builder(new ContextThemeWrapper(this, R.style.MyAlertDialogTheme));

        int black = Color.parseColor("#ffc30b");
        LinearLayout ll_alert_layout = new LinearLayout(this);
        ll_alert_layout.setOrientation(LinearLayout.VERTICAL);
        final EditText ed_input = new EditText(this);
        ed_input.setSingleLine();
        ed_input.setTextColor(getColor(R.color.yellow));
        ed_input.setTextColor(black);


        ll_alert_layout.addView(ed_input);


        alertbox.setTitle("Enter Project Name");
        //alertbox.setMessage("Enter your text");

        //setting linear layout to alert dialog

        LinearLayout layout = (LinearLayout) findViewById(R.id.ll_alert_layout);

        alertbox.setView(ll_alert_layout);


        alertbox.setNegativeButton("CANCEL",
                new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface arg0, int arg1) {

                        // will automatically dismiss the dialog and will do nothing

                    }
                });


        alertbox.setPositiveButton("OK",
                new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface arg0, int arg1) {

                        String input_text = ed_input.getText().toString();
                        ArrayList<String> previous_projects = new ArrayList<String>();
                        String temp = "";

                        if(input_text.equals(temp)){

                            Toast.makeText(getApplicationContext(), "Enter project Name", Toast.LENGTH_SHORT).show();
                            Log.d(TAG,"Empty project added");
                            return;



                        }

                        //New Code

                        Cursor cursor = adapter.getCursor();

                        cursor.moveToFirst();
                        if (cursor.getCount() > 0) {
                            do {
                                String name_of_project = cursor.getString(cursor.getColumnIndexOrThrow("projectName"));
                                previous_projects.add(name_of_project);
                                Log.d(TAG, "Same Name Check!" + name_of_project);
                            } while (cursor.moveToNext());


                        }


                        //boolean same_projects used to indicate if there is a same project.


                        if (previous_projects.contains(input_text)) {
                            Log.d(TAG, "Same project name exists");
                            Toast.makeText(getApplicationContext(), "Same project Name exists", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(getApplicationContext(), input_text, Toast.LENGTH_SHORT).show();
                            addProject(input_text);
                            refreshListView3();
                        }




                        //End New Code


                        // do your action with input string

                    }
                });
        alertbox.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(getApplicationContext(), "Cancel", Toast.LENGTH_SHORT).show();
            }
        });
        alertbox.show();
    }

    private void projectDeleteDialog(final int position, final int id1, String name) {
        AlertDialog.Builder alertbox = new AlertDialog.Builder(this);
        LinearLayout ll_alert_layout = new LinearLayout(this);
        ll_alert_layout.setOrientation(LinearLayout.VERTICAL);
        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.project_edit_dialog, null);
        final EditText projectName = (EditText) view.findViewById(R.id.taskName);
        projectName.setText(name);
        Cursor cursor = adapter.getCursor();
        cursor.moveToPosition(position);
        final int id = cursor.getInt(cursor.getColumnIndexOrThrow("_id"));
        //alertbox.setMessage("Enter your text");

        //setting linear layout to alert dialog

        alertbox.setView(view);
        alertbox.setTitle("Edit Project!");

        alertbox.setPositiveButton("OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                        Toast.makeText(getApplicationContext(),
                                "OK was clicked",
                                Toast.LENGTH_SHORT).show();
                        Project project = Adapter.getItem(position);

                        Adapter.remove(project);
                        Adapter.notifyDataSetChanged();
                        deleteProject(project.getId());
                        refreshListView3();
                    }
                });
        alertbox.setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(getApplicationContext(),
                        android.R.string.no, Toast.LENGTH_SHORT).show();
            }
        });
        alertbox.setNeutralButton("Delete", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Log.v(TAG, "Delete Project Pressed!");
                deleteProject(id1);
                refreshListView3();
            }
        });
        alertbox.setCancelable(false);
        alertbox.show();

    }

    private void EditprojectDialog(final int position, final int id1, String name, final Project project) {
        AlertDialog.Builder alertbox = new AlertDialog.Builder(this);
        LinearLayout ll_alert_layout = new LinearLayout(this);
        ll_alert_layout.setOrientation(LinearLayout.VERTICAL);
        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.project_edit_dialog, null);
        final EditText projectName = (EditText) view.findViewById(R.id.taskName);
        projectName.setText(name);
        Cursor cursor = adapter.getCursor();
        cursor.moveToPosition(position);
        final int id = cursor.getInt(cursor.getColumnIndexOrThrow("_id"));
        //alertbox.setMessage("Enter your text");

        //setting linear layout to alert dialog

        alertbox.setView(view);
        alertbox.setTitle("Edit Project!");

        alertbox.setPositiveButton("OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        project.setName(projectName.getText().toString());
                        handler.updateProject(project);
                        refreshListView3();
                    }
                });
        alertbox.setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(getApplicationContext(),
                        android.R.string.no, Toast.LENGTH_SHORT).show();
            }
        });
        alertbox.setNeutralButton("Delete", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Log.v(TAG, "Delete Project Pressed!");
                adapter.getCursor().moveToPosition(position);
                Cursor cursor1 = adapter.getCursor();
                int id1 = cursor1.getInt(cursor1.getColumnIndexOrThrow("_id"));
                String projectName = cursor1.getString(cursor1.getColumnIndexOrThrow("projectName"));
                final Project project = new Project(id1, projectName);
                handler.deleteProject(id1);
                handler.deleteTasks(projectName);
                refreshListView3();


                Snackbar snackbar = Snackbar
                        .make(coordinatorLayout, "Project is Deleted", Snackbar.LENGTH_LONG)
                        .setAction("UNDO", new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                handler.addProject(project);
                                refreshListView3();
                                Snackbar snackbar1 = Snackbar.make(coordinatorLayout, "Project is restored!", Snackbar.LENGTH_SHORT);
                                snackbar1.show();
                            }
                        });

                snackbar.show();
            }
        });
        alertbox.setCancelable(false);
        alertbox.show();

    }

    //Database Code
    private void addProject(String name) {
        Random random = new Random();
        int id = random.nextInt(1000);
        ProjectHelper helper = new ProjectHelper(getApplicationContext());
        helper.addProject(new Project(id, name));

        //handler.addTask(new Task(id,name));

    }

    private void deleteProject(int id) {
        handler.deleteProject(id);
        Log.v(TAG, " " + projectName);
        handler.deleteTasks(projectName);
    }

    private void refreshListView3() {


        Cursor cursor1 = db.rawQuery("SELECT * FROM projects", null);
        adapter.changeCursor(cursor1);
        Log.v(TAG, "refreshListView Called!");


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.projects_toolbar_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {

            case android.R.id.home:
            mDrawerLayout.openDrawer(GravityCompat.START);
            return true;

            case R.id.share:
                //createShareDialog();
                int sum = handler.getChallengePoints();
                Toast.makeText(getApplicationContext(),"Total points are "+Integer.toString(sum),Toast.LENGTH_SHORT).show();
                return true;

            case R.id.myProfile:

                //Toast.makeText(getApplicationContext(),"Profile Clicked",Toast.LENGTH_SHORT).show();
                //createAccountDialog();
                printLevel();

                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void updateTimeChallengeStreak() {

        int temp = streakCount + 1;
        Log.d(TAGC, "Streak Count value:" + streakCount);

        if (streakCount == 0) {
            Log.d(TAGC, "First day");
            editor.putInt(streak, temp);
        } else {

            editor.putInt(streak, temp);
            Log.d(TAGC, "Temp value: " + temp);
        }


        editor.commit();

    }

    private void check() {


        String date_today = today.toString();
        Log.d(TAGC, "Date today " + date_today);
        String date_stored = sharedPreferences.getString(dateCheck, null);
        Log.d(TAGC, "Date stored " + date_stored);



        if (date_stored == null) {

            Log.d(TAGC, "date_stored is null");
            editor.putString(dateCheck, date_today);
            editor.putString(challenges_created_1,"false");
            editor.commit();

            return;
        }

        if(date_stored.isEmpty()){

            Log.d(TAGC, "date_stored is null");
            editor.putString(dateCheck, date_today);
            editor.putString(challenges_created_1,"false");
            editor.commit();

        }

        else {
            if (date_stored.equals(date_today)) {


                Log.d(TAGC, "Same day no change");
                Log.d(TAGC, "Streak count value :" + streakCount);
            } else {
                streakCount++;
                Log.d(TAGC, "StreakCount updated :" + streakCount);
                editor.putInt(streak, streakCount);
                Log.d(TAGC,"***Update TimeChallenges called!");
                handler.updateTimeChallenges(streakCount);
                handler.updateTaskChallenges2();
                handler.updateDueDateChallenges();
            }
            editor.putString(challenges_created_1,"true");

        }


        editor.commit();
        ProjectHelper helper = new ProjectHelper(getApplicationContext());
        helper.updateTimeChallenges(streakCount);
        helper.updateTaskChallenges2();
        helper.updateDueDateChallenges();

    }

    private void checkLevel(){

        int level = sharedPreferences.getInt(player_level,0);
        int points = handler.getChallengePoints();


        Log.d(TAGC, "Player Level " + Integer.toString(level));

        if(points>35 && points<70){

            String check = sharedPreferences.getString(level2_challenges,"");

            if(check ==""){

                createLevel2Challenges();
                editor.putString(level2_challenges,"true");
            }


        }

        else if(points>70 && points<105){

            String check = sharedPreferences.getString(level3_challenges,"");

            if(check ==""){

                createLevel3Challenges();
                editor.putString(level3_challenges,"true");

            }


        }

        if(level == 0){

            editor.putInt(player_level,1);
            Log.d(TAGD,"First boot");
            Log.d(TAGD, "Player Level After if loop" + Integer.toString(sharedPreferences.getInt(player_level,0)));

        }

        if(level>0){
            Log.d(TAGD,"Welcome");
            Log.d(TAGD,"Player level "+Integer.toString(level));
        }

        editor.commit();
        Toast.makeText(getApplicationContext(),"checkLevel called",Toast.LENGTH_SHORT).show();

    }

    private void checkPoints(){

        int points = handler.getChallengePoints();

        if(points>35 && points<70){
            createLevel2Challenges();
        }

        else if(points>70 && points<105){
            createLevel3Challenges();

        }


    }

    private int getLevel(){

        int level = sharedPreferences.getInt(player_level,0);
        return level;
    }

    private void updateLevel(){

        int points = handler.getTotalPoints();
        int level;

        if(points>50 && points<100){

            editor.putInt(player_level,1);

        }




    }

    private void createTaskChallenges_1(){

        int level = sharedPreferences.getInt(player_level,0);
        Log.d(TAGC, "Player Level " + Integer.toString(level));

        if(level==1){

            //Challenge challenge1 = new Challenge("Complete 10 tasks!",10);
            //Challenge challenge2 = new Challenge("Complete 20 tasks!",20);
            //Challenge challenge3 = new Challenge("Complete 30 tasks!",30);
            Challenge challenge7 = new Challenge("complete 123 tasks!",123,"taskChallenge",0,20);
            Challenge challenge8 = new Challenge("complete 73 tasks!",73,"taskChallenge",1,20);
            Challenge challenge9 = new Challenge("complete 17 tasks!",17,"taskChallenge",2,10);
            Challenge challenge10 = new Challenge("Use TaskM for 10 days",10,"timeChallenge",3,10);
            Challenge challenge11 = new Challenge("Use TaskM for 15 days",15,"timeChallenge",4,10);






            //TimeChallenge challenge4 = new TimeChallenge("Use TaskM for 10 days!",0,10);
            //TimeChallenge challenge5 = new TimeChallenge("Use TaskM for 20 days!",0,20);
            //TimeChallenge challenge6 = new TimeChallenge("Use TaskM for 30 days!",0,30);



            //handler.addTaskChallenge(challenge1);
            //handler.addTaskChallenge(challenge2);
            //handler.addTaskChallenge(challenge3);

            //handler.addChallenge(challenge4);
            //handler.addChallenge(challenge5);
            //handler.addChallenge(challenge6);


            handler.addNewChallenge(challenge7);
            handler.addNewChallenge(challenge8);
            handler.addNewChallenge(challenge9);
            handler.addNewChallenge(challenge10);
            handler.addNewChallenge(challenge11);



        }


        editor.putString(challenges_created_1,"true");
        editor.commit();
    }



    private void createLevel1Challenges(){

        int level = sharedPreferences.getInt(player_level,0);
        Log.d(TAGC, "Player Level " + Integer.toString(level));

        if(level==1){


            Challenge challenge1 = new Challenge("complete 20 tasks!",2,"taskChallenge",0,60);
            Challenge challenge2 = new Challenge("complete 15 tasks before due date",15,"dueDateChallenge",1,30);
            Challenge challenge3 = new Challenge("Use TaskM for 5 days",10,"timeChallenge",3,10);
            Challenge challenge4 = new Challenge("Use TaskM for 10 days",15,"timeChallenge",4,15);



            handler.addNewChallenge(challenge1);
            handler.addNewChallenge(challenge2);
            handler.addNewChallenge(challenge3);
            handler.addNewChallenge(challenge4);

        }


        editor.putString(challenges_created_1,"true");
        editor.commit();


    }




    private void createLevel2Challenges(){

        int level = sharedPreferences.getInt(player_level,0);
        Log.d(TAGC, "Player Level " + Integer.toString(level));


            //Challenge challenge1 = new Challenge("Complete 10 tasks!",10);
            //Challenge challenge2 = new Challenge("Complete 20 tasks!",20);
            //Challenge challenge3 = new Challenge("Complete 30 tasks!",30);
            Challenge challenge1 = new Challenge("complete 40 tasks!",50,"taskChallenge",0,20);
            Challenge challenge2 = new Challenge("complete 30 tasks before due date",30,"dueDateChallenge",1,30);
            Challenge challenge3 = new Challenge("Use TaskM for 15 days",20,"timeChallenge",3,10);
            Challenge challenge4 = new Challenge("Use TaskM for 20 days",25,"timeChallenge",4,15);



            handler.addNewChallenge(challenge1);
            handler.addNewChallenge(challenge2);
            handler.addNewChallenge(challenge3);
            handler.addNewChallenge(challenge4);





        editor.putString(challenges_created_1,"true");
        editor.commit();

        Log.d(TAGC,"Level2 challenges created");


    }


    private void createLevel3Challenges(){

        int level = sharedPreferences.getInt(player_level,0);
        Log.d(TAGC, "Player Level " + Integer.toString(level));



            //Challenge challenge1 = new Challenge("Complete 10 tasks!",10);
            //Challenge challenge2 = new Challenge("Complete 20 tasks!",20);
            //Challenge challenge3 = new Challenge("Complete 30 tasks!",30);
            Challenge challenge1 = new Challenge("complete 75 tasks!",75,"taskChallenge",0,20);
            Challenge challenge2 = new Challenge("complete 40 tasks before due date",40,"dueDateChallenge",1,30);
            Challenge challenge3 = new Challenge("Use TaskM for 20 days",20,"timeChallenge",3,10);
            Challenge challenge4 = new Challenge("Use TaskM for 25 days",25,"timeChallenge",4,15);



            handler.addNewChallenge(challenge1);
            handler.addNewChallenge(challenge2);
            handler.addNewChallenge(challenge3);
            handler.addNewChallenge(challenge4);




        editor.putString(challenges_created_1,"true");
        editor.commit();

        Log.d(TAGC,"Level3 challenges created");


    }





    private void createShareDialog(){

        Intent intent = new Intent(Intent.ACTION_SEND);

// Always use string resources for UI text.
// This says something like "Share this photo with"
        String title = "Hello World";
// Create intent to show chooser
        Intent chooser = Intent.createChooser(intent, title);

// Verify the intent will resolve to at least one activity
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(chooser);
        }
    }

    private void createAccountDialog() {
        AlertDialog.Builder alertbox = new AlertDialog.Builder(new ContextThemeWrapper(this, R.style.MyAlertDialogTheme));

        LinearLayout ll_alert_layout = new LinearLayout(this);
        ll_alert_layout.setOrientation(LinearLayout.VERTICAL);
        final EditText ed_input = new EditText(this);
        ed_input.setHint("Enter your name");

        ed_input.setSingleLine();
        ed_input.setTextColor(getColor(R.color.yellow));
        ll_alert_layout.addView(ed_input);


        alertbox.setTitle("Account");
        //alertbox.setMessage("Enter your text");

        //setting linear layout to alert dialog

        alertbox.setView(ll_alert_layout);


        alertbox.setNegativeButton("CANCEL",
                new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface arg0, int arg1) {

                        // will automatically dismiss the dialog and will do nothing

                    }
                });


        alertbox.setPositiveButton("OK",
                new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface arg0, int arg1) {




                    }
                });
        alertbox.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(getApplicationContext(), "Cancel", Toast.LENGTH_SHORT).show();
            }
        });
        alertbox.show();
    }

    private void printLevel(){

        Toast.makeText(getApplicationContext(),"Level is "+Integer.toBinaryString(getLevel()),Toast.LENGTH_SHORT).show();

    }

}