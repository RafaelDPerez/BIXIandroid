package com.bixi.bixi.Interfaces;

import com.bixi.bixi.Pojos.ProductsSearch;

/**
 * Created by telynet on 1/5/2017.
 */

public interface HomeInteractor {
    void buscarOferta(OnHomeOfertasFinishListener listener);
    void loadProductsFromServer(ProductsSearch productsSearch,OnHomeOfertasFinishListener listener);
}
