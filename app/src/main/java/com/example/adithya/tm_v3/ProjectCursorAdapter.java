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
import android.widget.TextView;

public class ProjectCursorAdapter extends CursorAdapter {

    CheckBox checkBox;
    private static final String TAG = "TAG";
    ProjectHelper helper;
    SQLiteDatabase db;

    public ProjectCursorAdapter(Context context, Cursor cursor){
        super(context,cursor,0);

    }


    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.layout_projectlist_item, parent, false);

    }


    // The bindView method is used to bind all data to a given view
    // such as setting the text on a TextView.
    @Override
    public void bindView(View view, Context context, final Cursor cursor) {
        // Find fields to populate in inflated template

        //Cursor taskCursor = "SELECT * FROM task";
        helper = new ProjectHelper(context);
        TextView tvBody = (TextView) view.findViewById(R.id.tvBody);
        TextView number_of_tasks = (TextView)view.findViewById(R.id.total_number_of_tasks);
        // Extract properties from cursor
        String body = cursor.getString(cursor.getColumnIndexOrThrow("projectName"));
        //int count = cursor.getCount();
        int count;
        count = helper.getProjectTaskCount(body);
        /*
        try{
             count = helper.getProjectTaskCount(body);
        }
        catch (Exception e){
            Log.d(TAG,"Exception"+e);
        }
        finally {
            count = 0;

        }

*/


        Log.d(TAG,"Count value "+count);



        // Populate fields with extracted properties
        tvBody.setText(body);
        number_of_tasks.setText(Integer.toString(count)+ " tasks remaining!");

        int cursorPosition = cursor.getPosition();
        Log.v(TAG,"Cursor Position ***"+Integer.toString(cursorPosition));

    }
}
