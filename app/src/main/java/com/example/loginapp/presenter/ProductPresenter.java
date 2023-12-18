package com.example.loginapp.presenter;

import com.example.loginapp.data.remote.dto.Product;
import com.example.loginapp.model.interator.ProductInterator;
import com.example.loginapp.model.listener.ProductListener;
import com.example.loginapp.view.fragment.ProductView;

public class ProductPresenter implements ProductListener {
    private ProductView view;

    private ProductInterator interator;

    public ProductPresenter(ProductView view) {
        this.view = view;
        interator = new ProductInterator(this);
    }

    public void getProduct(int productId) {
        interator.getProduct(productId);
    }
    @Override
    public void onGetProduct(Product product) {
        view.onLoadProduct(product);
    }

    @Override
    public void loadError(String message) {
        view.onLoadError(message);
    }
}
