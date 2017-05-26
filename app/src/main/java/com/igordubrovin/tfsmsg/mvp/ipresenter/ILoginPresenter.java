package com.igordubrovin.tfsmsg.mvp.ipresenter;

import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.hannesdorfmann.mosby3.mvp.MvpPresenter;
import com.igordubrovin.tfsmsg.mvp.iview.ILoginView;

public interface ILoginPresenter extends MvpPresenter<ILoginView> {
    void onClickBtnCheckLogin(String login, String password);
    void handleSignInResult(GoogleSignInResult result);
}
