package com.igordubrovin.tfsmsg.di.components;

import com.igordubrovin.tfsmsg.activities.MessagesActivity;
import com.igordubrovin.tfsmsg.di.dagger2_annotation.ActivityScope;
import com.igordubrovin.tfsmsg.di.modules.MessageModule;

import dagger.Subcomponent;

/**
 * Created by Игорь on 15.05.2017.
 */
@ActivityScope
@Subcomponent(modules = {MessageModule.class})
public interface MessageScreenComponent {
    void inject(MessagesActivity messagesActivity);
}
