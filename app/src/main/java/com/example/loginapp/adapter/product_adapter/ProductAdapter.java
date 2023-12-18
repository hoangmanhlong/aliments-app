package com.example.loginapp.adapter.product_adapter;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.example.loginapp.data.remote.dto.Product;
import com.example.loginapp.databinding.LayoutProductItemBinding;

public class ProductAdapter extends ListAdapter<Product, ProductAdapter.ItemViewHolder> {
    public ProductAdapter() {
        super(DiffCallback);
    }
    private OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.onItemClickListener = listener;
    }

    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ItemViewHolder itemViewHolder =  new ItemViewHolder(
            LayoutProductItemBinding.inflate(
                LayoutInflater.from(parent.getContext()),
                parent,
                false
            )
        );

        itemViewHolder.itemView.setOnClickListener(v -> {
            int position =itemViewHolder.getAdapterPosition();
            onItemClickListener.onItemClick(getItem(position));
        });
        return itemViewHolder;
    }

    @Override
    public void onBindViewHolder(ItemViewHolder holder, int position) {
        holder.bind(getItem(position));
    }

    public static class ItemViewHolder extends RecyclerView.ViewHolder {
        private final LayoutProductItemBinding binding;

        public ItemViewHolder(LayoutProductItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(Product product) {
            binding.setProduct(product);
        }
    }

    public static final DiffUtil.ItemCallback<Product> DiffCallback = new DiffUtil.ItemCallback<Product>() {
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

