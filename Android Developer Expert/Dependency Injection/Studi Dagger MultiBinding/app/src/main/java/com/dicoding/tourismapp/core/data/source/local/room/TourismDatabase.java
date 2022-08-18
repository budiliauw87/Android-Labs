package com.dicoding.tourismapp.core.data.source.local.room;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.dicoding.tourismapp.core.data.source.local.entity.TourismEntity;

/**
 * Created by Budiliauw87 on 2022-08-14.
 * budiliauw87.github.io
 * Budiliauw87@gmail.com
 */
@Database(entities = {TourismEntity.class},version = 1,exportSchema = false)
public abstract class TourismDatabase extends RoomDatabase {
    public abstract TourismDao tourismDao();
    /*private static volatile TourismDatabase INSTANCE;

    public static TourismDatabase getInstance(Context context){
        if(INSTANCE == null){
            synchronized (TourismDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            TourismDatabase.class, "Tourism.db").build();
                }
            }
        }
        return INSTANCE;
    }*/
}
