package com.example.user.retrofitdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

public class ImageViewer extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_viewer);

        String id = getIntent().getStringExtra("ImageId");

        ImageView imageview = (ImageView) findViewById(R.id.imageShow);
        Glide.with(this)
                .load(id)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(imageview);
    }
}
