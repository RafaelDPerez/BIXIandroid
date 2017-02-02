package com.bixi.bixi.Network;

import com.bixi.bixi.BuildConfig;
import com.bixi.bixi.Interfaces.LoginService;
import com.bixi.bixi.Utility.Constants;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by JohnnyGilMejia on 1/21/2017.
 */

public class Injector {



    private static Retrofit provideRetrofit2(String baseUrl)
    {

        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.addInterceptor(new LogJsonInterceptor());

        return new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create(provideGsonSetLetient()))
                .client(provideLoggingCapableHttpClient())
                .build();
    }

    private static Gson provideGsonSetLetient()
    {

        return new GsonBuilder()
            .setLenient()
            .create();
    }

    private static OkHttpClient provideLoggingCapableHttpClient()
    {
        HttpLoggingInterceptor loggin = new HttpLoggingInterceptor();
        loggin.setLevel(BuildConfig.DEBUG ? HttpLoggingInterceptor.Level.BODY : HttpLoggingInterceptor.Level.NONE);

        return new OkHttpClient.Builder()
                .addInterceptor(loggin)
                .build();
    }

    public  static UserService provideCreateUserService()
    {
        return provideRetrofit2(Constants.url).create(UserService.class);
    }

}
