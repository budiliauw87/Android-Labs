package com.dicoding.tourismapp.core.utils;

import android.annotation.SuppressLint;

import com.dicoding.tourismapp.core.data.source.local.entity.TourismEntity;
import com.dicoding.tourismapp.core.data.source.remote.response.TourismResponse;
import com.dicoding.tourismapp.core.domain.model.Tourism;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Budiliauw87 on 2022-08-15.
 * budiliauw87.github.io
 * Budiliauw87@gmail.com
 */
public class DataMapper {
    public static List<TourismEntity> mapResponsesToEntities(List<TourismResponse> tourismResponseList){
        @SuppressLint({"NewApi", "LocalSuppress"})
        List<TourismEntity> tourismEntities = tourismResponseList.stream().map(tourismResponse -> {
            TourismEntity tourismEntity = new TourismEntity();
            tourismEntity.setId(tourismResponse.getId());
            tourismEntity.setName(tourismResponse.getName());
            tourismEntity.setDescription(tourismResponse.getDescription());
            tourismEntity.setImage(tourismResponse.getImage());
            tourismEntity.setAddress(tourismResponse.getAddress());
            tourismEntity.setLike(tourismResponse.getLike());
            tourismEntity.setLatitude(tourismResponse.getLatitude());
            tourismEntity.setLongitude(tourismResponse.getLongitude());
            tourismEntity.setIsFavorite(tourismEntity.getIsFavorite());
            return tourismEntity;
        }).collect(Collectors.toList());
        return tourismEntities;
    }
    public static List<Tourism> mapEntitiesToDomain(List<TourismEntity> tourismEntities){
        @SuppressLint({"NewApi", "LocalSuppress"})
        List<Tourism> tourismList = tourismEntities.stream().map(tourismEntity -> {
            Tourism tourism = new Tourism();
            tourism.setId(tourismEntity.getId());
            tourism.setName(tourismEntity.getName());
            tourism.setDescription(tourismEntity.getDescription());
            tourism.setImage(tourismEntity.getImage());
            tourism.setAddress(tourismEntity.getAddress());
            tourism.setLike(tourismEntity.getLike());
            tourism.setLatitude(tourismEntity.getLatitude());
            tourism.setLongitude(tourismEntity.getLongitude());
            tourism.setIsFavorite(tourismEntity.getIsFavorite());
            return tourism;
        }).collect(Collectors.toList());
        return tourismList;
    }


    public static TourismEntity mapDomainToEntity(Tourism tourism){
        TourismEntity tourismEntity = new TourismEntity();
        tourismEntity.setId(tourism.getId());
        tourismEntity.setName(tourism.getName());
        tourismEntity.setDescription(tourism.getDescription());
        tourismEntity.setImage(tourism.getImage());
        tourismEntity.setAddress(tourism.getAddress());
        tourismEntity.setLike(tourism.getLike());
        tourismEntity.setLatitude(tourism.getLatitude());
        tourismEntity.setLongitude(tourism.getLongitude());
        tourismEntity.setIsFavorite(tourism.getIsFavorite());
        return tourismEntity;
    }

}
