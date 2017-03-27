package com.bixi.bixi.Interactors;

import com.bixi.bixi.Interfaces.OnFinishListener;
import com.bixi.bixi.Interfaces.PasswordActivityInteractor;
import com.bixi.bixi.Network.UserService;
import com.bixi.bixi.Pojos.ObjSearchProducts.ProductsLiketItJson;
import com.bixi.bixi.Pojos.OffersPointsClaim;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Johnny Gil Mejia on 3/27/17.
 */

public class PasswordActivityInteractorImpl implements PasswordActivityInteractor {

    private UserService service;

    public PasswordActivityInteractorImpl(UserService service)
    {
        this.service = service;
    }
    @Override
    public void reclaimOffersFromServer(String token, OffersPointsClaim obj, final OnFinishListener listener) {
        service.reclaimOffer(token,obj).enqueue(new Callback<ProductsLiketItJson>() {
            @Override
            public void onResponse(Call<ProductsLiketItJson> call, Response<ProductsLiketItJson> response) {
                if(response.isSuccessful())
                {
                    if(response.body().getSceResponseCode() == 0)
                    {
                        listener.success(response.body().getSceResponseMsg());
                    }else
                        listener.error(response.body().getSceResponseMsg());
                }else
                    listener.error(response.body().getSceResponseMsg());
            }

            @Override
            public void onFailure(Call<ProductsLiketItJson> call, Throwable t) {
                    listener.error("Error");
            }
        });
    }

    @Override
    public void addPointOffersToServer(String token, OffersPointsClaim obj,final OnFinishListener listener) {
        service.addPointsOffer(token,obj).enqueue(new Callback<ProductsLiketItJson>() {
            @Override
            public void onResponse(Call<ProductsLiketItJson> call, Response<ProductsLiketItJson> response) {
                if(response.isSuccessful())
                {
                    if(response.body().getSceResponseCode() == 0)
                    {
                        listener.success(response.body().getSceResponseMsg());
                    }else
                        listener.error(response.body().getSceResponseMsg());
                }else
                    listener.error(response.body().getSceResponseMsg());
            }

            @Override
            public void onFailure(Call<ProductsLiketItJson> call, Throwable t) {
                    listener.error("Error");
            }
        });
    }
}
