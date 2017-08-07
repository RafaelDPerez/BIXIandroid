package com.bixi.bixi.Network;

import com.bixi.bixi.BIXI;
import com.bixi.bixi.BuildConfig;
import com.bixi.bixi.Interfaces.LoginService;
import com.bixi.bixi.Utility.Constants;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import timber.log.Timber;

/**
 * Created by JohnnyGilMejia on 1/21/2017.
 */

public class Injector {

    private static final String CACHE_CONTROL = "Cache-Control";

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


        return new OkHttpClient.Builder()
                .addInterceptor(provideHttpLoginInterceptor())
                .addInterceptor(provideOfflineCacheInterceptor())
                .addNetworkInterceptor(provideCacheInterceptor())
                .cache(provideCache())
                .build();
    }

    private static Cache provideCache ()
    {
        Cache cache = null;
        try
        {
            cache = new Cache( new File(BIXI.getInstance().getCacheDir(), "http-cache" ),
                    10 * 1024 * 1024 ); // 10 MB
        }
        catch (Exception e)
        {
            Timber.e( e, "Could not create Cache!" );
        }
        return cache;
    }

    public static Interceptor provideCacheInterceptor()
    {
        return new Interceptor()
        {
            @Override
            public Response intercept (Chain chain) throws IOException
            {
                Response response = chain.proceed( chain.request() );

                // re-write response header to force use of cache
                CacheControl cacheControl = new CacheControl.Builder()
                        .maxAge( 2, TimeUnit.MINUTES )
                        .build();

                return response.newBuilder()
                        .header( CACHE_CONTROL, cacheControl.toString() )
                        .build();
            }
        };
    }

    public static Interceptor provideOfflineCacheInterceptor()
    {
        return new Interceptor()
        {
            @Override
            public Response intercept (Chain chain) throws IOException
            {
                Request request = chain.request();

                if ( !BIXI.hasNetwork() )
                {
                    CacheControl cacheControl = new CacheControl.Builder()
                            .maxStale( 7, TimeUnit.DAYS )
                            .build();

                    request = request.newBuilder()
                            .cacheControl( cacheControl )
                            .build();
                }

                return chain.proceed( request );
            }
        };
    }

    private static HttpLoggingInterceptor provideHttpLoginInterceptor()
    {
        HttpLoggingInterceptor loggin = new HttpLoggingInterceptor( new HttpLoggingInterceptor.Logger()
        {
            @Override
            public void log (String message)
            {
                Timber.d( message );
            }
        } );
        loggin.setLevel(BuildConfig.DEBUG ? HttpLoggingInterceptor.Level.BODY : HttpLoggingInterceptor.Level.NONE);
        return loggin;
    }

    public  static UserService provideCreateUserService()
    {
        return provideRetrofit2(Constants.url).create(UserService.class);
    }

}
