package com.example.adithya.tm_v3;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Random;

public class InboxActivity extends AppCompatActivity {


    String TAG = "TAG";
    String TAGD = "TAGD";
    ListView list_of_tasks;
    FloatingActionButton fab;
    TextView emptyView;
    ProjectHelper handler1;
    SQLiteDatabase db;
    Cursor cursor;
    TaskCursorAdapter Adapter;
    CoordinatorLayout coordinatorLayout;
    Date getTime = new Date();
    View emptyView1;
    LinearLayout layout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inbox);



        coordinatorLayout = (CoordinatorLayout)findViewById(R.id.coordinatorLayout);
        handler1 = new ProjectHelper(InboxActivity.this);
        db = handler1.getWritableDatabase();
        list_of_tasks = (ListView)findViewById(R.id.list_of_tasks);
        emptyView = (TextView)findViewById(R.id.emptyView);
        layout = (LinearLayout)findViewById(R.id.emptyView1);
        list_of_tasks.setEmptyView(layout);
        fab = (FloatingActionButton)findViewById(R.id.fab);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                createTaskDialog3();

            }
        });

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //ActionBar actionbar = getSupportActionBar();
        //actionbar.setHomeAsUpIndicator(R.drawable.ic_arrow_back_black_48dp);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow_back_black_24dp);
        //setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Inbox");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        setListData();

        //list_of_tasks onItemClickListener
        list_of_tasks.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, final View view, int position, long id) {

                Log.v(TAG,"ListView Item clicked(position) "+position);
                //Toast.makeText(getApplicationContext(), adapter.getItem(position).getName(), Toast.LENGTH_SHORT).show();
                //adapter.remove(array_tasks.remove(position));
                Adapter.getCursor().moveToPosition(position);
                Cursor cursor1 = Adapter.getCursor();
                final int id1 = cursor1.getInt(cursor1.getColumnIndexOrThrow("_id"));
                String taskName = cursor1.getString(cursor1.getColumnIndexOrThrow("taskName"));
                String dueDate = cursor1.getString(cursor1.getColumnIndexOrThrow("dueDate"));
                //String tempDate = cursor1.getString(cursor1.getColumnIndexOrThrow("dueDate2"));
                String date = cursor1.getString(cursor1.getColumnIndexOrThrow("Date"));
                int priority = cursor1.getInt(cursor1.getColumnIndexOrThrow("priority"));

                LocalDate dateCompleted = LocalDate.now();
                String dateCompletedTemp  =  dateCompleted.toString();
                final Task task = new Task(id1, taskName, "inbox", dueDate, date,priority,dateCompletedTemp);
                //Adapter.setCheck();
                final CheckBox checkBox = view.findViewById(R.id.checkBox);
                checkBox.setChecked(true);
                LocalDate date1 = LocalDate.now();
                String temp = date1.toString();
                handler1.updateTaskOnComplete(taskName,temp);
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
                                        refreshListView3();
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
                        refreshListView3();

                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }
                });

                animation1.setDuration(750);
                view.startAnimation(animation1);

                /*
                checkBox.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        checkBox.setChecked(true);
                    }
                });
                */


                //Snackbar snackbar = Snackbar
                //      .make(coordinatorLayout, "I'm Awweesoome", Snackbar.LENGTH_LONG);


                //handler1.deleteTask(array_tasks.get(position).getName());
            }
        });
        list_of_tasks.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

                Adapter.getCursor().moveToPosition(position);
                Cursor cursor1 = Adapter.getCursor();
                int id1 = cursor1.getInt(cursor1.getColumnIndexOrThrow("_id"));
                String name_of_task = cursor1.getString(cursor1.getColumnIndexOrThrow("taskName"));
                String det = cursor1.getString(cursor1.getColumnIndexOrThrow("dueDate"));
                String dat = cursor1.getString(cursor1.getColumnIndexOrThrow("Date"));
                int priority = cursor1.getInt(cursor1.getColumnIndexOrThrow("priority"));
                //Task task = new Task(id,name_of_task,pName,det);
                Task task = new Task(id1, name_of_task, "inbox", det, dat,priority);
                Log.d(TAG, name_of_task);
                longClickAlertDialog(position,id1, name_of_task, det, task);
                return true;
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.tasks_toolbar_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case android.R.id.home:
                onBackPressed();
                return true;

            case R.id.sortByDate:
                Toast.makeText(getApplicationContext(),"Chippin in ",Toast.LENGTH_SHORT).show();
                showTasksByDate();

                return true;

            case R.id.sortByPriority:
                sortByPriority();
                return true;

        }

        return super.onOptionsItemSelected(item);
    }
    private void createTaskDialog3() {
        AlertDialog.Builder alertbox = new AlertDialog.Builder(this,R.style.MyAlertDialogTheme);

        //MaterialAlertDialogBuilder dialog = new MaterialAlertDialogBuilder(this);
        LinearLayout ll_alert_layout = new LinearLayout(this);
        ll_alert_layout.setOrientation(LinearLayout.VERTICAL);
        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.fragment_new_task_dialog, null);
        //TaskName editText for getting name of the task.
        final EditText taskName = (EditText) view.findViewById(R.id.taskName);
        //dueDate editText for getting dAt
        final EditText dueDate = (EditText) view.findViewById(R.id.dueDate);
        final SeekBar seekBar = (SeekBar)view.findViewById(R.id.priority);
        final TextView textView = (TextView)view.findViewById(R.id.priority_text);
        //alertbox.setMessage("Enter your text");



        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                textView.setText(Integer.toString(seekBar.getProgress()));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        //setting linear layout to alert dialog

        alertbox.setView(view);
        alertbox.setTitle("Add new Task!");

        //dialog.setView(view);
        //dialog.setTitle("Add New Task");

        dueDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Get Current Date
                final Calendar c = Calendar.getInstance();
                int mYear = c.get(Calendar.YEAR);
                final int mMonth = c.get(Calendar.MONTH);
                int mDay = c.get(Calendar.DAY_OF_MONTH);


                DatePickerDialog datePickerDialog = new DatePickerDialog(InboxActivity.this,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {



                                monthOfYear++;

                                String date = "";

                                Log.d(TAGD,"monthOfyear" +Integer.toString(monthOfYear));

                                if(monthOfYear<10){

                                    date = Integer.toString(year) + "-0" + Integer.toString(monthOfYear) + "-" + Integer.toString(dayOfMonth);

                                }

                                else {

                                    date = Integer.toString(year) + "-" + Integer.toString(monthOfYear) + "-" + Integer.toString(dayOfMonth);

                                }




                                Log.v(TAG, date);

                                dueDate.setText(date);

                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();


            }
        });


        alertbox.setNegativeButton("Cancel",
                new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface arg0, int arg1) {

                        // will automatically dismiss the dialog and will do nothing

                    }
                });


        alertbox.setPositiveButton("Ok",
                new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface arg0, int arg1) {
                        String input_text = taskName.getText().toString();
                        String dAt = dueDate.getText().toString();
                        ArrayList<String> previous_tasks = new ArrayList<String>();
                        long length;
                        int priority =seekBar.getProgress();
                        String priority_text = Integer.toString(priority);
                        textView.setText(priority_text);
                        if(getTime.getTime()>0){

                            length = getTime.getTime();
                            Log.d(TAG,"getTime "+length);
                        }
                        else{
                            length = 0;
                            Log.d(TAG,"getTime "+length);

                        }

                        Log.d(TAG,"Number of milliseconds "+Long.toString(length));

                        Cursor cursor = Adapter.getCursor();
                        cursor.moveToFirst();

                        //Generate previously added tasks....
                        if(cursor.getCount()>0){
                            do{

                                String name_of_task = cursor.getString(cursor.getColumnIndexOrThrow("taskName"));
                                previous_tasks.add(name_of_task);
                                Log.d(TAG,"Same Name Check!" +name_of_task);
                            }while (cursor.moveToNext());

                        }


                        if(previous_tasks.contains(input_text)){
                            Log.d(TAG,"Same tasks exist");
                            Toast.makeText(getApplicationContext(),"Task with same name exists",Toast.LENGTH_SHORT).show();
                        }
                        else {
                            Log.d(TAG,"Same tasks do not exist");
                            if (input_text.isEmpty()) {
                                Toast.makeText(getApplicationContext(), "Enter Task Name", Toast.LENGTH_SHORT).show();
                            } else {
                                if (dAt.isEmpty()) {
                                    Log.v(TAG, "DueDate EditText is * " + dAt);
                                    dAt = "";
                                }
                                Log.v(TAG, "AlertBox ok pressed " + dAt);
                                Toast.makeText(getApplicationContext(), dAt + "*Toast", Toast.LENGTH_SHORT).show();
                                //Toast.makeText(getApplication  Context(),input_text,Toast.LENGTH_SHORT).show();
                                //addProject(input_text);
                                //refreshListView();
                                Random random = new Random();
                                int id = random.nextInt(1000);
                                //
                                // handler.addTask(new Task(id,input_text));
                                //Optional Code*****
                                String DAT = createDate(dAt);
                                //Optional Code***** Optional Argument DAT
                                Task task = new Task(id, input_text, "inbox", dAt, DAT,priority);
                                //task.setDueDate2(LocalDate.now().toString());
                                handler1.addTask(task);
                                refreshListView3();
                            }
                        }

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


        //dialog.show();
    }

    //Back button code
   /*

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    */

    private void longClickAlertDialog(final int position,int id, String name, String date, final Task task) {
        AlertDialog.Builder alertbox = new AlertDialog.Builder(this);

        LinearLayout ll_alert_layout = new LinearLayout(this);
        ll_alert_layout.setOrientation(LinearLayout.VERTICAL);
        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.fragment_new_task_dialog, null);
        final EditText taskName = (EditText) view.findViewById(R.id.taskName);
        final EditText dueDate = (EditText) view.findViewById(R.id.dueDate);
        final SeekBar seekBar = (SeekBar)view.findViewById(R.id.priority);
        final TextView priorityText = (TextView)view.findViewById(R.id.priority_text);
        //alertbox.setMessage("Enter your text");

        taskName.setText(name);

        alertbox.setView(view);
        alertbox.setTitle("Edit your Task!");
        dueDate.setText(date);
        seekBar.setProgress(task.getPriority());
        priorityText.setText(Integer.toString(task.getPriority()));


        dueDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                // Get Current Date
                final Calendar c = Calendar.getInstance();
                int mYear = c.get(Calendar.YEAR);
                int mMonth = c.get(Calendar.MONTH);
                int mDay = c.get(Calendar.DAY_OF_MONTH);


                DatePickerDialog datePickerDialog = new DatePickerDialog(InboxActivity.this,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                String date = Integer.toString(year) + " " + Integer.toString(monthOfYear) + " " + Integer.toString(dayOfMonth);
                                Log.v(TAG, date);

                                dueDate.setText(date);

                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();



            }
        });

        alertbox.setPositiveButton("OK",
                new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface arg0, int arg1) {

                        task.setName(taskName.getText().toString());
                        task.setdAt(dueDate.getText().toString());

                        handler1.updateTask(task.getId(), task);
                        refreshListView3();
                        Toast.makeText(getApplicationContext(), "Update Task Called!", Toast.LENGTH_SHORT).show();


                        /*
                        String input_text = taskName.getText().toString();
                        String dAt = dueDate.getText().toString();
                        if(dAt.isEmpty()){
                            dAt = "";
                        }
                        Log.v(TAG,"AlertBox ok pressed "+dAt);
                        Toas-t.makeText(getApplicationContext(),dAt+"*Toast",Toast.LENGTH_SHORT).show();
                        //Toast.makeText(getApplicationContext(),input_text,Toast.LENGTH_SHORT).show();
                        //addProject(input_text);
                        //refreshListView();
                        Random random = new Random();
                        int id = random.nextInt(1000);
                        //
                        // handler.addTask(new Task(id,input_text));
                        handler1.addTask(new Task(id,input_text,pName,dAt));
                        refreshListView3();
                        // do your action with input string
                        */
                    }
                });


        alertbox.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(getApplicationContext(), "Cancel", Toast.LENGTH_SHORT).show();
            }
        });

        alertbox.setNeutralButton("Delete", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(getApplicationContext(), "Delete", Toast.LENGTH_SHORT).show();
                //handler1.deleteTask(task.getId());
                getAdapterCount();
                //If code doesnt work just remove the if condition...
                refreshListView3();

                Adapter.getCursor().moveToPosition(position);
                Cursor cursor1 = Adapter.getCursor();
                int id1 = cursor1.getInt(cursor1.getColumnIndexOrThrow("_id"));
                String name_of_task = cursor1.getString(cursor1.getColumnIndexOrThrow("taskName"));
                String det = cursor1.getString(cursor1.getColumnIndexOrThrow("dueDate"));
                String dat = cursor1.getString(cursor1.getColumnIndexOrThrow("Date"));

                //Task task = new Task(id,name_of_task,pName,det);
                final Task task = new Task(id1, name_of_task, "inbox", det, dat);
                handler1.deleteTask(id1);
                refreshListView3();



                Snackbar snackbar = Snackbar
                        .make(coordinatorLayout, "Project is Deleted", Snackbar.LENGTH_LONG)
                        .setAction("UNDO", new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                handler1.addTask(task);
                                refreshListView3();
                                Snackbar snackbar1 = Snackbar.make(coordinatorLayout, "Project is restored!", Snackbar.LENGTH_SHORT);
                                snackbar1.show();
                            }
                        });

                snackbar.show();
            }
        });
        alertbox.show();


    }


    //Database Code
    private void setListData() {
        Log.d(TAG, "setListData called");
        Cursor cursor1 = db.query("tasks",
                new String[]{"_id", "taskName", "projectName", "dueDate", "Date","priority"},
                "projectName" + "=?",
                new String[]{"inbox"},
                null,
                null,
                null,
                null);
        handler1 = new ProjectHelper(this);
        Adapter = new TaskCursorAdapter(getApplicationContext(), cursor1);
        list_of_tasks.setAdapter(Adapter);
    }


    private void refreshListView3() {
        Cursor cursor1 = db.query("tasks",
                new String[]{"_id", "taskName", "projectName", "dueDate", "Date","priority"},
                "projectName" + "=?",
                new String[]{"inbox"},
                null,
                null,
                null,
                null);
        Adapter.changeCursor(cursor1);
    }

    private String createDate(String date) {
        String DATE = "";

        if (date == "") {
            DATE = "";
        } else if (date != "") {

            String[] stringArray = date.split("-");
            Log.v(TAG, "Split array date*** " + stringArray[0]);
            Log.v(TAG, "Split array date*** " + stringArray[1]);
            Log.v(TAG, "Split array date*** " + stringArray[2]);

            int year = Integer.parseInt(stringArray[0]);
            int month = Integer.parseInt(stringArray[1]) -1;
            int day = Integer.parseInt(stringArray[2]);

            Date date1 = new GregorianCalendar(year, month, day).getTime();
            Log.d(TAGD,"Due Date "+date1.toString());
            Log.v(TAG,"*** *** ***"+date1.getTime());
            SimpleDateFormat sdf = new SimpleDateFormat("EEE, MMM d");
            DATE = sdf.format(date1);

            Log.v(TAG, "Task Constructor ***" + Integer.toString(year));


        }

        return DATE;

    }

    private void showTasksByDate(){

        cursor = db.rawQuery("SELECT * FROM tasks  WHERE projectName = ? ORDER BY date_in_ms",new String[]{"inbox"});
        TaskCursorAdapter adapter = new TaskCursorAdapter(getApplicationContext(),cursor);
        list_of_tasks.setAdapter(adapter);

    }

    private void sortByPriority(){

        Toast.makeText(getApplicationContext(),"Sort By Priority!",Toast.LENGTH_SHORT).show();
        cursor = db.rawQuery("SELECT * FROM tasks WHERE projectName = ? ORDER BY priority DESC",new String[]{"inbox"});
        TaskCursorAdapter adapter = new TaskCursorAdapter(getApplicationContext(),cursor);
        list_of_tasks.setAdapter(adapter);

    }

    private void getAdapterCount(){
        int count = Adapter.getCount();
        Log.v(TAG,"Adapter Count "+count);

    }


}
