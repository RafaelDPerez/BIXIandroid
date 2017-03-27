package com.bixi.bixi.Interactors;

import com.bixi.bixi.Interfaces.OnHistoricoFinishListener;
import com.bixi.bixi.Interfaces.TransaccionesInteractor;
import com.bixi.bixi.Network.UserService;
import com.bixi.bixi.Pojos.HistorialPojo;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Johnny Gil Mejia on 3/27/17.
 */

public class TransaccionesInteractorImpl implements TransaccionesInteractor {
    UserService service;


    public  TransaccionesInteractorImpl(UserService service)
    {
        this.service = service;
    }
    @Override
    public void loadHistoricalFromServer(String token, final OnHistoricoFinishListener listener) {
        service.getHistorical(token).enqueue(new Callback<HistorialPojo>() {
            @Override
            public void onResponse(Call<HistorialPojo> call, Response<HistorialPojo> response) {
                if(response.isSuccessful())
                {
                    if(response.body().getSceResponseCode() == 0)
                    {
                        listener.loadSuccesfully(response.body());
                    }else
                        listener.historicalError();
                }else
                    listener.historicalError();
            }

            @Override
            public void onFailure(Call<HistorialPojo> call, Throwable t) {
                    listener.historicalError();
            }
        });

    }
}
