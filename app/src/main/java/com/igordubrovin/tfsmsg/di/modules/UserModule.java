package com.igordubrovin.tfsmsg.di.modules;

import com.igordubrovin.tfsmsg.di.dagger2_annotation.UserScope;
import com.igordubrovin.tfsmsg.utils.LoginManager;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Игорь on 15.05.2017.
 */
@Module
public class UserModule {

    @UserScope
    @Provides
    String provideUserName(LoginManager loginManager){
        return loginManager.login();
    }

}
