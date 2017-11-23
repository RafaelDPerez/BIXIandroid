package com.bixi.bixi.Views;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.Typeface;
import android.os.Build;
import android.support.annotation.StringRes;
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

import com.bixi.bixi.Interfaces.GeneralInterface;
import com.bixi.bixi.Interfaces.HomePresenter;
import com.bixi.bixi.Interfaces.HomeView;
import com.bixi.bixi.Login;
import com.bixi.bixi.Pojos.ObjSearchProducts.ProductsJson;
import com.bixi.bixi.Pojos.ObjSearchProducts.ProductsLiketItJson;
import com.bixi.bixi.Pojos.Oferta;
import com.bixi.bixi.Pojos.OffersPointsClaim;
import com.bixi.bixi.Presenter.HomePresenterImpl;
import com.bixi.bixi.Presenter.PasswordActivityPresenterImpl;
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

public class DetailActivity extends AppCompatActivity implements HomeView,GeneralInterface {

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

    @BindView(R.id.imageView2)
    ImageView imgLikeIt;

    @BindView(R.id.progressBar3)
    ProgressBar pb;

    String productId;
    String token;

    HomePresenter presenter;
    private int position;

    private int positionImages = 0;
    private String[] selItemArray;
    private int cantidadMaximaImagenes = 0;
    private PasswordActivityPresenterImpl presenterPassword;
    private boolean likeIt = false;
    private boolean likeItOriginal = false;

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
        imgGoLeft.setVisibility(View.INVISIBLE);
        imgGoRight.setVisibility(View.INVISIBLE);
        Bundle extras = getIntent().getExtras();
        presenter = new HomePresenterImpl(this);
        presenterPassword = new PasswordActivityPresenterImpl(this);
        tvBixiPoints.setVisibility(View.VISIBLE);
        if(extras != null)
        {
            titulo.setText(extras.getString("nombre"));
            setImagen(extras.getString("url"));
            detalle.setText(extras.getString("detalle"));
            tvBixiPoints.setText(extras.getString("bixiPoints")+"T");
            productId = extras.getString("product_id");
            token = extras.getString(Constants.extraToken);
            position = extras.getInt("offerDisplay",0);
            likeIt = extras.getBoolean("likeIt");
            likeItOriginal = extras.getBoolean("likeIt");
            selItemArray = extras.getStringArray("arrayImages");
            cantidadMaximaImagenes = selItemArray.length;
        }

        if(cantidadMaximaImagenes == 0) {
            imgGoRight.setVisibility(View.INVISIBLE);
        }

        if(cantidadMaximaImagenes >0)
            btnOnClicks();
        else {
            imgGoRight.setVisibility(View.INVISIBLE);
            imgGoLeft.setVisibility(View.INVISIBLE);
        }

        setImageLikeOrNot();


