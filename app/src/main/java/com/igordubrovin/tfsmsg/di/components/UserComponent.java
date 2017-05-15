package com.igordubrovin.tfsmsg.di.components;

import com.igordubrovin.tfsmsg.di.dagger2_annotation.UserScope;
import com.igordubrovin.tfsmsg.di.modules.MessageModule;
import com.igordubrovin.tfsmsg.di.modules.NavigationModule;
import com.igordubrovin.tfsmsg.di.modules.UserModule;

import dagger.Subcomponent;

/**
 * Created by Игорь on 15.05.2017.
 */
@UserScope
@Subcomponent(modules = {UserModule.class})
public interface UserComponent {
    NavigationScreenComponent addNavigationScreenComponent(NavigationModule navigationModule);
    MessageScreenComponent addMessageScreenComponent(MessageModule messageModule);
}
