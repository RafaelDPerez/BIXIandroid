package com.bixi.bixi;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.bixi.bixi.Utility.Constants;
import com.bixi.bixi.Views.HomeActivity;

/**
 * Created by telynet on 12/25/2016.
 */

public class SplashActivity extends AppCompatActivity {

    // Note: Your consumer key and secret should be obfuscated in your source code before shipping.


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SharedPreferences prefs =
                getSharedPreferences(Constants.appPreferences, Context.MODE_PRIVATE);
        String token = prefs.getString("token", "");
        Intent intent;
        if (token != null && !token.equals("")) {
            intent = new Intent(this, homeDrawable.class);
            intent.putExtra(Constants.extraToken,token);
        } else
        {
            intent = new Intent(this, Login.class);
        }


        startActivity(intent);
        finish();
    }
}
