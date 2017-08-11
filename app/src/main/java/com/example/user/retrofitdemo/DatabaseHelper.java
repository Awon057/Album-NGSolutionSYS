package com.example.user.retrofitdemo;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by User on 8/2/2017.
 */

public class DatabaseHelper extends SQLiteOpenHelper {

    public DatabaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS Task(Id integer primary key, Name VARCHAR,Email VARCHAR);");
        db.execSQL("CREATE TABLE IF NOT EXISTS Image(Id integer, Link VARCHAR);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
    public void addInformations(SQLiteDatabase db,List<photo> photos) {
        try {
            db.delete("Task", null, null);
            db.delete("Image", null, null);


            for (int i = 0; i < photos.size(); i++) {
                ContentValues cv = new ContentValues();
                cv.put("Name", photos.get(i).getAlbumName());
                Log.d("Name",photos.get(i).getAlbumName());
                cv.put("Email", photos.get(i).getUserName());
                final Long id = db.insert("Task", null, cv);

                ContentValues imv = new ContentValues();
                ArrayList<String> imageLink = photos.get(i).getImages();
                for (String S : imageLink) {
                    imv.put("Id", i);
                    imv.put("Link", S);
                    db.insert("Image", null, imv);
                }
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
    }
}
