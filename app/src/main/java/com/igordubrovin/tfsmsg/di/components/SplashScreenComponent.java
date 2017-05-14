package com.igordubrovin.tfsmsg.di.components;

import com.igordubrovin.tfsmsg.adapters.ImageAdapter;
import com.igordubrovin.tfsmsg.di.dagger2_annotation.ChatScreenScope;
import com.igordubrovin.tfsmsg.di.modules.SplashModule;
import com.igordubrovin.tfsmsg.mvp.presenters.SplashPresenter;

import dagger.Subcomponent;

/**
 * Created by Ксения on 14.05.2017.
 */

@ChatScreenScope
@Subcomponent(modules = {SplashModule.class})
public interface SplashScreenComponent {
    ImageAdapter getImageAdapter();
    SplashPresenter getSplashPresenter();
}
