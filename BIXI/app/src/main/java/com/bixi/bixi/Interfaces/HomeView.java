package com.bixi.bixi.Interfaces;

import com.bixi.bixi.Pojos.Oferta;

import java.util.List;

/**
 * Created by telynet on 1/5/2017.
 */

public interface HomeView {

    void showProgress();
    void hideProgress();

    void setError();
    void operacionExitosa(List<Oferta> ofertas);
}
