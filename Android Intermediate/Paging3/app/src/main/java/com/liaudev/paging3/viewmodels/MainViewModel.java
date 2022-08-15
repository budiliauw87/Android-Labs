package com.liaudev.paging3.viewmodels;

import android.app.Application;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelKt;
import androidx.paging.Pager;
import androidx.paging.PagingConfig;
import androidx.paging.PagingData;
import androidx.paging.PagingLiveData;

import com.liaudev.paging3.database.Book;
import com.liaudev.paging3.repository.BookRepository;

import java.util.List;

import io.reactivex.Flowable;
import kotlinx.coroutines.CoroutineScope;

/**
 * Created by Budiliauw87 on 2021-12-20.
 * budiliauw87.github.io
 * Budiliauw87@gmail.com
 */
public class MainViewModel extends ViewModel {
    private BookRepository bookRepository;
    public Flowable<PagingData<Book>> pagingDataFlow;
    public LiveData<PagingData<Book>> pagingDataLiveData;

    public MainViewModel(Application application) {
        bookRepository = new BookRepository(application);
        initPaging();
    }

    private void initPaging() {
        CoroutineScope viewModelScope = ViewModelKt.getViewModelScope(this);
        PagingConfig config = new PagingConfig(7);
        Pager<Integer, Book> pager = new Pager(config, () -> bookRepository.getBooks());
        pagingDataLiveData = PagingLiveData.cachedIn(PagingLiveData.getLiveData(pager), viewModelScope);
    }

    public void addAllDummy(List<Book> books) {
        bookRepository.insertAll(books);
    }

    public LiveData<PagingData<Book>> getList() {
        return pagingDataLiveData;
    }


}
