package com.example.user.retrofitdemo.util;

import com.example.user.retrofitdemo.model.Photos;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by User on 8/2/2017.
 */

public interface ApiInterface {

    @GET("awon")
    Call<Photos> getPhotos();
}
