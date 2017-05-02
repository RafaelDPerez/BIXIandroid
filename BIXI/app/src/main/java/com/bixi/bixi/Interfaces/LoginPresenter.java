package com.bixi.bixi.Interfaces;

/**
 * Created by telynet on 1/5/2017.
 */

public interface LoginPresenter {
    void validarUsuario(String user, String password);
    void validarUsuario_LoginSocial(String email);
}
