package com.example.loginapp.model.listener;

import com.example.loginapp.data.remote.api.dto.Product;

public interface ProductListener {
    void onGetProduct(Product apiProduct, boolean isFinished);

    void onMessage(String message);

    void enableFavorite(Boolean compare);
}
