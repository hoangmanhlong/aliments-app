package com.example.loginapp.view.fragment.product_detail;

import com.example.loginapp.data.remote.api.dto.Product;

public interface ProductView {
    void onLoadProduct(Product product);

    void onMessage(String message);

    void enableFavorite(Boolean compare);
}
