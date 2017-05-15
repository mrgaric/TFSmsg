package com.igordubrovin.tfsmsg.di.modules;

import android.content.Context;
import android.graphics.Point;
import android.view.WindowManager;

import com.igordubrovin.tfsmsg.adapters.ImageAdapter;
import com.igordubrovin.tfsmsg.di.dagger2_annotation.ActivityScope;

import dagger.Module;
import dagger.Provides;

@Module
public class SplashModule {

    @ActivityScope
    @Provides
    Point providePoint(){
        return new Point();
    }

    @ActivityScope
    @Provides
    ImageAdapter provideImageAdapter(WindowManager windowManager, Point point, Context context){
        windowManager.getDefaultDisplay().getSize(point);
        int width = point.x/2;
        int height = point.y/3;
        return new ImageAdapter(context, width, height);
    }
}
