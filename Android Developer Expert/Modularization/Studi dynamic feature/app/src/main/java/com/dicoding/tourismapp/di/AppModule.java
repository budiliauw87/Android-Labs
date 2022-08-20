package com.dicoding.tourismapp.di;

import com.dicoding.tourismapp.core.domain.usecase.TourismInteractor;
import com.dicoding.tourismapp.core.domain.usecase.TourismUseCase;

import javax.inject.Singleton;

import dagger.Binds;
import dagger.Module;
import dagger.hilt.InstallIn;
import dagger.hilt.components.SingletonComponent;

/**
 * Created by Budiliauw87 on 2022-08-18.
 * budiliauw87.github.io
 * Budiliauw87@gmail.com
 */
@Module
@InstallIn(SingletonComponent.class)
abstract class AppModule {
    @Binds
    @Singleton
    abstract TourismUseCase provideTourismUseCase(TourismInteractor tourismInteractor);
}
