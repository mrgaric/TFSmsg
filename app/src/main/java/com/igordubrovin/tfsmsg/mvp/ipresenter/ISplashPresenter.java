package com.igordubrovin.tfsmsg.mvp.ipresenter;

import com.hannesdorfmann.mosby3.mvp.MvpPresenter;
import com.igordubrovin.tfsmsg.mvp.iview.ISplashView;

public interface ISplashPresenter extends MvpPresenter<ISplashView> {
    void checkLoginState();
}
