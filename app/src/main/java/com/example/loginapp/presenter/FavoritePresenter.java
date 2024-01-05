package com.example.loginapp.presenter;

import com.example.loginapp.data.remote.api.dto.Product;
import com.example.loginapp.model.interator.FavoriteInterator;
import com.example.loginapp.model.listener.FavoriteListener;
import com.example.loginapp.view.fragment.product_favorite.FavoriteView;

import java.util.List;

public class FavoritePresenter implements FavoriteListener {

    private FavoriteInterator interator;

    private FavoriteView view;

    public FavoritePresenter(FavoriteView view) {
        this.view = view;
        interator = new FavoriteInterator(this);
        interator.getFavoriteProductFromFirebase();
    }
    @Override
    public void onLoadFavoriteProducts(List<Product> products) {
        view.onLoadFavoriteProducts(products);
    }

    @Override
    public void onMessage(String message) {
        view.onMessage(message);
    }
}
