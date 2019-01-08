package com.example.user.retrofitdemo.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;

import com.example.user.retrofitdemo.model.photo;

import java.util.List;

/**
 * Created by User on 8/11/2017.
 */

public class BackgroundTask extends AsyncTask<Object, Void, String> {
    Context context;
    private List<photo> photos;

    public BackgroundTask(Context context) {
        this.context = context;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected String doInBackground(Object... params) {

        SQLiteDatabase db = (SQLiteDatabase) params[0];
        photos = (List<photo>) params[1];

        DatabaseHelper mDatabaseHelper = new DatabaseHelper(context, "", null, 1);
        mDatabaseHelper.addInformations(db,photos);
        //return "Data Inserted";

        return null;
    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }

    @Override
    protected void onPostExecute(String result) {
        //Toast.makeText(context,result,Toast.LENGTH_LONG).show();
    }
}
