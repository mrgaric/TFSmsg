package com.igordubrovin.tfsmsg.di.components;

import com.igordubrovin.tfsmsg.di.modules.CommonModule;
import com.igordubrovin.tfsmsg.utils.DateHelper;
import com.igordubrovin.tfsmsg.utils.DialogItem;
import com.igordubrovin.tfsmsg.utils.ImageAnimation;
import com.igordubrovin.tfsmsg.utils.MessageItem;

import dagger.Subcomponent;

/**
 * Created by Игорь on 15.05.2017.
 */
@Subcomponent(modules = {CommonModule.class})
public interface CommonComponent {
    ImageAnimation getImageAnimation();
    DialogItem getDialogItem();
    MessageItem getMessageItem();
    DateHelper getDateHelper();
}
