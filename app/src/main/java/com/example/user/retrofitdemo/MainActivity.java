package com.example.user.retrofitdemo;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.R.attr.id;
import static android.R.attr.name;
import static com.example.user.retrofitdemo.R.id.albumname;
import static com.example.user.retrofitdemo.R.id.image;
import static com.example.user.retrofitdemo.R.id.user;

public class MainActivity extends AppCompatActivity implements RecyclerAdapter.OnclickListener {


    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private  RecyclerAdapter adapter;
    private List<photo> photos;
    private ApiInterface apiInterface;
    private DatabaseHelper myHelper;
    private SQLiteDatabase sqliteDB;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        adapter = new RecyclerAdapter(this);
        recyclerView.setAdapter(adapter);

        myHelper = new DatabaseHelper(MainActivity.this,"STUDDB",null,1);
        sqliteDB = myHelper.getWritableDatabase();

        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);

        Call<Photos> call = apiInterface.getPhotos();
        call.enqueue(new Callback<Photos>() {
            @Override
            public void onResponse(Call<Photos> call, Response<Photos> response) {
                photos = response.body().getPhotos();
                Log.d("Test",photos.size()+"");
                adapter.setPhotos(photos);

               /* for (int i=0;i<photos.size();i++)
                {
                    ArrayList<String > aaa =photos.get(i).getImages();
                    for (String S: aaa){

                    }
                }*/


                //Toast.makeText(MainActivity.this,"Data Size " +photos.size(), Toast.LENGTH_SHORT).show();
                    try{
                        for (int i=0;i<photos.size();i++) {
                            ContentValues cv = new ContentValues();
                            cv.put("Name", albumname);
                            cv.put("Email", user);
                            final Long id = sqliteDB.insert("Task", null, cv);

                            ContentValues imv = new ContentValues();
                            ArrayList<String > imageLink = photos.get(i).getImages();
                            for (String S: imageLink){
                                imv.put("Id",id);
                                imv.put("Link",S);
                                sqliteDB.insert("Image",null,imv);
                            }


                            //cv.put("Id",id);
                            //Toast.makeText(MainActivity.this, "Data Inserted " + id, Toast.LENGTH_SHORT).show();
                            //Toast.makeText(CreateTask.this,String.valueOf(id),Toast.LENGTH_SHORT).show();
                        }
                    }
                    catch (Exception e){
                        e.printStackTrace();
                    }

            }

            @Override
            public void onFailure(Call<Photos> call, Throwable t) {
                Log.d("Test","Fail");
            }
        });

    }

    @Override
    public void onItemClick(View view, int position) {
      //  Toast.makeText(getApplicationContext(),""+position,Toast.LENGTH_SHORT).show();

        ArrayList<String > imageLink =photos.get(position).getImages();

        Intent i = new Intent(MainActivity.this,PhotoGallery.class);
        i.putExtra("ID",position);
        i.putStringArrayListExtra("DataArray",imageLink);
        startActivity(i);

    }
}
