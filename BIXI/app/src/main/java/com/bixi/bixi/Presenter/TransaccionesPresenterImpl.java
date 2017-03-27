package com.bixi.bixi.Presenter;

import com.bixi.bixi.Interactors.TransaccionesInteractorImpl;
import com.bixi.bixi.Interfaces.OnHistoricoFinishListener;
import com.bixi.bixi.Interfaces.TransaccionesInteractor;
import com.bixi.bixi.Interfaces.TransaccionesPresenter;
import com.bixi.bixi.Network.Injector;
import com.bixi.bixi.Pojos.HistorialPojo;
import com.bixi.bixi.Views.TransaccionesFragment;

/**
 * Created by Johnny Gil Mejia on 3/27/17.
 */

public class TransaccionesPresenterImpl implements TransaccionesPresenter,OnHistoricoFinishListener {

    TransaccionesInteractor interactor;
    TransaccionesFragment view;
    public TransaccionesPresenterImpl(TransaccionesFragment view)
    {
        this.view = view;
        this.interactor = new TransaccionesInteractorImpl(Injector.provideCreateUserService());
    }

    @Override
    public void getHistoricalFromServer(String token) {
        view.showPB();
        interactor.loadHistoricalFromServer(token,this);
    }

    @Override
    public void historicalError() {
        view.hidePB();
    }

    @Override
    public void loadSuccesfully(HistorialPojo pojo) {
        view.hidePB();
        view.success(pojo);
    }
}
