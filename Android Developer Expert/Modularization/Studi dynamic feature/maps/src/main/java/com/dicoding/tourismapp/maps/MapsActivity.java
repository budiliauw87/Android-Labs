package com.dicoding.tourismapp.maps;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.dicoding.tourismapp.di.MapsModuleDependencies;
import com.dicoding.tourismapp.maps.databinding.ActivityMapsBinding;

import javax.inject.Inject;

import dagger.hilt.android.EntryPointAccessors;


//@AndroidEntryPoint
public class MapsActivity extends AppCompatActivity {
    private ActivityMapsBinding binding;
    private MapsViewModel mapsViewModel;
    @Inject
    ViewModelFactory factory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        DaggerMapComponent.builder().context(this).appDependencies(
                        EntryPointAccessors.fromApplication(
                                getApplicationContext(),
                                MapsModuleDependencies.class
                        )
                ).build().inject(this);
        binding = ActivityMapsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        mapsViewModel = new ViewModelProvider(this,(ViewModelProvider.Factory) factory).get(MapsViewModel.class);
        setSupportActionBar(binding.toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("Maps");
        }

        mapsViewModel.getAllTourism().observe(this, tourism -> {
            if (tourism != null) {
                switch (tourism.status) {
                    case LOADING:
                        binding.progressBar.setVisibility(View.VISIBLE);
                        break;
                    case SUCCESS:
                        binding.tvMaps.setText("This is map of :"+tourism.data.get(0).getName());
                        binding.progressBar.setVisibility(View.GONE);
                        break;
                    case ERROR:
                        binding.progressBar.setVisibility(View.GONE);
                        binding.tvError.setVisibility(View.VISIBLE);
                        binding.tvError.setText(tourism.message);
                        break;
                }
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        binding = null;
    }
}