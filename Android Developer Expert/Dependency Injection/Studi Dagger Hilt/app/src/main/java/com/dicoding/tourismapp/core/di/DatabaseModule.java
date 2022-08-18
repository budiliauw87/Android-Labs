package com.dicoding.tourismapp.core.di;

import android.content.Context;

import androidx.room.Room;

import com.dicoding.tourismapp.core.data.source.local.room.TourismDao;
import com.dicoding.tourismapp.core.data.source.local.room.TourismDatabase;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.android.qualifiers.ApplicationContext;
import dagger.hilt.components.SingletonComponent;

/**
 * Created by Budiliauw87 on 2022-08-17.
 * budiliauw87.github.io
 * Budiliauw87@gmail.com
 */

@Module
@InstallIn({SingletonComponent.class})
public class DatabaseModule {

    @Singleton
    @Provides
    static TourismDatabase provideDatabase(@ApplicationContext Context context){
        return Room.databaseBuilder(context.getApplicationContext(),
                TourismDatabase.class, "Tourism.db").build();
    }

    @Provides
    static TourismDao provideTourismDao(TourismDatabase database){
        return database.tourismDao();
    }

}
