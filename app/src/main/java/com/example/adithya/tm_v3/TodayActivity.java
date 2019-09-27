package com.example.adithya.tm_v3;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;

import java.time.LocalDate;
import java.util.Date;

public class TodayActivity extends AppCompatActivity {

    ListView list_of_tasks;
    TaskCursorAdapter Adapter;
    TextView emptyView, dateView;
    SQLiteDatabase db;
    CoordinatorLayout coordinatorLayout;
    LinearLayout emptyView1;
    ProjectHelper handler1;
    private static final String TAG = "TAG";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_today);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        coordinatorLayout = findViewById(R.id.coordinatorLayout);
        //ActionBar actionbar = getSupportActionBar();
        //actionbar.setHomeAsUpIndicator(R.drawable.ic_arrow_back_black_48dp);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow_back_black_24dp);
        //setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Today");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        list_of_tasks = (ListView) findViewById(R.id.list_of_tasks);
        emptyView1 = (LinearLayout)findViewById(R.id.emptyView);
        dateView = (TextView) findViewById(R.id.date);
        list_of_tasks.setEmptyView(emptyView1);
        handler1 = new ProjectHelper(this);
        db = handler1.getWritableDatabase();
        setListData();



    }



    //Back button code
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    //Database Code
    private void setListData() {
        Log.d(TAG, "TodayActivity setListData called");
        Date date = new Date();
        LocalDate today_date = LocalDate.now();
        String temp = today_date.toString();

        int day = today_date.getDayOfMonth();
        int month = today_date.getMonthValue();
        int year = today_date.getYear();

        Log.v(TAG, "Day " + day);
        Log.v(TAG, "Month " + month);
        Log.v(TAG, "Year " + year);

        String temp1 = year + "-" + month + "-" + day;


        dateView.setText("dueDate *" + temp + "*");
        Log.d(TAG, "TodayActivity Today  Date is *" + temp + "*");
        Cursor cursor1 = db.query("tasks",
                new String[]{"_id", "taskName", "projectName", "dueDate", "Date", "priority", "date_in_ms"},
                "dueDate" + "=?",
                new String[]{String.valueOf(temp)},
                null,
                null,
                null,
                null);

        handler1 = new ProjectHelper(this);
        Adapter = new TaskCursorAdapter(getApplicationContext(), cursor1);
        list_of_tasks.setAdapter(Adapter);

        list_of_tasks.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, final View view, int position, long id) {

                Log.v(TAG, "ListView Item clicked(position) " + position);
                //Toast.makeText(getApplicationContext(), adapter.getItem(position).getName(), Toast.LENGTH_SHORT).show();
                //adapter.remove(array_tasks.remove(position));


                Adapter.getCursor().moveToPosition(position);
                Cursor cursor1 = Adapter.getCursor();
                final int id1 = cursor1.getInt(cursor1.getColumnIndexOrThrow("_id"));
                String taskName = cursor1.getString(cursor1.getColumnIndexOrThrow("taskName"));
                String dueDate = cursor1.getString(cursor1.getColumnIndexOrThrow("dueDate"));
                String date = cursor1.getString(cursor1.getColumnIndexOrThrow("Date"));
                String pName = cursor1.getString(cursor1.getColumnIndexOrThrow("projectName"));
                int priority = cursor1.getInt(cursor1.getColumnIndexOrThrow("priority"));
                LocalDate dateCompleted = LocalDate.now();
                String dateCompletedTemp = dateCompleted.toString();
                final Task task = new Task(id1, taskName, pName, dueDate, date, priority, dateCompletedTemp);

                Log.v(TAG,"Task name"+taskName);
                Log.v(TAG,"DueDate"+dueDate);
                Log.v(TAG,"Project Name "+pName);

                //Adapter.setCheck();
                final CheckBox checkBox = view.findViewById(R.id.checkBox);
                checkBox.setChecked(true);

                LocalDate date1 = LocalDate.now();
                String temp = date1.toString();
                handler1.updateTaskOnComplete(taskName, temp);
                handler1.deleteTask(id1);
                handler1.archiveTask(task);

                Animation animation1 = new AlphaAnimation(0.3f,1.0f);
                animation1.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        Snackbar snackbar = Snackbar
                                .make(coordinatorLayout, "Task is Completed", Snackbar.LENGTH_LONG)
                                .setAction("UNDO", new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        handler1.addTask(task);
                                        handler1.deleteArchivedTask(id1);
                                        setListDataOnComplete();
                                        //Checkbox showing checked after restoring....
                                        checkBox.setChecked(false);
                                        Snackbar snackbar1 = Snackbar.make(coordinatorLayout, "Task is restored!", Snackbar.LENGTH_SHORT);
                                        snackbar1.show();
                                    }
                                });

                        snackbar.show();
                        //nackbar.show();
                        handler1.deleteTask(id1);
                        handler1.archiveTask(task);
                        setListDataOnComplete();

                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }
                });

                animation1.setDuration(750);
                view.startAnimation(animation1);




            }
        });


    }

    private void setListDataOnComplete(){


        LocalDate today_date = LocalDate.now();
        String temp = today_date.toString();

        int day = today_date.getDayOfMonth();
        int month = today_date.getMonthValue();
        int year = today_date.getYear();

        Log.v(TAG, "Day " + day);
        Log.v(TAG, "Month " + month);
        Log.v(TAG, "Year " + year);

        String temp1 = year + "-" + month + "-" + day;


        dateView.setText("dueDate *" + temp1 + "*");
        Log.d(TAG, "TodayActivity Today  Date is *" + temp + "*");
        Cursor cursor1 = db.query("tasks",
                new String[]{"_id", "taskName", "projectName", "dueDate", "Date", "priority", "date_in_ms"},
                "dueDate" + "=?",
                new String[]{String.valueOf(temp1)},
                null,
                null,
                null,
                null);

        handler1 = new ProjectHelper(this);
        Adapter = new TaskCursorAdapter(getApplicationContext(), cursor1);
        list_of_tasks.setAdapter(Adapter);
    }

}
