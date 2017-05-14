package com.igordubrovin.tfsmsg.di.modules;

import android.content.Context;

import com.igordubrovin.tfsmsg.utils.LoginManager;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Ксения on 12.05.2017.
 */
@Module
public class UtilsModule {

    @Provides
    @Singleton
    LoginManager provideLoginManager(Context context){
        return new LoginManager(context);
    }
}
