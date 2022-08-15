package com.dicoding.tourismapp.core.di;

import android.content.Context;

import com.dicoding.tourismapp.core.data.TourismRepository;
import com.dicoding.tourismapp.core.data.source.local.LocalDataSource;
import com.dicoding.tourismapp.core.data.source.local.room.TourismDatabase;
import com.dicoding.tourismapp.core.data.source.remote.RemoteDataSource;
import com.dicoding.tourismapp.core.domain.repository.ITourismRepository;
import com.dicoding.tourismapp.core.domain.usecase.TourismInteractor;
import com.dicoding.tourismapp.core.domain.usecase.TourismUseCase;
import com.dicoding.tourismapp.core.utils.AppExecutors;
import com.dicoding.tourismapp.core.utils.JSONHelper;

/**
 * Created by Budiliauw87 on 2020-12-27.
 * liautech.com
 * Budiliauw87@gmail.com
 */
public class Injection {

    public static ITourismRepository provideRepository(Context context) {
        RemoteDataSource remoteDataSource = RemoteDataSource.getInstance(new JSONHelper(context));
        TourismDatabase tourismDatabase = TourismDatabase.getInstance(context);
        LocalDataSource localDataSource = LocalDataSource.getInstance(tourismDatabase);
        AppExecutors appExecutors = new AppExecutors();
        return TourismRepository.getInstance(remoteDataSource, localDataSource, appExecutors);
    }

    public static TourismUseCase provideTourismUseCase (Context context) {
        ITourismRepository iTourismRepository = provideRepository(context);
        return TourismInteractor.getInstance(iTourismRepository);
    }
}
