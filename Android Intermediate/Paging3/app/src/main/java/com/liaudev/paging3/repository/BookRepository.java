package com.liaudev.paging3.repository;

import android.app.Application;


import androidx.paging.PagingSource;

import com.liaudev.paging3.database.Book;
import com.liaudev.paging3.database.BookDao;
import com.liaudev.paging3.database.BookRoomDatabase;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by Budiliauw87 on 2021-12-20.
 * budiliauw87.github.io
 * Budiliauw87@gmail.com
 */
public class BookRepository {
    private BookDao mBookDao;
    private ExecutorService executorService;

    public BookRepository(Application application) {
        executorService = Executors.newSingleThreadExecutor();
        BookRoomDatabase db = BookRoomDatabase.getDatabase(application);
        mBookDao = db.bookDao();
    }

    public PagingSource<Integer, Book> getBooks() {
        return mBookDao.getAllBooks();
    }

    public void insertAll(List<Book> books) {
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                //execute insert all book
                mBookDao.insertAll(books);
            }
        });

    }
}
