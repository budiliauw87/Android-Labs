package com.dicoding.tourismapp.di;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.dicoding.tourismapp.core.ui.ViewModelFactory;
import com.dicoding.tourismapp.detail.DetailTourismViewModel;
import com.dicoding.tourismapp.favorite.FavoriteViewModel;
import com.dicoding.tourismapp.home.HomeViewModel;

import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;

/**
 * Created by Budiliauw87 on 2022-08-19.
 * budiliauw87.github.io
 * Budiliauw87@gmail.com
 */
@Module
public abstract class ViewModelModule {
    @Binds
    @IntoMap
    @ViewModelKey(HomeViewModel.class)
    abstract ViewModel bindHomeViewModel(HomeViewModel viewModel);


    @Binds
    @IntoMap
    @ViewModelKey(FavoriteViewModel.class)
    abstract ViewModel bindFavoriteViewModel(FavoriteViewModel viewModel);

    @Binds
    @IntoMap
    @ViewModelKey(DetailTourismViewModel.class)
    abstract ViewModel bindDetailTourismViewModel(DetailTourismViewModel viewModel);

    @Binds
    public abstract ViewModelProvider.Factory binViewModelFactory(ViewModelFactory factory);

}
