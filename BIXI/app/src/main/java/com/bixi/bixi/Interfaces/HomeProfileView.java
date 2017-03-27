package com.bixi.bixi.Interfaces;

import com.bixi.bixi.Pojos.UserLogin;

/**
 * Created by Johnny Gil Mejia on 3/25/17.
 */

public interface HomeProfileView {
    void operacionExitosa(UserLogin obj);
    void error();
    void showProgress();
    void hideProgress();
}
