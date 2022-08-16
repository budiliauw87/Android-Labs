package com.dicoding.tourismapp.detail;

import androidx.lifecycle.ViewModel;

import com.dicoding.tourismapp.core.domain.model.Tourism;
import com.dicoding.tourismapp.core.domain.usecase.TourismUseCase;

/**
 * Created by Budiliauw87 on 2022-08-14.
 * budiliauw87.github.io
 * Budiliauw87@gmail.com
 */
public class DetailTourismViewModel extends ViewModel {
    private TourismUseCase tourismUseCase;
    public DetailTourismViewModel(TourismUseCase tourismUseCase) {
        this.tourismUseCase = tourismUseCase;
    }
    public void setFavoriteTourism(Tourism tourism, int newStatus){
        tourismUseCase.setFavorite(tourism,newStatus);
    }
}
