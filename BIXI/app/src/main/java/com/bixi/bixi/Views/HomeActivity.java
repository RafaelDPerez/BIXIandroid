package com.bixi.bixi.Views;

import android.animation.Animator;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AnimationUtils;
import android.view.animation.Interpolator;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.bixi.bixi.Adaptadores.RVAdapterHome;
import com.bixi.bixi.Interfaces.HomePresenter;
import com.bixi.bixi.Interfaces.HomeView;
import com.bixi.bixi.Interfaces.RecyclerViewClickListener;
import com.bixi.bixi.Login;
import com.bixi.bixi.MapsActivity;
import com.bixi.bixi.Pojos.ObjSearchProducts.ProductsJson;
import com.bixi.bixi.Pojos.ObjSearchProducts.ProductsLiketItJson;
import com.bixi.bixi.Pojos.Oferta;
import com.bixi.bixi.Presenter.HomePresenterImpl;
import com.bixi.bixi.R;
import com.bixi.bixi.Utility.Constants;
import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HomeActivity extends AppCompatActivity implements HomeView, RecyclerViewClickListener {

    @BindView(R.id.my_recycler_view_home)
    RecyclerView rv;

    @BindView(R.id.progressBarHome)
    ProgressBar bar;

    @BindView(R.id.floatingActionButton)
    FloatingActionMenu fab;

    private List<Oferta> oferta;
    boolean click = false;
    private FloatingActionButton fabMapa;
    private HomePresenter presenter;
    private String token;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);
        presenter = new HomePresenterImpl(this);
        Bundle extras = getIntent().getExtras();
        if(extras != null){
            token = extras.getString(Constants.extraToken);
        }


        rv.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        rv.setLayoutManager(llm);

        bar.setVisibility(View.GONE);
        rv.setVisibility(View.VISIBLE);

        setFA();
     //   makeNoLimits();
        inicializarFab();
        onClickFabs();
    }

    private void onClickFabs()
    {

        fabMapa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(HomeActivity.this, MapsActivity.class);
                startActivity(myIntent);
            }
        });

    }

    private void inicializarFab()
    {
        fabMapa = initSubFabs("",R.mipmap.ic_map_white_24dp);
        fab.addMenuButton(fabMapa);
    }

    private void makeNoLimits()
    {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window w = getWindow(); // in Activity's onCreate() for instance
            w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);


        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        presenter.cargarOfertas();
        if(token != null && !token.equals(""))
            presenter.cargarProductsFromServer("botella");

    }

    private FloatingActionButton initSubFabs(String label, int drawable)
    {

        FloatingActionButton fab = new FloatingActionButton(getApplicationContext());
        fab.setColorNormal(getResources().getColor(R.color.purpleBixi));
        fab.setColorPressed(getResources().getColor(R.color.purpleBixi2));
        fab.setButtonSize(FloatingActionButton.SIZE_NORMAL);
        fab.setLabelText(label);
        fab.setImageResource(drawable);

        return fab;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menuhome, menu);
        return super.onCreateOptionsMenu(menu);
    }

    // handle button activities
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.mybutton) {
            // do something here

            Intent i = new Intent(HomeActivity.this,SearchActivity.class);
            startActivity(i);
            /*
            De momento ya no cargamos ese fragmento,
            en lugar de eso cargamos una nueva pantalla completa.
             */
            //loadSearchFragment();
        }
        return super.onOptionsItemSelected(item);
    }

    private void loadSearchFragment()
    {

            LayoutInflater inflater = LayoutInflater.from(this);
            View dialog_layout = inflater.inflate(R.layout.home_search_layout, null);

            AlertDialog.Builder db = new AlertDialog.Builder(this);
            db.setView(dialog_layout);


            db.setPositiveButton(R.string.aceptar, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {

                }
            });

            db.setNegativeButton(R.string.cancel, new

                    DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });


            AlertDialog dialog = db.show();
        dialog.getButton(Dialog.BUTTON_POSITIVE).setTextColor(getResources().getColor(R.color.purpleBixi));
        dialog.getButton(Dialog.BUTTON_NEGATIVE).setTextColor(getResources().getColor(R.color.purpleBixi));
    }

    private void setFA()
    {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            fab.setScaleX(0);
            fab.setScaleY(0);

            final Interpolator interpolator = AnimationUtils.loadInterpolator(getBaseContext(),android.R.interpolator.fast_out_slow_in);

            fab.animate()
                    .scaleX(1)
                    .scaleY(1)
                    .setInterpolator(interpolator)
                    .setDuration(600)
                    .setStartDelay(1000)
                    .setListener(new Animator.AnimatorListener() {
                        @Override
                        public void onAnimationStart(Animator animation) {

                        }

                        @Override
                        public void onAnimationEnd(Animator animation) {
//                            fab.animate()
//                                    .scaleY(0)
//                                    .scaleX(0)
//                                    .setInterpolator(interpolator)
//                                    .setDuration(600)
//                                    .start();
                        }

                        @Override
                        public void onAnimationCancel(Animator animation) {

                        }

                        @Override
                        public void onAnimationRepeat(Animator animation) {

                        }
                    });
        }
    }


    @Override
    public void showProgress() {
        bar.setVisibility(View.VISIBLE);
        rv.setVisibility(View.GONE);
    }

    @Override
    public void hideProgress() {
        bar.setVisibility(View.GONE);
        rv.setVisibility(View.VISIBLE);
    }

    @Override
    public void setError() {
        Toast.makeText(this,"No ha sido posible cargar las ofertas",Toast.LENGTH_SHORT);
    }

    @Override
    public void operacionExitosa(List<Oferta> ofertas) {
      //  presenter = new HomePresenterImpl(this);
      //  this.oferta = ofertas;
      //  RVAdapterHome adapter = new RVAdapterHome(ofertas,this,this);
       // rv.setAdapter(adapter);
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


    @Override
    public void recyclerViewListClicked(View v, int position) {

        Intent i = new Intent(this,DetailActivity.class);
        i.putExtra("nombre",getItem(position).getNombre());
        i.putExtra("url",getItem(position).getDireccionImagen());
        startActivity(i);


    }

    public Oferta getItem(int position)
    {
        return oferta.get(position);
    }
}
