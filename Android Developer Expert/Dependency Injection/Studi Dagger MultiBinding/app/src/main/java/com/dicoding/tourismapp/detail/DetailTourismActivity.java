package com.dicoding.tourismapp.detail;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.Glide;
import com.dicoding.tourismapp.MyApp;
import com.dicoding.tourismapp.R;
import com.dicoding.tourismapp.core.domain.model.Tourism;
import com.dicoding.tourismapp.core.ui.ViewModelFactory;
import com.dicoding.tourismapp.databinding.ActivityDetailTourismBinding;

import javax.inject.Inject;

public class DetailTourismActivity extends AppCompatActivity {
    private Tourism tourism;
    private ActivityDetailTourismBinding binding;
    private DetailTourismViewModel detailTourismViewModel;
    @Inject
    ViewModelFactory factory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ((MyApp) getApplicationContext()).appComponent.inject(this);
        super.onCreate(savedInstanceState);
        binding = ActivityDetailTourismBinding.inflate(getLayoutInflater());

        detailTourismViewModel = new ViewModelProvider(this, (ViewModelProvider.Factory) factory).get(DetailTourismViewModel.class);
        setContentView(binding.getRoot());
        setSupportActionBar(binding.toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        tourism = getIntent().getParcelableExtra("extra_data");
        showDetailTourism(tourism);
    }

    private void showDetailTourism(final Tourism tourism) {
        if (tourism != null) {
            binding.toolbar.setTitle(tourism.getName());
            binding.content.tvDetailDescription.setText(tourism.getDescription());
            Glide.with(this)
                    .load(tourism.getImage()).into(binding.ivDetailImage);
            setStatusFavorite(tourism.getIsFavorite());
            binding.fab.setOnClickListener((v)->{
                int statusFavorite = tourism.getIsFavorite()==0?1:0;
                tourism.setIsFavorite(statusFavorite);
                detailTourismViewModel.setFavoriteTourism(tourism,statusFavorite);
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