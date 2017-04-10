package com.bixi.bixi.Views;


import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Build;
import android.os.Bundle;
import android.app.Fragment;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.bixi.bixi.Adaptadores.RVAdapterHomeLikeIt;
import com.bixi.bixi.Interfaces.HomePresenter;
import com.bixi.bixi.Interfaces.HomeView;
import com.bixi.bixi.Interfaces.RecyclerViewClickListenerHome;
import com.bixi.bixi.Pojos.ObjSearchProducts.ProductsJson;
import com.bixi.bixi.Pojos.ObjSearchProducts.ProductsLiketItJson;
import com.bixi.bixi.Pojos.ObjSearchProducts.ResultProductsJson;
import com.bixi.bixi.Pojos.ObjSearchProducts.ResultProductsLikerItJson;
import com.bixi.bixi.Pojos.Oferta;
import com.bixi.bixi.Presenter.HomePresenterImpl;
import com.bixi.bixi.R;
import com.bixi.bixi.Utility.Constants;
import com.github.clans.fab.FloatingActionMenu;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeLikeIt extends Fragment implements HomeView,RecyclerViewClickListenerHome {

    @BindView(R.id.my_recycler_view_home)
    RecyclerView rv;

    @BindView(R.id.progressBarHome)
    ProgressBar bar;

    @BindView(R.id.floatingActionButton)
    FloatingActionMenu fab;

    @BindView(R.id.swipeRefreshLayout)
    SwipeRefreshLayout swipeRefreshLayout;

    private static HomeLikeIt instance;
    private View view;
    private HomePresenter presenter;
    private String token;
    private ArrayList<ResultProductsLikerItJson> resultProductsLikerItJson;
    private RVAdapterHomeLikeIt adapter;

    public HomeLikeIt() {
        // Required empty public constructor
    }

    public static HomeLikeIt getInstance()
    {
        if(instance == null)
            instance = new HomeLikeIt();

        return instance;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        loadView(inflater,container);
        ButterKnife.bind(this,view);
        presenter = new HomePresenterImpl(this);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Bundle extras = getActivity().getIntent().getExtras();
        if(extras != null){
            token = extras.getString(Constants.extraToken);
        }

        rv.addItemDecoration(
                new HorizontalDividerItemDecoration.Builder(getActivity())
                        .color(Color.GRAY)
                        .margin(50)
                        .build());

        rv.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        rv.setLayoutManager(llm);

        bar.setVisibility(View.GONE);
        rv.setVisibility(View.VISIBLE);

        bar.getIndeterminateDrawable().setColorFilter(Color.BLUE, PorterDuff.Mode.MULTIPLY);
        presenter.cargarProductosFavoritosFromServer(token);
        swipeRefreshRV();
    }

    private void loadView(LayoutInflater inflater, ViewGroup container)
    {
        view = inflater.inflate(R.layout.activity_home,container,false);
    }

    private void swipeRefreshRV()
    {
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // Refresh items
                refreshItems();
            }
        });
    }

    private void refreshItems() {
        presenter.cargarProductosFavoritosFromServer(token);
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
     //   Toast.makeText(getActivity(),"No ha sido posible cargar las ofertas",Toast.LENGTH_SHORT).show();
        if(swipeRefreshLayout.isRefreshing())
            swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void operacionExitosa(List<Oferta> ofertas) {

    }

    @Override
    public void operacionExitosaFromServer(ProductsJson productsJson) {

    }

    @Override
    public void onResume() {
        super.onResume();
    //    presenter.cargarProductosFavoritosFromServer(token);
    }

    @Override
    public void operacionExitosaLikeItFromServer(ProductsLiketItJson productsLiketItJson) {
        if(productsLiketItJson.getResult() != null)
            resultProductsLikerItJson = (ArrayList<ResultProductsLikerItJson>) productsLiketItJson.getResult();


        adapter = new RVAdapterHomeLikeIt(resultProductsLikerItJson,getInstance().getActivity(),this);
        rv.setAdapter(adapter);
        if(swipeRefreshLayout.isRefreshing())
            swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void updateRV(boolean success,int position) {
        if(success)
        {
            adapter.removeItem(position);
        }
    }

    public ResultProductsLikerItJson getItem(int position)
    {
        return resultProductsLikerItJson.get(position);
    }

    @Override
    public void recyclerViewListClicked(View v, int position, ResultProductsJson resultProductsJson) {


    }

    @Override
    public void recyclerViewClicked(View v, int position) {
        ResultProductsLikerItJson obj = getItem(position);
        String url = "";
        if(obj.getImages() != null && obj.getImages().size() > 0 && obj.getImages().get(0) != null)
            url = obj.getImages().get(0);
        else
            url = "http://static.viagrupo.com/userupload/vargas01-04.png";
        Intent i = new Intent(getActivity(),DetailActivity.class);
        i.putExtra("nombre",obj.getName());
        i.putExtra("url",url);
        i.putExtra("detalle",obj.getDescription());
        i.putExtra("bixiPoints",obj.getPoints());
        i.putExtra(Constants.extraToken,token);

/*
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
        {
            String animationName = getActivity().getString(R.string.animation_img);
            ActivityOptionsCompat activityOptionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation(getActivity(),v,animationName);
            startActivity(i,activityOptionsCompat.toBundle());
        }else
        */
            startActivity(i);
    }

    @Override
    public void recyclerViewRemoveItem(View v, int position) {
        //resultProductsLikerItJson.remove(position);
        ResultProductsLikerItJson obj = getItem(position);
  //      adapter.removeItem(position);
        presenter.dislikeProductsFromServer(token,obj.getProductId(),position);
    }

    @Override
    public void recyclerViewLiketItem(View v, int position, ResultProductsJson resultProductsJson) {

    }
}
