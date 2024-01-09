package com.example.loginapp.view.fragment.product_favorite;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.loginapp.App;
import com.example.loginapp.R;
import com.example.loginapp.adapter.search_favorite_cart_adapter.CommonProductAdapter;
import com.example.loginapp.data.remote.api.dto.Product;
import com.example.loginapp.databinding.FragmentFavoriteProductBinding;
import com.example.loginapp.presenter.FavoritePresenter;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.List;


public class FavoriteProductFragment extends Fragment implements FavoriteView {

    private final String TAG = this.toString();
    public static String TAG_Origin = "Check context";

    private FavoritePresenter presenter;

    private FragmentFavoriteProductBinding binding;
    private RecyclerView recyclerView;
    private final CommonProductAdapter adapter = new CommonProductAdapter();

    @Nullable
    @Override
    public View onCreateView(
        @NonNull LayoutInflater inflater,
        @Nullable ViewGroup container,
        @Nullable Bundle savedInstanceState
    ) {
        binding = FragmentFavoriteProductBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        presenter = new FavoritePresenter(this);
        recyclerView = binding.favoriteRecyclerView;

        LinearLayout bottomNavigationView =
            requireActivity().findViewById(R.id.bottom_navigation);
        bottomNavigationView.setVisibility(View.VISIBLE);
        if (isAdded()) {
            Log.d(TAG_Origin, "onViewCreated: Has context");
        } else Log.d(TAG_Origin, "onViewCreated: No context");
    }

    @Override
    public void onLoadFavoriteProducts(List<Product> products) {
        adapter.setOnItemClickListener(product -> {goProductScreen(product.getId());});
        adapter.submitList(products);
        recyclerView.setAdapter(adapter);
    }

    private void goProductScreen(int productId) {
        Bundle bundle = new Bundle();
        bundle.putInt("productId", productId);
        Navigation.findNavController(binding.getRoot())
            .navigate(R.id.action_favoriteProductFragment_to_productFragment, bundle);
    }

    @Override
    public void onMessage(String message) {
        Toast.makeText(requireActivity(), message, Toast.LENGTH_SHORT).show();
    }
}