package com.example.loginapp.view.fragment.cart;

import com.example.loginapp.model.entity.FirebaseProduct;

import java.util.List;

public interface CartView {
    void onLoadFavoriteProducts(List<FirebaseProduct> products);

    void onMessage(String message);
}