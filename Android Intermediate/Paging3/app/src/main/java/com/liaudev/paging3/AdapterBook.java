package com.liaudev.paging3;

import android.annotation.SuppressLint;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.paging.PagingDataAdapter;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.liaudev.paging3.database.Book;
import com.liaudev.paging3.databinding.ItemBookBinding;

/**
 * Created by Budiliauw87 on 2021-12-20.
 * budiliauw87.github.io
 * Budiliauw87@gmail.com
 */
public class AdapterBook extends PagingDataAdapter<Book,AdapterBook.MyViewHolder> {

    public AdapterBook(){
        super(DIFF_CALLBACK);
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemBookBinding itemBookBinding = ItemBookBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new AdapterBook.MyViewHolder(itemBookBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.bind(position);
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        final ItemBookBinding binding;
        public MyViewHolder(ItemBookBinding itemBookBinding) {
            super(itemBookBinding.getRoot());
            this.binding = itemBookBinding;
        }
        void bind(int position){
            Book book = getItem(position);
            Log.d("DEBUG_DATA", "Book Name:"+book.getTitle());
            binding.tvItemTitle.setText(book.getTitle());
            binding.tvItemDescription.setText(book.getDescription());
        }
    }

    private static DiffUtil.ItemCallback<Book> DIFF_CALLBACK =
            new DiffUtil.ItemCallback<Book>() {
                @Override
                public boolean areItemsTheSame(@NonNull Book oldItem, @NonNull Book newItem) {
                    return oldItem.getId() == newItem.getId();
                }

                @SuppressLint("DiffUtilEquals")
                @Override
                public boolean areContentsTheSame(@NonNull Book oldItem, @NonNull Book newItem) {
                    return oldItem.equals(newItem);
                }
            };
}
