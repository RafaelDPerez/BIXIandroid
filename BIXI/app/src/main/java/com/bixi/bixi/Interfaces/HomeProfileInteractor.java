package com.bixi.bixi.Interfaces;

/**
 * Created by Johnny Gil Mejia on 3/25/17.
 */

public interface HomeProfileInteractor {
    void getUserInformationFromServer(String token, OnHomeProfileFinishListener listener);
}
