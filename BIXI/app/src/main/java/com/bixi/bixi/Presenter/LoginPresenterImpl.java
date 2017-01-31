package com.bixi.bixi.Presenter;

import android.util.Log;

import com.bixi.bixi.Interactors.LoginServiceImpl;
import com.bixi.bixi.Interfaces.LoginService;
import com.bixi.bixi.Interfaces.LoginPresenter;
import com.bixi.bixi.Interfaces.LoginView;
import com.bixi.bixi.Interfaces.OnLoginFinishListener;
import com.bixi.bixi.Network.Injector;
import com.bixi.bixi.Network.UserService;
import com.bixi.bixi.Pojos.CreateUserResponse;
import com.bixi.bixi.Pojos.UserCreate;
import com.bixi.bixi.Pojos.UserLogin;
import com.bixi.bixi.Pojos.UserSimple;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by telynet on 1/5/2017.
 */

public class LoginPresenterImpl  implements LoginPresenter, OnLoginFinishListener {

    private LoginView view;
    private LoginService interactor;
    private UserService service;

    public LoginPresenterImpl(LoginView view) {
        this.view = view;
        interactor = new LoginServiceImpl(Injector.provideCreateUserService());
    }



    @Override
    public void validarUsuario(String user, String password)
    {
        if(view != null)
        {
            view.showProgress();
            UserSimple obj = new UserSimple(user,password);
            interactor.validarUser(obj,this);

        }
    }

    @Override
    public void apiError(String error) {
        if(view != null)
            view.hideProgress();

        view.setError(error);
    }

    @Override
    public void exitoOperacion(String token) {
        if(view != null)
            view.hideProgress();

        view.setToken(token);
        view.navigateToHome();
    }
}
