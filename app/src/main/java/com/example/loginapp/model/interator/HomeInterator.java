package com.example.loginapp.model.interator;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.loginapp.App;
import com.example.loginapp.data.AppSharedPreferences;
import com.example.loginapp.data.local.AppDao;
import com.example.loginapp.data.local.AppDatabase;
import com.example.loginapp.data.remote.AppApiService;
import com.example.loginapp.data.remote.dto.ProductResponse;
import com.example.loginapp.model.listener.HomeListener;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeInterator {

    private final HomeListener listener;

    public HomeInterator(HomeListener listener) {
        this.listener = listener;
    }

    public void logout() {
        AppSharedPreferences.getInstance(App.getInstances().getApplicationContext()
            .getApplicationContext()).setLoginStatus(false);
        listener.goOverviewScreen();
        listener.onLogoutMessage("Signed out successfully");
    }

    public void getListProductFromNetwork() {
        listener.showProcessBar(true);
        Call<ProductResponse> call = AppApiService.retrofit.getProducts();
        call.enqueue(new Callback<ProductResponse>() {
            @Override
            public void onResponse(Call<ProductResponse> call, Response<ProductResponse> response) {
                if (response.isSuccessful()) {
                    ProductResponse productResponse = response.body();
                    if (productResponse != null) {
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

    public void getCategories() {

        Call<List<String>> call = AppApiService.retrofit.getCategories();

        call.enqueue(new Callback<List<String>>() {
            @Override
            public void onResponse(Call<List<String>> call, Response<List<String>> response) {
                if(response.isSuccessful()) {
                    List<String> categories = response.body();
                    if(categories != null) {
                        categories.add(0, "Default");
                        listener.onLoadCategories(categories);

                    } else {
                        listener.onLoadError("No Category item");
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<String>> call, Throwable t) {
                listener.onLoadError(t.getMessage());
            }
        });
    }

    public void getProductOfCategory(String category) {
        listener.showProcessBar(true);
        Call<ProductResponse> call = AppApiService.retrofit.getProductOfCategory(category);
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
