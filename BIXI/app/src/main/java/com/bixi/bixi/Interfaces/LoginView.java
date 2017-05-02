package com.bixi.bixi.Interfaces;

/**
 * Created by telynet on 1/5/2017.
 */

public interface LoginView {

    void showProgress();
    void hideProgress();

    void setErrorUser();
    void setErrorPassword();

    void setError(String error);

    void setToken(String token);
    void navigateToHome();

    void navigateToRegister();

}
