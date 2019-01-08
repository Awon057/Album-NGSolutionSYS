package com.example.user.retrofitdemo.model;

import com.example.user.retrofitdemo.model.photo;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by User on 8/2/2017.
 */

public class Photos {
    @SerializedName("photos")
    ArrayList<photo> photos;

    public void setPhotos(ArrayList<photo> photos) {
        this.photos = photos;
    }

    public ArrayList<photo> getPhotos() {
        return photos;
    }
}
