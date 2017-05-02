package com.bixi.bixi.Interfaces;

import com.bixi.bixi.Pojos.UserLogin;
import com.bixi.bixi.Pojos.UserSimple;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by telynet on 1/5/2017.
 */

public interface LoginService {
    void validarUser(UserSimple obj, OnLoginFinishListener listener);
    void validarUser_LoginSocial(UserSimple obj, OnLoginFinishListener listener);
}