package com.igordubrovin.tfsmsg.mvp.iview;

import com.hannesdorfmann.mosby3.mvp.MvpView;

public interface ILoginView extends MvpView {
    void loginSuccessful();
    void showError();
}
