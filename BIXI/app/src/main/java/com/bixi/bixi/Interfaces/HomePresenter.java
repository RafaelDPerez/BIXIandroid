package com.bixi.bixi.Interfaces;

import com.bixi.bixi.Pojos.ProductsSearch;

/**
 * Created by telynet on 1/5/2017.
 */

public interface HomePresenter {
    void cargarOfertas();
    void cargarProductsFromServer(String token,String search);
}
