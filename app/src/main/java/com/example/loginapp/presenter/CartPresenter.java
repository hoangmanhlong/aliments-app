package com.example.loginapp.presenter;

import com.example.loginapp.data.remote.api.dto.Product;
import com.example.loginapp.model.entity.FirebaseProduct;
import com.example.loginapp.model.interator.CartInterator;
import com.example.loginapp.model.interator.FavoriteInterator;
import com.example.loginapp.model.listener.CartListener;
import com.example.loginapp.view.fragment.cart.CartView;
import com.example.loginapp.view.fragment.product_favorite.FavoriteView;

import java.util.List;

public class CartPresenter implements CartListener {
    private CartInterator interator;

    private CartView view;

    public CartPresenter(CartView view) {
        this.view = view;
        interator = new CartInterator(this);
        interator.getFavoriteProductFromFirebase();
    }
    @Override
    public void onLoadFavoriteProducts(List<FirebaseProduct> products) {
        view.onLoadFavoriteProducts(products);
    }

    @Override
    public void onMessage(String message) {
        view.onMessage(message);
    }

    public void addQuantity(int quantity, int productId) {
        interator.addQuantity(quantity, productId);
    }
}
