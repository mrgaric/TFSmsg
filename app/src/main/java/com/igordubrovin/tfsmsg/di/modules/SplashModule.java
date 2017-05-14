package com.igordubrovin.tfsmsg.di.modules;

import android.content.Context;
import android.graphics.Point;
import android.view.WindowManager;

import com.igordubrovin.tfsmsg.adapters.ImageAdapter;
import com.igordubrovin.tfsmsg.di.dagger2_annotation.ChatScreenScope;
import com.igordubrovin.tfsmsg.mvp.presenters.SplashPresenter;
import com.igordubrovin.tfsmsg.utils.LoginManager;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Ксения on 14.05.2017.
 */

@Module
public class SplashModule {

    @ChatScreenScope
    @Provides
    Point providePoint(){
        return new Point();
    }

    @ChatScreenScope
    @Provides
    ImageAdapter provideImageAdapter(WindowManager windowManager, Point point, Context context){
        windowManager.getDefaultDisplay().getSize(point);
        int width = point.x/2;
        int height = point.y/3;
        return new ImageAdapter(context, width, height);
    }

    @ChatScreenScope
    @Provides
    SplashPresenter provideSplashPresenter(LoginManager loginManager){
        return new SplashPresenter(loginManager);
    }
}
