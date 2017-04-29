package com.igordubrovin.tfsmsg.utils;

import android.app.Application;

import com.raizlabs.android.dbflow.config.FlowManager;

/**
 * Created by Игорь on 25.04.2017.
 */

public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        FlowManager.init(this);
        PrefManager.newInstance(this);
    }
}
