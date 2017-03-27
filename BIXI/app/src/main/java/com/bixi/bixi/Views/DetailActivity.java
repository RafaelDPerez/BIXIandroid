package com.bixi.bixi.Views;

import android.content.Intent;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.bixi.bixi.R;
import com.bixi.bixi.Utility.Constants;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class DetailActivity extends AppCompatActivity {

    @BindView(R.id.imageViewDetail)
    ImageView imgDetail;
    @BindView(R.id.textViewTitulo)
    TextView titulo;
    @BindView(R.id.textViewDetalle)
    TextView detalle;
    @BindView(R.id.tvBixiPoints)
    TextView tvBixiPoints;

    String productId;
    String token;

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
            detalle.setText(extras.getString("detalle"));
            tvBixiPoints.setText(extras.getString("bixiPoints")+"B");
            productId = extras.getString("product_id");
            token = extras.getString(Constants.extraToken);
        }
    }

    @OnClick(R.id.btnReclamarOfertaDetail)
    void reclamarOferta()
    {
        Intent i = new Intent(DetailActivity.this,PasswordActivity.class);
        i.putExtra("product_id",productId);
        i.putExtra(Constants.extraToken,token);
        startActivity(i);
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
