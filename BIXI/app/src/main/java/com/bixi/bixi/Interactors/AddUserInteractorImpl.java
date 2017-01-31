package com.bixi.bixi.Interactors;

import com.bixi.bixi.Interfaces.AddUserInteractor;
import com.bixi.bixi.Interfaces.OnAddUserFinishListener;

/**
 * Created by telynet on 1/20/2017.
 */

public class AddUserInteractorImpl implements AddUserInteractor {
    @Override
    public void createUser(String nombre, String edad, String sexo, String movil, String email, String password, String passwordConfirm, OnAddUserFinishListener listener) {
        if(!password.equals(passwordConfirm))
        {
            listener.setErrorPasswordsNotMatch();
        }else
        {
            listener.exito();
        }
    }
}
