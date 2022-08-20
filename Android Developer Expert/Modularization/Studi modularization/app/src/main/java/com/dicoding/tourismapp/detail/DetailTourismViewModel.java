package com.dicoding.tourismapp.detail;

import androidx.lifecycle.ViewModel;

import com.dicoding.tourismapp.core.domain.model.Tourism;
import com.dicoding.tourismapp.core.domain.usecase.TourismUseCase;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

/**
 * Created by Budiliauw87 on 2022-08-14.
 * budiliauw87.github.io
 * Budiliauw87@gmail.com
 */
@HiltViewModel
public class DetailTourismViewModel extends ViewModel {
    private TourismUseCase tourismUseCase;
    @Inject
    public DetailTourismViewModel(TourismUseCase tourismUseCase) {
        this.tourismUseCase = tourismUseCase;
    }
    public void setFavoriteTourism(Tourism tourism, int newStatus){
        tourismUseCase.setFavorite(tourism,newStatus);
    }
}
