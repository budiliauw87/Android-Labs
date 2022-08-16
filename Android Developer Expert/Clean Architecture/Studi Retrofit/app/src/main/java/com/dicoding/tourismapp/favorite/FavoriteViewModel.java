package com.dicoding.tourismapp.favorite;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.dicoding.tourismapp.core.domain.model.Tourism;
import com.dicoding.tourismapp.core.domain.usecase.TourismUseCase;

import java.util.List;

/**
 * Created by Budiliauw87 on 2022-08-14.
 * budiliauw87.github.io
 * Budiliauw87@gmail.com
 */
public class FavoriteViewModel extends ViewModel {
    private TourismUseCase tourismUseCase;

    public FavoriteViewModel(TourismUseCase tourismUseCase){
        this.tourismUseCase = tourismUseCase;
    }
    public LiveData<List<Tourism>> getFavoriteTourism() {
        return tourismUseCase.getFavoriteTourism();
    }

}
