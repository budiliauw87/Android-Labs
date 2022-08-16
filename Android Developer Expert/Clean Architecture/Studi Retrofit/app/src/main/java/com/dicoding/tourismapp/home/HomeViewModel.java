package com.dicoding.tourismapp.home;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.dicoding.tourismapp.core.data.Resource;
import com.dicoding.tourismapp.core.domain.model.Tourism;
import com.dicoding.tourismapp.core.domain.usecase.TourismUseCase;

import java.util.List;

public class HomeViewModel extends ViewModel {
    private TourismUseCase tourismUseCase;

    public HomeViewModel(TourismUseCase tourismUseCase) {
        this.tourismUseCase = tourismUseCase;
    }

    public LiveData<Resource<List<Tourism>>> getAllTourism() {
        return tourismUseCase.getAllTourism();
    }
}