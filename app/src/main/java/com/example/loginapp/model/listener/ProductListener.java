package com.example.loginapp.model.listener;

import com.example.loginapp.data.remote.dto.Product;

public interface ProductListener {
    void onGetProduct(Product product);

    void loadError(String message);
}
