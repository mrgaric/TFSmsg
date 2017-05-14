package com.igordubrovin.tfsmsg.di.components;

import android.widget.ImageView;

import com.igordubrovin.tfsmsg.di.modules.SingleModule;
import com.igordubrovin.tfsmsg.utils.ImageAnimation;

import dagger.Subcomponent;

/**
 * Created by Ксения on 14.05.2017.
 */
@Subcomponent(modules = SingleModule.class)
public interface SingleComponent {
    ImageAnimation getImageAnimation();
}
