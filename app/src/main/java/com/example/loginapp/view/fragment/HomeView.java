package com.example.loginapp.view.fragment;

import com.example.loginapp.data.remote.dto.Product;

import java.util.List;

public interface HomeView {
    void goOverviewScreen();
    void onLogoutMessage(String message);

    void onLoadProduct(List<Product> products);

    void onLoadError(String message);

    void onLoadCategories(List<String> categories);

    void showProcessBar(Boolean show);

}
