package com.igordubrovin.tfsmsg.di.modules;

import com.google.firebase.auth.FirebaseAuth;
import com.igordubrovin.tfsmsg.di.dagger2_annotation.UserScope;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Игорь on 15.05.2017.
 */
@Module
public class UserModule {

    @UserScope
    @Provides
    String provideUserName(FirebaseAuth firebaseAuth){
        return firebaseAuth.getCurrentUser().getDisplayName();
    }

}
