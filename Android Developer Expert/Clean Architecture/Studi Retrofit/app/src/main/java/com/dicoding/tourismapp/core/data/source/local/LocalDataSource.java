package com.dicoding.tourismapp.core.data.source.local;

import androidx.lifecycle.LiveData;

import com.dicoding.tourismapp.core.data.source.local.entity.TourismEntity;
import com.dicoding.tourismapp.core.data.source.local.room.TourismDatabase;

import java.util.List;

/**
 * Created by Budiliauw87 on 2022-08-14.
 * budiliauw87.github.io
 * Budiliauw87@gmail.com
 */
public class LocalDataSource {

    private TourismDatabase tourismDatabase;
    private static LocalDataSource instance;

    private LocalDataSource(TourismDatabase database) {
        this.tourismDatabase = database;
    }

    public static LocalDataSource getInstance(TourismDatabase tourismDatabase) {
        if (instance == null) {
            instance = new LocalDataSource(tourismDatabase);
        }
        return instance;
    }

    public LiveData<List<TourismEntity>> getAllTourism(){
        return tourismDatabase.tourismDao().getAllTourism();
    }

    public LiveData<List<TourismEntity>> getFavoriteTourism(){
        return tourismDatabase.tourismDao().getFavoriteTourism();
    }

    public void insertTourism(List<TourismEntity> tourismEntities) {
        tourismDatabase.tourismDao().insertTourism(tourismEntities);
    }

    public void setFavoriteTourism(TourismEntity tourismEntity, int isFavorite) {
        tourismEntity.setIsFavorite(isFavorite);
        tourismDatabase.tourismDao().updateFavoriteTourism(tourismEntity);
    }
}
