package com.bixi.bixi.Interfaces;

import com.bixi.bixi.Pojos.UpdateUserInfoPojo;

/**
 * Created by Johnny Gil Mejia on 3/25/17.
 */

public interface ResetPasswordInteractor {
    void updateUserInformationFromServer(String token, UpdateUserInfoPojo obj, onResetPasswordFinishListener listener);
}
