package com.igordubrovin.tfsmsg.mvp.presenters;

import android.support.annotation.VisibleForTesting;

import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.hannesdorfmann.mosby3.mvp.MvpBasePresenter;
import com.igordubrovin.tfsmsg.mvp.ipresenter.ILoginPresenter;
import com.igordubrovin.tfsmsg.mvp.iview.ILoginView;

import javax.inject.Inject;

public class LoginPresenter extends MvpBasePresenter<ILoginView>
        implements ILoginPresenter{
    @VisibleForTesting
    public Boolean success;
    private final FirebaseAuth firebaseAuth;

    @Inject
    public LoginPresenter(FirebaseAuth firebaseAuth){
        this.firebaseAuth = firebaseAuth;
    }

    @Override
    public void attachView(ILoginView view) {
        super.attachView(view);
        if (success != null) {
            returnResultView(success);
            success = null;
        }
    }

    @Override
    public void onClickBtnCheckLogin(String login, String password) {
        //TODO
    }

    @Override
    public void handleSignInResult(GoogleSignInResult result) {
        if (result.isSuccess()) {
            GoogleSignInAccount acct = result.getSignInAccount();
            firebaseAuth(acct);
        }
    }

    private void firebaseAuth(GoogleSignInAccount acct) {
        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        firebaseAuth.signInWithCredential(credential)
                .addOnSuccessListener(authResult -> {
                    FirebaseUser user = authResult.getUser();
                    if (user != null)
                        setSuccess(true);
                    else
                        setSuccess(false);
                    })
                .addOnFailureListener(e -> setSuccess(false));
    }

    @VisibleForTesting
    public void returnResultView(Boolean success){
        if (success)
            getView().loginSuccessful();
        else
            getView().showError();
    }

    @VisibleForTesting
    public void setSuccess(Boolean success) {
        if (isViewAttached())
            returnResultView(success);
        else
            this.success = success;
    }
}
