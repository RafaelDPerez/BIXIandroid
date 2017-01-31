package com.bixi.bixi.Interfaces;

import com.bixi.bixi.Pojos.Oferta;

import java.util.List;

/**
 * Created by telynet on 1/5/2017.
 */

public interface OnHomeOfertasFinishListener {
    void ofertasCargadas(List<Oferta> ofertas);
    void ofertasError();
}
