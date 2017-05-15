package com.igordubrovin.tfsmsg.di.components;

import com.igordubrovin.tfsmsg.activities.SplashActivity;
import com.igordubrovin.tfsmsg.di.dagger2_annotation.ActivityScope;
import com.igordubrovin.tfsmsg.di.modules.SplashModule;

import dagger.Subcomponent;

@ActivityScope
@Subcomponent(modules = {SplashModule.class})
public interface SplashScreenComponent {
    void inject(SplashActivity splashActivity);
}
