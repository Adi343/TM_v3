package com.example.adithya.tm_v3;

import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

public class HabitsViewActivity extends AppCompatActivity {
    TextView textView;
    FloatingActionButton fab;
    ProjectHelper handler1;
    SQLiteDatabase db;
    Cursor cursor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_habits_view);


        textView = (TextView)findViewById(R.id.textView);
        fab = (FloatingActionButton)findViewById(R.id.fab);
        handler1 = new ProjectHelper(HabitsViewActivity.this);
        db = handler1.getWritableDatabase();

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createHabitDialog();
            }
        });
    }

    private void createHabitDialog() {
        AlertDialog.Builder alertbox = new AlertDialog.Builder(this);

        LinearLayout ll_alert_layout = new LinearLayout(this);
        ll_alert_layout.setOrientation(LinearLayout.VERTICAL);
        final EditText ed_input = new EditText(this);
        ed_input.setSingleLine();
        ll_alert_layout.addView(ed_input);

        alertbox.setTitle("Enter Habit");
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

                        String input_text = ed_input.getText().toString();
                        Toast.makeText(getApplicationContext(),input_text,Toast.LENGTH_SHORT).show();
                        Random random = new Random();
                        int id = random.nextInt(1000);
                        handler1.addHabit(new Habit(id,input_text));


                        // do your action with input string
                    }
                });
        alertbox.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(getApplicationContext(),"Cancel",Toast.LENGTH_SHORT).show();
            }
        });
        alertbox.show();

    }
}
