package com.dicoding.tourismapp.favorite;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.dicoding.tourismapp.core.ui.TourismAdapter;
import com.dicoding.tourismapp.databinding.FragmentFavoriteBinding;
import com.dicoding.tourismapp.detail.DetailTourismActivity;

import dagger.hilt.android.AndroidEntryPoint;

/**
 * Created by Budiliauw87 on 2022-08-14.
 * budiliauw87.github.io
 * Budiliauw87@gmail.com
 */
@AndroidEntryPoint
public class FavoriteFragment extends Fragment {
    private FragmentFavoriteBinding binding;
    private FavoriteViewModel favoriteViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentFavoriteBinding.inflate(inflater,container,false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        if(binding!=null){
            favoriteViewModel = new ViewModelProvider(this).get(FavoriteViewModel.class);
            TourismAdapter tourismAdapter = new TourismAdapter(tourismEntity -> {
                Intent intent = new Intent(getActivity(), DetailTourismActivity.class);
                intent.putExtra("extra_data",tourismEntity);
                startActivity(intent);
            });
            binding.rvTourism.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
            binding.rvTourism.setHasFixedSize(true);
            binding.rvTourism.setAdapter(tourismAdapter);
            favoriteViewModel.getFavoriteTourism().observe(getViewLifecycleOwner(),tourismEntities ->{
                tourismAdapter.setData(tourismEntities);
                if (tourismEntities.size() > 0) {
                    binding.viewEmpty.getRoot().setVisibility(View.GONE);
                } else {
                    binding.viewEmpty.getRoot().setVisibility(View.VISIBLE);
                }
            });
        }
    }
}
