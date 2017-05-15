package com.igordubrovin.tfsmsg.di.modules;

import com.igordubrovin.tfsmsg.adapters.DialogsAdapter;
import com.igordubrovin.tfsmsg.di.dagger2_annotation.ActivityScope;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Игорь on 15.05.2017.
 */
@Module
public class NavigationModule {

    @ActivityScope
    @Provides
    DialogsAdapter provideDialogsAdapter(){
        return new DialogsAdapter();
    }
}
