package com.igordubrovin.tfsmsg.mvp.ipresenter;

import com.hannesdorfmann.mosby3.mvp.MvpPresenter;
import com.igordubrovin.tfsmsg.mvp.iview.ISplashView;

/**
 * Created by Ксения on 14.05.2017.
 */

public interface ISplashPresenter extends MvpPresenter<ISplashView> {
    void checkLoginState();
}
