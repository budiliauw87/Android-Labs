package com.dicoding.tourismapp.di;

import com.dicoding.tourismapp.core.domain.usecase.TourismUseCase;

import dagger.hilt.EntryPoint;
import dagger.hilt.InstallIn;
import dagger.hilt.components.SingletonComponent;

/**
 * Created by Budiliauw87 on 2022-08-20.
 * budiliauw87.github.io
 * Budiliauw87@gmail.com
 */
@EntryPoint
@InstallIn(SingletonComponent.class)
public interface MapsModuleDependencies {
    TourismUseCase tourismUseCase();
}
