package com.example.loginapp.model.listener;

import com.example.loginapp.data.remote.api.dto.Product;
import com.example.loginapp.model.entity.FirebaseProduct;

import java.util.List;

public interface CartListener {
    void onLoadFavoriteProducts(List<FirebaseProduct> products);

    void onMessage(String message);
}
