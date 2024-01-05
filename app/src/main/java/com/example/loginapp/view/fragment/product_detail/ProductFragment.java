package com.example.loginapp.view.fragment.product_detail;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.example.loginapp.R;
import com.example.loginapp.data.remote.api.dto.Product;
import com.example.loginapp.databinding.FragmentProductBinding;
import com.example.loginapp.presenter.ProductPresenter;
import com.example.loginapp.view.fragment.bottom_sheet.ModalBottomSheetFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class ProductFragment extends Fragment implements ProductView {
    public static final String PRODUCT_KEY = "product";

    private FragmentProductBinding binding;

    private ModalBottomSheetFragment modalBottomSheetFragment;

    private ProductPresenter presenter;

    private Product tempProduct;

    private static int productId;

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
        binding.setFragment(this);
        LinearLayout bottomNavigationView =
            requireActivity().findViewById(R.id.bottom_navigation);
        bottomNavigationView.setVisibility(View.GONE);
        if (getArguments() != null) {
            productId = getArguments().getInt("productId");
            presenter.getProduct(productId);
        }
        modalBottomSheetFragment = new ModalBottomSheetFragment();
    }

    public void showBottomSheet() {
        Bundle bundle = new Bundle();
        bundle.putInt(PRODUCT_KEY, tempProduct.getId());
        modalBottomSheetFragment.setArguments(bundle);
        modalBottomSheetFragment.show(requireActivity().getSupportFragmentManager(), ModalBottomSheetFragment.TAG);
    }

    public void onNavigateIconClick() {
        Navigation.findNavController(binding.getRoot()).navigateUp();
    }

    public void onFavoriteIconClick() {
        presenter.addFavoriteProduct(tempProduct);
        binding.favoriteBtn.setImageResource(R.drawable.ic_favorite);
    }

    public void onCartBtnClick() {
        presenter.addToCart(tempProduct);
    }

    @Override
    public void onLoadProduct(Product product) {
        binding.setProduct(product);
        tempProduct = product;
    }

    @Override
    public void onMessage(String message) {
        Toast.makeText(this.getContext(), message, Toast.LENGTH_SHORT).show();
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void enableFavorite(Boolean compare) {
        if (compare) binding.favoriteBtn.setImageResource(R.drawable.ic_favorite);
    }
}