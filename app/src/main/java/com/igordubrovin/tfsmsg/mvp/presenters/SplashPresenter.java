package com.igordubrovin.tfsmsg.mvp.presenters;

import android.support.annotation.VisibleForTesting;

import com.google.firebase.auth.FirebaseAuth;
import com.hannesdorfmann.mosby3.mvp.MvpBasePresenter;
import com.igordubrovin.tfsmsg.mvp.ipresenter.ISplashPresenter;
import com.igordubrovin.tfsmsg.mvp.iview.ISplashView;

import javax.inject.Inject;

public class SplashPresenter extends MvpBasePresenter<ISplashView>
        implements ISplashPresenter {

    private FirebaseAuth firebaseAuth;

    @Inject
    public SplashPresenter(FirebaseAuth firebaseAuth){
        this.firebaseAuth = firebaseAuth;
    }

    @Override
    public void checkLoginState() {
        checkUser();
    }

    @VisibleForTesting
    public void returnResultView(boolean success){
        if (success)
            getView().showNavigationActivity();
        else
            getView().showLoginActivity();
    }

    private void checkUser(){
        if (firebaseAuth.getCurrentUser() != null){
            returnResultView(true);
        } else
            returnResultView(false);
    }
}
