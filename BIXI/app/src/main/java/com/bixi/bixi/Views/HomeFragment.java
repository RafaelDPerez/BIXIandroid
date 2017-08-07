package com.bixi.bixi.Views;


import android.app.Activity;
import android.app.Fragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
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
import com.bixi.bixi.Login;
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
import com.bixi.bixi.bixi.basics.ApplyCustomFont;
import com.bixi.bixi.homeDrawable;
import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;

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

    private ProductsJson productsJson;
    boolean click = false;
    private FloatingActionButton fabMapa;
    private HomePresenter presenter;
    private String token;

    static final int SEARCH_REQUEST = 1;
    static final int DETAIL_REQUEST = 2;

    public HomeFragment() {
        // Required empty public constructor
    }

    public static HomeFragment getInstance()
    {
        if(instance == null) {
            instance = new HomeFragment();
        }

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
        setHasOptionsMenu(true);

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
        LinearLayoutManager llm = new LinearLayoutManager(getInstance().getActivity());
        rv.setLayoutManager(llm);

        bar.setVisibility(View.GONE);
        rv.setVisibility(View.VISIBLE);
        bar.getIndeterminateDrawable().setColorFilter(Color.BLUE, PorterDuff.Mode.MULTIPLY);

       // setFA();
      //  inicializarFab();
        onClickFabs();

        presenter.cargarProductsFromServer(token,"botella");
        swipeRefreshRV();

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menuhome, menu);
        super.onCreateOptionsMenu(menu,inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.mybutton) {
            // do something here
            Intent i = new Intent(getInstance().getActivity(),SearchActivity.class);
            startActivityForResult(i,SEARCH_REQUEST);
        }

        return super.onOptionsItemSelected(item);
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
       presenter.cargarProductsFromServer(token,"botella");
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
        presenter.cargarProductsFromServer(token,"botella");
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
                this.productsJson.getResult().get(i).setMaxquantityOffers(this.productsJson.getResult().get(i).getProducts().size());
            }

        }
        result = this.productsJson.getResult();

        Activity contex = getInstance().getActivity();
        if(contex == null)
            contex = getInstance().getActivity();
        RVAdapterHome adapter = new RVAdapterHome(result,contex,this,false,true);
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
        Intent i = new Intent(getInstance().getActivity(),DetailActivity.class);
        i.putExtra("nombre",obj.getProducts().get(posiSubOfert).getName());
        if(obj.getProducts().get(posiSubOfert).getImages() != null && obj.getProducts().get(posiSubOfert).getImages().size() > 0)
            i.putExtra("url",obj.getProducts().get(posiSubOfert).getImages().get(0));
        else
            i.putExtra("url","");

        i.putExtra("detalle",obj.getProducts().get(posiSubOfert).getDescription());
        i.putExtra("bixiPoints",obj.getProducts().get(posiSubOfert).getPoints());
        i.putExtra("product_id",obj.getProducts().get(posiSubOfert).getProductId());
        i.putExtra("offerDisplay",posiSubOfert);
        i.putExtra("likeIt",obj.getProducts().get(posiSubOfert).getIsFavorite());

        String[] selItemArray = new String[obj.getProducts().get(posiSubOfert).getImages().size()];
        for(int x = 0;x<obj.getProducts().get(posiSubOfert).getImages().size();x++)
        {
            selItemArray[x] = obj.getProducts().get(posiSubOfert).getImages().get(x);
        }
        i.putExtra("arrayImages",selItemArray);
        i.putExtra(Constants.extraToken,token);

        /*
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
        {
            String animationName = getActivity().getString(R.string.animation_img);
            ActivityOptionsCompat activityOptionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation(getActivity(),v,animationName);
            startActivity(i,activityOptionsCompat.toBundle());
        }else
        */
            startActivityForResult(i,DETAIL_REQUEST);

    }

    @Override
    public void recyclerViewListClicked(View v, int position, Product product) {

    }

    @Override
    public void recyclerViewClicked(View v, int position) {

    }

    @Override
    public void recyclerViewRemoveItem(View v, int position) {

    }

    @Override
    public void recyclerViewLiketItem(View v, int position, ResultProductsJson resultProductsJson, boolean likeIt) {
        if(token != null && !token.equals(""))
        {
            ResultProductsJson obj = resultProductsJson;
            int posiSubOfert = obj.getOferDisplay();

            if(likeIt)
                presenter.likeProductsFromServer(token,obj.getProducts().get(posiSubOfert).getProductId(),position);
            else
                presenter.dislikeProductsFromServer(token,obj.getProducts().get(posiSubOfert).getProductId(),position);


        }else {
            alertaToken();
        }

    }

    public ResultProductsJson getItem(int position)
    {
        return productsJson.getResult().get(position);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == SEARCH_REQUEST)
        {
            if(resultCode == Activity.RESULT_OK)
            {
                presenter.cargarProductsFromServer(
                        token,
                        data.getStringExtra("search"),
                        data.getStringExtra("ubicacionId"),
                        data.getStringExtra("ordenar"),
                        data.getStringExtra("isOffer"),
                        data.getIntExtra("pointFrom",10),
                        data.getIntExtra("pointTo",300));
            }
        }else if(requestCode == DETAIL_REQUEST && resultCode == Activity.RESULT_OK)
        {
            if(data.getBooleanExtra("changes",false))
                presenter.cargarProductsFromServer(token,"botella");
        }
    }

    private void alertaToken()
    {

        final AlertDialog alertDialog = new AlertDialog.Builder(getInstance().getActivity(),R.style.DialogAlertTheme).create();
        alertDialog.setTitle("Alerta");
        alertDialog.setMessage("Debe iniciar sesión o registrarse.");
        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "Aceptar",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Intent newIntent = new Intent(getInstance().getActivity(),Login.class);
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

    @Override
    public void onDetach() {
        super.onDetach();
        presenter.detachView();
        presenter = null;
    }
}
