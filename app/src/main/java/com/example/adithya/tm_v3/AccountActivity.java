package com.example.adithya.tm_v3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;

import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.mikhaellopez.circularprogressbar.CircularProgressBar;

public class AccountActivity extends AppCompatActivity {



    TextView textView,taskCount,challengesCount,emptyView;
    CardView playerCard;
    ImageAdapter2 imageAdapter;
    ImageAdapter3 adapter;
    ProjectHelper handler1;
    SQLiteDatabase db;
    ListView listOfChallenges;


    Integer[] imageIDs = {
            R.drawable.black_flag, R.drawable.dragon_head, R.drawable.icon1,R.drawable.rank1,
    R.drawable.rank2,R.drawable.rank3
    };


    ImageView circularView;
    GridView gridView;
    ProjectHelper handler;
    private final static String TAG = "TAG";
    Animation animation,animation1,animation2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);

        handler = new ProjectHelper(this);
        Log.v(TAG,"Total Task Count "+Long.toString(handler.getTaskCount()));
        Log.v(TAG,"Completed Task Count "+Long.toString(handler.getCompletedTaskCount()));


        Toolbar toolbar = findViewById(R.id.toolbar);





        handler1 = new ProjectHelper(this);

        db = handler1.getWritableDatabase();

        Cursor cursor1 = db.query("challenges",
                new String[]{"_id", "imageNo"},
                null,
                null,
                null,
                null,
                null,
                null);

        Cursor cursor2 = db.rawQuery("SELECT * FROM completedChallenges",null);
        imageAdapter = new ImageAdapter2(this,cursor2);
        adapter = new ImageAdapter3(this,cursor2,true);


        //animation = AnimationUtils.loadAnimation(this,R.anim.bounce);
        //animation1 = AnimationUtils.loadAnimation(this,R.anim.fade_out);


    //    circularView = (ImageView)findViewById(R.id.circularBar);
        playerCard = (CardView)findViewById(R.id.playerCard);
        textView = (TextView)findViewById(R.id.text);
        taskCount = (TextView)findViewById(R.id.taskCount);
        challengesCount = (TextView)findViewById(R.id.challengesCount);
        gridView = (GridView)findViewById(R.id.gridView);

        emptyView = (TextView)findViewById(R.id.emptyView);
        //gridView.setAdapter(new ImageAdapter(this));

        gridView.setAdapter(imageAdapter);

        listOfChallenges = (ListView)findViewById(R.id.listOfChallenges);

        listOfChallenges.setAdapter(adapter);

        listOfChallenges.setEmptyView(emptyView);
        //CircularProgressBar circularProgressBar = findViewById(R.id.circularProgressBar);

        //circularProgressBar.setProgressWithAnimation(70f,1000L);





        //playerCard.startAnimation(animation);
        taskCount.setAnimation(animation1);
        challengesCount.setAnimation(animation1);


        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow_back_black_24dp);
        //getSupportActionBar().setTitle(null);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        setAccountData();

        //circularImageBar(circularView,50);




    }

    private void circularImageBar(ImageView iv2, int i) {


        Bitmap b = Bitmap.createBitmap(300, 300,Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(b);
        Paint paint = new Paint();

        paint.setColor(Color.parseColor("#000000"));
        paint.setStrokeWidth(10);
        paint.setStyle(Paint.Style.STROKE);
        canvas.drawCircle(150, 150, 140, paint);

        paint.setColor(Color.parseColor("#DC143C"));
        paint.setStrokeWidth(10);
        paint.setStyle(Paint.Style.FILL);
        final RectF oval = new RectF();
        paint.setStyle(Paint.Style.STROKE);
        oval.set(10,10,290,290);

        canvas.drawArc(oval, 270, ((i*360)/100), false, paint);
        paint.setStrokeWidth(0);
        paint.setTextAlign(Paint.Align.CENTER);
        paint.setColor(Color.parseColor("#8E8E93"));
        paint.setTextSize(140);

        canvas.drawText(""+i, 150, 150+(paint.getTextSize()/3), paint);

        iv2.setImageBitmap(b);
    }

    private void setAccountData(){
        int tasks = handler1.getCompletedTaskCount();

        int challenges = handler1.getCompletedChallengesCount();

        taskCount.setText(Integer.toString(tasks));
        challengesCount.setText(Integer.toString(challenges));

    }

    }
