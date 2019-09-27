package com.example.adithya.tm_v3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;

import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.LocalDate;
import java.util.Date;

public class ChallengesActivity extends AppCompatActivity {


    private static final String TAGC = "TAGC";
    ListView challengesList;
    TextView emptyView1;
    ProjectHelper handler;
    SQLiteDatabase db;
    Cursor cursor,cursor1,cursor2;
    ChallengeCursorAdapter adapter,adapter1,adapter2;
    private static final String ChallengePreferences = "ChallengePreferences";
    private static final String AppPreferences = "AppPreferences";
    private static final String streak = "streak";
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    String[] temp = {"tai","lor","dur","den"};



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_challenges);

        handler = new ProjectHelper(this);


        sharedPreferences = getSharedPreferences(AppPreferences, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();

        //handler.updateTimeChallenge(sharedPreferences.getInt(streak,0));
        //handler.updateTaskChallenge();


        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow_back_black_24dp);
        getSupportActionBar().setTitle("Challenges");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        db = handler.getWritableDatabase();

        cursor = db.rawQuery("SELECT  * FROM timeChallenges", null);
        cursor1 = db.rawQuery("SELECT * FROM taskChallenges WHERE status = ?",new String[]{"null for now"});
        cursor2 = db.rawQuery("SELECT * FROM challenges WHERE status =?",new String[]{"null for now"});

        adapter = new ChallengeCursorAdapter(this, cursor);
        adapter1 = new ChallengeCursorAdapter(this,cursor1);
        adapter2 = new ChallengeCursorAdapter(this,cursor2);


        challengesList = (ListView)findViewById(R.id.list_of_challenges);
        emptyView1 = (TextView)findViewById(R.id.emptyView1);


        challengesList.setEmptyView(emptyView1);

        challengesList.setAdapter(adapter2);

    }


}