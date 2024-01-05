package com.example.loginapp.model.listener;

import com.example.loginapp.data.remote.api.dto.Product;

import java.util.List;

public interface FavoriteListener {
    void onLoadFavoriteProducts(List<Product> products);

    void onMessage(String message);
}
