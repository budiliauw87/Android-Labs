package com.dicoding.tourismapp.core.utils;

import android.content.Context;
import android.util.Log;

import com.dicoding.tourismapp.R;
import com.dicoding.tourismapp.core.data.source.remote.response.TourismResponse;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Budiliauw87 on 2022-12-23.
 * liautech.com
 * Budiliauw87@gmail.com
 */
public class JSONHelper {
    private Context context;

    public JSONHelper(Context context) {
        this.context = context;
    }

    @SuppressWarnings("ResultOfMethodCallIgnored")
    private String parsingFileToString() {
        try {
            //InputStream is = context.getAssets().open(fileName);
            InputStream is = context.getResources().openRawResource(R.raw.tourism);
            byte[] buffer = new byte[is.available()];
            is.read(buffer);
            is.close();
            return new String(buffer);
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public List<TourismResponse> loadData() {
        List<TourismResponse> list = new ArrayList<>();
        try {
            JSONObject responseObject = new JSONObject(parsingFileToString());
            JSONArray listArray = responseObject.getJSONArray("places");
            for (int i = 0; i < listArray.length(); i++) {
                JSONObject jsonObject = listArray.getJSONObject(i);
                TourismResponse tourismResponse = new TourismResponse();
                tourismResponse.setId(jsonObject.getString("id"));
                tourismResponse.setName(jsonObject.getString("name"));
                tourismResponse.setDescription(jsonObject.getString("description"));
                tourismResponse.setAddress(jsonObject.getString("address"));
                tourismResponse.setLatitude(jsonObject.getDouble("latitude"));
                tourismResponse.setLongitude(jsonObject.getDouble("longitude"));
                tourismResponse.setLike(jsonObject.getInt("like"));
                tourismResponse.setImage(jsonObject.getString("image"));
                list.add(tourismResponse);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return list;
    }
}
