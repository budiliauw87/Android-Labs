package com.dicoding.tourismapp.di;

import com.dicoding.tourismapp.core.domain.usecase.TourismInteractor;
import com.dicoding.tourismapp.core.domain.usecase.TourismUseCase;

import dagger.Binds;
import dagger.Module;

/**
 * Created by Budiliauw87 on 2022-08-18.
 * budiliauw87.github.io
 * Budiliauw87@gmail.com
 */
@Module
abstract class AppModule {
    @Binds
    abstract TourismUseCase provideTourismUseCase(TourismInteractor tourismInteractor);
}
