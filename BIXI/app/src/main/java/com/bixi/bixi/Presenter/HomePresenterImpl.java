package com.bixi.bixi.Presenter;

import com.bixi.bixi.Interactors.HomeInteractorImpl;
import com.bixi.bixi.Interfaces.HomeInteractor;
import com.bixi.bixi.Interfaces.HomePresenter;
import com.bixi.bixi.Interfaces.HomeView;
import com.bixi.bixi.Interfaces.OnHomeOfertasFinishListener;
import com.bixi.bixi.MapsActivity;
import com.bixi.bixi.Network.Injector;
import com.bixi.bixi.Pojos.ObjSearchProducts.ProductsJson;
import com.bixi.bixi.Pojos.ObjSearchProducts.ProductsLiketItJson;
import com.bixi.bixi.Pojos.ObjSearchProducts.ResultProductsLikerItJson;
import com.bixi.bixi.Pojos.Oferta;
import com.bixi.bixi.Pojos.ProductsSearch;
import com.bixi.bixi.Views.HomeFragment;
import com.bixi.bixi.Views.HomeLikeIt;

import java.util.List;

/**
 * Created by Johnny Gil Mejia on 1/5/2017.
 */

public class HomePresenterImpl implements HomePresenter, OnHomeOfertasFinishListener {
  //  HomeView view;
    HomeInteractor iteractor;
    HomeFragment view;
    HomeLikeIt viewLikeIt;
    MapsActivity viewMaps;

    public HomePresenterImpl(HomeView view) {
     //   this.view = view;
       injectService();
    }
    public HomePresenterImpl(HomeFragment view) {
        this.view = view;
        injectService();
    }

    public HomePresenterImpl(HomeLikeIt view) {
        this.viewLikeIt = view;
        injectService();
    }

    public HomePresenterImpl(MapsActivity view)
    {
        this.viewMaps = view;
        injectService();
    }

    public void injectService()
    {
        iteractor = new HomeInteractorImpl(Injector.provideCreateUserService());
    }

    @Override
    public void cargarOfertas() {
        view.showProgress();
        iteractor.buscarOferta(this);
    }

    @Override
    public void cargarProductsFromServer(String search) {
        if(view != null)
            view.showProgress();
        if(viewMaps != null)
            viewMaps.showProgress();

        ProductsSearch obj = new ProductsSearch();
        obj.setSearch("");
        iteractor.loadProductsFromServer(obj,this);
    }

    @Override
    public void cargarProductosFavoritosFromServer(String token) {
        viewLikeIt.showProgress();
        iteractor.loadProductsFavoritosFromServer(token,this);
    }

    @Override
    public void likeProductsFromServer(String token, String id,int position) {
        ResultProductsLikerItJson obj = new ResultProductsLikerItJson();
        obj.setProductId(id);
        iteractor.likeOffer(token,obj,this,position);
    }

    @Override
    public void dislikeProductsFromServer(String token, String id, int position) {
        ResultProductsLikerItJson obj = new ResultProductsLikerItJson();
        obj.setProductId(id);
        iteractor.dislikeOffer(token,obj,this,position);
    }


    @Override
    public void ofertasCargadas(List<Oferta> ofertas) {
        view.hideProgress();
        view.operacionExitosa(ofertas);
    }

    @Override
    public void ofertasError() {
        if(view != null)
        {
            view.hideProgress();
            view.setError();
        }else if(viewMaps != null)
        {

        }else if(viewLikeIt != null)
        {
            viewLikeIt.hideProgress();
            viewLikeIt.setError();
        }

    }

    @Override
    public void ofertasCargadasFromServer(ProductsJson productsJson) {
        if(view != null)
        {
            view.hideProgress();
            view.operacionExitosaFromServer(productsJson);
        }else if(viewMaps != null)
        {
            viewMaps.hideProgress();
            viewMaps.operacionExitosaFromServer(productsJson);
        }
    }

    @Override
    public void ofertasLiketItCargadasFromServer(ProductsLiketItJson productsLiketItJson) {
        viewLikeIt.hideProgress();
        viewLikeIt.operacionExitosaLikeItFromServer(productsLiketItJson);
    }

    @Override
    public void ofertasLiketSuccesfully(boolean success,int position) {
        view.updateRV(success,position);
    }

    @Override
    public void ofertasDislikeSuccesfully(boolean success, int position) {
        viewLikeIt.updateRV(success,position);
    }
}
