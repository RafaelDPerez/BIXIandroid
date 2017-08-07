package com.bixi.bixi.Interfaces;

import com.bixi.bixi.Pojos.ObjSearchProducts.ResultProductsJson;
import com.bixi.bixi.Pojos.ObjSearchProducts.ResultProductsLikerItJson;
import com.bixi.bixi.Pojos.ProductsSearch;
import com.bixi.bixi.Pojos.Result;

/**
 * Created by telynet on 1/5/2017.
 */

public interface HomeInteractor {
    void buscarOferta(OnHomeOfertasFinishListener listener);
    void loadProductsFromServer(String token,ProductsSearch productsSearch,OnHomeOfertasFinishListener listener);
    void loadProductsFavoritosFromServer(String token,OnHomeOfertasFinishListener listener);
    void likeOffer(String token, ResultProductsLikerItJson obj,OnHomeOfertasFinishListener listener, int position);
    void dislikeOffer(String token, ResultProductsLikerItJson obj,OnHomeOfertasFinishListener listener,int position);
}
