package com.bixi.bixi.Presenter;

import com.bixi.bixi.Interactors.PasswordActivityInteractorImpl;
import com.bixi.bixi.Interfaces.OnFinishListener;
import com.bixi.bixi.Interfaces.PasswordActivityInteractor;
import com.bixi.bixi.Interfaces.PasswordActivityPresenter;
import com.bixi.bixi.Network.Injector;
import com.bixi.bixi.Pojos.OffersPointsClaim;
import com.bixi.bixi.Views.AddPointsActivity;
import com.bixi.bixi.Views.DetailActivity;
import com.bixi.bixi.Views.PasswordActivity;

/**
 * Created by Johnny Gil Mejia on 3/27/17.
 */

public class PasswordActivityPresenterImpl implements PasswordActivityPresenter, OnFinishListener {

    PasswordActivity view;
    DetailActivity viewDetail;
    AddPointsActivity viewAdd;
    PasswordActivityInteractor interactor;

    public PasswordActivityPresenterImpl(PasswordActivity view)
    {
        this.view = view;
        this.interactor = new PasswordActivityInteractorImpl(Injector.provideCreateUserService());
    }

    public PasswordActivityPresenterImpl(DetailActivity view)
    {
        this.viewDetail = view;
        this.interactor = new PasswordActivityInteractorImpl(Injector.provideCreateUserService());
    }

    public PasswordActivityPresenterImpl(AddPointsActivity view)
    {
        this.viewAdd = view;
        this.interactor = new PasswordActivityInteractorImpl(Injector.provideCreateUserService());
    }

    @Override
    public void reclaimOffer(String token, OffersPointsClaim obj) {
        if(view != null)
            view.showProgress();
        if(viewDetail != null)
            viewDetail.showProgress();
        interactor.reclaimOffersFromServer(token,obj,this);
    }

    @Override
    public void addPointsOffer(String token, OffersPointsClaim onj) {
        if(viewAdd != null)
            viewAdd.showProgress();
        interactor.addPointOffersToServer(token,onj,this);
    }

    @Override
    public void destroyReference() {
        view = null;
    }

    @Override
    public void success(String msg) {
        if(view != null)
        {
            view.hideProgress();
            view.exito(msg);
        }
        if(viewDetail != null)
        {
            viewDetail.hideProgress();
            viewDetail.exito(msg);
        }
        if(viewAdd != null)
        {
            viewAdd.hideProgress();
            viewAdd.exito(msg);
        }

    }

    @Override
    public void error(String msg) {
        if(view != null)
        {
            view.hideProgress();
            view.error(msg);
        }
        if(viewDetail != null)
        {
            viewDetail.hideProgress();
            viewDetail.exito(msg);
        }
        if(viewAdd != null)
        {
            viewAdd.hideProgress();
            viewAdd.exito(msg);
        }

    }
}
