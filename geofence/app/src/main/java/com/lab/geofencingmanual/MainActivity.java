package com.lab.geofencingmanual;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.lab.geofencingmanual.databinding.ActivityMainBinding;

import org.osmdroid.api.IMapController;
import org.osmdroid.config.Configuration;
import org.osmdroid.events.MapEventsReceiver;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.overlay.MapEventsOverlay;
import org.osmdroid.views.overlay.Marker;
import org.osmdroid.views.overlay.Polygon;

import java.util.List;

public class MainActivity extends AppCompatActivity implements MapEventsReceiver {

    private static final String TAG = MainActivity.class.getSimpleName();

    private Marker markerLocation;
    private GeoPoint radGeopoint, selectedGeo;

    // radius from location circle
    final private int radiusDistance = 50;
    private ActivityMainBinding binding;

    private final ActivityResultLauncher<String> requestPermissionLauncher =
            registerForActivityResult(new ActivityResultContracts.RequestPermission(), isGranted -> {
                if (isGranted) {
                    // Permission is granted. Continue the action or workflow in your
                    // app.
                } else {

                }
            });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        Configuration.getInstance().load(this, PreferenceManager.getDefaultSharedPreferences(this));

        if (ContextCompat.checkSelfPermission(
                this, Manifest.permission.ACCESS_FINE_LOCATION) ==
                PackageManager.PERMISSION_GRANTED) {
            Log.e(TAG, "Permission granted");
            initMap();
            hideError();
            // You can use the API that requires the permission.

        } else if (ActivityCompat.shouldShowRequestPermissionRationale(
                this, Manifest.permission.ACCESS_FINE_LOCATION)) {
            // In an educational UI, explain to the user why your app requires this
            // permission for a specific feature to behave as expected, and what
            // features are disabled if it's declined. In this UI, include a
            // "cancel" or "no thanks" button that lets the user continue
            // using your app without granting the permission.
            Log.e(TAG, "Permission shouldShowRequestPermissionRationale");
        } else {
            // You can directly ask for the permission.
            // The registered ActivityResultCallback gets the result of this request.
            requestPermissionLauncher.launch(
                    Manifest.permission.ACCESS_FINE_LOCATION);
        }
        setContentView(binding.getRoot());
    }

    /**
     * Method to check cordinat is in area circepoly
     */
    private void checkIsLocationGeo() {
        if (selectedGeo == null) return;
        try {
            //calculate distance radius
            double distanceFromRadius = radGeopoint.distanceToAsDouble(selectedGeo);
            final String statusIsOnRadius = (distanceFromRadius <= radiusDistance)
                    ? getString(R.string.valid) : getString(R.string.invalid);

            final int colorText = (distanceFromRadius <= radiusDistance)
                    ? getColor(R.color.green) : getColor(R.color.red);

            binding.tvInfo.setText(statusIsOnRadius);
            binding.tvInfo.setTextColor(colorText);
            Log.e(TAG, " Distance is : " + String.valueOf(distanceFromRadius));
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(MainActivity.this, "Something when Wrong !", Toast.LENGTH_SHORT).show();
        }
    }

    private void initMap() {
        binding.mapView.setTileSource(TileSourceFactory.MAPNIK);
        binding.mapView.setVisibility(VISIBLE);
        binding.mapView.setMultiTouchControls(true);

        binding.mapView.addOnFirstLayoutListener((v, left, top, right, bottom) -> {
            Log.e(TAG, "onFirstLayout  " + left + top + right + bottom);
            IMapController mapController = binding.mapView.getController();
            radGeopoint = new GeoPoint(-6.23307, 106.79370);
            mapController.zoomTo(19.5);
            mapController.setCenter(radGeopoint);
//            mapController.animateTo(radGeopoint);

            //adding circle
            List<GeoPoint> circle = Polygon.pointsAsCircle(radGeopoint, 50);
            Polygon polygonCircle = new Polygon(binding.mapView);
            polygonCircle.getOutlinePaint().setColor(
                    ContextCompat.getColor(getApplicationContext(), R.color.blue_300));
            polygonCircle.getFillPaint().setColor(
                    ContextCompat.getColor(getApplicationContext(), R.color.bg_circle_map)
            );
            polygonCircle.setPoints(circle);
            polygonCircle.setOnClickListener((polygon, mapView, geoPoint) -> {
                setSelectedGeo(geoPoint);
                return true;
            });
            binding.mapView.getOverlayManager().add(polygonCircle);


            new Handler().postDelayed(() -> {
                mapController.zoomTo(20, 1500L);
                mapController.animateTo(radGeopoint);
            }, 1500);
        });

        MapEventsOverlay mapEventsOverlay = new MapEventsOverlay(this);
        binding.mapView.getOverlays().add(mapEventsOverlay);


    }

    private void hideError() {
        binding.mapView.setVisibility(VISIBLE);
        binding.bottomLayout.setVisibility(VISIBLE);
        binding.errLayout.setVisibility(GONE);
    }

    private void showErrorLayout() {
        binding.mapView.setVisibility(GONE);
        binding.bottomLayout.setVisibility(GONE);
        binding.errLayout.setVisibility(VISIBLE);
    }

    private void setSelectedGeo(GeoPoint geoPoint) {
        if(markerLocation == null) {
            markerLocation = new Marker(binding.mapView);
            markerLocation.setTitle("Selection cordinates");

        }
        if (geoPoint != null) {
            selectedGeo = geoPoint;
            final String lat = String.format("%.5f", geoPoint.getLatitude());
            final String lng = String.format("%.5f", geoPoint.getLongitude());
            binding.tvLocation.setText(lat + "," + lng);

            //update geopoint in marker
            markerLocation.setPosition(geoPoint);
            //adding marker to mapview
            binding.mapView.getOverlays().add(markerLocation);
            binding.mapView.invalidate();

            //show in title
            if(!markerLocation.isInfoWindowShown())  markerLocation.showInfoWindow();
            checkIsLocationGeo();
        }

    }

    @Override
    public boolean singleTapConfirmedHelper(GeoPoint geoPoint) {
        setSelectedGeo(geoPoint);
        return false;
    }

    @Override
    public boolean longPressHelper(GeoPoint p) {
        Log.e(TAG, "LongClicked Lat :  " + p.getLatitude()
                + "\nLng : " + p.getLongitude());
        return false;
    }
}