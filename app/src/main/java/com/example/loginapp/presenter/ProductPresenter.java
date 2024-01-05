package com.example.loginapp.presenter;

import com.example.loginapp.data.remote.api.dto.Product;
import com.example.loginapp.model.interator.ProductInterator;
import com.example.loginapp.model.listener.ProductListener;
import com.example.loginapp.view.fragment.product_detail.ProductView;

import java.util.List;

public class ProductPresenter implements ProductListener {
    private final ProductView view;

    private final ProductInterator interator;

    public ProductPresenter(ProductView view) {
        this.view = view;
        interator = new ProductInterator(this);
    }

    public void addFavoriteProduct(Product product) {
        interator.saveFavoriteProduct(product);
    }

    public void getProduct(int productId) {
        interator.getProduct(productId);
    }

    @Override
    public void onGetProduct(Product product, boolean isFinished) {
        List<Product> products = interator.products;
        if (isFinished) {
            if (product != null && products.size() > 0) {
                view.onLoadProduct(product);
                interator.compare(product, products.get(0));
            } else {
                view.onLoadProduct(products.get(0));
            }
        }
    }

    public void addToCart(Product product) {
        interator.saveCart(product);
    }

    @Override
    public void onMessage(String message) {
        view.onMessage(message);
    }

    @Override
    public void enableFavorite(Boolean compare) {
        view.enableFavorite(compare);
    }
}
