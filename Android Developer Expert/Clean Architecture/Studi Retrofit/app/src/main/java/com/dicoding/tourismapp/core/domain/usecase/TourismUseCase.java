package com.dicoding.tourismapp.core.domain.usecase;

import androidx.lifecycle.LiveData;

import com.dicoding.tourismapp.core.data.Resource;
import com.dicoding.tourismapp.core.domain.model.Tourism;

import java.util.List;

/**
 * Created by Budiliauw87 on 2022-08-15.
 * budiliauw87.github.io
 * Budiliauw87@gmail.com
 */
public interface TourismUseCase {
    LiveData<Resource<List<Tourism>>> getAllTourism();
    LiveData<List<Tourism>> getFavoriteTourism();
    void setFavorite(Tourism tourism, int stateFav);
}
