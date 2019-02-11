package com.example.mrrobot.ihome.Services.ApiHome;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class HomeApiService  {

    private static Retrofit RETROFIT;
    private static IHomeApiService INSTANCE;
    public static IHomeApiService getInstance(){
        //The adapter will be a singleton

        if(RETROFIT == null) {
            Gson gson = new GsonBuilder()
                    .setLenient()
                    .create();
            RETROFIT = new Retrofit.Builder()
                    .baseUrl(ApiHomeConstants.BASE_URL)

                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .build();
        }
        INSTANCE=RETROFIT.create(IHomeApiService.class);

        return INSTANCE;
    }

}
