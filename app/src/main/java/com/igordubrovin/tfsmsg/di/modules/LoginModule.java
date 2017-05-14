package com.igordubrovin.tfsmsg.di.modules;

import com.igordubrovin.tfsmsg.di.dagger2_annotation.ChatScreenScope;
import com.igordubrovin.tfsmsg.mvp.models.LoginModel;
import com.igordubrovin.tfsmsg.mvp.presenters.LoginPresenter;
import com.igordubrovin.tfsmsg.utils.LoginManager;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Ксения on 13.05.2017.
 */

@Module
public class LoginModule {

    @Provides
    @ChatScreenScope
    LoginPresenter provideLoginPresenter(LoginModel loginModel){
        return new LoginPresenter(loginModel);
    }

    @Provides
    @ChatScreenScope
    LoginModel provideLoginModel(LoginManager loginManager){
        return new LoginModel(loginManager);
    }
}
