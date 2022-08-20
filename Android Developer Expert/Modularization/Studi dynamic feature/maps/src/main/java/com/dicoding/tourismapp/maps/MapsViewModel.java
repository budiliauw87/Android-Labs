package com.dicoding.tourismapp.maps;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.LiveDataReactiveStreams;
import androidx.lifecycle.ViewModel;

import com.dicoding.tourismapp.core.data.Resource;
import com.dicoding.tourismapp.core.domain.model.Tourism;
import com.dicoding.tourismapp.core.domain.usecase.TourismUseCase;

import java.util.List;

/**
 * Created by Budiliauw87 on 2022-08-20.
 * budiliauw87.github.io
 * Budiliauw87@gmail.com
 */
public class MapsViewModel extends ViewModel {
    private TourismUseCase tourismUseCase;

    public MapsViewModel(TourismUseCase tourismUseCase) {
        this.tourismUseCase = tourismUseCase;
    }

    public LiveData<Resource<List<Tourism>>> getAllTourism() {
        return LiveDataReactiveStreams.fromPublisher(tourismUseCase.getAllTourism());
    }
}
