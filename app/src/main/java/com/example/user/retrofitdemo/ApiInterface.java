package com.example.user.retrofitdemo;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;

/**
 * Created by User on 8/2/2017.
 */

public interface ApiInterface {

    @GET("awon")
    Call<Photos> getPhotos();
}
