package com.dicoding.tourismapp.core.data.source.local.entity;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

/**
 * Created by Budiliauw87 on 2022-08-14.
 * budiliauw87.github.io
 * Budiliauw87@gmail.com
 */

@Entity(tableName = "tourism")
public class TourismEntity implements Parcelable {
    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "id")
    private String id;

    @ColumnInfo(name = "name")
    private String name;

    @ColumnInfo(name = "description")
    private String description;

    @ColumnInfo(name = "address")
    private String address;

    @ColumnInfo(name = "image")
    private String image;

    @ColumnInfo(name = "latitude", defaultValue = "0.0")
    private Double latitude;

    @ColumnInfo(name = "longitude", defaultValue = "0.0")
    private Double longitude;

    @ColumnInfo(name = "like", defaultValue = "0")
    private int like;

    @ColumnInfo(name = "isFavorite", defaultValue = "0")
    private int isFavorite;



    public TourismEntity() {
    }

    protected TourismEntity(Parcel in) {
        id = in.readString();
        name = in.readString();
        description = in.readString();
        address = in.readString();
        image = in.readString();
        if (in.readByte() == 0) {
            latitude = null;
        } else {
            latitude = in.readDouble();
        }
        if (in.readByte() == 0) {
            longitude = null;
        } else {
            longitude = in.readDouble();
        }
        like = in.readInt();
        isFavorite = in.readInt();
    }

    public static final Creator<TourismEntity> CREATOR = new Creator<TourismEntity>() {
        @Override
        public TourismEntity createFromParcel(Parcel in) {
            return new TourismEntity(in);
        }

        @Override
        public TourismEntity[] newArray(int size) {
            return new TourismEntity[size];
        }
    };

    @NonNull
    public String getId() {
        return id;
    }

    public void setId(@NonNull String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public int getLike() {
        return like;
    }

    public void setLike(int like) {
        this.like = like;
    }

    public int getIsFavorite() {
        return isFavorite;
    }

    public void setIsFavorite(int isFavorite) {
        this.isFavorite = isFavorite;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(id);
        parcel.writeString(name);
        parcel.writeString(description);
        parcel.writeString(address);
        parcel.writeString(image);
        if (latitude == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeDouble(latitude);
        }
        if (longitude == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeDouble(longitude);
        }
        parcel.writeInt(like);
        parcel.writeInt(isFavorite);
    }
}
