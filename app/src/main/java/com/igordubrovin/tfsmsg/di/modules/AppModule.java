package com.igordubrovin.tfsmsg.di.modules;

import android.content.Context;
import android.view.WindowManager;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Ксения on 12.05.2017.
 */
@Module
public class AppModule {
    private Context context;

    public AppModule(Context context){
        this.context = context;
    }

    @Provides
    @Singleton
    Context provideContext(){
        return context;
    }

    @Provides
    @Singleton
    WindowManager provideWindowManager (Context context) {
        return (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
    }
}
