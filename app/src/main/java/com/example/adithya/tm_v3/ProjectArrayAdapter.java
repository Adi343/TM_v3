package com.example.adithya.tm_v3;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class ProjectArrayAdapter extends ArrayAdapter<Project> {

    ProjectHelper helper = new ProjectHelper(getContext());
    private static final String TAG1 = "TAG1";

    public ProjectArrayAdapter(Context context, ArrayList<Project> users) {
        super(context, 0, users);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Log.d(TAG1,"Inside getView()");
        // Get the data item for this position
        Project project = getItem(position);
        String projectName = project.getName();
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.layout_projectlist_item, parent, false);
        }
        // Lookup view for data population
        TextView tvName = (TextView) convertView.findViewById(R.id.tvBody);
        TextView taskCountView = (TextView)convertView.findViewById(R.id.total_number_of_tasks);
        String taskCountText ="GOAT tasks remaining.";
        // Populate the data into the template view using the data object
        tvName.setText(projectName);
        Log.d(TAG1,projectName);
        taskCountView.setText(taskCountText);
        Log.d(TAG1,"Log is working!");
        Log.d(TAG1,projectName+" "+taskCountText);

        // Return the completed view to render on screen
        return convertView;
    }
}
