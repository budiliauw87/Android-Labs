package com.dicoding.tourismapp.core.ui;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.dicoding.tourismapp.core.domain.usecase.TourismUseCase;
import com.dicoding.tourismapp.detail.DetailTourismViewModel;
import com.dicoding.tourismapp.di.AppScope;
import com.dicoding.tourismapp.favorite.FavoriteViewModel;
import com.dicoding.tourismapp.home.HomeViewModel;

import java.util.Map;

import javax.inject.Inject;
import javax.inject.Provider;

/**
 * Created by Budiliauw87 on 2020-12-27.
 * liautech.com
 * Budiliauw87@gmail.com
 */
@AppScope
public class ViewModelFactory extends ViewModelProvider.NewInstanceFactory {

    //private final TourismUseCase tourismUseCase;
    private static final String TAG = "ViewModelFactory";
    private final Map<Class<? extends ViewModel>, Provider<ViewModel>> creators;

    @Inject
    public ViewModelFactory(Map<Class<? extends ViewModel>, Provider<ViewModel>> creators) {
        this.creators = creators;
        //this.tourismUseCase = tourismUseCase;
    }
    @NonNull
    @Override

    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        Provider<?extends ViewModel> creator = creators.get(modelClass);
        if (creator == null){
            for (Map.Entry<Class<? extends ViewModel>,Provider<ViewModel>> entry: creators.entrySet()){
                if (modelClass.isAssignableFrom(entry.getKey())){
                    creator = entry.getValue();
                    break;
                }
            }
        }

        if(creator == null){
            throw new IllegalArgumentException("unknown class"+ modelClass);
        }
        try{
            return (T) creator.get();
        }catch (Exception e){
            throw new RuntimeException(e);
        }

    }
}
