package com.dicoding.tourismapp.di;

import com.dicoding.tourismapp.core.domain.usecase.TourismInteractor;
import com.dicoding.tourismapp.core.domain.usecase.TourismUseCase;

import dagger.Binds;
import dagger.Module;
import dagger.hilt.InstallIn;
import dagger.hilt.android.components.ViewModelComponent;
import dagger.hilt.android.scopes.ViewModelScoped;

/**
 * Created by Budiliauw87 on 2022-08-18.
 * budiliauw87.github.io
 * Budiliauw87@gmail.com
 */
@Module
@InstallIn({ViewModelComponent.class})
abstract class AppModule {
    @Binds
    @ViewModelScoped
    abstract TourismUseCase provideTourismUseCase(TourismInteractor tourismInteractor);
}
