package com.dicoding.tourismapp.home;

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
import com.dicoding.tourismapp.databinding.FragmentHomeBinding;
import com.dicoding.tourismapp.detail.DetailTourismActivity;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    private FragmentHomeBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        if (binding != null) {

            homeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);
            TourismAdapter tourismAdapter = new TourismAdapter(tourismEntity -> {
                Intent intent = new Intent(getActivity(), DetailTourismActivity.class);
                intent.putExtra("extra_data", tourismEntity);
                startActivity(intent);
            });
            binding.rvTourism.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
            binding.rvTourism.setHasFixedSize(true);
            binding.rvTourism.setAdapter(tourismAdapter);

            homeViewModel.getAllTourism().observe(getViewLifecycleOwner(), tourism -> {
                if (tourism != null) {
                    switch (tourism.status) {
                        case LOADING:
                            binding.progressBar.setVisibility(View.VISIBLE);
                            break;
                        case SUCCESS:
                            binding.progressBar.setVisibility(View.GONE);
                            tourismAdapter.setData(tourism.data);
                            break;
                        case ERROR:
                            binding.progressBar.setVisibility(View.GONE);
                            binding.viewError.getRoot().setVisibility(View.VISIBLE);
                            break;
                    }
                }
            });

        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}