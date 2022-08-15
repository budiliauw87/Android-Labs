package com.dicoding.tourismapp.home;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.dicoding.tourismapp.core.data.Resource;
import com.dicoding.tourismapp.core.data.TourismRepository;
import com.dicoding.tourismapp.core.data.source.local.entity.TourismEntity;

import java.util.List;

public class HomeViewModel extends ViewModel {
    private TourismRepository tourismRepository;

    public HomeViewModel(TourismRepository repository) {
        this.tourismRepository = repository;
    }

    public LiveData<Resource<List<TourismEntity>>> getAllTourism() {
        return tourismRepository.getAllTourism();
    }
}