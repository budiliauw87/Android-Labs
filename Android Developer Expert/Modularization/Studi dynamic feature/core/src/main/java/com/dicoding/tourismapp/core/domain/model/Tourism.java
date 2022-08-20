package com.dicoding.tourismapp.core.domain.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Budiliauw87 on 2022-08-15.
 * budiliauw87.github.io
 * Budiliauw87@gmail.com
 */
public class Tourism implements Parcelable {
    private String id;
    private String name;
    private String description;
    private String address;
    private String image;
    private Double latitude, longitude;
    private int like;
    private int isFavorite;
    public Tourism() {
    }

    protected Tourism(Parcel in) {
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

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(name);
        dest.writeString(description);
        dest.writeString(address);
        dest.writeString(image);
        if (latitude == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeDouble(latitude);
        }
        if (longitude == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeDouble(longitude);
        }
        dest.writeInt(like);
        dest.writeInt(isFavorite);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Tourism> CREATOR = new Creator<Tourism>() {
        @Override
        public Tourism createFromParcel(Parcel in) {
            return new Tourism(in);
        }

        @Override
        public Tourism[] newArray(int size) {
            return new Tourism[size];
        }
    };

    public String getId() {
        return id;
    }

    public void setId(String id) {
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
}
