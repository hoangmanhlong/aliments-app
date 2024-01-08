package com.example.loginapp.adapter.cart_adapter;

import com.example.loginapp.model.entity.FirebaseProduct;

public interface CartItemClickListener {
    void onItemClick(FirebaseProduct product);

    void updateQuantity(int id, int quantity);

    void onDeleteProduct(int id);

    void updateTotal(int price);
}
