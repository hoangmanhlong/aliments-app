package com.example.loginapp.view.fragment.cart;

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
import androidx.recyclerview.widget.RecyclerView;

import com.example.loginapp.App;
import com.example.loginapp.R;
import com.example.loginapp.adapter.CartAdapter;
import com.example.loginapp.adapter.search_favorite_cart_adapter.CommonProductAdapter;
import com.example.loginapp.data.remote.api.dto.Product;
import com.example.loginapp.databinding.FragmentCartBinding;
import com.example.loginapp.model.entity.FirebaseProduct;
import com.example.loginapp.presenter.CartPresenter;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.List;

public class CartFragment extends Fragment implements CartView {

    private CartPresenter presenter;

    private FragmentCartBinding binding;

    private RecyclerView recyclerView;
    private final CartAdapter adapter = new CartAdapter();

    @Nullable
    @Override
    public View onCreateView(
        @NonNull LayoutInflater inflater,
        @Nullable ViewGroup container,
        @Nullable Bundle savedInstanceState
    ) {
        binding = FragmentCartBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        presenter = new CartPresenter(this);
        recyclerView = binding.favoriteRecyclerView;

        LinearLayout bottomNavigationView =
            requireActivity().findViewById(R.id.bottom_navigation);
        bottomNavigationView.setVisibility(View.VISIBLE);
    }

    @Override
    public void onLoadFavoriteProducts(List<FirebaseProduct> products) {
//        adapter.setOnItemClickListener(product -> {goProductScreen(product.getId());});
        adapter.submitList(products);
        recyclerView.setAdapter(adapter);
    }

    private void goProductScreen(int productId) {
        Bundle bundle = new Bundle();
        bundle.putInt("productId", productId);
        Navigation.findNavController(binding.getRoot())
            .navigate(R.id.action_cartFragment_to_productFragment, bundle);
    }

    @Override
    public void onMessage(String message) {
        Toast.makeText(App.getApplication(), message, Toast.LENGTH_SHORT).show();
    }
}