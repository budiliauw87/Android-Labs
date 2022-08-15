package com.liaudev.paging3.database;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;

/**
 * Created by Budiliauw87 on 2021-12-20.
 * budiliauw87.github.io
 * Budiliauw87@gmail.com
 */
@Database(entities = {Book.class}, version = 1, exportSchema = false)
public abstract class BookRoomDatabase extends RoomDatabase {
    public abstract BookDao bookDao();

    private static volatile BookRoomDatabase INSTANCE;

    public static BookRoomDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (BookRoomDatabase.class) {
                INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                        BookRoomDatabase.class, "book_database")
                        .addCallback(new Callback() {
                            @Override
                            public void onCreate(@NonNull SupportSQLiteDatabase db) {
                                addDummy();
                            }
                        })
                        .build();

            }
        }
        return INSTANCE;
    }

    private static void addDummy() {
        Executors.newSingleThreadExecutor().execute(() -> {
            final List<Book> list = new ArrayList<>();
            for (int i = 0; i < 40; i++) {
                Book dummyBook = new Book();
                dummyBook.setTitle("Example " + i);
                dummyBook.setDescription("Descreption Book " + i);
                list.add(dummyBook);
            }
            INSTANCE.bookDao().insertAll(list);
        });
    }
}
