package com.dicoding.tourismapp.core.di;

import com.dicoding.tourismapp.core.data.source.remote.network.ApiService;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Budiliauw87 on 2022-08-17.
 * budiliauw87.github.io
 * Budiliauw87@gmail.com
 */
@Module
public class NetworkModule {

    @Provides
    static OkHttpClient provideOkHttpClient() {
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor()
                .setLevel(HttpLoggingInterceptor.Level.BODY);
        return new OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)
                .build();
    }

    @Provides
    static ApiService provideApiService(OkHttpClient client) {
        return new retrofit2.Retrofit.Builder()
                .baseUrl("https://tourism-api.dicoding.dev/")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(client)
                .build()
                .create(ApiService.class);
    }

}
