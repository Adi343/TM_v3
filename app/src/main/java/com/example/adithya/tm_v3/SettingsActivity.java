package com.example.adithya.tm_v3;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.GridView;
import android.widget.ImageView;

import androidx.appcompat.widget.Toolbar;

public class SettingsActivity extends AppCompatActivity {

    ImageView imageView;
    GridView gridView;
    ProjectHelper handler1;
    CustomAdapter adapter;

    private static final String TAG = "TAG";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow_back_black_24dp);
        getSupportActionBar().setTitle("Settings");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        handler1 = new ProjectHelper(getApplicationContext());

        imageView = (ImageView)findViewById(R.id.imageView);
        gridView = (GridView)findViewById(R.id.gridView);

        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.rank1);

        Bitmap bitmap1 = BitmapFactory.decodeResource(getResources(), R.drawable.rank2);


        Bitmap bitmap2 = BitmapFactory.decodeResource(getResources(), R.drawable.rank3);




        handler1.insertImg(1,"rank1",bitmap);
        handler1.insertImg(2,"rank2",bitmap1);
        handler1.insertImg(3,"rank3",bitmap2);

        imageView.setImageBitmap(handler1.getImage(1));


        adapter = new CustomAdapter(getApplicationContext());
        gridView.setAdapter(adapter);

        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }


    //Back button code
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }


}
