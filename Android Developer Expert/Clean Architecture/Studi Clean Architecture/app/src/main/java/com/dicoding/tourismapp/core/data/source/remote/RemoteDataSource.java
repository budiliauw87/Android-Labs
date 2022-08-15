package com.dicoding.tourismapp.core.data.source.remote;

import android.os.Handler;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.dicoding.tourismapp.core.data.source.remote.network.ApiResponse;
import com.dicoding.tourismapp.core.data.source.remote.response.TourismResponse;
import com.dicoding.tourismapp.core.utils.JSONHelper;

import java.util.List;

/**
 * Created by Budiliauw87 on 2022-08-14.
 * budiliauw87.github.io
 * Budiliauw87@gmail.com
 */
public class RemoteDataSource {
    private static RemoteDataSource INSTANCE;
    private JSONHelper jsonHelper;

    private RemoteDataSource(JSONHelper jsonHelper) {
        this.jsonHelper = jsonHelper;
    }

    public static RemoteDataSource getInstance(JSONHelper helper) {
        if (INSTANCE == null) {
            INSTANCE = new RemoteDataSource(helper);
        }
        return INSTANCE;
    }

    public LiveData<ApiResponse<List<TourismResponse>>> getAllTourism() {
        MutableLiveData<ApiResponse<List<TourismResponse>>> resultData = new MutableLiveData<>();

        new Handler().postDelayed(()->{
            try {
                List<TourismResponse> list = jsonHelper.loadData();
                if(list.size() > 0){
                    resultData.setValue(ApiResponse.success(list));
                }else{
                    resultData.setValue(ApiResponse.empty("Empty",null));
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        },2000);
        return  resultData;
    }
}
