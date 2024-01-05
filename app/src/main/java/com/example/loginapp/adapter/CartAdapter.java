package com.example.loginapp.adapter;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.example.loginapp.R;
import com.example.loginapp.databinding.LayoutItemCartBinding;
import com.example.loginapp.model.entity.FirebaseProduct;
import com.example.loginapp.presenter.CartPresenter;

public class CartAdapter extends ListAdapter<FirebaseProduct, CartAdapter.ItemCartViewHolder> {

//    public CartPresenter presenter;

    public CartAdapter() {
        super(DiffCallback);
    }

    @Override
    public ItemCartViewHolder onCreateViewHolder(
        ViewGroup parent, int viewType
    ) {
        ItemCartViewHolder itemViewHolder = new ItemCartViewHolder(LayoutItemCartBinding.inflate(
            LayoutInflater.from(parent.getContext()),
            parent,
            false
        ));
        return itemViewHolder;
    }

    @Override
    public void onBindViewHolder(ItemCartViewHolder holder, int position) {
        holder.bind(getItem(position));
    }

    public static class ItemCartViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private final LayoutItemCartBinding binding;

        public ItemCartViewHolder(LayoutItemCartBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
            binding.getRoot().setOnClickListener(this);
            binding.add.setOnClickListener(this);
            binding.minus.setOnClickListener(this);
        }


        public void bind(FirebaseProduct product) {
            binding.setProduct(product);
        }

        @Override
        public void onClick(View v) {
//            int quantity = Integer.parseInt(binding.quantity.getText().toString());
//            if (v.getId() == R.id.add) {
//                presenter.addQuantity(quantity + 1, productId);
//            }
//            if (v.getId() == R.id.minus) {
//                if (quantity > 1) {
//                    presenter.addQuantity(quantity - 1, productId);
//                }
//            }
        }
    }

    public static final DiffUtil.ItemCallback<FirebaseProduct> DiffCallback =
        new DiffUtil.ItemCallback<FirebaseProduct>() {

            @Override
            public boolean areItemsTheSame(
                @NonNull FirebaseProduct oldItem, @NonNull FirebaseProduct newItem
            ) {
                return oldItem.getId() == newItem.getId();
            }

            @SuppressLint("DiffUtilEquals")
            @Override
            public boolean areContentsTheSame(
                @NonNull FirebaseProduct oldItem, @NonNull FirebaseProduct newItem
            ) {
                return oldItem.equals(newItem);
            }
        };

}
