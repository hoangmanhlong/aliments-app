package com.example.loginapp.view.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.example.loginapp.R;
import com.example.loginapp.data.remote.dto.Product;
import com.example.loginapp.databinding.FragmentProductBinding;
import com.example.loginapp.presenter.ProductPresenter;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class ProductFragment extends Fragment implements ProductView {
    private FragmentProductBinding binding;
    private ProductPresenter presenter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @SuppressLint("ResourceType")
    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.id.product_screen_app_bar, menu);
    }

//    @Override
//    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
//        switch (item.getItemId()) {
//            case R.id.action_settings:  {
//                // Navigate to settings screen.
//                return true;
//            }
//            case R.id.action_done: {
//                // Save profile changes.
//                return true;
//            }
//            default:
//                return super.onOptionsItemSelected(item);
//        }
//
//    }

    @Nullable
    @Override
    public View onCreateView(
        @NonNull LayoutInflater inflater,
        @Nullable ViewGroup container,
        @Nullable Bundle savedInstanceState
    ) {
        binding = FragmentProductBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        presenter = new ProductPresenter(this);
        BottomNavigationView bottomNavigationView = getActivity().findViewById(R.id.bottom_navigation);
        bottomNavigationView.setVisibility(View.GONE);
        if (getArguments() != null) {
            int productId = getArguments().getInt("productId");
            presenter.getProduct(productId);
        }

        binding.productScreenAppBar.setNavigationOnClickListener(v -> onNavigateIconClick());
    }

    private void onNavigateIconClick() {
        Navigation.findNavController(binding.getRoot()).navigateUp();
    }

    @Override
    public void onLoadProduct(Product product) {
        binding.setProduct(product);
    }

    @Override
    public void onLoadError(String message) {
        Toast.makeText(this.getContext(), message, Toast.LENGTH_SHORT).show();
    }
}