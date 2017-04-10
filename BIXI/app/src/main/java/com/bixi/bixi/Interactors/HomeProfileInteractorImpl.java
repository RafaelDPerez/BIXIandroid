package com.bixi.bixi.Interactors;

import com.bixi.bixi.Interfaces.HomeProfileInteractor;
import com.bixi.bixi.Interfaces.OnHomeProfileFinishListener;
import com.bixi.bixi.Network.UserService;
import com.bixi.bixi.Pojos.UserLogin;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Johnny Gil Mejia on 3/25/17.
 */

public class HomeProfileInteractorImpl implements HomeProfileInteractor {

    UserService service;
    public HomeProfileInteractorImpl(UserService service)
    {
        this.service = service;
    }

    @Override
    public void getUserInformationFromServer(String token, final OnHomeProfileFinishListener listener) {
        service.getUserInformation(token).enqueue(new Callback<UserLogin>() {
            @Override
            public void onResponse(Call<UserLogin> call, Response<UserLogin> response) {
                if(response.isSuccessful())
                {
                    if(response.body().getSceResponseMsg().equals("OK"))
                    {
                        listener.userInformationLoadFromServerSuccessfully(response.body());
                    }else
                    {
                        listener.userInformationLoadFromServerError();
                    }
                }else
                {
                    if(response != null && response.body() != null && response.body().getSceResponseMsg() != null)
                    {
                        listener.userInformationLoadFromServerError();
                    }
                }
            }

            @Override
            public void onFailure(Call<UserLogin> call, Throwable t)
            {
                    listener.userInformationLoadFromServerError();
            }
        });
    }
}
