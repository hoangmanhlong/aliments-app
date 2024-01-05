package com.example.loginapp.view.fragment.product_favorite;

import com.example.loginapp.data.remote.api.dto.Product;

import java.util.List;

public interface FavoriteView {
    void onLoadFavoriteProducts(List<Product> products);

    void onMessage(String message);
}
