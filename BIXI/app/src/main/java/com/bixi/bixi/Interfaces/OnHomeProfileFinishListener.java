package com.bixi.bixi.Interfaces;

import com.bixi.bixi.Pojos.ObjSearchProducts.ProductsJson;
import com.bixi.bixi.Pojos.Oferta;
import com.bixi.bixi.Pojos.UserLogin;

import java.util.List;

/**
 * Created by Johnny Gil Mejia on 3/25/17.
 */

public interface OnHomeProfileFinishListener {
    void userInformationLoadFromServerSuccessfully(UserLogin userObj);
    void userInformationLoadFromServerError();
}
