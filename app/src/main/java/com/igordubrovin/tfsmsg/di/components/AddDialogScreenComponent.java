package com.igordubrovin.tfsmsg.di.components;

import com.igordubrovin.tfsmsg.activities.AddDialogActivity;
import com.igordubrovin.tfsmsg.di.dagger2_annotation.ActivityScope;
import com.igordubrovin.tfsmsg.di.modules.AddDialogModule;

import dagger.Subcomponent;

/**
 * Created by Ксения on 30.05.2017.
 */
@ActivityScope
@Subcomponent(modules = AddDialogModule.class)
public interface AddDialogScreenComponent {
    void inject(AddDialogActivity addDialogActivity);
}
