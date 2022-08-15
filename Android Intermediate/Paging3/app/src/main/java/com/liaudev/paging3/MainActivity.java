package com.liaudev.paging3;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;
import android.widget.Toast;

import com.liaudev.paging3.database.Book;
import com.liaudev.paging3.databinding.ActivityMainBinding;
import com.liaudev.paging3.viewmodels.MainViewModel;
import com.liaudev.paging3.viewmodels.ViewModelFactory;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
    private AdapterBook adapterBook;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        ViewModelFactory factory = ViewModelFactory.getInstance(getApplication());
        MainViewModel mainViewModel = new ViewModelProvider(this, factory).get(MainViewModel.class);
        adapterBook = new AdapterBook();
        binding.rvBooks.setLayoutManager(new LinearLayoutManager(this));
        binding.rvBooks.setAdapter(adapterBook);

        mainViewModel.getList().observe(this,(bookPagingData)->{
            adapterBook.submitData(getLifecycle(),bookPagingData);
        });

        binding.fabAdd.setOnClickListener((v)->{
            Toast.makeText(getApplicationContext(), "FAB on clicked", Toast.LENGTH_SHORT).show();
            final List<Book> list = new ArrayList<>();
            for (int i = 0; i < 40; i++) {
                Book dummyBook = new Book();
                dummyBook.setTitle("Example " + i);
                dummyBook.setDescription("Descreption Book " + i);
                list.add(dummyBook);
            }
            mainViewModel.addAllDummy(list);
        });
    }
}