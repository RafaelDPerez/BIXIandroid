package com.bixi.bixi.Presenter;

import com.bixi.bixi.Interactors.HomeInteractorImpl;
import com.bixi.bixi.Interfaces.HomeInteractor;
import com.bixi.bixi.Interfaces.HomePresenter;
import com.bixi.bixi.Interfaces.HomeView;
import com.bixi.bixi.Interfaces.OnHomeOfertasFinishListener;
import com.bixi.bixi.Network.Injector;
import com.bixi.bixi.Pojos.Oferta;
import com.bixi.bixi.Pojos.ProductsSearch;

import java.util.List;

/**
 * Created by telynet on 1/5/2017.
 */

public class HomePresenterImpl implements HomePresenter, OnHomeOfertasFinishListener {
    HomeView view;
    HomeInteractor iteractor;

    public HomePresenterImpl(HomeView view) {
        this.view = view;
        iteractor = new HomeInteractorImpl(Injector.provideCreateUserService());
    }

    @Override
    public void cargarOfertas() {
        view.showProgress();
        iteractor.buscarOferta(this);
    }

    @Override
    public void cargarProductsFromServer(String token, String search) {
        view.showProgress();
        ProductsSearch obj = new ProductsSearch();
        obj.setSearch(search);
        iteractor.loadProductsFromServer(token,obj,this);
    }


    @Override
    public void ofertasCargadas(List<Oferta> ofertas) {
        view.hideProgress();
        view.operacionExitosa(ofertas);
    }

    @Override
    public void ofertasError() {
        view.hideProgress();
        view.setError();
    }
}
