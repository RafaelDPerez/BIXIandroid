package com.bixi.bixi.bixi.basics;

import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.bixi.bixi.R;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class RecuperarContrasena extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recuperar_contrasena);
        inicializaciones();

    }

    @OnClick(R.id.imgFinish)
    void sendRequestToServer()
    {
        finish();
    }

    private void inicializaciones()
    {
        ButterKnife.bind(this);
        makeNoLimits();
        ApplyCustomFont.applyFont(this,findViewById(R.id.content_recuperar_contrasena),"fonts/Corbel.ttf");
    }


    private void makeNoLimits()
    {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window w = getWindow(); // in Activity's onCreate() for instance
            w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);


        }
    }

}
