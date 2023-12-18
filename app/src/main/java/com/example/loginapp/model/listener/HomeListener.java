package com.example.loginapp.model.listener;

import com.example.loginapp.data.remote.dto.Product;

import java.util.List;

public interface HomeListener {
    void goOverviewScreen();
    void onLogoutMessage(String message);

    void onLoadProducts(List<Product> products);

    void onLoadError(String message);

    void onLoadCategories(List<String> categories);

    void showProcessBar(Boolean show);

}
