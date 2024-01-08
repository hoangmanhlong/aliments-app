package com.example.loginapp.view.activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentManager;
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.fragment.NavHostFragment;

import com.example.loginapp.R;
import com.example.loginapp.databinding.ActivityMainBinding;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    private static final long DOUBLE_CLICK_TIME_DELTA = 300;
    // Thời gian giữa hai lần nhấn (milliseconds)
    private long lastClickTime = 0;

    private boolean backPressedOnce = false;
    private static final int BACK_PRESS_INTERVAL = 2000; // 2 seconds

    private NavController navController;

    private ActivityMainBinding binding;

    private LinearLayout[] linearLayouts;

    private FragmentManager fragmentManager;

    private View[] views;

    private ImageButton[] imageButtons;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("Init", "2");
        if (FirebaseAuth.getInstance().getCurrentUser() == null) {
            startActivity(new Intent(MainActivity.this, LoginActivity.class));
            this.finish();
        }

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        fragmentManager = getSupportFragmentManager();
        NavHostFragment navHostFragment = (NavHostFragment) fragmentManager
                .findFragmentById(R.id.main_container);

        navController = navHostFragment.getNavController();

//        BottomNavigationView navigationView = binding.bottomNavigation;
//
//        NavigationUI.setupWithNavController(navigationView, navController);

        linearLayouts = new LinearLayout[]{
                binding.home,
                binding.search,
                binding.cart,
                binding.favorite,
                binding.user
        };

        views = new View[]{
                binding.homeView,
                binding.searchView,
                binding.cartView,
                binding.favoriteView,
                binding.userView
        };

        imageButtons = new ImageButton[]{
                binding.homeIcon,
                binding.searchIcon,
                binding.cartIcon,
                binding.favoriteIcon,
                binding.userIcon
        };

        for (int i = 0; i < linearLayouts.length; i++) {
            final int index = i;
            linearLayouts[i].setOnClickListener(v -> handleLinearLayoutClick(index));
        }

    }

    @SuppressLint("ResourceAsColor")
    private void handleLinearLayoutClick(int selectedIndex) {
        for (int i = 0; i < linearLayouts.length; i++) {
            if (i == selectedIndex) {
                ColorStateList colorStateList =
                        ContextCompat.getColorStateList(this, R.color.black);
                views[i].setVisibility(View.VISIBLE);
                views[i].setBackgroundColor(R.color.md_theme_dark_background);
                views[i].setBackgroundColor(Color.BLACK);
                imageButtons[i].setBackgroundTintList(colorStateList);
                switch (selectedIndex) {
                    case 0:
                        navController.popBackStack();
                        navController.navigate(R.id.homeFragment);
                        break;
                    case 1:
                        navController.navigate(R.id.searchProductFragment);
                        break;
                    case 2:
                        navController.navigate(R.id.cartFragment);
                        break;
                    case 3:
                        navController.navigate(R.id.favoriteProductFragment);
                        break;
                    case 4:
                        navController.navigate(R.id.userProfileFragment);
                        break;
                }
            } else {
                views[i].setVisibility(View.GONE);
                views[i].setBackgroundColor(R.color.white);
                ColorStateList colorStateList =
                        ContextCompat.getColorStateList(this, R.color.gray_500);
                imageButtons[i].setBackgroundTintList(colorStateList);
            }
        }
    }

//    @Override
//    public void onBackPressed() {
//        if (fragmentManager.getBackStackEntryCount() == 0) {
//            if (backPressedOnce) {
//                super.onBackPressed(); // Exit the app
//            } else {
//                backPressedOnce = true;
//                Toast.makeText(this, "Press back again to exit", Toast.LENGTH_SHORT).show();
//                new Handler().postDelayed(() -> backPressedOnce = false, BACK_PRESS_INTERVAL);
//            }
//        } else {
//            super.onBackPressed();
//        }
//    }

    @Override
    public void onBackPressed() {
        NavDestination currentDestination = navController.getCurrentDestination();

        if (currentDestination != null && currentDestination.getId() == R.id.homeFragment) {
            // Kiểm tra thời điểm giữa hai lần nhấn Back
            long clickTime = System.currentTimeMillis();
            if (clickTime - lastClickTime < DOUBLE_CLICK_TIME_DELTA) {
                // Nếu đã nhấn Back hai lần trong khoảng thời gian quy định, thoát ứng dụng
                super.onBackPressed();
            } else {
                // Nếu chỉ nhấn một lần, thông báo cho người dùng
                Toast.makeText(this, "Nhấn Back thêm một lần để thoát", Toast.LENGTH_SHORT).show();
            }
            lastClickTime = clickTime;
        } else {
            // Nếu không ở trên màn hình chính, xử lý nút Back theo mặc định
            super.onBackPressed();
        }
    }
}
