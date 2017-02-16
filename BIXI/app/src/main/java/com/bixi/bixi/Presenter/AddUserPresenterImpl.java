package com.bixi.bixi.Presenter;

import android.util.Log;

import com.bixi.bixi.Interactors.AddUserInteractorImpl;
import com.bixi.bixi.Interfaces.AddUserInteractor;
import com.bixi.bixi.Interfaces.AddUserPresenter;
import com.bixi.bixi.Interfaces.OnAddUserFinishListener;
import com.bixi.bixi.Network.UserService;
import com.bixi.bixi.Pojos.CreateUserResponse;
import com.bixi.bixi.Pojos.UserCreate;
import com.bixi.bixi.Views.AddUserActivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by telynet on 1/20/2017.
 */

public class AddUserPresenterImpl implements AddUserPresenter, OnAddUserFinishListener {

    private final AddUserActivity view;
    private final AddUserInteractor interactor;
    private final UserService service;

    public AddUserPresenterImpl(AddUserActivity view, UserService service)
    {
        this.view = view;
        interactor = new AddUserInteractorImpl();
        this.service = service;
    }

    @Override
    public void createUser(String nombre, String edad, String sexo, String movil, String email, String password, String passwordConfirm) {
        if(view != null)
        {
            view.showProgress();
            service.putCreateUser(nombre,email,password,passwordConfirm).enqueue(new Callback<CreateUserResponse>() {
                @Override
                public void onResponse(Call<CreateUserResponse> call, Response<CreateUserResponse> response) {
                    if(response.isSuccessful())
                    {
                        Log.e("Create_Response",response.body().getSceResponseMsg());
                    }
                    view.hideProgress();
                }

                @Override
                public void onFailure(Call<CreateUserResponse> call, Throwable t) {
                    Log.e("Create_Response",t.getMessage());
                    view.hideProgress();
                }
            });
         //   interactor.createUser(nombre, edad, sexo, movil, email, password, passwordConfirm,this);
        }
    }

    @Override
    public void createuser(UserCreate userCreate) {
        if(view != null)
        {
            view.showProgress();
            if(checkIfHaveInfo(userCreate.getFirst_name()) && checkIfHaveInfo(userCreate.getBirth_date()) && checkIfHaveInfo(userCreate.getGender()) && checkIfHaveInfo(userCreate.getPhone1()) && checkIfHaveInfo(userCreate.getEmail()) && checkIfHaveInfo(userCreate.getPassword()) && checkIfHaveInfo(userCreate.getPassword_confirm()) && checkIfHaveInfo(userCreate.getLast_name()) && checkIfHaveInfo(userCreate.getDocument_id()))
            {

            if(userCreate.getPassword().equals(userCreate.getPassword_confirm()))
            {
                service.putCreateUser(userCreate).enqueue(new Callback<CreateUserResponse>() {
                    @Override
                    public void onResponse(Call<CreateUserResponse> call, Response<CreateUserResponse> response) {
                        if(response.isSuccessful())
                        {
                            Log.e("Create_Response",response.body().getSceResponseMsg());
                            view.hideProgress();
                            if(response.body().getSceResponseCode() == 1)
                                view.errorCamposIncorrectos(response.body().getSceResponseMsg());
                            else
                                view.exito(response.body().getSceResponseMsg());
                        }else
                        {
                            view.hideProgress();
                            Log.e("Create_Response",response.errorBody().toString());
                        }
                    }

                    @Override
                    public void onFailure(Call<CreateUserResponse> call, Throwable t) {
                        Log.e("Create_Response",t.getMessage());
                        view.hideProgress();
                    }
                });
            }else
            {
                view.hideProgress();
                view.setErrorPassworsNotMatch();
            }
        }else
            {
                view.hideProgress();
                view.SetErrorNeedToCompleteAllForm();
            }
        }
    }

    private boolean checkIfHaveInfo(String string)
    {
        if(string != null && !string.equals(""))
            return true;
        else
            return false;
    }

    @Override
    public void setErrorPasswordsNotMatch() {
        if(view != null)
        {
            view.hideProgress();
            view.setErrorPassworsNotMatch();
        }

    }

    @Override
    public void exito() {
        if(view != null)
        {
            view.hideProgress();
            //view.exito();
        }
    }

    @Override
    public void error() {

    }
}
