package com.example.loginapp.view.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.example.loginapp.R;
import com.example.loginapp.adapter.discount_adapter.DiscountAdapter;
import com.example.loginapp.adapter.overview_slider.SliderAdapter;
import com.example.loginapp.adapter.product_adapter.ProductAdapter;
import com.example.loginapp.data.remote.dto.Product;
import com.example.loginapp.databinding.FragmentHomeBinding;
import com.example.loginapp.presenter.HomePresenter;
import com.example.loginapp.view.activities.LoginActivity;
import com.example.loginapp.view.activities.MainActivity;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.smarteist.autoimageslider.IndicatorView.animation.type.IndicatorAnimationType;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;

import java.util.List;

public class HomeFragment extends Fragment implements HomeView {
    private FragmentHomeBinding binding;
    private HomePresenter homePresenter;
    private RecyclerView productRecyclerview;
    private ProductAdapter productAdapter = new ProductAdapter();;

    private Spinner spinner;

    private SliderView sliderView;

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
        binding.setHomeFragment(this);
        spinner = binding.categories;
        homePresenter = new HomePresenter(this);

        BottomNavigationView bottomNavigationView = getActivity().findViewById(R.id.bottom_navigation);
        bottomNavigationView.setVisibility(View.VISIBLE);

        productRecyclerview = binding.productRecyclerview;
        productRecyclerview.setHasFixedSize(true);

        sliderView = binding.discountSliderView;
        DiscountAdapter adapter = new DiscountAdapter();
        binding.discountSliderView.setSliderAdapter(adapter);
        sliderView.setIndicatorAnimation(IndicatorAnimationType.WORM);
        sliderView.setSliderTransformAnimation(SliderAnimations.SIMPLETRANSFORMATION);
        sliderView.setAutoCycleDirection(SliderView.AUTO_CYCLE_DIRECTION_BACK_AND_FORTH);
        sliderView.setScrollTimeInSec(4);
        sliderView.startAutoCycle();

        binding.nestedScrollView.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
                @Override
                public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
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

                }
            });

        binding.buttonScroll.setOnClickListener( v -> {binding.nestedScrollView.smoothScrollTo(0, 0);});

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

    public void getLogout() {
        homePresenter.logout();
    }

    @Override
    public void goOverviewScreen() {
       startActivity(new Intent(requireActivity(), LoginActivity.class));
    }

    @Override
    public void onLogoutMessage(String message) {
        Toast.makeText(this.getContext(), message, Toast.LENGTH_SHORT).show();
        getActivity().finish();
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
    }

    @Override
    public void onLoadError(String message) {
        Toast.makeText(this.getContext(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onLoadCategories(List<String> categories) {
        ArrayAdapter<String> adapter =
            new ArrayAdapter<>(this.requireContext(), android.R.layout.simple_spinner_item, categories);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // Xử lý sự kiện khi một mục được chọn
                Object selectedCategory = parent.getItemAtPosition(position);
                // Thực hiện các thao tác cần thiết với mục được chọn
                // Ví dụ: load danh sách sản phẩm tương ứng với danh mục được chọn
                if (!selectedCategory.toString().equals(categories.get(0))) {
                    binding.recommended.setVisibility(View.GONE);
                    homePresenter.getProductOfCategory(selectedCategory.toString());
                } else {
                    binding.recommended.setVisibility(View.VISIBLE);
                    homePresenter.getProducts();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Xử lý sự kiện khi không có mục nào được chọn
            }
        });
    }
}