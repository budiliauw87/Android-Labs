package com.dicoding.tourismapp.favorite;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.dicoding.tourismapp.core.data.Resource;
import com.dicoding.tourismapp.core.data.TourismRepository;
import com.dicoding.tourismapp.core.data.source.local.entity.TourismEntity;

import java.util.List;

/**
 * Created by Budiliauw87 on 2022-08-14.
 * budiliauw87.github.io
 * Budiliauw87@gmail.com
 */
public class FavoriteViewModel extends ViewModel {
    private TourismRepository tourismRepository;

    public FavoriteViewModel(TourismRepository repository){
        this.tourismRepository = repository;
    }
    public LiveData<List<TourismEntity>> getFavoriteTourism() {
        return tourismRepository.getFavoriteTourism();
    }

}
