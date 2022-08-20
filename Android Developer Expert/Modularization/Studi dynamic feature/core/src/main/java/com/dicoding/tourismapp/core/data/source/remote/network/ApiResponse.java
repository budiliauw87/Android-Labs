package com.dicoding.tourismapp.core.data.source.remote.network;

import static com.dicoding.tourismapp.core.data.source.remote.network.StatusRespone.EMPTY;
import static com.dicoding.tourismapp.core.data.source.remote.network.StatusRespone.ERROR;
import static com.dicoding.tourismapp.core.data.source.remote.network.StatusRespone.SUCCESS;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;


/**
 * Created by Budiliauw87 on 2020-12-28.
 * liautech.com
 * Budiliauw87@gmail.com
 */
public class ApiResponse<T> {
    public final StatusRespone statusRespone;
    public final String message;
    public final T body;


    public ApiResponse(@NonNull StatusRespone status, @Nullable T body, @Nullable String message) {
        this.statusRespone = status;
        this.body = body;
        this.message = message;
    }

    public static <T> ApiResponse<T> success(@Nullable T body) {
        return new ApiResponse<>(SUCCESS, body, null);
    }

    public static <T> ApiResponse<T> empty(String msg, @Nullable T body) {
        return new ApiResponse<>(EMPTY, body, msg);
    }

    public static <T> ApiResponse<T> error(String msg, @Nullable T body) {
        return new ApiResponse<>(ERROR, body, msg);
    }

}
