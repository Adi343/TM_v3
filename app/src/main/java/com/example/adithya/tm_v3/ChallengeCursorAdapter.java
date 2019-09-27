package com.example.adithya.tm_v3;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import androidx.cursoradapter.widget.CursorAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

public class ChallengeCursorAdapter extends CursorAdapter {

    CheckBox checkBox;
    private static final String TAG = "TAG";
    ProjectHelper helper;
    SQLiteDatabase db;



    public ChallengeCursorAdapter(Context context, Cursor cursor){
        super(context,cursor,0);

    }


    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.challenge_item_layout, parent, false);

    }


    // The bindView method is used to bind all data to a given view
    // such as setting the text on a TextView.
    @Override
    public void bindView(View view, Context context, final Cursor cursor) {
        // Find fields to populate in inflated template

        //Cursor taskCursor = "SELECT * FROM task";
        helper = new ProjectHelper(context);
        TextView challengeName = (TextView) view.findViewById(R.id.challengeName);
        TextView challengeStreak = (TextView)view.findViewById(R.id.challengeStreaak);
        TextView challengeTotal = (TextView)view.findViewById(R.id.challengeTotal);
        ProgressBar progressBar = (ProgressBar)view.findViewById(R.id.challengeProgressBar);
        ImageView challengeIcon = (ImageView)view.findViewById(R.id.challengeIcon);

        // Extract properties from cursor
        String name = cursor.getString(cursor.getColumnIndexOrThrow("challengeName"));
        int streak = cursor.getInt(cursor.getColumnIndexOrThrow("streak"));
        int total = cursor.getInt(cursor.getColumnIndexOrThrow("total"));
        int imageNo = cursor.getInt(cursor.getColumnIndexOrThrow("imageNo"));




        switch (imageNo){




            case 0:
                challengeIcon.setImageResource(R.drawable.black_flag);
                break;


            case 1:
                challengeIcon.setImageResource(R.drawable.dragon_head);
                break;

            case 2:
                challengeIcon.setImageResource(R.drawable.rank1);
                break;

            case 3:
                challengeIcon.setImageResource(R.drawable.rank2);
                break;

            case 4:
                challengeIcon.setImageResource(R.drawable.rank2);
                break;
        }

        progressBar.setMin(0);
        progressBar.setMax(total);
        progressBar.setProgress(streak);
        //int count = cursor.getCount();


        // Populate fields with extracted properties
        challengeName.setText(name);
        challengeStreak.setText(Integer.toString(streak)+ " streak");
        challengeTotal.setText(Integer.toString(total)+ " total");

        int cursorPosition = cursor.getPosition();
        Log.v(TAG,"Cursor Position ***"+Integer.toString(cursorPosition));

    }
}
