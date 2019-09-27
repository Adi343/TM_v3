package com.example.adithya.tm_v3;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CursorAdapter;
import android.widget.TextView;

public class EventCursorAdapter extends CursorAdapter {

    public EventCursorAdapter(Context context, Cursor cursor){
        super(context,cursor,0);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.even_item_layout, parent, false);
    }
    // The bindView method is used to bind all data to a given view
    // such as setting the text on a TextView.
    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        // Find fields to populate in inflated template
        TextView name = (TextView)view.findViewById(R.id.eventName);
        TextView startDate = (TextView)view.findViewById(R.id.startDate);
        TextView endDate = (TextView) view.findViewById(R.id.endDate);
        // Extract properties from cursor
        String NAME = cursor.getString(cursor.getColumnIndexOrThrow("eventName"));
        String date1 = cursor.getString(cursor.getColumnIndexOrThrow("startDate"));
        String date2 = cursor.getString(cursor.getColumnIndexOrThrow("endDate"));
        // Populate fields with extracted properties
        name.setText(NAME);
        startDate.setText(date1);
        endDate.setText(date2);
    }


}
