package com.example.loginapp.model.interator;

import android.util.Log;

import com.example.loginapp.data.remote.api.AppApiService;
import com.example.loginapp.data.remote.api.dto.Product;
import com.example.loginapp.data.remote.api.dto.ProductResponse;
import com.example.loginapp.model.listener.SearchListener;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchProductInterator {
    private SearchListener listener;
    public SearchProductInterator(SearchListener listener) {
        this.listener = listener;
    }

    public void searchProduct(String query) {
        Call<ProductResponse> call = AppApiService.retrofit.searchProduct(query);

        call.enqueue(new Callback<ProductResponse>() {
            @Override
            public void onResponse(Call<ProductResponse> call, Response<ProductResponse> response) {
                if (response.isSuccessful()) {
                    ProductResponse productResponse = response.body();
                    if (productResponse != null) {
                        Log.d(this.toString(), productResponse.getProducts().toString());
                        List<Product> products = productResponse.getProducts();
                        if (products.isEmpty()) {
                            listener.showProcessBar(false);
                            listener.onListEmpty(true);
                        } else {
                            listener.onListEmpty(false);
                            listener.onLoadProducts(products);
                            listener.showProcessBar(false);
                        }

                    } else {
                        listener.onLoadError("Load data fail");
                    }
                }
            }

            @Override
            public void onFailure(Call<ProductResponse> call, Throwable t) {
                listener.onLoadError(t.getMessage());
            }
        });
    }
}
