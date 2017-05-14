package com.igordubrovin.tfsmsg.di.components;

import com.igordubrovin.tfsmsg.activities.LoginActivity;
import com.igordubrovin.tfsmsg.activities.SplashActivity;
import com.igordubrovin.tfsmsg.di.modules.AppModule;
import com.igordubrovin.tfsmsg.di.modules.LoginModule;
import com.igordubrovin.tfsmsg.di.modules.SingleModule;
import com.igordubrovin.tfsmsg.di.modules.SplashModule;
import com.igordubrovin.tfsmsg.di.modules.UtilsModule;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {AppModule.class, UtilsModule.class})
public interface AppComponent {
    void inject(LoginActivity loginActivity);
    void inject(SplashActivity splashActivity);
    LoginScreenComponent addLoginScreenComponent(LoginModule loginModule);
    SplashScreenComponent addSplashScreenComponent(SplashModule splashModule);
    SingleComponent addSingleComponent(SingleModule singleModule);
}
