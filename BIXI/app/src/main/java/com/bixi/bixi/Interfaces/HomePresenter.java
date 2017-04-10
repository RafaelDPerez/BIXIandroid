package com.bixi.bixi.Interfaces;

import com.bixi.bixi.Pojos.ObjSearchProducts.ResultProductsLikerItJson;
import com.bixi.bixi.Pojos.ProductsSearch;

/**
 * Created by telynet on 1/5/2017.
 */

public interface HomePresenter {
    void cargarOfertas();
    void cargarProductsFromServer(String search);
    void cargarProductsFromServer(String search, String ubicacionId,String ordenarPor,String isOffer,int pointFrom, int pointTo);
    void cargarProductosFavoritosFromServer(String token);
    void likeProductsFromServer(String token, String id, int position);
    void dislikeProductsFromServer(String token, String id, int position);


}
