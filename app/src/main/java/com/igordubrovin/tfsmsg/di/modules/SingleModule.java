package com.igordubrovin.tfsmsg.di.modules;

import com.igordubrovin.tfsmsg.utils.ImageAnimation;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Ксения on 14.05.2017.
 */
@Module
public class SingleModule {

    @Provides
    ImageAnimation provideImageAnimation(){
        return new ImageAnimation();
    }
}
