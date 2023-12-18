package com.example.loginapp.data.remote;


import com.example.loginapp.data.remote.dto.CartResponse;
import com.example.loginapp.data.remote.dto.Product;
import com.example.loginapp.data.remote.dto.ProductResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface AppApi {
    @GET("products")
    Call<ProductResponse> getProducts();

    @GET("products/{id}")
    Call<Product> getProduct(@Path("id") int productId);

    @GET("products/categories")
    Call<List<String>> getCategories();

    @GET("products/category/{categoryName}")
    Call<ProductResponse> getProductOfCategory(@Path("categoryName") String category);

    @GET("products/search")
    Call<ProductResponse> searchProduct(@Query("q") String query);

}
