package com.bixi.bixi;

import android.app.Application;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import timber.log.Timber;

/**
 * Created by Johnny Gil Mejia on 2/2/17.
 */

public class BIXI extends Application {

    private static BIXI instance;

    @Override
    public void onCreate() {
        super.onCreate();

        instance = this;

        if(BuildConfig.DEBUG)
        {
            Timber.plant(new Timber.DebugTree());
        }

        Timber.i("Creating our Application");

    }

    public static BIXI getInstance()
    {
        return instance;
    }

    public static boolean hasNetwork ()
    {
        return getInstance().checkIfHasNetwork();
    }

    public boolean checkIfHasNetwork()
    {
        ConnectivityManager cm = (ConnectivityManager)
                getSystemService(
                        Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = cm.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isConnected();
    }
}
