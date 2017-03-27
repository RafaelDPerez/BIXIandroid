package com.bixi.bixi.Interactors;

import com.bixi.bixi.Interfaces.ResetPasswordInteractor;
import com.bixi.bixi.Interfaces.onResetPasswordFinishListener;
import com.bixi.bixi.Network.UserService;
import com.bixi.bixi.Pojos.UpdateUserInfoPojo;
import com.bixi.bixi.Pojos.UserLogin;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Johnny Gil Mejia on 3/25/17.
 */

public class ResetPasswordInteractorImpl implements  ResetPasswordInteractor {
    UserService service;

    public ResetPasswordInteractorImpl(UserService service)
    {
        this.service = service;
    }

    @Override
    public void updateUserInformationFromServer(String token, UpdateUserInfoPojo obj, final onResetPasswordFinishListener listener) {
            service.updateUserPassword(token, obj).enqueue(new Callback<UserLogin>() {
                public void onResponse(Call<UserLogin> call, Response<UserLogin> response) {
                    if (response.isSuccessful()) {
                        if (response.body().getSceResponseCode().equals("0")) {
                            listener.informationUpdatedSuccesfully(response.body().getSceResponseMsg());
                        } else {
                            listener.error(response.body().getSceResponseMsg());
                        }
                    } else {
                        if (response != null && response.body() != null && response.body().getSceResponseMsg() != null) {
                            listener.error(response.body().getSceResponseMsg());
                        }
                    }
                }

                @Override
                public void onFailure(Call<UserLogin> call, Throwable t) {

                }
            });
    }
}
