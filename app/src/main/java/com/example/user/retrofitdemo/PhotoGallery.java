package com.example.user.retrofitdemo;

import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.example.user.retrofitdemo.R.id.albumname;
import static com.example.user.retrofitdemo.R.id.user;

public class PhotoGallery extends AppCompatActivity {


    private Retrofit mRetrofit;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private  GallaryAdapter adapter;
    private List<photo> photos;
    private ApiInterface apiInterface;
    private DatabaseHelper myHelper;
    private SQLiteDatabase sqliteDB;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_gallery);

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        layoutManager = new GridLayoutManager(this,2);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        adapter = new GallaryAdapter(this);
        recyclerView.setAdapter(adapter);


        ArrayList<String> photoData = getIntent().getStringArrayListExtra("DataArray");
        final int value = getIntent().getIntExtra("ID",0);

        adapter.setLinks(photoData);



        /*if(value == 0) {

            Glide.with(this)
                    .load("https://c1.staticflickr.com/9/8008/7479371438_1ac292b9b9_b.jpg")
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(imageView);
        }
        else {
            Glide.with(this)
                    .load("https://www.acsu.buffalo.edu/~naziarah/3.jpg")
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(imageView);
        }*/
    }

}
