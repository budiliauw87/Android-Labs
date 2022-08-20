package com.dicoding.tourismapp.core.di;

import com.dicoding.tourismapp.core.data.TourismRepository;
import com.dicoding.tourismapp.core.domain.repository.ITourismRepository;

import dagger.Binds;
import dagger.Module;
import dagger.hilt.InstallIn;
import dagger.hilt.components.SingletonComponent;

/**
 * Created by Budiliauw87 on 2022-08-17.
 * budiliauw87.github.io
 * Budiliauw87@gmail.com
 */
//@Module(includes = {NetworkModule.class, DatabaseModule.class})
@Module
@InstallIn({SingletonComponent.class})
abstract class RepositoryModule {
    @Binds
    abstract ITourismRepository provideRepository(TourismRepository tourismRepository);
}
