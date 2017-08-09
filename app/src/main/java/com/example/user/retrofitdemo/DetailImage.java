package com.example.user.retrofitdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

public class DetailImage extends AppCompatActivity {

    private ImageView mImageView;
    private String URL;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_image);

        mImageView = (ImageView) findViewById(R.id.picture);
        URL = getIntent().getStringExtra("URL");

        Glide.with(this)
                .load(URL)
                .asBitmap()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(mImageView);
    }
}
