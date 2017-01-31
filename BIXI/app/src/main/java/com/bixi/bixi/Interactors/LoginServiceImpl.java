package com.bixi.bixi.Interactors;

import android.os.Handler;
import android.util.Log;

import com.bixi.bixi.Interfaces.LoginService;
import com.bixi.bixi.Interfaces.OnLoginFinishListener;
import com.bixi.bixi.Network.Injector;
import com.bixi.bixi.Network.UserService;
import com.bixi.bixi.Pojos.UserLogin;
import com.bixi.bixi.Pojos.UserSimple;
import com.twitter.sdk.android.core.models.User;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by telynet on 1/5/2017.
 */

public class LoginServiceImpl implements LoginService {

    UserService service;
    public LoginServiceImpl(UserService service)
    {
        this.service = service;
    }


    @Override
    public void validarUser(UserSimple obj, final OnLoginFinishListener listener) {
        service.postAttempLogin(obj).enqueue(new Callback<UserLogin>() {
            @Override
            public void onResponse(Call<UserLogin> call, Response<UserLogin> response) {
                if(response.isSuccessful())
                {
                    if(response.body().getSceResponseMsg().equals("OK"))
                    {
                        listener.exitoOperacion(response.body().getResult().getToken());

                    }else
                    {
                        listener.apiError(response.body().getSceResponseMsg());
                    }

                }else
                {
                    listener.apiError(response.errorBody().toString());
                }
            }

            @Override
            public void onFailure(Call<UserLogin> call, Throwable t) {
                listener.apiError(t.getMessage());
            }
        });
    }

}
