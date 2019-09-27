package com.example.adithya.tm_v3;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import androidx.fragment.app.DialogFragment;
import androidx.core.app.NavUtils;
import androidx.appcompat.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class NewTaskDialog extends DialogFragment implements View.OnClickListener{

    private EditText dueDate;
    private EditText startTime;
    private EditText endTime;
    private ImageView imageView;
    private Button button;
    private static final String TAG = "TAG";

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the Builder class for convenient dialog construction
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

            LayoutInflater inflater = getActivity().getLayoutInflater();


            // Inflate and set the layout for the dialog
            // Pass null as the parent view because its going in the dialog layout
            builder.setView(inflater.inflate(R.layout.fragment_new_task_dialog, null))
                    .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            // Button Click Works
                            Log.v(TAG,dueDate.getText().toString());
                            Log.v(TAG,startTime.getText().toString());
                            Log.v(TAG,endTime.getText().toString());
                            Toast.makeText(getContext(),"Ok",Toast.LENGTH_SHORT).show();
                        }
                    })
                    .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            // Button click works
                            Toast.makeText(getContext(),"Cancel",Toast.LENGTH_SHORT).show();
                        }
                    });
            // Create the AlertDialog object and return it
            return builder.create();
        }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
            Log.v(TAG,"onCreateView called!");
        View view = inflater.inflate(R.layout.fragment_new_task_dialog, container,false);
        dueDate = (EditText) view.findViewById(R.id.dueDate);


        Log.v(TAG,"Line 68 reached!");

        dueDate.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                Log.v(TAG,"dueDate Clicked");
                return true;
            }
        });



        return view;
    }



    @Override
    public void onClick(View v) {

            switch (v.getId()){
                case R.id.dueDate:
                    Toast.makeText(getContext(),"Due Date Clicked",Toast.LENGTH_SHORT).show();
                    Log.v(TAG,"dueDate Clicked");
                    break;

            }

    }
}
