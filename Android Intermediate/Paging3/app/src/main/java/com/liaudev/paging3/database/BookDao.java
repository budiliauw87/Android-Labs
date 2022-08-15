package com.liaudev.paging3.database;

import androidx.lifecycle.LiveData;
import androidx.paging.PagingSource;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

/**
 * Created by Budiliauw87 on 2021-12-20.
 * budiliauw87.github.io
 * Budiliauw87@gmail.com
 */
@Dao
public interface BookDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Book book);

    @Query("SELECT * from book ORDER BY id ASC")
    PagingSource<Integer, Book> getAllBooks();

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertAll(List<Book> list);
}
