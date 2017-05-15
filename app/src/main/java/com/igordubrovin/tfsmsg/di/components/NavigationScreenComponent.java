package com.igordubrovin.tfsmsg.di.components;

import com.igordubrovin.tfsmsg.activities.NavigationActivity;
import com.igordubrovin.tfsmsg.di.dagger2_annotation.ActivityScope;
import com.igordubrovin.tfsmsg.di.modules.NavigationModule;
import com.igordubrovin.tfsmsg.fragments.DialogsFragment;

import dagger.Subcomponent;

/**
 * Created by Игорь on 15.05.2017.
 */

@ActivityScope
@Subcomponent(modules = {NavigationModule.class})
public interface NavigationScreenComponent {
    void inject(NavigationActivity navigationActivity);
    void inject(DialogsFragment dialogsFragment);
}
