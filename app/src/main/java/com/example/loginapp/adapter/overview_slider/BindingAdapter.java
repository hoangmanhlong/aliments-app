package com.example.loginapp.adapter.overview_slider;

import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.loginapp.adapter.product_adapter.ProductAdapter;
import com.example.loginapp.data.remote.dto.Product;

import java.util.List;
import java.util.Locale;

public class BindingAdapter {
    @androidx.databinding.BindingAdapter("imgRes")
    public static void loadImage(ImageView view, int res){
        view.setImageResource(res);
    }

    @androidx.databinding.BindingAdapter("imgUrl")
    public static void loadImageUrl(ImageView view, String imgUrl) {
        Glide.with(view.getContext())
            .load(imgUrl)
            .into(view);
    }

    @androidx.databinding.BindingAdapter("listData")
    public static void setRecyclerViewData(RecyclerView recyclerView, List<Product> products) {
        // Check if the RecyclerView has an adapter
        if (recyclerView.getAdapter() == null) {
            // If no adapter exists, create one and set it on the RecyclerView
            ProductAdapter adapter = new ProductAdapter();
            recyclerView.setAdapter(adapter);
        }

        // Update the data in the existing adapter
        if (recyclerView.getAdapter() instanceof ProductAdapter) {
            ProductAdapter productAdapter = (ProductAdapter) recyclerView.getAdapter();
            productAdapter.submitList(products);
        }
    }

    @androidx.databinding.BindingAdapter("app:doubleToString")
    public static void doubleToString(TextView view, double value) {
        // Format the double value as needed
        String formattedValue = String.format(Locale.getDefault(), "%.1f", value);
        view.setText(formattedValue);
    }

}