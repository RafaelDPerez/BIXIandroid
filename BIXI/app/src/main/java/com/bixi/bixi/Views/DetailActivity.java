package com.bixi.bixi.Views;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.Typeface;
import android.os.Build;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bixi.bixi.Interfaces.HomePresenter;
import com.bixi.bixi.Interfaces.HomeView;
import com.bixi.bixi.Login;
import com.bixi.bixi.Pojos.ObjSearchProducts.ProductsJson;
import com.bixi.bixi.Pojos.ObjSearchProducts.ProductsLiketItJson;
import com.bixi.bixi.Pojos.Oferta;
import com.bixi.bixi.Presenter.HomePresenterImpl;
import com.bixi.bixi.R;
import com.bixi.bixi.Utility.Constants;
import com.bixi.bixi.bixi.basics.ApplyCustomFont;
import com.bixi.bixi.bixi.basics.FontChangeCrawler;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class DetailActivity extends AppCompatActivity implements HomeView {

    @BindView(R.id.ivOferta)
    ImageView imgDetail;
    @BindView(R.id.textViewTitulo)
    TextView titulo;
    @BindView(R.id.textViewDetalle)
    TextView detalle;
    @BindView(R.id.tvBixiPoints)
    TextView tvBixiPoints;

    @BindView(R.id.imageView4)
    ImageView imgGoLeft;

    @BindView(R.id.imageView3)
    ImageView imgGoRight;

    @BindView(R.id.progressBar3)
    ProgressBar pb;

    String productId;
    String token;

    HomePresenter presenter;
    private int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        overridePendingTransition(0, 0);
        ButterKnife.bind(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // add back arrow to toolbar
        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setTitle("");
        }
        pb.getIndeterminateDrawable().setColorFilter(getResources().getColor(R.color.purpleBixi2), PorterDuff.Mode.MULTIPLY);
        pb.setVisibility(View.VISIBLE);
      //  makeNoLimits();
        Bundle extras = getIntent().getExtras();
        presenter = new HomePresenterImpl(this);
        tvBixiPoints.setVisibility(View.VISIBLE);
        imgGoLeft.setVisibility(View.INVISIBLE);
        imgGoRight.setVisibility(View.INVISIBLE);
        if(extras != null)
        {
            titulo.setText(extras.getString("nombre"));
            setImagen(extras.getString("url"));
            detalle.setText(extras.getString("detalle"));
            tvBixiPoints.setText(extras.getString("bixiPoints")+"B");
            productId = extras.getString("product_id");
            token = extras.getString(Constants.extraToken);
            position = extras.getInt("offerDisplay",0);
        }

        ApplyCustomFont.applyFont(this,findViewById(R.id.detail_detail_root),"fonts/Corbel.ttf");
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle arrow click here
        if (item.getItemId() == android.R.id.home) {
            finish(); // close this activity and return to preview activity (if there is any)
        }

        return super.onOptionsItemSelected(item);
    }

    @OnClick(R.id.btnReclamarOfertaDetail)
    void reclamarOferta()
    {
        if(token != null && !token.equals(""))
        {
            Intent i = new Intent(DetailActivity.this,PasswordActivity.class);
            i.putExtra("product_id",productId);
            i.putExtra(Constants.extraToken,token);
            startActivity(i);
        }else
        {
            alertaToken();
        }

    }

    private void makeNoLimits()
    {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window w = getWindow(); // in Activity's onCreate() for instance
            w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);


        }
    }

    @OnClick(R.id.imageView2)
    void clickLike()
    {
        if(token != null && !token.equals(""))
        {
            presenter.likeProductsFromServer(token,productId,position);
        }else
        {
            alertaToken();
        }

    }

    private void setImagen(String url)
    {
        Picasso.with(this)
                .load(url)
                .fit()
                .placeholder(R.color.colorAccent)
                .error(R.color.colorAccent)
                .into(imgDetail, new Callback() {
                    @Override
                    public void onSuccess() {
                        pb.setVisibility(View.INVISIBLE);
                    }

                    @Override
                    public void onError() {
                        pb.setVisibility(View.INVISIBLE);
                    }
                });
    }

    @Override
    public void showProgress() {

    }

    @Override
    public void hideProgress() {

    }

    @Override
    public void setError() {

    }

    @Override
    public void operacionExitosa(List<Oferta> ofertas) {

    }

    @Override
    public void operacionExitosaFromServer(ProductsJson productsJson) {

    }

    @Override
    public void operacionExitosaLikeItFromServer(ProductsLiketItJson productsLiketItJson) {

    }

    @Override
    public void updateRV(boolean success, int position) {

    }

    private void alertaToken()
    {

        final AlertDialog alertDialog = new AlertDialog.Builder(DetailActivity.this,R.style.DialogAlertTheme).create();
        alertDialog.setTitle("Alerta");
        alertDialog.setMessage("Debe iniciar sesión o registrarse.");
        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "Aceptar",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Intent newIntent = new Intent(DetailActivity.this,Login.class);
                        startActivity(newIntent);
                    }
                });
        alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "Cancelar",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        alertDialog.dismiss();
                    }
                });
        alertDialog.show();
    }
}
