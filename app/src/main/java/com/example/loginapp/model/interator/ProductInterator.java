package com.example.loginapp.model.interator;

import com.example.loginapp.data.remote.AppApiService;
import com.example.loginapp.data.remote.dto.Product;
import com.example.loginapp.model.listener.ProductListener;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductInterator {

    private ProductListener listener;

    public ProductInterator(ProductListener listener) {
        this.listener = listener;

    }

    public void getProduct(int id) {
        Call<Product> call = AppApiService.retrofit.getProduct(id);
        call.enqueue(new Callback<Product>() {
            @Override
            public void onResponse(Call<Product> call, Response<Product> response) {
                if(response.isSuccessful()) {
                    Product product = response.body();
                    if(product != null) {
                        listener.onGetProduct(product);
                    }
                }
            }

            @Override
            public void onFailure(Call<Product> call, Throwable t) {
                listener.loadError(t.getMessage());
            }
        });
    }
}
