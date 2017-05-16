package com.igordubrovin.tfsmsg.di.modules;

import com.igordubrovin.tfsmsg.db.DialogItem;
import com.igordubrovin.tfsmsg.db.MessageItem;
import com.igordubrovin.tfsmsg.utils.DateHelper;
import com.igordubrovin.tfsmsg.utils.ImageAnimation;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Игорь on 15.05.2017.
 */
@Module
public class CommonModule {

    @Provides
    ImageAnimation provideImageAnimation(){
        return new ImageAnimation();
    }

    @Provides
    DialogItem provideDialogItem(){
        return new DialogItem();
    }

    @Provides
    MessageItem provideMessageItem(){
        return new MessageItem();
    }

    @Provides
    DateHelper provideDateHelper(){
        return new DateHelper();
    }
}
