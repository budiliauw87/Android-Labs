package com.dicoding.tourismapp.core.data.source.remote.network;

import com.dicoding.tourismapp.core.data.source.remote.response.ListTourismResponse;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by Budiliauw87 on 2022-08-16.
 * budiliauw87.github.io
 * Budiliauw87@gmail.com
 */
public interface ApiService {
    @GET("list")
    Call<ListTourismResponse>  getList();
}
