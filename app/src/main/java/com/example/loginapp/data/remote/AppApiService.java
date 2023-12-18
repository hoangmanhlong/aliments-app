package com.example.loginapp.data.remote;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AppApiService {
    private static String BASE_URL =  "https://dummyjson.com";
    public static AppApi retrofit = new Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl(BASE_URL)
        .build()
        .create(AppApi.class);
}
