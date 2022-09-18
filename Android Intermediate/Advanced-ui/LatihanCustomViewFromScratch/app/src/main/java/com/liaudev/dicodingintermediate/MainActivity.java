package com.liaudev.dicodingintermediate;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.liaudev.dicodingintermediate.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.finishButton.setOnClickListener((v) -> {
            SeatView.Seat seat = binding.seatsView.getSelectedSeat();
            final String toastText = seat != null ? "Kursi Anda nomor "+seat.getName() : "Silakan pilih kursi terlebih dahulu.";
            Toast.makeText(this, toastText, Toast.LENGTH_SHORT).show();
        });
    }

}