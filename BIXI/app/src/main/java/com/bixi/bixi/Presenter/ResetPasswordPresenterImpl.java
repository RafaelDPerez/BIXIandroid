package com.bixi.bixi.Presenter;

import com.bixi.bixi.Interactors.ResetPasswordInteractorImpl;
import com.bixi.bixi.Interfaces.ResetPasswordInteractor;
import com.bixi.bixi.Interfaces.ResetPasswordPresenter;
import com.bixi.bixi.Interfaces.onResetPasswordFinishListener;
import com.bixi.bixi.Network.Injector;
import com.bixi.bixi.Network.UserService;
import com.bixi.bixi.Pojos.Result;
import com.bixi.bixi.Pojos.UpdateUserInfoPojo;
import com.bixi.bixi.Views.ResetPasswordActivity;

/**
 * Created by Johnny Gil Mejia on 3/25/17.
 */

public class ResetPasswordPresenterImpl implements ResetPasswordPresenter, onResetPasswordFinishListener {

    private ResetPasswordActivity view;
    private ResetPasswordInteractor interactor;

    public ResetPasswordPresenterImpl(ResetPasswordActivity view)
    {
        this.view = view;
        this.interactor = new ResetPasswordInteractorImpl(Injector.provideCreateUserService());
    }

    @Override
    public void updateUserInformation(String token,Result userInfo, String oldPassword, String newPassword, String newPasswordConfirm) {
        view.showProgress();
        if(newPassword != null && !newPassword.equals("") && newPasswordConfirm != null && newPassword.equals(newPasswordConfirm))
        {
            String firstName = userInfo.getFirst_name() != null ? userInfo.getFirst_name() : null;
            String lastName = userInfo.getLast_name() != null ? userInfo.getLast_name() : null;
            String documentId = userInfo.getDocument_id() != null ? userInfo.getDocument_id() : null;
            String address = userInfo.getAddress() != null ? userInfo.getAddress() : null;
            String email = userInfo.getEmail() != null ? userInfo.getEmail() : null;
            String oldPass = oldPassword;
            String newPass = newPassword;
            String newPassCornf = newPasswordConfirm;

            UpdateUserInfoPojo pojo = new UpdateUserInfoPojo();
            pojo.setFirstName(firstName);
            pojo.setLastName(lastName);
            pojo.setDocumentId(documentId);
            pojo.setAddress(address);
            pojo.setEmail(email);
            pojo.setGender(userInfo.getGender());
            pojo.setBirthDate(userInfo.getBirth_date());
            pojo.setPhone2(userInfo.getPhone2());
            pojo.setOldPassword(oldPass);
            pojo.setNewPassword(newPass);
            pojo.setNewPasswordConfirm(newPasswordConfirm);

            interactor.updateUserInformationFromServer(token,pojo,this);
        }else
        {
            error("Las contraseñas no coinciden.");
        }

    }

    @Override
    public void informationUpdatedSuccesfully(String msg) {
        view.exito(msg);
        view.hideProgress();
    }

    @Override
    public void error(String msg) {
        view.error(msg);
        view.hideProgress();
    }
}
