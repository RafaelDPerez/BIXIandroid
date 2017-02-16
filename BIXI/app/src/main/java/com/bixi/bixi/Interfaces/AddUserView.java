package com.bixi.bixi.Interfaces;

/**
 * Created by telynet on 1/20/2017.
 */

public interface AddUserView {
    void showProgress();
    void hideProgress();

    void setErrorPassworsNotMatch();
    void SetErrorNeedToCompleteAllForm();

    void exito(String mensaje);
    void errorCamposIncorrectos(String mensaje);
}
