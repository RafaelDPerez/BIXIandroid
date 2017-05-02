package com.bixi.bixi.Presenter;

import com.bixi.bixi.Interactors.LoginServiceImpl;
import com.bixi.bixi.Interfaces.LoginService;
import com.bixi.bixi.Interfaces.LoginPresenter;
import com.bixi.bixi.Interfaces.LoginView;
import com.bixi.bixi.Interfaces.OnLoginFinishListener;
import com.bixi.bixi.Network.Injector;
import com.bixi.bixi.Network.UserService;
import com.bixi.bixi.Pojos.UserSimple;
import com.bixi.bixi.Utility.Hash;

/**
 * Created by Johnny Gil on 1/5/2017.
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
    public void validarUsuario_LoginSocial(String email) {
        if(view != null)
        {
            view.showProgress();
            UserSimple obj = new UserSimple();
            Hash hash = new Hash();

            obj.setEmail(email);
            String md5 = hash.md5(email);
            obj.setEncrypted(hash.sha1(md5));
            interactor.validarUser_LoginSocial(obj,this);
        }
    }

    @Override
    public void apiError(String error) {
        if(view != null)
        {
            view.hideProgress();
            view.setError(error);
        }
    }

    @Override
    public void exitoOperacion(String token) {
        if(view != null)
        {
            view.hideProgress();
            view.setToken(token);
            view.navigateToHome();
        }
    }

    @Override
    public void apiError_Social(String error) {
        if(view != null)
        {
            view.hideProgress();
            view.navigateToRegister();
        }
    }
}
