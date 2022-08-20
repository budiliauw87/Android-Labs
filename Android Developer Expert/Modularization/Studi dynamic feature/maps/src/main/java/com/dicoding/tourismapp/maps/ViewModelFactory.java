package com.dicoding.tourismapp.maps;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.dicoding.tourismapp.core.domain.usecase.TourismUseCase;

import javax.inject.Inject;

/**
 * Created by Budiliauw87 on 2020-12-27.
 * liautech.com
 * Budiliauw87@gmail.com
 */
public class ViewModelFactory extends ViewModelProvider.NewInstanceFactory {

    private final TourismUseCase tourismUseCase;

    @Inject
    public ViewModelFactory(TourismUseCase tourismUseCase) {
        this.tourismUseCase = tourismUseCase;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {

        if (modelClass.isAssignableFrom(MapsViewModel.class)) {
            return (T) new MapsViewModel(tourismUseCase);
        }
        throw new IllegalArgumentException("Unknown ViewModel class: " + modelClass.getName());
    }
}
