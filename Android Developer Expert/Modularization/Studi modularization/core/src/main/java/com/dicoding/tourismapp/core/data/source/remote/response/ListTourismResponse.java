package com.dicoding.tourismapp.core.data.source.remote.response;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Budiliauw87 on 2022-08-16.
 * budiliauw87.github.io
 * Budiliauw87@gmail.com
 */
public class ListTourismResponse {
    @SerializedName("error")
    private boolean error;
    @SerializedName("message")
    private String message;
    @SerializedName("places")
    private List<TourismResponse> tourismResponseList;

    public ListTourismResponse(){}

    public boolean isError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<TourismResponse> getTourismResponseList() {
        return tourismResponseList;
    }

    public void setTourismResponseList(List<TourismResponse> tourismResponseList) {
        this.tourismResponseList = tourismResponseList;
    }
}
