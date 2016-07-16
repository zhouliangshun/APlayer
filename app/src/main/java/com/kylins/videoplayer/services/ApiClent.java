package com.kylins.videoplayer.services;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by j-zhouliangshun on 2016/7/16.
 */
public class ApiClent {
    private static ApiClent ourInstance = new ApiClent();
    private DataApi dataApi;

    public static ApiClent getInstance() {
        return ourInstance;
    }

    private ApiClent() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://www.4006000790.com")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        dataApi = retrofit.create(DataApi.class);
    }

    public DataApi getDataApi() {
        return dataApi;
    }
}
