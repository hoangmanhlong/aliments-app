package com.example.loginapp.model.listener;

import com.example.loginapp.data.remote.api.dto.Product;

import java.util.List;

public interface SearchListener {
    void onLoadProducts(List<Product> products);

    void onLoadError(String message);

    void showProcessBar(Boolean show);

    void onListEmpty(Boolean show);
}
