package com.igordubrovin.tfsmsg.utils;

import android.app.Application;

import com.igordubrovin.tfsmsg.di.components.AddDialogScreenComponent;
import com.igordubrovin.tfsmsg.di.components.AppComponent;
import com.igordubrovin.tfsmsg.di.components.CommonComponent;
import com.igordubrovin.tfsmsg.di.components.DaggerAppComponent;
import com.igordubrovin.tfsmsg.di.components.LoginScreenComponent;
import com.igordubrovin.tfsmsg.di.components.MessageScreenComponent;
import com.igordubrovin.tfsmsg.di.components.NavigationScreenComponent;
import com.igordubrovin.tfsmsg.di.components.SplashScreenComponent;
import com.igordubrovin.tfsmsg.di.components.UserComponent;
import com.igordubrovin.tfsmsg.di.modules.AddDialogModule;
import com.igordubrovin.tfsmsg.di.modules.AppModule;
import com.igordubrovin.tfsmsg.di.modules.CommonModule;
import com.igordubrovin.tfsmsg.di.modules.LoginModule;
import com.igordubrovin.tfsmsg.di.modules.MessageModule;
import com.igordubrovin.tfsmsg.di.modules.NavigationModule;
import com.igordubrovin.tfsmsg.di.modules.SplashModule;
import com.igordubrovin.tfsmsg.di.modules.UserModule;
import com.igordubrovin.tfsmsg.di.modules.UtilsModule;
import com.raizlabs.android.dbflow.config.FlowManager;

/**
 * Created by Игорь on 25.04.2017.
 */

public class App extends Application {

    private static AppComponent appComponent;
    private static LoginScreenComponent loginScreenComponent;
    private static SplashScreenComponent splashScreenComponent;
    private static NavigationScreenComponent navigationScreenComponent;
    private static CommonComponent commonComponent;
    private static UserComponent userComponent;
    private static MessageScreenComponent messageScreenComponent;
    private static AddDialogScreenComponent addDialogScreenComponent;

    @Override
    public void onCreate() {
        super.onCreate();
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

    public static SplashScreenComponent plusSplashScreenComponent(){
        if (splashScreenComponent == null)
            splashScreenComponent = App.getAppComponent().addSplashScreenComponent(new SplashModule());
        return splashScreenComponent;
    }

    public static NavigationScreenComponent plusNavigationScreenComponent(){
        if (navigationScreenComponent == null)
            navigationScreenComponent = userComponent.addNavigationScreenComponent(new NavigationModule());
        return navigationScreenComponent;
    }

    public static UserComponent plusUserComponent(){
        if (userComponent == null)
            userComponent = App.getAppComponent().addUserComponent(new UserModule());
        return userComponent;
    }

    public static void clearUserComponent(){
        userComponent = null;
    }

    public static CommonComponent plusCommonComponent(){
        if (commonComponent == null)
            commonComponent = App.getAppComponent().addSingleComponent(new CommonModule());
        return commonComponent;
    }

    public static MessageScreenComponent plusMessageScreenComponent(){
        if (messageScreenComponent == null)
            messageScreenComponent = userComponent.addMessageScreenComponent(new MessageModule());
        return messageScreenComponent;
    }

    public static AddDialogScreenComponent plusAddDialogScreenComponent(){
        if (addDialogScreenComponent == null)
            addDialogScreenComponent = getAppComponent().addAddDialogScreenComponent(new AddDialogModule());
        return addDialogScreenComponent;
    }
}
