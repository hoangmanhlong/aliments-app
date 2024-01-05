package com.example.loginapp.presenter;

import com.example.loginapp.data.remote.api.dto.Product;
import com.example.loginapp.model.entity.UserData;
import com.example.loginapp.model.interator.HomeInterator;
import com.example.loginapp.model.listener.HomeListener;
import com.example.loginapp.view.fragment.home.HomeView;

import java.util.List;

public class HomePresenter implements HomeListener {
    private final HomeView view;
    private final HomeInterator interator;

    public HomePresenter(HomeView view) {
        this.view = view;
        interator = new HomeInterator(this);
        interator.getUserData();
        interator.getCategories();
    }

    public void getProducts() {
        interator.getListProductFromNetwork();
    }

    public void getProductOfCategory(String category) {
        interator.getProductOfCategory(category);
    }

    @Override
    public void getUserData(UserData userData) {
        view.getUserData(userData);
    }

    @Override
    public void onLoadProducts(List<Product> products) {
        view.onLoadProduct(products);
    }

    @Override
    public void onLoadError(String message) {
        view.onLoadError(message);
    }

    @Override
    public void onLoadCategories(List<String> categories) {
        view.onLoadCategories(categories);
    }

    @Override
    public void showProcessBar(Boolean show) {
        view.showProcessBar(show);
    }

}
