package com.dicoding.tourismapp.di;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import androidx.lifecycle.ViewModel;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import dagger.MapKey;
import kotlin.annotation.MustBeDocumented;

/**
 * Created by Budiliauw87 on 2022-08-19.
 * budiliauw87.github.io
 * Budiliauw87@gmail.com
 */

@MustBeDocumented
@Target({METHOD})
@Retention(RUNTIME)
@MapKey
public @interface ViewModelKey {
    Class<? extends ViewModel> value();
}
