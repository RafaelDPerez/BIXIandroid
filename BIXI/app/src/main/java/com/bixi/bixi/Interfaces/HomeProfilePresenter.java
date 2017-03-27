package com.bixi.bixi.Interfaces;

import com.bixi.bixi.Pojos.Result;

/**
 * Created by Johnny Gil Mejia on 3/25/17.
 */

public interface HomeProfilePresenter {
    void getUserInformation(String token);
    void updateUserInformationPicture(String token,Result userInfo);
}
