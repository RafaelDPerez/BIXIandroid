package com.bixi.bixi;

import android.app.Application;

import timber.log.Timber;

/**
 * Created by Johnny Gil Mejia on 2/2/17.
 */

public class BIXI extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        if(BuildConfig.DEBUG)
        {
            Timber.plant(new Timber.DebugTree());
        }else
        {
   //         Timber.plant(new ReleaseTree());
        }

    }
}
