package com.liaudev.dicodingintermediate;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
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
        // Melakukan pengecekan saat pertama kali activity terbentuk
        setMyButtonEnable();
        // Menambahkan metode ketika text terjadi perubahan
        binding.myEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                setMyButtonEnable();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });


        // Menambahkan aksi klik kepada button
        binding.myButton.setOnClickListener(v -> Toast.makeText(this, binding.myEditText.getText().toString(), Toast.LENGTH_SHORT).show());
    }

    // Metode untuk mengubah disable dan enable pada button
    private void setMyButtonEnable() {
        final String result = binding.myEditText.getText().toString();
        binding.myButton.setEnabled(result != null && !result.isEmpty());
    }
}