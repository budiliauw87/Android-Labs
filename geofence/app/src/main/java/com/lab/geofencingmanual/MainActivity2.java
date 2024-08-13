package com.lab.geofencingmanual;

import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.lab.geofencingmanual.databinding.ActivityMainBinding;

import org.osmdroid.api.IMapController;
import org.osmdroid.config.Configuration;
import org.osmdroid.events.MapListener;
import org.osmdroid.events.ScrollEvent;
import org.osmdroid.events.ZoomEvent;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.overlay.CopyrightOverlay;
import org.osmdroid.views.overlay.Marker;
import org.osmdroid.views.overlay.MinimapOverlay;
import org.osmdroid.views.overlay.Polygon;
import org.osmdroid.views.overlay.compass.CompassOverlay;
import org.osmdroid.views.overlay.compass.InternalCompassOrientationProvider;
import org.osmdroid.views.overlay.mylocation.GpsMyLocationProvider;
import org.osmdroid.views.overlay.mylocation.MyLocationNewOverlay;

import java.util.List;

public class MainActivity2 extends AppCompatActivity {
    private MyLocationNewOverlay mLocationOverlay;
    private CompassOverlay mCompassOverlay = null;
    private Marker markerLocation;

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        Configuration.getInstance().load(this, PreferenceManager.getDefaultSharedPreferences(this));
        setContentView(binding.getRoot());

        binding.mapView.setTileSource(TileSourceFactory.MAPNIK);
        binding.mapView.setVisibility(View.VISIBLE);
        binding.mapView.setMultiTouchControls(true);
        binding.mapView.addMapListener(new MapListener() {
            @Override
            public boolean onScroll(ScrollEvent event) {
                Log.e("Main","Scroll ev :" +event.toString());
                return false;
            }

            @Override
            public boolean onZoom(ZoomEvent event) {
                Log.e("Main","Zoom ev :" +event.toString());
                return false;
            }
        });

        IMapController mapController = binding.mapView.getController();
        GeoPoint startPoint = new GeoPoint(-6.23307, 106.79370);
        mapController.zoomTo(19.5, 2000L);
        mapController.animateTo(startPoint);
//        mapController.animateTo(startPoint);

//        markerLocation = new Marker(binding.mapView);
//        markerLocation.showInfoWindow();
//        markerLocation.setTitle("PT BUANA VARIA KOMPUTAMA");
//        markerLocation.setPosition(startPoint);
//        binding.mapView.getOverlayManager().add(markerLocation);

        //adding circle
        List<GeoPoint> circle = Polygon.pointsAsCircle(startPoint, 50);
        Polygon polygonCircle = new Polygon(binding.mapView);
        polygonCircle.getOutlinePaint().setColor(
                ContextCompat.getColor(getApplicationContext(), R.color.blue_300));
        polygonCircle.getFillPaint().setColor(
                ContextCompat.getColor(getApplicationContext(), R.color.bg_circle_map)
        );
        polygonCircle.setPoints(circle);
        polygonCircle.setOnClickListener((polygon, mapView, eventPos) -> {
            return true;
        });
        binding.mapView.getOverlayManager().add(polygonCircle);


        new Handler().postDelayed(() -> {
            mapController.zoomTo(20, 1500L);
            mapController.animateTo(startPoint);

            addingMyLocOverlay();
        }, 1500);
    }

    private void addCompasOverlay() {
        if (binding.mapView == null) return;
        mCompassOverlay = new CompassOverlay(this,
                new InternalCompassOrientationProvider(this),
                binding.mapView);
        mCompassOverlay.enableCompass();
        binding.mapView.getOverlays().add(mCompassOverlay);
    }

    private void addingCopyright() {
        if (binding.mapView == null) return;
        //Copyright overlay
        CopyrightOverlay mCopyrightOverlay = new CopyrightOverlay(this);
        //i hate this very much, but it seems as if certain versions of android and/or
        //device types handle screen offsets differently
        binding.mapView.getOverlays().add(mCopyrightOverlay);
    }

    //mini map
    private void addMiniOverlay() {
        if (binding.mapView == null) return;
        final DisplayMetrics dm = getApplicationContext().getResources().getDisplayMetrics();
        MinimapOverlay mMinimapOverlay = new MinimapOverlay(this,
                binding.mapView.getTileRequestCompleteHandler());
        mMinimapOverlay.setWidth(dm.widthPixels / 5);
        mMinimapOverlay.setHeight(dm.heightPixels / 5);
        binding.mapView.getOverlays().add(mMinimapOverlay);
    }

    //My Location
    private void addingMyLocOverlay() {
        GpsMyLocationProvider gpsMyLocationProvider =  new GpsMyLocationProvider(this);

        mLocationOverlay = new MyLocationNewOverlay(gpsMyLocationProvider, binding.mapView);
        mLocationOverlay.enableMyLocation();
        binding.mapView.getOverlays().add(mLocationOverlay);

//        double lat = mLocationOverlay.getMyLocation().getLatitude();
//        double lng = mLocationOverlay.getMyLocation().getLongitude();

    }
}