package com.bixi.bixi.Views;

import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.bixi.bixi.R;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailActivity extends AppCompatActivity {

    @BindView(R.id.imageViewDetail)
    ImageView imgDetail;
    @BindView(R.id.textViewTitulo)
    TextView titulo;
    @BindView(R.id.textViewDetalle)
    TextView detalle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        ButterKnife.bind(this);
      //  makeNoLimits();
        Bundle extras = getIntent().getExtras();
        if(extras != null)
        {
            titulo.setText(extras.getString("nombre"));
            setImagen(extras.getString("url"));
        }
    }

    private void makeNoLimits()
    {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window w = getWindow(); // in Activity's onCreate() for instance
            w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);


        }
    }

    private void setImagen(String url)
    {
        Picasso.with(this)
                .load(url)
                .fit()
                .placeholder(R.color.colorPrimary)
                .error(R.color.colorAccent)
                .into(imgDetail);
    }

}
