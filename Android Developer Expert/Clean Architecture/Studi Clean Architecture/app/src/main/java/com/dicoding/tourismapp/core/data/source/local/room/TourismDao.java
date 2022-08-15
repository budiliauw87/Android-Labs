package com.dicoding.tourismapp.core.data.source.local.room;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.dicoding.tourismapp.core.data.source.local.entity.TourismEntity;

import java.util.List;

/**
 * Created by Budiliauw87 on 2022-08-14.
 * budiliauw87.github.io
 * Budiliauw87@gmail.com
 */

@Dao
public interface TourismDao {

    @Query("SELECT * FROM tourism")
    LiveData<List<TourismEntity>> getAllTourism();

    @Query("SELECT * FROM tourism where isFavorite = 1")
    LiveData<List<TourismEntity>> getFavoriteTourism();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertTourism(List<TourismEntity> tourismEntities);

    @Update
    void updateFavoriteTourism(TourismEntity tourismEntity);
}
