package com.dicoding.tourismapp.di;

import com.dicoding.tourismapp.core.di.CoreComponent;
import com.dicoding.tourismapp.detail.DetailTourismActivity;
import com.dicoding.tourismapp.favorite.FavoriteFragment;
import com.dicoding.tourismapp.home.HomeFragment;

import dagger.Component;

/**
 * Created by Budiliauw87 on 2022-08-18.
 * budiliauw87.github.io
 * Budiliauw87@gmail.com
 */
@AppScope
@Component(
        dependencies = { CoreComponent.class },
        modules = {AppModule.class , ViewModelModule.class}
)
public interface AppComponent {
    @Component.Factory
    interface Factory{
        AppComponent create(CoreComponent component);
    }
    void inject(HomeFragment fragment);
    void inject(FavoriteFragment fragment);
    void inject(DetailTourismActivity activity);

}
