package com.bixi.bixi.Network;

import com.bixi.bixi.Interfaces.LoginService;
import com.bixi.bixi.Utility.Constants;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by telynet on 1/21/2017.
 */

public class Injector {



    public static Retrofit provideRetrofit2(String baseUrl)
    {

        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.addInterceptor(new LogJsonInterceptor());

        return new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create(provideGsonSetLetient()))
                .client(httpClient.build())
                .build();
    }

    public static Gson provideGsonSetLetient()
    {

        return new GsonBuilder()
            .setLenient()
            .create();
    }

    public  static UserService provideCreateUserService()
    {
        return provideRetrofit2(Constants.url).create(UserService.class);
    }

}
