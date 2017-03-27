package com.bixi.bixi.Interfaces;

import com.bixi.bixi.Pojos.ObjSearchProducts.ProductsJson;
import com.bixi.bixi.Pojos.ObjSearchProducts.ProductsLiketItJson;
import com.bixi.bixi.Pojos.Oferta;

import java.util.List;

/**
 * Created by Johnny Gil Mejia on 1/5/2017.
 */

public interface OnHomeOfertasFinishListener {
    void ofertasCargadas(List<Oferta> ofertas);
    void ofertasError();
    void ofertasCargadasFromServer(ProductsJson productsJson);
    void ofertasLiketItCargadasFromServer(ProductsLiketItJson productsLiketItJson);
    void ofertasLiketSuccesfully(boolean success, int position);
    void ofertasDislikeSuccesfully(boolean success, int position);
}
