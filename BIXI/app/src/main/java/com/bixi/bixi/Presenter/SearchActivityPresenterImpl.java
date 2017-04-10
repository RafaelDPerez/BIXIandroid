package com.bixi.bixi.Presenter;

import com.bixi.bixi.Interactors.SearchActivityInteractorImpl;
import com.bixi.bixi.Interfaces.OnComboFinishListener;
import com.bixi.bixi.Interfaces.SearchActivityInteractor;
import com.bixi.bixi.Interfaces.SearchActivityPresenter;
import com.bixi.bixi.Interfaces.SearchActivityView;
import com.bixi.bixi.Network.Injector;
import com.bixi.bixi.Pojos.Combos.ComboGeneralPojo;
import com.bixi.bixi.Views.SearchActivity;

/**
 * Created by Johnny Gil Mejia on 4/9/17.
 */

public class SearchActivityPresenterImpl implements SearchActivityPresenter,OnComboFinishListener {

    private SearchActivityView view;
    private SearchActivityInteractor interactor;

    public SearchActivityPresenterImpl(SearchActivityView view)
    {
        this.view = view;
        interactor = new SearchActivityInteractorImpl(Injector.provideCreateUserService());
    }

    @Override
    public void loadTypeCommerceList() {
        interactor.loadTypeCommerceFromServer(this);
    }

    @Override
    public void typesCommerceLoadSuccesfullyFromServer(ComboGeneralPojo comboGeneralPojo) {
        view.operacionExitosa(comboGeneralPojo);
    }

    @Override
    public void typesCommerceLoadUnsuccedfullyFromServer(String error) {
        view.error(error);
    }
}
