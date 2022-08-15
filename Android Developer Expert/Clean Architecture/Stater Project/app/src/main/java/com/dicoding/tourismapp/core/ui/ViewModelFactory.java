package com.dicoding.tourismapp.core.ui;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.dicoding.tourismapp.core.data.TourismRepository;
import com.dicoding.tourismapp.core.di.Injection;
import com.dicoding.tourismapp.detail.DetailTourismViewModel;
import com.dicoding.tourismapp.favorite.FavoriteViewModel;
import com.dicoding.tourismapp.home.HomeViewModel;

/**
 * Created by Budiliauw87 on 2020-12-27.
 * liautech.com
 * Budiliauw87@gmail.com
 */
public class ViewModelFactory extends ViewModelProvider.NewInstanceFactory {
    private static volatile ViewModelFactory INSTANCE;
    private final TourismRepository tourismRepository;

    private ViewModelFactory(TourismRepository repository) {
        tourismRepository = repository;
    }

    public static ViewModelFactory getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (ViewModelFactory.class) {
                if (INSTANCE == null) {
                    INSTANCE = new ViewModelFactory(Injection.provideRepository(context));
                }
            }
        }
        return INSTANCE;
    }

    @NonNull
    @Override

    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {

        if (modelClass.isAssignableFrom(HomeViewModel.class)) {
            return (T) new HomeViewModel(tourismRepository);
        }else if(modelClass.isAssignableFrom(DetailTourismViewModel.class)){
            return (T) new DetailTourismViewModel(tourismRepository);
        }else if(modelClass.isAssignableFrom(FavoriteViewModel.class)){
            return (T) new FavoriteViewModel(tourismRepository);
        }
        throw new IllegalArgumentException("Unknown ViewModel class: " + modelClass.getName());
    }
}
