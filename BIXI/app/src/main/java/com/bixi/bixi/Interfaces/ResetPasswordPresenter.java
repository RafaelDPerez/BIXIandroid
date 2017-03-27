package com.bixi.bixi.Interfaces;

import com.bixi.bixi.Pojos.Result;

/**
 * Created by Johnny Gil Mejia on 3/25/17.
 */

public interface ResetPasswordPresenter {
    void updateUserInformation(String token, Result userInfo,String oldPassword, String newPassword, String newPasswordConfirm);
}
