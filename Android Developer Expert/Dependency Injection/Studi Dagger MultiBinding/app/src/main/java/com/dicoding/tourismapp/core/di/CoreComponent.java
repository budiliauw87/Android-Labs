package com.dicoding.tourismapp.core.di;

import android.content.Context;

import com.dicoding.tourismapp.core.domain.repository.ITourismRepository;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;

/**
 * Created by Budiliauw87 on 2022-08-17.
 * budiliauw87.github.io
 * Budiliauw87@gmail.com
 */
@Singleton
@Component(  modules = {RepositoryModule.class})
public interface CoreComponent {

    @Component.Factory
    interface Factory{
        CoreComponent create(@BindsInstance Context context);
    }

    ITourismRepository provideRepository();

}
