package com.example.adithya.tm_v3;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class TaskArrayAdapter extends ArrayAdapter<Task> {

    public TaskArrayAdapter(Context context, ArrayList<Task> users) {
        super(context, 0, users);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        Task task = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.taskitem_layout, parent, false);
        }
        // Lookup view for data population
        TextView tvName = (TextView) convertView.findViewById(R.id.tvBody);

        // Populate the data into the template view using the data object
        tvName.setText(task.getName());

        // Return the completed view to render on screen
        return convertView;
    }
}