package com.dicoding.tourismapp.core.domain.usecase;

import androidx.lifecycle.LiveData;

import com.dicoding.tourismapp.core.data.Resource;
import com.dicoding.tourismapp.core.domain.model.Tourism;
import com.dicoding.tourismapp.core.domain.repository.ITourismRepository;

import java.util.List;

/**
 * Created by Budiliauw87 on 2022-08-15.
 * budiliauw87.github.io
 * Budiliauw87@gmail.com
 */
public class TourismInteractor implements TourismUseCase {
    private volatile static TourismInteractor INSTANCE = null;
    private ITourismRepository iTourismRepository;

    public TourismInteractor(ITourismRepository tourismRepository) {
        this.iTourismRepository = tourismRepository;
    }

    public static TourismInteractor getInstance(ITourismRepository iTourismRepository) {
        if (INSTANCE == null) {
            synchronized (TourismInteractor.class) {
                if (INSTANCE == null) {
                    INSTANCE = new TourismInteractor(iTourismRepository);
                }
            }
        }
        return INSTANCE;
    }

    @Override
    public LiveData<Resource<List<Tourism>>> getAllTourism() {
        return iTourismRepository.getAllTourism();
    }

    @Override
    public LiveData<List<Tourism>> getFavoriteTourism() {
        return iTourismRepository.getFavoriteTourism();
    }

    @Override
    public void setFavorite(Tourism tourism, int stateFav) {
        iTourismRepository.setFavorite(tourism,stateFav);
    }
}
