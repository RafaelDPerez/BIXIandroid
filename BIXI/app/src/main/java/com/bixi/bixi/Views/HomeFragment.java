package com.bixi.bixi.Views;


import android.app.Fragment;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bixi.bixi.Adaptadores.RVAdapterHome;
import com.bixi.bixi.Interfaces.HomePresenter;
import com.bixi.bixi.Interfaces.HomeView;
import com.bixi.bixi.Interfaces.RecyclerViewClickListener;
import com.bixi.bixi.Interfaces.RecyclerViewClickListenerHome;
import com.bixi.bixi.MapsActivity;
import com.bixi.bixi.Pojos.ObjSearchProducts.Product;
import com.bixi.bixi.Pojos.ObjSearchProducts.ProductsJson;
import com.bixi.bixi.Pojos.ObjSearchProducts.ProductsLiketItJson;
import com.bixi.bixi.Pojos.ObjSearchProducts.ResultProductsJson;
import com.bixi.bixi.Pojos.ObjSearchProducts.ResultProductsLikerItJson;
import com.bixi.bixi.Pojos.Oferta;
import com.bixi.bixi.Presenter.HomePresenterImpl;
import com.bixi.bixi.R;
import com.bixi.bixi.Utility.Constants;
import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment implements HomeView,RecyclerViewClickListenerHome {

    private static HomeFragment instance;
    private View view;
    @BindView(R.id.my_recycler_view_home)
    RecyclerView rv;

    @BindView(R.id.progressBarHome)
    ProgressBar bar;

    @BindView(R.id.floatingActionButton)
    FloatingActionMenu fab;

    @BindView(R.id.swipeRefreshLayout)
    SwipeRefreshLayout swipeRefreshLayout;

    private List<Oferta> oferta;
    private ProductsJson productsJson;
    boolean click = false;
    private FloatingActionButton fabMapa;
    private HomePresenter presenter;
    private String token;


    public HomeFragment() {
        // Required empty public constructor
    }

    public static HomeFragment getInstance()
    {
        if(instance == null)
            instance = new HomeFragment();

        return instance;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        if(container == null)
            return null;

        loadView(inflater,container);
        ButterKnife.bind(this,view);
        presenter = new HomePresenterImpl(this);

        return view;
    }

    /**
     * Called when the fragment's activity has been created and this
     * fragment's view hierarchy instantiated.  It can be used to do final
     * initialization once these pieces are in place, such as retrieving
     * views or restoring state.  It is also useful for fragments that use
     * {@link #setRetainInstance(boolean)} to retain their instance,
     * as this callback tells the fragment when it is fully associated with
     * the new activity instance.  This is called after {@link #onCreateView}
     * and before {@link #onViewStateRestored(Bundle)}.
     *
     * @param savedInstanceState If the fragment is being re-created from
     *                           a previous saved state, this is the state.
     */
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        Bundle extras = getActivity().getIntent().getExtras();
        if(extras != null){
            token = extras.getString(Constants.extraToken);
        }

        rv.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        rv.setLayoutManager(llm);

        bar.setVisibility(View.GONE);
        rv.setVisibility(View.VISIBLE);
        bar.getIndeterminateDrawable().setColorFilter(Color.BLUE, PorterDuff.Mode.MULTIPLY);

       // setFA();
      //  inicializarFab();
        onClickFabs();

        presenter.cargarProductsFromServer("botella");
        swipeRefreshRV();

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
       presenter.cargarProductsFromServer("botella");
    }


    @Override
    public void onStart() {
        super.onStart();
 //       presenter.cargarOfertas();
 //           presenter.cargarProductsFromServer("botella");
    }

    @Override
    public void onResume() {
        super.onResume();

    }

    private void onClickFabs()
    {

    }

    private void loadView(LayoutInflater inflater, ViewGroup container) {
        // TODO Auto-generated method stub
        view = inflater.inflate(R.layout.activity_home, container, false);

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

        if(swipeRefreshLayout.isRefreshing())
            swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void setError() {
        Toast.makeText(getActivity(),"No ha sido posible cargar las ofertas",Toast.LENGTH_SHORT);
    }

    @Override
    public void operacionExitosa(List<Oferta> ofertas) {

    }

    @Override
    public void operacionExitosaFromServer(ProductsJson productsJson) {
        this.productsJson = productsJson;
        List<ResultProductsJson> result = productsJson.getResult();
        for(int i = 0; i<result.size();i++)
        {
            if(result.get(i) != null && result.get(i).getProducts().get(0) != null)
            {
                List<Product> obj = result.get(i).getProducts();
                for(int x = 0;x<obj.size();x++)
                {
                    List<String> objImage = new ArrayList<>();
                    if(x == 0) {

                        objImage.add("http://static.viagrupo.com/thumbs/unnamed_3_63_PNG_464x464.png");

                    }else if(x == 1)
                    {
                        objImage.add("http://static.viagrupo.com/thumbs/71207fa5-3a92-478e-b51d-ceab88d760e4_2_PNG_464x464_PNG_464x464.png");
                    }else if(x == 2)
                    {
                        objImage.add("http://static.viagrupo.com/thumbs/unnamed_17_JPG_464x464.jpg");
                    }
                    else if(x == 3)
                    {
                        objImage.add("http://static.viagrupo.com/thumbs/unnamed_1_11_JPG_464x464.jpg");
                    }else if(x == 4)
                    {
                        objImage.add("http://static.viagrupo.com/thumbs/unnamed_11_11_PNG_464x464.png");
                    }
                    else if(x == 5)
                    {
                        objImage.add("http://static.viagrupo.com/userupload/onelia.png");
                    }else
                    {
                        objImage.add("http://static.viagrupo.com/userupload/vargas01-04.png");
                    }
                    this.productsJson.getResult().get(i).getProducts().get(x).setImages(objImage);
                    this.productsJson.getResult().get(i).setMaxquantityOffers(this.productsJson.getResult().get(i).getProducts().size());
                }
            }

        }
        result = this.productsJson.getResult();
        RVAdapterHome adapter = new RVAdapterHome(result,getInstance().getActivity(),this);
        rv.setAdapter(adapter);

        if(swipeRefreshLayout.isRefreshing())
            swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void operacionExitosaLikeItFromServer(ProductsLiketItJson productsLiketItJson) {

    }

    @Override
    public void updateRV(boolean success,int position) {

    }

    @Override
    public void recyclerViewListClicked(View v, int position,ResultProductsJson resultProductsJson) {

        ResultProductsJson obj = resultProductsJson;
        int posiSubOfert = obj.getOferDisplay();
        Intent i = new Intent(getActivity(),DetailActivity.class);
        i.putExtra("nombre",obj.getProducts().get(posiSubOfert).getName());
        i.putExtra("url",obj.getProducts().get(posiSubOfert).getImages().get(0));
        i.putExtra("detalle",obj.getProducts().get(posiSubOfert).getDescription());
        i.putExtra("bixiPoints",obj.getProducts().get(posiSubOfert).getPoints());
        i.putExtra("product_id",obj.getProducts().get(posiSubOfert).getProductId());
        i.putExtra(Constants.extraToken,token);

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
        {
            String animationName = getActivity().getString(R.string.animation_img);
            ActivityOptionsCompat activityOptionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation(getActivity(),v,animationName);
            startActivity(i,activityOptionsCompat.toBundle());
        }else
            startActivity(i);

    }

    @Override
    public void recyclerViewClicked(View v, int position) {

    }

    @Override
    public void recyclerViewRemoveItem(View v, int position) {

    }

    @Override
    public void recyclerViewLiketItem(View v, int position, ResultProductsJson resultProductsJson) {
        ResultProductsJson obj = resultProductsJson;
        int posiSubOfert = obj.getOferDisplay();
        presenter.likeProductsFromServer(token,obj.getProducts().get(posiSubOfert).getProductId(),position);
    }

    public ResultProductsJson getItem(int position)
    {
        return productsJson.getResult().get(position);
    }
}
