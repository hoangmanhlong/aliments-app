package com.example.loginapp.adapter.cart_adapter;

import android.annotation.SuppressLint;
import android.util.Log;
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
import com.example.loginapp.view.fragment.cart.OnSelectAllListener;

public class CartAdapter extends ListAdapter<FirebaseProduct, CartAdapter.ItemCartViewHolder> {
    private final String TAG = this.toString();

    private CartItemClickListener listener;

    public CartAdapter(CartItemClickListener listener) {
        super(DiffCallback);
        this.listener = listener;
    }

    @NonNull
    @Override
    public ItemCartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ItemCartViewHolder(LayoutItemCartBinding.inflate(
            LayoutInflater.from(parent.getContext()),
            parent,
            false
        ), listener);
    }

    @Override
    public void onBindViewHolder(ItemCartViewHolder holder, int position) {
        holder.bind(getItem(position));
    }

    public class ItemCartViewHolder extends RecyclerView.ViewHolder implements
        View.OnClickListener, OnSelectAllListener {
        private final LayoutItemCartBinding binding;

        private CartItemClickListener listener;

        public ItemCartViewHolder(LayoutItemCartBinding binding, CartItemClickListener listener) {
            super(binding.getRoot());
            this.binding = binding;
            this.listener = listener;
            binding.getRoot().setOnClickListener(this);
            binding.add.setOnClickListener(this);
            binding.minus.setOnClickListener(this);
            binding.checkbox.setOnClickListener(this);
        }

        public void bind(FirebaseProduct product) {
            binding.setProduct(product);
        }

        @Override
        public void onClick(View v) {
            int quantity = Integer.parseInt(binding.quantity.getText().toString());
            FirebaseProduct product = getItem(getAdapterPosition());
            if (v.getId() == R.id.minus) {
                if (quantity > 1) {
                    quantity--;
                    listener.updateQuantity(product.getId(), quantity);
                    binding.quantity.setText(String.valueOf(quantity));
                } else {
                    listener.onDeleteProduct(product.getId());
                }
            } else if (v.getId() == R.id.add) {
                quantity++;
                listener.updateQuantity(product.getId(), quantity);
                binding.quantity.setText(String.valueOf(quantity));
            } else if (v.getId() == R.id.checkbox) {
                if (binding.checkbox.isChecked()) {
                    listener.updateTotal(Math.multiplyExact(
                        product.getPrice(),
                        Integer.parseInt(product.getQuantity())
                    ));
                } else {
                    listener.updateTotal(-Math.multiplyExact(
                        product.getPrice(),
                        Integer.parseInt(product.getQuantity())
                    ));
                }
            } else {
                listener.onItemClick(product);
            }
        }

        @Override
        public void onSelectAll(boolean check) {
            binding.checkbox.setChecked(check);
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
