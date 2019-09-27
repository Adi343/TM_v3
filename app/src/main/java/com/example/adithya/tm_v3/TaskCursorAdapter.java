package com.example.adithya.tm_v3;

import android.content.Context;
import android.database.Cursor;
import androidx.cursoradapter.widget.CursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

public class TaskCursorAdapter extends CursorAdapter {

    CheckBox checkBox;

    public TaskCursorAdapter(Context context, Cursor cursor){
        super(context,cursor,0);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.taskitem_layout, parent, false);
    }
    // The bindView method is used to bind all data to a given view
    // such as setting the text on a TextView.
    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        // Find fields to populate in inflated template
        TextView DAT = (TextView)view.findViewById(R.id.DAT);
        TextView tvBody = (TextView) view.findViewById(R.id.tvBody);
        checkBox = (CheckBox)view.findViewById(R.id.checkBox);
        checkBox.setChecked(false);
        // Extract properties from cursor
        String body = cursor.getString(cursor.getColumnIndexOrThrow("taskName"));
        String date = cursor.getString(cursor.getColumnIndexOrThrow("Date"));
        // Populate fields with extracted properties
        tvBody.setText(body);
        DAT.setText(date);
    }
    public void setCheck(){
        checkBox.setChecked(true);
    }
}
