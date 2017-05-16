package com.igordubrovin.tfsmsg.di.modules;

import com.igordubrovin.tfsmsg.adapters.MessageAdapter;
import com.igordubrovin.tfsmsg.di.dagger2_annotation.ActivityScope;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Игорь on 15.05.2017.
 */
@Module
public class MessageModule {

    @ActivityScope
    @Provides
    MessageAdapter provideDialogsAdapter(String login){
        return new MessageAdapter(login);
    }
}
