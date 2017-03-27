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
import com.bixi.bixi.Views.HomeFragment;
import com.bixi.bixi.Views.HomeProfileFragment;

/**
 * Created by Johnny Gil Mejia on 3/25/17.
 */

public class HomeProfilePresenterImpl implements HomeProfilePresenter, OnHomeProfileFinishListener,onResetPasswordFinishListener {

    HomeProfileFragment view;
    HomeProfileInteractor interactor;
    ResetPasswordInteractor interactor2;

    public HomeProfilePresenterImpl(HomeProfileFragment view)
    {
        this.view = view;
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
        pojo.setPhone2(userInfo.getPhone2());
        pojo.setImage(userInfo.getImage());
        interactor2.updateUserInformationFromServer(token,pojo,this);

    }

    @Override
    public void userInformationLoadFromServerSuccessfully(UserLogin userObj) {
        view.hideProgress();
        view.operacionExitosa(userObj);
    }

    @Override
    public void userInformationLoadFromServerError() {
        view.hideProgress();
        view.error();
    }

    @Override
    public void informationUpdatedSuccesfully(String msg) {

    }

    @Override
    public void error(String msg) {

    }
}
