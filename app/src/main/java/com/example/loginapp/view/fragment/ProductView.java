package com.example.loginapp.view.fragment;

import com.example.loginapp.data.remote.dto.Product;

public interface ProductView {
    void onLoadProduct(Product product);

    void onLoadError(String message);
}
