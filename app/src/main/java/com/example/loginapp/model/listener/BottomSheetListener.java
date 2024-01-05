package com.example.loginapp.model.listener;

import com.example.loginapp.data.remote.api.dto.Product;

public interface BottomSheetListener {
    void onLoadProduct(Product product);
}
