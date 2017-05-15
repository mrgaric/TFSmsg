package com.igordubrovin.tfsmsg.di.dagger2_annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Scope;

/**
 * Created by Игорь on 15.05.2017.
 */
@Scope
@Retention(RetentionPolicy.RUNTIME)
public @interface UserScope {
}
