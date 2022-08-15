package com.dicoding.tourismapp.detail;

import androidx.lifecycle.ViewModel;

import com.dicoding.tourismapp.core.data.TourismRepository;
import com.dicoding.tourismapp.core.data.source.local.entity.TourismEntity;

/**
 * Created by Budiliauw87 on 2022-08-14.
 * budiliauw87.github.io
 * Budiliauw87@gmail.com
 */
public class DetailTourismViewModel extends ViewModel {
    private TourismRepository tourismRepository;
    public DetailTourismViewModel(TourismRepository repository) {
        this.tourismRepository = repository;
    }
    public void setFavoriteTourism(TourismEntity tourismEntity, int newStatus){
        tourismRepository.setFavorite(tourismEntity,newStatus);
    }
}
