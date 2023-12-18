package com.example.loginapp.model.interator;

import android.util.Log;

import com.example.loginapp.data.remote.AppApi;
import com.example.loginapp.data.remote.AppApiService;
import com.example.loginapp.data.remote.dto.ProductResponse;
import com.example.loginapp.model.listener.SearchListener;

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
                        listener.onLoadProducts(productResponse.getProducts());
                        listener.showProcessBar(false);
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
