package com.igordubrovin.tfsmsg.utils;

import android.app.Application;

import com.igordubrovin.tfsmsg.di.components.AppComponent;
import com.igordubrovin.tfsmsg.di.components.DaggerAppComponent;
import com.igordubrovin.tfsmsg.di.components.LoginScreenComponent;
import com.igordubrovin.tfsmsg.di.components.SingleComponent;
import com.igordubrovin.tfsmsg.di.components.SplashScreenComponent;
import com.igordubrovin.tfsmsg.di.modules.AppModule;
import com.igordubrovin.tfsmsg.di.modules.LoginModule;
import com.igordubrovin.tfsmsg.di.modules.SingleModule;
import com.igordubrovin.tfsmsg.di.modules.SplashModule;
import com.igordubrovin.tfsmsg.di.modules.UtilsModule;
import com.raizlabs.android.dbflow.config.FlowManager;

/**
 * Created by Игорь on 25.04.2017.
 */

public class App extends Application {

    private static AppComponent appComponent;
    private static LoginScreenComponent loginScreenComponent;
    private static SplashScreenComponent splashScreenComponent;
    private static SingleComponent singleComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        FlowManager.init(this);
        appComponent = DaggerAppComponent.builder()
                .appModule(new AppModule(this))
                .utilsModule(new UtilsModule())
                .build();
    }

    public static AppComponent getAppComponent(){
        return appComponent;
    }

    public static LoginScreenComponent plusLoginScreenComponent(){
        if (loginScreenComponent == null)
            loginScreenComponent = App.getAppComponent().addLoginScreenComponent(new LoginModule());
        return loginScreenComponent;
    }

    public static void clearLoginScreenComponent(){
        loginScreenComponent = null;
    }

    public static SplashScreenComponent plusSplashScreenComponent(){
        if (splashScreenComponent == null)
            splashScreenComponent = App.getAppComponent().addSplashScreenComponent(new SplashModule());
        return splashScreenComponent;
    }

    public static void clearSplashScreenComponent(){
        splashScreenComponent = null;
    }

    public static SingleComponent plusSingleComponent(){
        if (singleComponent == null)
            singleComponent = App.getAppComponent().addSingleComponent(new SingleModule());
        return singleComponent;
    }

    public static void clearSingleComponent(){
        singleComponent = null;
    }
}
