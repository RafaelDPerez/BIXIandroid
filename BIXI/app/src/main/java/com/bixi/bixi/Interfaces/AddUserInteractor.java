package com.bixi.bixi.Interfaces;

/**
 * Created by telynet on 1/20/2017.
 */

public interface AddUserInteractor {
    void createUser(String nombre, String edad, String sexo,String movil, String email, String password, String passwordConfirm, OnAddUserFinishListener listener);
}
