package com.igordubrovin.tfsmsg.di.components;

import com.igordubrovin.tfsmsg.di.dagger2_annotation.ChatScreenScope;
import com.igordubrovin.tfsmsg.di.modules.LoginModule;
import com.igordubrovin.tfsmsg.mvp.presenters.LoginPresenter;

import dagger.Subcomponent;

/**
 * Created by Ксения on 13.05.2017.
 */

@ChatScreenScope
@Subcomponent(modules = {LoginModule.class})
public interface LoginScreenComponent {
    LoginPresenter getLoginPresenter();
}