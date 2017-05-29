package com.igordubrovin.tfsmsg.di.modules;

import android.content.Context;
import android.view.WindowManager;

import com.google.firebase.auth.FirebaseAuth;
import com.igordubrovin.tfsmsg.firebase.dialog.DialogRepository;
import com.igordubrovin.tfsmsg.firebase.message.MessageRepository;
import com.igordubrovin.tfsmsg.utils.DialogIdManager;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class AppModule {
    private Context context;

    public AppModule(Context context){
        this.context = context;
    }

    @Provides
    @Singleton
    Context provideContext(){
        return context;
    }

    @Provides
    @Singleton
    WindowManager provideWindowManager (Context context) {
        return (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
    }

    @Provides
    @Singleton
    FirebaseAuth provideFirebaseAuth(){
        return FirebaseAuth.getInstance();
    }

    @Provides
    @Singleton
    DialogIdManager provideDialogIdManager(){
        return new DialogIdManager();
    }

    @Provides
    @Singleton
    DialogRepository provideDialogRepository(DialogIdManager dialogIdManager){
        return new DialogRepository(dialogIdManager);
    }

    @Provides
    @Singleton
    MessageRepository provideMessageRepository(DialogIdManager dialogIdManager){
        return new MessageRepository(dialogIdManager);
    }
}