package com.example.user.retrofitdemo.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.user.retrofitdemo.util.ApiClient;
import com.example.user.retrofitdemo.util.ApiInterface;
import com.example.user.retrofitdemo.data.BackgroundTask;
import com.example.user.retrofitdemo.data.DatabaseHelper;
import com.example.user.retrofitdemo.model.Photos;
import com.example.user.retrofitdemo.R;
import com.example.user.retrofitdemo.adapter.RecyclerAdapter;
import com.example.user.retrofitdemo.model.photo;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Cache;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements RecyclerAdapter.OnclickListener {


    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerAdapter adapter;
    private List<photo> photos;
    private ArrayList<photo> dbData;
    private ArrayList<photo> imageData;
    private ApiInterface apiInterface;
    private DatabaseHelper myHelper;
    private SQLiteDatabase sqliteDB;
    private SharedPreferences sp;
    SharedPreferences.Editor editor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("User List");

        recyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        adapter = new RecyclerAdapter(this);
        recyclerView.setAdapter(adapter);

        dbData = new ArrayList<>();

        sp = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();

        String value = sp.getString("KEY", null);


        myHelper = new DatabaseHelper(MainActivity.this, "STUDDB", null, 1);
        sqliteDB = myHelper.getWritableDatabase();


        ConnectivityManager cm = (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo active = cm.getActiveNetworkInfo();


        if (value == null) {
            if (active != null && active.isConnectedOrConnecting()) {
                dataSave();
            } else {
                Toast.makeText(getApplicationContext(), "Please Connect to the interner for the first time to Load data",
                        Toast.LENGTH_LONG).show();
            }

        } else {
            if (active != null && active.isConnectedOrConnecting()) {
                dataSave();
            } else {
                //  adapter.setPhotos(photos);
                // photos.clear();

                String[] columns = {"id", "Name", "Email"};
                Cursor c = sqliteDB.query("Task", columns, null, null, null, null, null);
                while (c.moveToNext()) {
                    int id = c.getInt(0);
                    String name = c.getString(1);
                    String user = c.getString(2);

                    Log.d("Database", String.valueOf(id));
                    Log.d("Database", name);
                    Log.d("Database", user);

                    photo p = new photo(name, user);
                    dbData.add(p);
                }
                if (dbData.size() > 0) {
                    adapter.setPhotos(dbData);
                    Log.d("dbData", String.valueOf(dbData.size()));
                }

                c.close();
            }
        }
    }

    public void dataSave(){

        final SharedPreferences.Editor editor = sp.edit();
        ApiClient api = new ApiClient();
        Cache cache = new Cache(getCacheDir(), 5 * 1024 * 1024);
        api.setOkHttpClient(cache);
        apiInterface = api.getApiClient().create(ApiInterface.class);

        Call<Photos> call = apiInterface.getPhotos();
        call.enqueue(new Callback<Photos>() {
            @Override
            public void onResponse(Call<Photos> call, Response<Photos> response) {
                photos = response.body().getPhotos();
                Log.d("Test", photos.size() + "");
                adapter.setPhotos(photos);

              //  Log.d("Task","BackgroundTask");
                BackgroundTask mBackgroundTask = new BackgroundTask(getApplicationContext());
                mBackgroundTask.execute(sqliteDB,photos);
                editor.putString("KEY", "ASD");
                editor.commit();
            }

            @Override
            public void onFailure(Call<Photos> call, Throwable t) {
                call.cancel();
                Log.d("Test", "Fail");
            }
        });

    }

    @Override
    public void onItemClick(View view, int position) {
        //  Toast.makeText(getApplicationContext(),""+position,Toast.LENGTH_SHORT).show();
        ArrayList<String> imageLink = new ArrayList<>();

        ConnectivityManager cm = (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo active = cm.getActiveNetworkInfo();

        if (active != null && active.isConnectedOrConnecting()) {
            imageLink = photos.get(position).getImages();
        } else {
            String[] columns = {"Link"};
            String[] pos = {String.valueOf(position)};
            Log.d("Task", position + "");
            Cursor c = sqliteDB.query("Image", columns, "Id LIKE ?", pos, null, null, null);
            //Cursor c = sqliteDB.rawQuery("Select * from Image where Id = " + position, null);

            // Log.d("Task", c.getCount() + "");

            while (c.moveToNext()) {
                // Log.d("Task", c.getCount() + "");
                //int id = c.getInt(0);
                String Link = c.getString(0);
                imageLink.add(Link);

            }
            c.close();

        }


        Intent i = new Intent(MainActivity.this, PhotoGallery.class);
        i.putExtra("ID", position);
        i.putStringArrayListExtra("DataArray", imageLink);
        startActivity(i);

        //  Toast.makeText(getApplicationContext(), position + "", Toast.LENGTH_SHORT).show();

    }

}
