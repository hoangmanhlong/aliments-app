package com.example.loginapp.adapter.search_favorite_cart_adapter;

import android.annotation.SuppressLint;
import android.view.DragEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.example.loginapp.adapter.product_adapter.OnItemClickListener;
import com.example.loginapp.data.remote.api.dto.Product;
import com.example.loginapp.databinding.LayoutCommonProductBinding;


public class CommonProductAdapter extends
    ListAdapter<Product, CommonProductAdapter.ItemViewHolder> {
    public CommonProductAdapter() {
        super(DiffCallback);
    }

    private OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.onItemClickListener = listener;
    }

    @Override
    public com.example.loginapp.adapter.search_favorite_cart_adapter.CommonProductAdapter.ItemViewHolder onCreateViewHolder(
        ViewGroup parent, int viewType
    ) {
        com.example.loginapp.adapter.search_favorite_cart_adapter.CommonProductAdapter.ItemViewHolder
            itemViewHolder =
            new com.example.loginapp.adapter.search_favorite_cart_adapter.CommonProductAdapter.ItemViewHolder(
                LayoutCommonProductBinding.inflate(
                    LayoutInflater.from(parent.getContext()),
                    parent,
                    false
                )
            );

        itemViewHolder.itemView.setOnClickListener(v -> {
            int position = itemViewHolder.getAdapterPosition();
            onItemClickListener.onItemClick(getItem(position));
        });
        return itemViewHolder;
    }

    @Override
    public void onBindViewHolder(
        com.example.loginapp.adapter.search_favorite_cart_adapter.CommonProductAdapter.ItemViewHolder holder,
        int position
    ) {
        holder.bind(getItem(position));
    }

    public static class ItemViewHolder extends RecyclerView.ViewHolder {
        private final LayoutCommonProductBinding binding;

        public ItemViewHolder(LayoutCommonProductBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(Product product) {
            binding.setProduct(product);
        }
    }

    public static final DiffUtil.ItemCallback<Product> DiffCallback =
        new DiffUtil.ItemCallback<Product>() {
            @Override
            public boolean areItemsTheSame(Product oldItem, Product newItem) {
                return oldItem.getId() == newItem.getId();
            }

            @SuppressLint("DiffUtilEquals")
            @Override
            public boolean areContentsTheSame(Product oldItem, Product newItem) {
                return oldItem.equals(newItem);
            }
        };
}