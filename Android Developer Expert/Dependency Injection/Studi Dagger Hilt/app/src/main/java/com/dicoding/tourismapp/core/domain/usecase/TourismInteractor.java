package com.dicoding.tourismapp.core.domain.usecase;

import com.dicoding.tourismapp.core.data.Resource;
import com.dicoding.tourismapp.core.domain.model.Tourism;
import com.dicoding.tourismapp.core.domain.repository.ITourismRepository;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Flowable;

/**
 * Created by Budiliauw87 on 2022-08-15.
 * budiliauw87.github.io
 * Budiliauw87@gmail.com
 */
public class TourismInteractor implements TourismUseCase {

    private ITourismRepository iTourismRepository;
    @Inject
    public TourismInteractor(ITourismRepository tourismRepository) {
        this.iTourismRepository = tourismRepository;
    }
    /*
    private volatile static TourismInteractor INSTANCE = null;
    public static TourismInteractor getInstance(ITourismRepository iTourismRepository) {
        if (INSTANCE == null) {
            synchronized (TourismInteractor.class) {
                if (INSTANCE == null) {
                    INSTANCE = new TourismInteractor(iTourismRepository);
                }
            }
        }
        return INSTANCE;
    }*/

    @Override
    public Flowable<Resource<List<Tourism>>> getAllTourism() {
        return iTourismRepository.getAllTourism();
    }

    @Override
    public Flowable<List<Tourism>> getFavoriteTourism() {
        return iTourismRepository.getFavoriteTourism();
    }

    @Override
    public void setFavorite(Tourism tourism, int stateFav) {
        iTourismRepository.setFavorite(tourism,stateFav);
    }
}
