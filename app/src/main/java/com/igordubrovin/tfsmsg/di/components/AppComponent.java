package com.igordubrovin.tfsmsg.di.components;

import android.content.Context;
import android.view.WindowManager;

import com.igordubrovin.tfsmsg.di.modules.AppModule;
import com.igordubrovin.tfsmsg.di.modules.CommonModule;
import com.igordubrovin.tfsmsg.di.modules.LoginModule;
import com.igordubrovin.tfsmsg.di.modules.SplashModule;
import com.igordubrovin.tfsmsg.di.modules.UserModule;
import com.igordubrovin.tfsmsg.di.modules.UtilsModule;
import com.igordubrovin.tfsmsg.utils.DBFlowHelper;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {AppModule.class, UtilsModule.class})
public interface AppComponent {
    Context getContext();
    WindowManager getWindowManager();
    DBFlowHelper getDbFlowHelper();
    LoginScreenComponent addLoginScreenComponent(LoginModule loginModule);
    SplashScreenComponent addSplashScreenComponent(SplashModule splashModule);
    UserComponent addUserComponent(UserModule UserModule);
    CommonComponent addSingleComponent(CommonModule commonModule);
}
