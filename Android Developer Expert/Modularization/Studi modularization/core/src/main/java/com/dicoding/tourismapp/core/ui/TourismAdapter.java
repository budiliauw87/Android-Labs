package com.dicoding.tourismapp.core.ui;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.dicoding.tourismapp.core.domain.model.Tourism;
import com.dicoding.tourismapp.core.databinding.ItemListTourismBinding;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Budiliauw87 on 2022-08-14.
 * budiliauw87.github.io
 * Budiliauw87@gmail.com
 */
public class TourismAdapter extends RecyclerView.Adapter<TourismAdapter.ListViewHolder> {
    private ArrayList<Tourism> listData;
    private ItemClickListener listener;
    public TourismAdapter(ItemClickListener itemClickListener) {
        this.listData = new ArrayList<>();
        this.listener = itemClickListener;
    }

    @NonNull
    @Override
    public ListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ListViewHolder(ItemListTourismBinding.inflate(LayoutInflater.from(parent.getContext()),
                parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ListViewHolder holder, int position) {
        holder.bind(listData.get(position));
    }

    @Override
    public int getItemCount() {
        return listData.size();
    }

    public void setData(List<Tourism> newListData) {
        if (newListData == null) return;
        listData.clear();
        listData.addAll(newListData);
        notifyDataSetChanged();
    }

    public class ListViewHolder extends RecyclerView.ViewHolder {
        ItemListTourismBinding binding;

        public ListViewHolder(@NonNull ItemListTourismBinding itemListTourismBinding) {
            super(itemListTourismBinding.getRoot());
            binding = itemListTourismBinding;
        }

        public void bind(Tourism tourism) {
            Glide.with(itemView.getContext())
                    .load(tourism.getImage()) // Load the image
                    .into(binding.ivItemImage);
            binding.tvItemTitle.setText(tourism.getName());
            binding.tvItemSubtitle.setText(tourism.getAddress());
            binding.getRoot().setOnClickListener((v)->{
                listener.onClickItem(listData.get(getBindingAdapterPosition()));
            });
        }
    }

    public interface ItemClickListener{
        void onClickItem(Tourism tourism);
    }
}
