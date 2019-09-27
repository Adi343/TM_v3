package com.example.adithya.tm_v3;



import android.content.Context;
import android.database.Cursor;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cursoradapter.widget.CursorAdapter;

public class ImageAdapter3 extends CursorAdapter {


    private String TAGC = "TAGC";

    public Integer[] mThumbIds = {
            R.drawable.black_flag, R.drawable.dragon_head,
            R.drawable.icon1, R.drawable.rank1,
            R.drawable.rank2, R.drawable.rank3};


    public ImageAdapter3(Context context, Cursor cursor){
        super(context,cursor,0);
    }

    public ImageAdapter3(Context context,Cursor cursor,boolean autoRequery){

        super(context,cursor,autoRequery);

    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.layout_account_challenge, parent, false);
    }
    // The bindView method is used to bind all data to a given view
    // such as setting the text on a TextView.
    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        // Find fields to populate in inflated template

        ImageView imageView = (ImageView)view.findViewById(R.id.challengeIcon);
        TextView textView = (TextView)view.findViewById(R.id.challengeName);

        int imageNo = cursor.getInt(cursor.getColumnIndexOrThrow("imageNo"));
        String challenge = cursor.getString(cursor.getColumnIndexOrThrow("challengeName"));

        textView.setText(challenge);
        switch (imageNo){

            case 0:

                imageView.setImageResource(mThumbIds[0]);
                Log.v(TAGC,"Image set 0");
                break;

            case 1:

                imageView.setImageResource(mThumbIds[1]);
                Log.v(TAGC,"Image set 1");
                break;


            case 2:

                imageView.setImageResource(mThumbIds[2]);

                Log.v(TAGC,"Image set 2");
                break;


            case 3:

                imageView.setImageResource(mThumbIds[3]);
                Log.v(TAGC,"Image set 3");
                break;


            case 4:

                imageView.setImageResource(mThumbIds[4]);
                Log.v(TAGC,"Image set 4");
                break;


            case 5:

                imageView.setImageResource(mThumbIds[5]);
                Log.v(TAGC,"Image set 5");
                break;
        }



    }

}
