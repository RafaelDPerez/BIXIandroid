package com.bixi.bixi.Interfaces;

import com.bixi.bixi.Pojos.UserCreate;

/**
 * Created by telynet on 1/20/2017.
 */

public interface AddUserPresenter {
    void createUser(String nombre, String edad, String sexo,String movil, String email, String password, String passwordConfirm);
    void createuser(UserCreate userCreate);
}
