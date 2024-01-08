package com.example.loginapp.view.activities;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentManager;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import com.example.loginapp.R;
import com.example.loginapp.databinding.ActivityMainBinding;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    private NavController navController;

    private ActivityMainBinding binding;

    private View[] views;

    private ImageView[] imageViews;
    ColorStateList colorStateList;

    private final int[] iconGray = {
        R.drawable.ic_home_gray,
        R.drawable.ic_search,
        R.drawable.ic_cart,
        R.drawable.ic_favorite_gray,
        R.drawable.ic_user_gray
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (FirebaseAuth.getInstance().getCurrentUser() == null) {
            startActivity(new Intent(MainActivity.this, LoginActivity.class));
            this.finish();
        }

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.setActivity(this);
        colorStateList =
            ContextCompat.getColorStateList(this, R.color.black);
        FragmentManager fragmentManager = getSupportFragmentManager();
        NavHostFragment navHostFragment = (NavHostFragment) fragmentManager
            .findFragmentById(R.id.main_container);

        assert navHostFragment != null;
        navController = navHostFragment.getNavController();

        views = new View[]{
            binding.homeView,
            binding.searchView,
            binding.cartView,
            binding.favoriteView,
            binding.userView
        };

        imageViews = new ImageView[]{
            binding.homeIcon,
            binding.searchIcon,
            binding.cartIcon,
            binding.favoriteIcon,
            binding.userIcon
        };
    }

    public void onHomeClick() {
        resetView();
        binding.homeView.setBackgroundResource(R.color.black);
        binding.homeIcon.setImageResource(R.drawable.ic_home_dark);
        navController.popBackStack();
        navController.navigate(R.id.homeFragment);
    }

    public void onSearchClick() {
        resetView();
        binding.searchView.setBackgroundResource(R.color.black);
        binding.searchIcon.setImageResource(R.drawable.ic_search_dark);
        navController.popBackStack();
        navController.navigate(R.id.searchProductFragment);
    }

    public void onCartClick() {
        resetView();
        binding.cartView.setBackgroundResource(R.color.black);
        binding.cartIcon.setImageResource(R.drawable.ic_cart_dark);
        navController.popBackStack();
        navController.navigate(R.id.cartFragment);
    }

    public void onFavoriteClick() {
        resetView();
        binding.favoriteView.setBackgroundResource(R.color.black);
        binding.favoriteIcon.setImageResource(R.drawable.ic_favorite_dark);
        navController.popBackStack();
        navController.navigate(R.id.favoriteProductFragment);
    }

    public void onUserClick() {
        resetView();
        binding.userView.setBackgroundResource(R.color.black);
        binding.userIcon.setImageResource(R.drawable.ic_user_dark);
        navController.popBackStack();
        navController.navigate(R.id.userProfileFragment);
    }

    private void resetView() {
        for (int i = 0; i < 5; i++) {
            views[i].setBackgroundResource(android.R.color.transparent);
            imageViews[i].setImageResource(iconGray[i]);
        }
    }
}
