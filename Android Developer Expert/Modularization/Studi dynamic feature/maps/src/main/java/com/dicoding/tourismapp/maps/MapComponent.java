package com.dicoding.tourismapp.maps;

import android.content.Context;

import com.dicoding.tourismapp.di.MapsModuleDependencies;

import dagger.BindsInstance;
import dagger.Component;

/**
 * Created by Budiliauw87 on 2022-08-20.
 * budiliauw87.github.io
 * Budiliauw87@gmail.com
 */
@Component(dependencies = {MapsModuleDependencies.class})
public interface MapComponent {
    void inject(MapsActivity activity);

    @Component.Builder
    interface Builder {
        Builder context(@BindsInstance Context context);
        Builder appDependencies(MapsModuleDependencies mapsModuleDependencies);
        MapComponent build();
    }
}
