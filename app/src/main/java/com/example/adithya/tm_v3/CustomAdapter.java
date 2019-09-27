package com.example.adithya.tm_v3;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

public class CustomAdapter extends BaseAdapter {

    private Context context;
    private LayoutInflater layoutInflater ;
    Cursor cursor;
    ProjectHelper handler1;

    public CustomAdapter(Context context){

        this.context = context;

        layoutInflater = LayoutInflater.from(context);

        handler1 = new ProjectHelper(context);


    }

    public int getCount(){

        return handler1.getImagesCount();


    }



    public long getItemId(int position){

        return position;


    }

    public Object getItem(int position){

        return position;


    }

    public View getView(int position, View convertView, ViewGroup parent){

        if(convertView == null){


            convertView = layoutInflater.inflate(R.layout.image_item_layout,parent,false);

            ImageView imageView = (ImageView)convertView.findViewById(R.id.imageView);

            imageView.setImageBitmap(handler1.getImage(position));
        }

        return convertView;


    }
}
