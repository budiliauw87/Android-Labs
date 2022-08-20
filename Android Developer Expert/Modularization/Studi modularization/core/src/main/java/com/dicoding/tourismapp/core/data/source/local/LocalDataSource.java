package com.dicoding.tourismapp.core.data.source.local;

import com.dicoding.tourismapp.core.data.source.local.entity.TourismEntity;
import com.dicoding.tourismapp.core.data.source.local.room.TourismDatabase;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Completable;
import io.reactivex.Flowable;

/**
 * Created by Budiliauw87 on 2022-08-14.
 * budiliauw87.github.io
 * Budiliauw87@gmail.com
 */
@Singleton
public class LocalDataSource {
    private TourismDatabase tourismDatabase;
    /*private static LocalDataSource instance;

    public static LocalDataSource getInstance(TourismDatabase tourismDatabase) {
        if (instance == null) {
            instance = new LocalDataSource(tourismDatabase);
        }
        return instance;
    }*/

    @Inject
    public LocalDataSource(TourismDatabase database) {
        this.tourismDatabase = database;
    }


    public Flowable<List<TourismEntity>> getAllTourism() {
        return tourismDatabase.tourismDao().getAllTourism();
    }

    public Flowable<List<TourismEntity>> getFavoriteTourism() {
        return tourismDatabase.tourismDao().getFavoriteTourism();
    }

    public Completable insertTourism(List<TourismEntity> tourismEntities) {
        return tourismDatabase.tourismDao().insertTourism(tourismEntities);
    }

    public void setFavoriteTourism(TourismEntity tourismEntity, int isFavorite) {
        tourismEntity.setIsFavorite(isFavorite);
        tourismDatabase.tourismDao().updateFavoriteTourism(tourismEntity);
    }
}
