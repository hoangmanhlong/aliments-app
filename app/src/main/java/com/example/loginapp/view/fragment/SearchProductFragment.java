package com.example.loginapp.view.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.example.loginapp.R;
import com.example.loginapp.adapter.product_adapter.ProductAdapter;
import com.example.loginapp.data.remote.dto.Product;
import com.example.loginapp.databinding.FragmentHomeBinding;
import com.example.loginapp.databinding.FragmentSearchProductBinding;
import com.example.loginapp.presenter.SearchPresenter;

import java.util.List;

public class SearchProductFragment extends Fragment implements SearchView {

    private FragmentSearchProductBinding binding;

    private SearchPresenter presenter;

    private RecyclerView productRecyclerview;
    private final ProductAdapter productAdapter = new ProductAdapter();;

    @androidx.annotation.Nullable
    @Override
    public View onCreateView(
        @NonNull LayoutInflater inflater,
        @androidx.annotation.Nullable ViewGroup container,
        @androidx.annotation.Nullable Bundle savedInstanceState
    ) {
        binding = FragmentSearchProductBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(
        @NonNull View view,
        @androidx.annotation.Nullable Bundle savedInstanceState
    ) {
        super.onViewCreated(view, savedInstanceState);

        presenter = new SearchPresenter(this);

        productRecyclerview = binding.searchRecyclerview;
        productRecyclerview.setHasFixedSize(true);
    }

    @Override
    public void showProcessBar(Boolean show) {
        if (show) {
            binding.loadSearchProcess.setVisibility(View.VISIBLE);
            binding.searchRecyclerview.setVisibility(View.GONE);
        } else {
            binding.loadSearchProcess.setVisibility(View.GONE);
            binding.searchRecyclerview.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onLoadProducts(List<Product> products) {
        productAdapter.setOnItemClickListener(product -> goProductScreen(product.getId()));
        productAdapter.submitList(products);
        productRecyclerview.setAdapter(productAdapter);
    }

    @Override
    public void onLoadError(String message) {
        Toast.makeText(this.getContext(), message, Toast.LENGTH_SHORT).show();
    }

    private void goProductScreen(int productId) {
        Bundle bundle = new Bundle();
        bundle.putInt("productId", productId);
        Navigation.findNavController(binding.getRoot())
            .navigate(R.id.action_homeFragment_to_productFragment, bundle);
    }

}
