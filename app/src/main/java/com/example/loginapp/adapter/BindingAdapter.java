package com.example.loginapp.adapter;

import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.loginapp.R;
import com.example.loginapp.adapter.product_adapter.ProductAdapter;
import com.example.loginapp.data.remote.api.dto.Product;

import java.util.List;
import java.util.Locale;

public class BindingAdapter {
    @androidx.databinding.BindingAdapter("imgRes")
    public static void loadImage(ImageView view, int res) {
        view.setImageResource(res);
    }

    @androidx.databinding.BindingAdapter("imgUrl")
    public static void loadImageUrl(ImageView view, String imgUrl) {
        if (imgUrl != null && !imgUrl.isEmpty()) {
            Glide.with(view.getContext())
                .load(imgUrl)
                .into(view);
        } else {
            view.setImageResource(R.drawable.ic_user);
        }
    }

    @androidx.databinding.BindingAdapter("app:doubleToString")
    public static void doubleToString(TextView view, double value) {
        // Format the double value as needed
        String formattedValue = String.format(Locale.getDefault(), "%.1f", value);
        view.setText(formattedValue);
    }
}