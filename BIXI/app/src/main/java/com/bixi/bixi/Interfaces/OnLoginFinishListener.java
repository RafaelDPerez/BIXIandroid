package com.bixi.bixi.Interfaces;

/**
 * Created by telynet on 1/5/2017.
 */

public interface OnLoginFinishListener {
    void apiError(String error);
    void exitoOperacion(String token);
}
