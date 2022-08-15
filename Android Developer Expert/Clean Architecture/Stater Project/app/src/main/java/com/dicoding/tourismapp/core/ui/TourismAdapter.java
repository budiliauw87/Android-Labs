package com.dicoding.tourismapp.core.ui;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.dicoding.tourismapp.core.data.source.local.entity.TourismEntity;
import com.dicoding.tourismapp.databinding.ItemListTourismBinding;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Budiliauw87 on 2022-08-14.
 * budiliauw87.github.io
 * Budiliauw87@gmail.com
 */
public class TourismAdapter extends RecyclerView.Adapter<TourismAdapter.ListViewHolder> {
    private ArrayList<TourismEntity> listData;
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

    public void setData(List<TourismEntity> newListData) {
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

        public void bind(TourismEntity tourismEntity) {
            Glide.with(itemView.getContext())
                    .load(tourismEntity.getImage()) // Load the image
                    .into(binding.ivItemImage);
            binding.tvItemTitle.setText(tourismEntity.getName());
            binding.tvItemSubtitle.setText(tourismEntity.getAddress());
            binding.getRoot().setOnClickListener((v)->{
                listener.onClickItem(listData.get(getBindingAdapterPosition()));
            });
        }
    }

    public interface ItemClickListener{
        void onClickItem(TourismEntity tourismEntity);
    }
}
