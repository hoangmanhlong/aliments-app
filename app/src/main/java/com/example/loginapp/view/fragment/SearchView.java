package com.example.loginapp.view.fragment;

import com.example.loginapp.data.remote.dto.Product;

import java.util.List;

public interface SearchView {
    void onLoadProducts(List<Product> products);

    void onLoadError(String message);

    void showProcessBar(Boolean show);
}
