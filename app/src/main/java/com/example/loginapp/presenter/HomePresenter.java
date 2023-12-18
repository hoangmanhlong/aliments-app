package com.example.loginapp.presenter;

import com.example.loginapp.data.remote.dto.Product;
import com.example.loginapp.model.interator.HomeInterator;
import com.example.loginapp.model.listener.HomeListener;
import com.example.loginapp.view.fragment.HomeView;

import java.util.List;

public class HomePresenter implements HomeListener {
    private HomeView view;
    private HomeInterator interator;

    public HomePresenter(HomeView view) {
        this.view = view;
        interator = new HomeInterator(this);

        interator.getCategories();
    }

    public void getProducts() {
        interator.getListProductFromNetwork();
    }

    public void logout() {
        interator.logout();
    }

    @Override
    public void goOverviewScreen() {
        view.goOverviewScreen();
    }

    @Override
    public void onLogoutMessage(String message) {
        view.onLogoutMessage(message);
    }

    public void getProductOfCategory(String category) {
        interator.getProductOfCategory(category);
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
