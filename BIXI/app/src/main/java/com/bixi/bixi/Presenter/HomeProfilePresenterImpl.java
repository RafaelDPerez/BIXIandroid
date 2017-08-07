package com.bixi.bixi.Presenter;

import com.bixi.bixi.Interactors.HomeProfileInteractorImpl;
import com.bixi.bixi.Interactors.ResetPasswordInteractorImpl;
import com.bixi.bixi.Interfaces.HomeProfileInteractor;
import com.bixi.bixi.Interfaces.HomeProfilePresenter;
import com.bixi.bixi.Interfaces.OnHomeProfileFinishListener;
import com.bixi.bixi.Interfaces.ResetPasswordInteractor;
import com.bixi.bixi.Interfaces.onResetPasswordFinishListener;
import com.bixi.bixi.Network.Injector;
import com.bixi.bixi.Pojos.Result;
import com.bixi.bixi.Pojos.UpdateUserInfoPojo;
import com.bixi.bixi.Pojos.UserLogin;
import com.bixi.bixi.Views.EditProfileActivity;
import com.bixi.bixi.Views.HomeFragment;
import com.bixi.bixi.Views.HomeProfileFragment;

/**
 * Created by Johnny Gil Mejia on 3/25/17.
 */

public class HomeProfilePresenterImpl implements HomeProfilePresenter, OnHomeProfileFinishListener,onResetPasswordFinishListener {

    HomeProfileFragment view;
    EditProfileActivity viewEdit;
    HomeProfileInteractor interactor;
    ResetPasswordInteractor interactor2;

    public HomeProfilePresenterImpl(HomeProfileFragment view)
    {
        this.view = view;
        interactor = new HomeProfileInteractorImpl(Injector.provideCreateUserService());
        interactor2 = new ResetPasswordInteractorImpl(Injector.provideCreateUserService());
    }

    public HomeProfilePresenterImpl(EditProfileActivity view)
    {
        this.viewEdit = view;
        interactor = new HomeProfileInteractorImpl(Injector.provideCreateUserService());
        interactor2 = new ResetPasswordInteractorImpl(Injector.provideCreateUserService());
    }

    @Override
    public void getUserInformation(String token) {
        view.showProgress();
        interactor.getUserInformationFromServer(token,this);
    }

    @Override
    public void updateUserInformationPicture(String token, Result userInfo) {
        String firstName = userInfo.getFirst_name() != null ? userInfo.getFirst_name() : null;
        String lastName = userInfo.getLast_name() != null ? userInfo.getLast_name() : null;
        String documentId = userInfo.getDocument_id() != null ? userInfo.getDocument_id() : null;
        String address = userInfo.getAddress() != null ? userInfo.getAddress() : null;
        String email = userInfo.getEmail() != null ? userInfo.getEmail() : null;

        UpdateUserInfoPojo pojo = new UpdateUserInfoPojo();
        pojo.setFirstName(firstName);
        pojo.setLastName(lastName);
        pojo.setDocumentId(documentId);
        pojo.setAddress(address);
        pojo.setEmail(email);
        pojo.setGender(userInfo.getGender());
        pojo.setBirthDate(userInfo.getBirth_date());
        pojo.setPhone1(userInfo.getPhone1());
        pojo.setPhone2(userInfo.getPhone2());
        if(userInfo.getImage() != null)
            pojo.setImage(userInfo.getImage());
        interactor2.updateUserInformationFromServer(token,pojo,this);

        if(viewEdit != null)
            viewEdit.showProgress();

    }

    @Override
    public void userInformationLoadFromServerSuccessfully(UserLogin userObj) {
        if(view != null)
        {
            view.hideProgress();
            view.operacionExitosa(userObj);
        }

    }

    @Override
    public void userInformationLoadFromServerError() {
        if(view != null)
        {
            view.hideProgress();
            view.error();
        }

    }

    @Override
    public void informationUpdatedSuccesfully(String msg) {

        if(viewEdit != null)
        {
            viewEdit.hideProgress();
            viewEdit.actualizadoExitoso();
        }
        if(view != null)
        {
            view.hideProgress();
            view.actualizadoExitoso();
        }
    }

    @Override
    public void error(String msg) {
        if(viewEdit != null)
        {
            viewEdit.hideProgress();
        }
        if(view != null)
        {
            view.hideProgress();
            view.actualizadoExitoso();
        }
    }
}
