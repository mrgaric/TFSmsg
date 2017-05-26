package com.igordubrovin.tfsmsg.di.modules;

import com.igordubrovin.tfsmsg.utils.DBFlowHelper;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Игорь on 15.05.2017.
 */
@Module
public class UtilsModule {
    @Provides
    @Singleton
    DBFlowHelper provideDBFlowHelper(){
        return new DBFlowHelper();
    }
}
