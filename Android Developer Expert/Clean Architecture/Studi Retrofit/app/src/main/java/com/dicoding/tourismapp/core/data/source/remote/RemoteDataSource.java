package com.dicoding.tourismapp.core.data.source.remote;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.dicoding.tourismapp.core.data.source.remote.network.ApiConfig;
import com.dicoding.tourismapp.core.data.source.remote.network.ApiResponse;
import com.dicoding.tourismapp.core.data.source.remote.network.ApiService;
import com.dicoding.tourismapp.core.data.source.remote.response.ListTourismResponse;
import com.dicoding.tourismapp.core.data.source.remote.response.TourismResponse;
import com.dicoding.tourismapp.core.utils.JSONHelper;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Budiliauw87 on 2022-08-14.
 * budiliauw87.github.io
 * Budiliauw87@gmail.com
 */
public class RemoteDataSource {
    private static RemoteDataSource INSTANCE;
    private JSONHelper jsonHelper;
    private ApiService service;

    private RemoteDataSource(ApiService apiService) {
        this.service = apiService;
    }

    public static RemoteDataSource getInstance(ApiService apiService) {
        if (INSTANCE == null) {
            INSTANCE = new RemoteDataSource(apiService);
        }
        return INSTANCE;
    }

    public LiveData<ApiResponse<List<TourismResponse>>> getAllTourism() {
        MutableLiveData<ApiResponse<List<TourismResponse>>> resultData = new MutableLiveData<>();
        Call<ListTourismResponse> client = ApiConfig.getApiService().getList();
        client.enqueue(new Callback<ListTourismResponse>() {
            @Override
            public void onResponse(Call<ListTourismResponse> call, Response<ListTourismResponse> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        List<TourismResponse> list = response.body().getTourismResponseList();
                        if(list !=null){
                            resultData.setValue(ApiResponse.success(list));
                        }else{
                            resultData.setValue(ApiResponse.empty("Empty",null));
                        }
                    }
                } else {
                    if (response.body() != null) {
                        Log.e("RemoteDatasource", "onFailure: " + response.body().getMessage());
                    }
                }
            }

            @Override
            public void onFailure(Call<ListTourismResponse> call, Throwable t) {
                resultData.setValue(ApiResponse.error(t.toString(),null));
                Log.e("RemoteDatasource", "onFailure: " + t.getMessage());
            }
        });
        return  resultData;
    }
}
