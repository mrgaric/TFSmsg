package com.igordubrovin.tfsmsg.di.components;

import com.igordubrovin.tfsmsg.activities.LoginActivity;
import com.igordubrovin.tfsmsg.di.dagger2_annotation.ActivityScope;
import com.igordubrovin.tfsmsg.di.modules.LoginModule;

import dagger.Subcomponent;

@ActivityScope
@Subcomponent(modules = {LoginModule.class})
public interface LoginScreenComponent {
    void inject(LoginActivity loginActivity);
}