        ApplyCustomFont.applyFont(this,findViewById(R.id.detail_detail_root),"fonts/Corbel.ttf");
    }

    private void setImageLikeOrNot()
    {
        if(likeIt)
            imgLikeIt.setImageResource(R.mipmap.likeit);
        else
            imgLikeIt.setImageResource(R.drawable.heart_outline);
    }

    private void btnOnClicks()
    {
        imgGoRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(positionImages < (cantidadMaximaImagenes - 1)) {
                    positionImages ++;

                    setImagen(selItemArray[positionImages]);

                    if (positionImages == (cantidadMaximaImagenes - 1)) {
                        updateArrows(imgGoRight, false);
                        updateArrows(imgGoLeft, true);
                    } else {
                        updateArrows(imgGoRight, true);
                        updateArrows(imgGoLeft, true);
                    }


                }

            }
        });

        imgGoLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(positionImages > 0) {

                    positionImages --;

                    setImagen(selItemArray[positionImages]);

                        if(positionImages == 0)
                        {
                            updateArrows(imgGoRight,true);
                            updateArrows(imgGoLeft,false);
                        }else
                        {
                            updateArrows(imgGoRight,true);
                            updateArrows(imgGoLeft,true);
                        }


                }

            }
        });
    }

    private void updateArrows(ImageView img, boolean show)
    {
        if(show)
            img.setVisibility(View.VISIBLE);
        else
            img.setVisibility(View.INVISIBLE);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle arrow click here
        if (item.getItemId() == android.R.id.home)
            finishActivity();

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        finishActivity();
    }

    private void finishActivity()
    {
        Intent returnIntent = new Intent();
        if(likeIt != likeItOriginal)
            returnIntent.putExtra("changes",true);
        else
            returnIntent.putExtra("changes",false);
        setResult(Activity.RESULT_OK,returnIntent);
        finish();
    }

    @OnClick(R.id.btnReclamarOfertaDetail)
    void reclamarOferta()
    {
        if(token != null && !token.equals(""))
        {
 //           Intent i = new Intent(DetailActivity.this,PasswordActivity.class);
 //           i.putExtra("product_id",productId);
 //           i.putExtra(Constants.extraToken,token);
 //           startActivity(i);
            askReclamarOferta();
        }else
        {
            alertaToken();
        }

    }

    private void askReclamarOferta()
    {
        AlertDialog.Builder builder;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            builder = new AlertDialog.Builder(this, android.R.style.Theme_Material_Light_Dialog);
        } else {
            builder = new AlertDialog.Builder(this);
        }
        builder.setTitle(titulo.getText().toString())
                .setMessage("¿Está seguro que desea reclamar esta oferta?")
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // continue with delete
                        OffersPointsClaim obj = new OffersPointsClaim();
                        obj.setProduct_id(productId);
                        presenterPassword.reclaimOffer(token,obj);
                    }
                })
                .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // do nothing
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }

    private void makeNoLimits()
    {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window w = getWindow(); // in Activity's onCreate() for instance
            w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean("likeIt",likeIt);
        outState.putBoolean("likeItOriginal",likeItOriginal);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        likeIt = savedInstanceState.getBoolean("likeIt");
        likeItOriginal = savedInstanceState.getBoolean("likeItOriginal");
        setImageLikeOrNot();

    }

    @OnClick(R.id.imageViewShare)
    void clicShare()
    {
            Intent shareIntent = new Intent(Intent.ACTION_SEND);
            shareIntent.setType("text/plain");
            shareIntent.putExtra(Intent.EXTRA_SUBJECT, "Mira esta oferta de Tixi Loyalty");
            shareIntent.putExtra(Intent.EXTRA_TEXT, "http://tixiloyalty.com/detalle-oferta/"+productId);
            startActivity(Intent.createChooser(shareIntent, "Share link using"));
    }

    @OnClick(R.id.imageView2)
    void clickLike()
    {
        if(token != null && !token.equals(""))
        {
            if(likeIt)
            {
                presenter.dislikeProductsFromServer(token,productId,position);
                likeIt = false;
                imgLikeIt.setImageResource(R.drawable.heart_outline);
            }else {
                presenter.likeProductsFromServer(token,productId,position);
                likeIt = true;
                imgLikeIt.setImageResource(R.mipmap.likeit);
            }

        }else
        {
            alertaToken();
        }

    }

    private void setImagen(String url)
    {
        if(url != null && !url.isEmpty())
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
        }else {
            pb.setVisibility(View.GONE);
        }

    }

    @Override
    public void showProgress() {
        pb.setVisibility(View.VISIBLE);
    }

    @Override
    public void exito(String msg) {
        AlertDialog.Builder builder = new AlertDialog.Builder(
                DetailActivity.this);
        builder.setMessage(msg);
        builder.setPositiveButton("OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,
                                        int which) {
                        finish();
                    }
                });
        builder.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                finish();
            }
        });
        builder.show();
    }

    @Override
    public void error(String msg) {
        AlertDialog.Builder builder = new AlertDialog.Builder(
                DetailActivity.this);
        builder.setTitle("Error");
        builder.setMessage(msg);
        builder.setPositiveButton("OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,
                                        int which) {

                    }
                });
        builder.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {

            }
        });
        builder.show();
    }

    @Override
    public void hideProgress() {
        pb.setVisibility(View.INVISIBLE);
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
