package com.example.loginapp.presenter;

import com.example.loginapp.data.remote.dto.Product;
import com.example.loginapp.model.interator.SearchProductInterator;
import com.example.loginapp.model.listener.SearchListener;
import com.example.loginapp.view.fragment.SearchView;

import java.util.List;

public class SearchPresenter implements SearchListener {
    private SearchProductInterator interator;
    private SearchView view;

    public SearchPresenter(SearchView view) {
        this.view = view;
        interator = new SearchProductInterator(this);
    }

    void onSearchProduct(String query) {
        interator.searchProduct(query);
    }
    @Override
    public void onLoadProducts(List<Product> products) {
        view.onLoadProducts(products);
    }

    @Override
    public void onLoadError(String message) {
        view.onLoadError(message);
    }

    @Override
    public void showProcessBar(Boolean show) {
        view.showProcessBar(show);
    }
}
