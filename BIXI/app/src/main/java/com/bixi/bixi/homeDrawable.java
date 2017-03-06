package com.bixi.bixi;

import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.bixi.bixi.Adaptadores.RVAdapterMenu;
import com.bixi.bixi.Interfaces.RecyclerViewClickListener;
import com.bixi.bixi.Pojos.SimpleMenuPojo;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;

import java.util.ArrayList;

import butterknife.BindView;

public class homeDrawable extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, RecyclerViewClickListener {

    RecyclerView rv;
    ArrayList<SimpleMenuPojo> menu;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_drawable);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);





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
        SimpleMenuPojo pojo2 = new SimpleMenuPojo("Mi Perfil","2");
        menu.add(pojo2);
        SimpleMenuPojo pojo3 = new SimpleMenuPojo("Ofertas que me gustaron","3");
        menu.add(pojo3);
        SimpleMenuPojo pojo4 = new SimpleMenuPojo("Configuración","4");
        menu.add(pojo4);
        SimpleMenuPojo pojo5 = new SimpleMenuPojo("Salir","5");
        menu.add(pojo5);

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
        getMenuInflater().inflate(R.menu.home_drawable, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.inicio) {
            // Handle the camera action
        } else if (id == R.id.mi_perfil) {

        } else if (id == R.id.ofertas_like) {

        } else if (id == R.id.configuracion) {

        } else if (id == R.id.salir) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void recyclerViewListClicked(View v, int position) {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
    }
}
