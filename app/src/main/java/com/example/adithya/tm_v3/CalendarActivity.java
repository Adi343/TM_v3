package com.example.adithya.tm_v3;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import androidx.annotation.NonNull;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CalendarView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;
import java.util.Random;

public class CalendarActivity extends AppCompatActivity {

    FloatingActionButton fab;
    ListView list_of_events;
    TextView emptyView;
    ProjectHelper handler1;
    EventCursorAdapter adapter;
    SQLiteDatabase db;
    CalendarView calendarView;

    private static final String TAG = "TAG";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);


        Toolbar toolbar = findViewById(R.id.toolbar);
        fab = (FloatingActionButton)findViewById(R.id.fab);
        list_of_events = (ListView)findViewById(R.id.list_of_events);
        emptyView = (TextView)findViewById(R.id.emptyView);
        calendarView = (CalendarView)findViewById(R.id.calendar);

        handler1 = new ProjectHelper(CalendarActivity.this);
        db = handler1.getWritableDatabase();
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow_back_black_24dp);
        getSupportActionBar().setTitle(null);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        list_of_events.setEmptyView(emptyView);

        //setListData();

        list_of_events.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });

        list_of_events.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Cursor cursor1 = adapter.getCursor();
                cursor1.moveToPosition(position);

                int id1 = cursor1.getInt(cursor1.getColumnIndexOrThrow("_id"));
                String eventName = cursor1.getString(cursor1.getColumnIndexOrThrow("eventName"));
                Toast.makeText(getApplicationContext(),"Long press"+position,Toast.LENGTH_SHORT).show();
                Project project = new Project(id1,eventName);
                EditEventDialog(position,id1,eventName,project);
                return true;
            }
        });

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createEventDialog();
            }
        });

        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                String date = "YEAR "+year + " MONTH "+month+"DAY "+dayOfMonth;
                Log.v(TAG,date);
            }
        });

        setListData();


    }

    private void createEventDialog() {
        AlertDialog.Builder alertbox = new AlertDialog.Builder(this);

        LinearLayout ll_alert_layout = new LinearLayout(this);
        ll_alert_layout.setOrientation(LinearLayout.VERTICAL);
        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.event_dialog_layout, null);
        final EditText eventName = (EditText) view.findViewById(R.id.taskName);
        final EditText startDate = (EditText) view.findViewById(R.id.startDate);
        final EditText endDate = (EditText)view.findViewById(R.id.endDate);
        //alertbox.setMessage("Enter your text");

        //setting linear layout to alert dialog

        alertbox.setView(view);
        alertbox.setTitle("Add new Event!");


        startDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Get Current Date
                final Calendar c = Calendar.getInstance();
                int mYear = c.get(Calendar.YEAR);
                int mMonth = c.get(Calendar.MONTH);
                int mDay = c.get(Calendar.DAY_OF_MONTH);


                DatePickerDialog datePickerDialog = new DatePickerDialog(CalendarActivity.this,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                String date = Integer.toString(year) + " " + Integer.toString(monthOfYear) + " " + Integer.toString(dayOfMonth);
                                Log.v(TAG, date);

                                startDate.setText(date);

                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();

            }
        });

        endDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Get Current Date
                final Calendar c = Calendar.getInstance();
                int mYear = c.get(Calendar.YEAR);
                int mMonth = c.get(Calendar.MONTH);
                int mDay = c.get(Calendar.DAY_OF_MONTH);


                DatePickerDialog datePickerDialog = new DatePickerDialog(CalendarActivity.this,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                String date = Integer.toString(year) + " " + Integer.toString(monthOfYear) + " " + Integer.toString(dayOfMonth);
                                Log.v(TAG, date);

                                endDate.setText(date);

                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();

            }
        });


        alertbox.setNegativeButton("CANCEL",
                new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface arg0, int arg1) {

                        // will automatically dismiss the dialog and will do nothing

                    }
                });


        alertbox.setPositiveButton("OK",
                new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface arg0, int arg1) {

                        String name = eventName.getText().toString();
                        String date1 = startDate.getText().toString();
                        String date2 = endDate.getText().toString();

                        if(name.isEmpty()){

                        }
                        else {
                            Toast.makeText(getApplicationContext(),name,Toast.LENGTH_SHORT).show();
                            Random random = new Random();
                            int id = random.nextInt(10000);
                            Event event = new Event(id,name,date1,date2);
                            handler1.addEvent(event
                            );
                        }
                        refreshListView();
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

    private void EditEventDialog(final int position, final int id1, String name, final Project project){
        AlertDialog.Builder alertbox = new AlertDialog.Builder(this);
        LinearLayout ll_alert_layout = new LinearLayout(this);
        ll_alert_layout.setOrientation(LinearLayout.VERTICAL);
        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.event_dialog_layout,null);
        final EditText eventName = (EditText)view.findViewById(R.id.eventName);
        final EditText startDate = (EditText)view.findViewById(R.id.startDate);
        final EditText endDate = (EditText)view.findViewById(R.id.endDate);

        Cursor cursor = adapter.getCursor();
        cursor.moveToPosition(position);
        final int id = cursor.getInt(cursor.getColumnIndexOrThrow("_id"));
        String NAME = cursor.getString(cursor.getColumnIndexOrThrow("eventName"));
        String date1 = cursor.getString(cursor.getColumnIndexOrThrow("startDate"));
        String date2 = cursor.getString(cursor.getColumnIndexOrThrow("endDate"));

        eventName.setText(NAME);
        startDate.setText(date1);
        endDate.setText(date2);
        //alertbox.setMessage("Enter your text");

        //setting linear layout to alert dialog

        alertbox.setView(view);
        alertbox.setTitle("Edit Project!");

        alertbox.setPositiveButton("OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        project.setName(eventName.getText().toString());
                        handler1.updateProject(project);
                        refreshListView();
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
                Log.v(TAG,"Delete Project Pressed!");
                handler1.deleteEvent(id);
                refreshListView();
            }
        });
        alertbox.setCancelable(false);
        alertbox.show();

    }


    //Database Code
    private void setListData() {
        Log.d(TAG, "setListData called");
        Cursor cursor1 = db.rawQuery("SELECT * FROM calendar",null);
        handler1 = new ProjectHelper(this);
        adapter = new EventCursorAdapter(getApplicationContext(), cursor1);
        list_of_events.setAdapter(adapter);
    }

    private void refreshListView(){
        Cursor cursor = db.rawQuery("SELECT * FROM calendar",null);
        adapter.changeCursor(cursor);
        list_of_events.setAdapter(adapter);
    }

}
