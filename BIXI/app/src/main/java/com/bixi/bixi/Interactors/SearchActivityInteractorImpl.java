package com.bixi.bixi.Interactors;

import com.bixi.bixi.Interfaces.OnComboFinishListener;
import com.bixi.bixi.Interfaces.SearchActivityInteractor;
import com.bixi.bixi.Network.UserService;
import com.bixi.bixi.Pojos.Combos.ComboGeneralPojo;
import com.twitter.sdk.android.core.models.User;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Johnny Gil Mejia on 4/9/17.
 */

public class SearchActivityInteractorImpl implements SearchActivityInteractor {

    UserService service;

    public SearchActivityInteractorImpl(UserService service)
    {
        this.service = service;
    }


    @Override
    public void loadTypeCommerceFromServer(final OnComboFinishListener listener) {
        service.getTyppeCommerceList().enqueue(new Callback<ComboGeneralPojo>() {
            @Override
            public void onResponse(Call<ComboGeneralPojo> call, Response<ComboGeneralPojo> response) {
                if(response.isSuccessful())
                {
                    if(response.body().getSceResponseCode() == 0)
                    {
                        listener.typesCommerceLoadSuccesfullyFromServer(response.body());
                    }else
                    {
                        listener.typesCommerceLoadUnsuccedfullyFromServer("");
                    }
                }else
                {
                    listener.typesCommerceLoadUnsuccedfullyFromServer("");
                }
            }

            @Override
            public void onFailure(Call<ComboGeneralPojo> call, Throwable t) {
                listener.typesCommerceLoadUnsuccedfullyFromServer("");
            }
        });
    }
}
