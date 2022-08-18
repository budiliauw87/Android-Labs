package com.dicoding.tourismapp.core.data.source.remote;

import com.dicoding.tourismapp.core.data.source.remote.network.ApiResponse;
import com.dicoding.tourismapp.core.data.source.remote.network.ApiService;
import com.dicoding.tourismapp.core.data.source.remote.response.TourismResponse;
import com.dicoding.tourismapp.core.utils.JSONHelper;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.PublishSubject;

/**
 * Created by Budiliauw87 on 2022-08-14.
 * budiliauw87.github.io
 * Budiliauw87@gmail.com
 */
public class RemoteDataSource {
    //private static RemoteDataSource INSTANCE;
    private JSONHelper jsonHelper;
    private ApiService service;

    @Inject
    public RemoteDataSource(ApiService apiService) {
        this.service = apiService;
    }
    /*
    public static RemoteDataSource getInstance(ApiService apiService) {
        if (INSTANCE == null) {
            INSTANCE = new RemoteDataSource(apiService);
        }
        return INSTANCE;
    }*/

    public Flowable<ApiResponse<List<TourismResponse>>> getAllTourism() {
        PublishSubject<ApiResponse<List<TourismResponse>>> resultData = PublishSubject.create();
        service.getList()
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .take(1)
                .subscribe(response->{
                    List<TourismResponse> list = response.getTourismResponseList();
                    if(list !=null){
                        resultData.onNext(ApiResponse.success(list));
                    }else{
                        resultData.onNext(ApiResponse.empty("Empty",null));
                    }
                },error->{
                    resultData.onNext(ApiResponse.error(error.toString(),null));
                });
        return resultData.toFlowable(BackpressureStrategy.BUFFER);
    }
}
