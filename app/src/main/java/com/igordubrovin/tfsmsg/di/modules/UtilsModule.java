package com.igordubrovin.tfsmsg.di.modules;

import android.content.Context;

import com.igordubrovin.tfsmsg.utils.DBFlowHelper;
import com.igordubrovin.tfsmsg.utils.LoginManager;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Игорь on 15.05.2017.
 */
@Module
public class UtilsModule {

    @Singleton
    @Provides
    LoginManager provideLoginManager(Context context){
        return new LoginManager(context);
    }


    @Provides
    @Singleton
    DBFlowHelper provideDBFlowHelper(){
        return new DBFlowHelper();
    }
}
