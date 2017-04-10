package com.bixi.bixi;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.DialogPreference;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.bixi.bixi.Adaptadores.RVAdapterMenu;
import com.bixi.bixi.Interfaces.RecyclerViewClickListener;
import com.bixi.bixi.Pojos.SimpleMenuPojo;
import com.bixi.bixi.Utility.Constants;
import com.bixi.bixi.Views.HomeActivity;
import com.bixi.bixi.Views.HomeFragment;
import com.bixi.bixi.Views.HomeLikeIt;
import com.bixi.bixi.Views.HomeProfileFragment;
import com.bixi.bixi.Views.SearchActivity;
import com.bixi.bixi.Views.TransaccionesFragment;
import com.google.android.gms.maps.MapView;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;

import java.util.ArrayList;

import butterknife.BindView;

public class homeDrawable extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, RecyclerViewClickListener {

    RecyclerView rv;
    ArrayList<SimpleMenuPojo> menu;
    private HomeFragment homeFragment;
    private HomeProfileFragment homeProfileFragment;
    public static Fragment actual_Fragment = null;
    private String token;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_drawable);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        SharedPreferences prefs =
                getSharedPreferences(Constants.appPreferences, Context.MODE_PRIVATE);
       token = prefs.getString("token", "");

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        View headerLayout = navigationView.getHeaderView(0);

        rv = (RecyclerView) navigationView.findViewById(R.id.list_view_inside_nav);
        rv.addItemDecoration(
                new HorizontalDividerItemDecoration.Builder(this)
                        .color(Color.WHITE)
                        .build());
        initializeRV();
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        if(actual_Fragment == null)
        {
            getFragmentHome();
            Log.d("Fragment null?","si");
        }else
        {
            Fragment fg = actual_Fragment;
            actual_Fragment = null;
            if(fg instanceof HomeProfileFragment)
                getFragmenProfile();
            else if(fg instanceof HomeLikeIt)
                getFragmentHomeLikeIt();
            else if(fg instanceof TransaccionesFragment)
                launchHistorical();
            else
                getFragmentHome();

        }

    }



    private void initializeRV()
    {

        LinearLayoutManager llm = new LinearLayoutManager(this);
        rv.setLayoutManager(llm);
        menu = createMenuObj();

        RVAdapterMenu adapterMenu = new RVAdapterMenu(menu,this,this);
        rv.setAdapter(adapterMenu);
    }

    private ArrayList<SimpleMenuPojo> createMenuObj()
    {
        ArrayList<SimpleMenuPojo> menu = new ArrayList<>();
        SimpleMenuPojo pojo1 = new SimpleMenuPojo("Inicio","0");
        menu.add(pojo1);
        SimpleMenuPojo pojo2 = new SimpleMenuPojo("Mi Perfil","1");
        menu.add(pojo2);
        SimpleMenuPojo pojo3 = new SimpleMenuPojo("Ofertas que me gustan","2");
        menu.add(pojo3);
        SimpleMenuPojo pojo4 = new SimpleMenuPojo("Ofertas cerca de mi","3");
        menu.add(pojo4);
        SimpleMenuPojo pojo5 = new SimpleMenuPojo("Transacciones","4");
        menu.add(pojo5);
        SimpleMenuPojo pojo6 = new SimpleMenuPojo("Salir","5");
        menu.add(pojo6);
        return menu;
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
     //   if(actual_Fragment instanceof HomeFragment)
       //     getMenuInflater().inflate(R.menu.menuhome, menu);

        return true;
    }



    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();



        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void recyclerViewListClicked(View v, int position) {

        if(position == 0)
            getFragmentHome();
        else if(position == 1) {

            if(isTokenValid())
                getFragmenProfile();
            else
                alertaToken();
        }
        else if(position == 2)
        {
            if(isTokenValid())
                getFragmentHomeLikeIt();
            else
                alertaToken();
        }
        else if(position == 3)
            launchMapsActivity();
        else if(position == 4) {

            if(isTokenValid())
                launchHistorical();
            else
                alertaToken();
        }
        else if(position == 5)
            logout();

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
    }

    private boolean isTokenValid()
    {
        SharedPreferences prefs =
                getSharedPreferences(Constants.appPreferences, Context.MODE_PRIVATE);
        String token = prefs.getString("token", "");

        if(token != null && !token.equals(""))
            return true;
        else
            return false;
    }

    private void launchHistorical() {
        if(!(actual_Fragment instanceof TransaccionesFragment))
        {
            FragmentTransaction ft;
            TransaccionesFragment fg = TransaccionesFragment.getInstance();
            fg.setRetainInstance(false);

            ft = getFragmentManager().beginTransaction();
            if(actual_Fragment != null)
                ft.remove(actual_Fragment);
            actual_Fragment = fg;
            ft.replace(R.id.details,fg);
            ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
            ft.commit();
        }
    }

    private void launchMapsActivity()
    {
        SharedPreferences prefs =
                getSharedPreferences(Constants.appPreferences, Context.MODE_PRIVATE);
        String token = prefs.getString("token", "");
        Intent myIntent = new Intent(this, MapsActivity.class);
        myIntent.putExtra(Constants.extraToken,token);
        startActivity(myIntent);
    }

    private void logout()
    {
        SharedPreferences prefs =
                getSharedPreferences(Constants.appPreferences, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("token", "");
        editor.commit();
        Intent newIntent = new Intent(this,Login.class);
        newIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        actual_Fragment = null;
        startActivity(newIntent);
    }

    private void getFragmentHomeLikeIt()
    {
        if(!(actual_Fragment instanceof HomeLikeIt))
        {
            FragmentTransaction ft;
            HomeLikeIt fg = HomeLikeIt.getInstance();
            fg.setRetainInstance(false);

            ft = getFragmentManager().beginTransaction();
            if(actual_Fragment != null)
                ft.remove(actual_Fragment);
            actual_Fragment = fg;
            ft.replace(R.id.details,fg,"fg");
            ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
            ft.commit();
        }
    }

    private void getFragmentHome()
    {
        if(!(actual_Fragment instanceof HomeFragment))
        {
            FragmentTransaction ft;
            homeFragment = HomeFragment.getInstance();
            homeFragment.setRetainInstance(false);

            ft = getFragmentManager().beginTransaction();
            if (actual_Fragment != null)
                ft.remove(actual_Fragment);
            actual_Fragment = homeFragment;
            ft.replace(R.id.details, homeFragment);
            ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
            ft.commit();
        }

    }

    private void getFragmenProfile()
    {
        if(!(actual_Fragment instanceof  HomeProfileFragment))
        {
            FragmentTransaction ft;
            homeProfileFragment = HomeProfileFragment.getInstance();
            homeProfileFragment.setRetainInstance(false);

            ft = getFragmentManager().beginTransaction();
            if(actual_Fragment != null)
                ft.remove(actual_Fragment);
            actual_Fragment = homeProfileFragment;
            ft.replace(R.id.details,homeProfileFragment);
            ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
            ft.commit();
        }

    }

    private void alertaToken()
    {

        final AlertDialog alertDialog = new AlertDialog.Builder(homeDrawable.this,R.style.DialogAlertTheme).create();
        alertDialog.setTitle("Alerta");
        alertDialog.setMessage("Debe iniciar sesión o registrarse.");
        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "Aceptar",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Intent newIntent = new Intent(homeDrawable.this,Login.class);
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
