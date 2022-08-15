package com.dicoding.tourismapp.detail;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.Glide;
import com.dicoding.tourismapp.R;
import com.dicoding.tourismapp.core.data.source.local.entity.TourismEntity;
import com.dicoding.tourismapp.core.ui.ViewModelFactory;
import com.dicoding.tourismapp.databinding.ActivityDetailTourismBinding;

public class DetailTourismActivity extends AppCompatActivity {
    private TourismEntity tourismEntity;
    private ActivityDetailTourismBinding binding;
    private DetailTourismViewModel detailTourismViewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDetailTourismBinding.inflate(getLayoutInflater());
        ViewModelFactory factory = ViewModelFactory.getInstance(this);
        detailTourismViewModel = new ViewModelProvider(this, (ViewModelProvider.Factory) factory).get(DetailTourismViewModel.class);
        setContentView(binding.getRoot());
        setSupportActionBar(binding.toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        tourismEntity = getIntent().getParcelableExtra("extra_data");
        showDetailTourism(tourismEntity);
    }

    private void showDetailTourism(TourismEntity tourismEntity) {
        if (tourismEntity != null) {
            binding.toolbar.setTitle(tourismEntity.getName());
            binding.content.tvDetailDescription.setText(tourismEntity.getDescription());
            Glide.with(this)
                    .load(tourismEntity.getImage()).into(binding.ivDetailImage);
            setStatusFavorite(tourismEntity.getIsFavorite());
            binding.fab.setOnClickListener((v)->{
                int statusFavorite = tourismEntity.getIsFavorite()==0?1:0;
                tourismEntity.setIsFavorite(statusFavorite);
                detailTourismViewModel.setFavoriteTourism(tourismEntity,statusFavorite);
                setStatusFavorite(statusFavorite);
            });
        }
    }

    private void setStatusFavorite(int statusFavorite) {
        if (statusFavorite == 1) {
            binding.fab.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_favorite_white));
        } else {
            binding.fab.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_not_favorite_white));
        }
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