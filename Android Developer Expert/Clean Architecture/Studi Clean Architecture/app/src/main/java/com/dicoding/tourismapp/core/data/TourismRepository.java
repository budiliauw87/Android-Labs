package com.dicoding.tourismapp.core.data;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Transformations;

import com.dicoding.tourismapp.core.data.source.local.LocalDataSource;
import com.dicoding.tourismapp.core.data.source.local.entity.TourismEntity;
import com.dicoding.tourismapp.core.data.source.remote.RemoteDataSource;
import com.dicoding.tourismapp.core.data.source.remote.network.ApiResponse;
import com.dicoding.tourismapp.core.data.source.remote.response.TourismResponse;
import com.dicoding.tourismapp.core.domain.model.Tourism;
import com.dicoding.tourismapp.core.domain.repository.ITourismRepository;
import com.dicoding.tourismapp.core.utils.AppExecutors;
import com.dicoding.tourismapp.core.utils.DataMapper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Budiliauw87 on 2022-08-14.
 * budiliauw87.github.io
 * Budiliauw87@gmail.com
 */
public class TourismRepository implements ITourismRepository {
    private volatile static TourismRepository INSTANCE = null;
    private final RemoteDataSource remoteDataSource;
    private final LocalDataSource localDataSource;
    private final AppExecutors appExecutors;

    private TourismRepository(@NonNull RemoteDataSource remoteDataSource, @NonNull LocalDataSource localDataSource,
                              @NonNull AppExecutors executors) {
        this.remoteDataSource = remoteDataSource;
        this.localDataSource = localDataSource;
        this.appExecutors = executors;
    }

    public static TourismRepository getInstance(RemoteDataSource remoteSource, LocalDataSource localSource, AppExecutors executors) {
        if (INSTANCE == null) {
            synchronized (TourismRepository.class) {
                if (INSTANCE == null) {
                    INSTANCE = new TourismRepository(remoteSource, localSource, executors);
                }
            }
        }
        return INSTANCE;
    }
    @Override
    public LiveData<Resource<List<Tourism>>> getAllTourism() {
        return new NetworkBoundResource<List<Tourism>, List<TourismResponse>>(appExecutors) {

            @Override
            protected Boolean shouldFetch(List<Tourism> data) {
                return (data == null) || (data.size() == 0);
            }

            @Override
            protected LiveData<List<Tourism>> loadFromDB() {
                return Transformations.map(localDataSource.getAllTourism(),input -> {
                    return DataMapper.mapEntitiesToDomain(input);
                });
            }

            @Override
            protected LiveData<ApiResponse<List<TourismResponse>>> createCall() {
                return remoteDataSource.getAllTourism();
            }

            @Override
            protected void saveCallResult(List<TourismResponse> data) {
                List<TourismEntity> tourismEntityList = new ArrayList<>();
                for (TourismResponse tourismResponse : data) {
                    TourismEntity tourismEntity = new TourismEntity();
                    tourismEntity.setId(tourismResponse.getId());
                    tourismEntity.setName(tourismResponse.getName());
                    tourismEntity.setAddress(tourismResponse.getAddress());
                    tourismEntity.setDescription(tourismResponse.getDescription());
                    tourismEntity.setLatitude(tourismResponse.getLatitude());
                    tourismEntity.setLongitude(tourismResponse.getLongitude());
                    tourismEntity.setImage(tourismResponse.getImage());
                    tourismEntity.setLike(tourismResponse.getLike());
                    tourismEntityList.add(tourismEntity);
                }
                localDataSource.insertTourism(tourismEntityList);
            }
        }.asLiveData();
    }
    @Override
    public LiveData<List<Tourism>> getFavoriteTourism() {
        return Transformations.map(localDataSource.getFavoriteTourism(),input -> {
            return DataMapper.mapEntitiesToDomain(input);
        });
    }
    @Override
    public void setFavorite(Tourism tourism, int stateFav) {
        TourismEntity entity = DataMapper.mapDomainToEntity(tourism);
        appExecutors.diskIO().execute(() -> localDataSource.setFavoriteTourism(entity,stateFav));
    }
}
