package com.dicoding.tourismapp.core.domain.repository;

import com.dicoding.tourismapp.core.data.Resource;
import com.dicoding.tourismapp.core.domain.model.Tourism;

import java.util.List;

import io.reactivex.Flowable;

/**
 * Created by Budiliauw87 on 2022-08-15.
 * budiliauw87.github.io
 * Budiliauw87@gmail.com
 */
public interface ITourismRepository {
    Flowable<Resource<List<Tourism>>> getAllTourism();
    Flowable<List<Tourism>> getFavoriteTourism();
    void setFavorite(Tourism tourism, int stateFav);
}
