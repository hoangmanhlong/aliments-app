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

import java.util.ArrayList;
import java.util.List;

public class CartAdapter extends ListAdapter<FirebaseProduct, CartAdapter.ItemCartViewHolder> {
    private final String TAG = this.toString();

    private final CartItemClickListener listener;

    private RecyclerView recyclerView;

    public CartAdapter(CartItemClickListener listener, RecyclerView recyclerView) {
        super(DiffCallback);
        this.listener = listener;
        this.recyclerView = recyclerView;
    }

    public void checkAllItems(boolean isChecked) {
        for (int i = 0; i < getItemCount(); i++) {
            ItemCartViewHolder holder = (ItemCartViewHolder) recyclerView.findViewHolderForAdapterPosition(i);
            if (holder != null) {
                holder.binding.checkbox.setChecked(isChecked);
            }
        }
    }

    @NonNull
    @Override
    public ItemCartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemCartViewHolder holder = new ItemCartViewHolder(LayoutItemCartBinding.inflate(
            LayoutInflater.from(parent.getContext()),
            parent,
            false
        ), listener);
        return holder;
    }

    public void removeItem(int position) {
        listener.onDeleteProduct(getItem(position).getId());
        List<FirebaseProduct> currentList = new ArrayList<>(getCurrentList());
        Log.d(TAG, String.valueOf(currentList.size()));
        currentList.remove(position);
        Log.d(TAG, String.valueOf(currentList.size()));
        submitList(currentList);
    }

    public void restoreItem(FirebaseProduct item, int position) {
        List<FirebaseProduct> currentList = new ArrayList<>(getCurrentList());
        currentList.add(position, item);
        submitList(currentList);
    }

    @Override
    public void onBindViewHolder(ItemCartViewHolder holder, int position) {
        holder.bind(getItem(position));
    }

    public class ItemCartViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, CheckboxListener {
        private final LayoutItemCartBinding binding;

        private final CartItemClickListener listener;

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
                } else {
                    listener.onDeleteProduct(product.getId());
                }
            } else if (v.getId() == R.id.add) {
                quantity++;
                listener.updateQuantity(product.getId(), quantity);
            } else if (v.getId() == R.id.checkbox) {
                if (binding.checkbox.isChecked()) {
                    listener.saveToListSelected(product);
                } else {
                    listener.deleteFromList(product);
                }
            } else {
                listener.onItemClick(product);
            }
        }

        @Override
        public void onSelectAll(boolean show) {
            binding.checkbox.setChecked(show);
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
                return oldItem.getQuantity().equals(newItem.getQuantity());
            }
        };
}
