package com.example.user.retrofitdemo.util;

import android.content.Context;
import android.util.Log;

import java.io.File;

import okhttp3.Cache;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by User on 8/2/2017.
 */

public class ApiClient {


    public static final String Base_URL = "http://demo6706219.mockable.io/";
    public  Retrofit retrofit = null;
    OkHttpClient okHttpClient;

    public void setOkHttpClient(Cache cache) {
        Log.d("Cache",cache.toString());
        okHttpClient = new OkHttpClient.Builder()
                .cache(cache)
                .build();
    }

    public Retrofit getApiClient() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(Base_URL)
                    .client(okHttpClient)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }

}
