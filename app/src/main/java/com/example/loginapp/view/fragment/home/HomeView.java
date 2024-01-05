package com.example.loginapp.view.fragment.home;

import com.example.loginapp.data.remote.api.dto.Product;
import com.example.loginapp.model.entity.UserData;

import java.util.List;

public interface HomeView {
    void getUserData(UserData userData);
    void onLoadProduct(List<Product> products);

    void onLoadError(String message);

    void onLoadCategories(List<String> categories);

    void showProcessBar(Boolean show);

}
