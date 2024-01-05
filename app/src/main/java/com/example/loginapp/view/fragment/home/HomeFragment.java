package com.example.loginapp.view.fragment.home;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.example.loginapp.App;
import com.example.loginapp.R;
import com.example.loginapp.adapter.discount_adapter.DiscountAdapter;
import com.example.loginapp.adapter.product_adapter.ProductAdapter;
import com.example.loginapp.data.remote.api.dto.Product;
import com.example.loginapp.databinding.FragmentHomeBinding;
import com.example.loginapp.model.entity.UserData;
import com.example.loginapp.presenter.HomePresenter;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.smarteist.autoimageslider.IndicatorView.animation.type.IndicatorAnimationType;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;

import java.util.List;

public class HomeFragment extends Fragment implements HomeView {
    private final String TAG = this.toString();

    private FragmentHomeBinding binding;

    private HomePresenter homePresenter;

    private RecyclerView productRecyclerview;

    private final ProductAdapter productAdapter = new ProductAdapter();

    private Spinner spinner;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @androidx.annotation.Nullable
    @Override
    public View onCreateView(
        @NonNull LayoutInflater inflater,
        @androidx.annotation.Nullable ViewGroup container,
        @androidx.annotation.Nullable Bundle savedInstanceState
    ) {
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(
        @NonNull View view,
        @androidx.annotation.Nullable Bundle savedInstanceState
    ) {
        super.onViewCreated(view, savedInstanceState);
        homePresenter = new HomePresenter(this);
        binding.setHomeFragment(this);
        spinner = binding.categories;

        LinearLayout bottomNavigationView =
            requireActivity().findViewById(R.id.bottom_navigation);
        bottomNavigationView.setVisibility(View.VISIBLE);

        productRecyclerview = binding.productRecyclerview;
        productRecyclerview.setHasFixedSize(true);

        SliderView sliderView = binding.discountSliderView;
        DiscountAdapter adapter = new DiscountAdapter();
        binding.discountSliderView.setSliderAdapter(adapter);
        sliderView.setIndicatorAnimation(IndicatorAnimationType.WORM);
        sliderView.setSliderTransformAnimation(SliderAnimations.SIMPLETRANSFORMATION);
        sliderView.setAutoCycleDirection(SliderView.AUTO_CYCLE_DIRECTION_BACK_AND_FORTH);
        sliderView.setScrollTimeInSec(4);
        sliderView.startAutoCycle();

        binding.nestedScrollView.setOnScrollChangeListener((NestedScrollView.OnScrollChangeListener) (v, scrollX, scrollY, oldScrollX, oldScrollY) -> {
            // Xử lý sự kiện cuộn tại đây

            int totalHeight = v.getChildAt(0).getHeight();

            // Chiều cao màn hình
            int screenHeight = v.getHeight();

            // Nửa chiều cao của màn hình
            int halfScreenHeight = screenHeight / 2;

            // Kiểm tra xem người dùng có đang cuộn ở nửa trên hay nửa dưới
            if (scrollY + halfScreenHeight < totalHeight / 2) {
                // Người dùng đang cuộn ở nửa trên
                // Thực hiện các hành động tương ứng
                binding.buttonScroll.setVisibility(View.GONE);

            } else {
                // Người dùng đang cuộn ở nửa dưới
                // Thực hiện các hành động tương ứng
                binding.buttonScroll.setVisibility(View.VISIBLE);
            }

        });

        binding.buttonScroll.setOnClickListener(v -> {
            binding.nestedScrollView.smoothScrollTo(0, 0);
        });

    }

    @Override
    public void showProcessBar(Boolean show) {
        if (show) {
            binding.processBar.setVisibility(View.VISIBLE);
            binding.productRecyclerview.setVisibility(View.GONE);
        } else {
            binding.processBar.setVisibility(View.GONE);
            binding.productRecyclerview.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void getUserData(UserData userData) {
        binding.setUserData(new UserData(userData.getUsername(), userData.getPhotoUrl()));
    }


    private void goProductScreen(int productId) {
        Bundle bundle = new Bundle();
        bundle.putInt("productId", productId);
        Navigation.findNavController(binding.getRoot())
            .navigate(R.id.action_homeFragment_to_productFragment, bundle);
    }

    @Override
    public void onLoadProduct(List<Product> products) {
        productAdapter.setOnItemClickListener(product -> goProductScreen(product.getId()));
        productAdapter.submitList(products);
        productRecyclerview.setAdapter(productAdapter);
        binding.linearProcess.setVisibility(View.GONE);
    }

    @Override
    public void onLoadError(String message) {
        Toast.makeText(this.getContext(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onLoadCategories(List<String> categories) {

        ArrayAdapter<String> adapter =
            new ArrayAdapter<>(
                App.getApplication(),
                android.R.layout.simple_spinner_item,
                categories
            );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // Xử lý sự kiện khi một mục được chọn
                Object selectedCategory = parent.getItemAtPosition(position);
                // Thực hiện các thao tác cần thiết với mục được chọn
                // Ví dụ: load danh sách sản phẩm tương ứng với danh mục được chọn
                if (selectedCategory.toString().equals("Recommended")) {
                    homePresenter.getProducts();
                } else {
                    homePresenter.getProductOfCategory(selectedCategory.toString());
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }
}