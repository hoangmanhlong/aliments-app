package com.example.loginapp.view.fragment.cart;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.example.loginapp.App;
import com.example.loginapp.R;
import com.example.loginapp.adapter.cart_adapter.CartAdapter;
import com.example.loginapp.adapter.cart_adapter.CartItemClickListener;
import com.example.loginapp.databinding.FragmentCartBinding;
import com.example.loginapp.model.entity.FirebaseProduct;
import com.example.loginapp.presenter.CartPresenter;

import java.util.List;

public class CartFragment extends Fragment implements CartView, CartItemClickListener {

    private int total = 0;

    private CartPresenter presenter;

    private FragmentCartBinding binding;

    private RecyclerView recyclerView;
    private final CartAdapter adapter = new CartAdapter(this);

    private final OnSelectAllListener onSelectAllListener = new OnSelectAllListener() {
        @Override
        public void onSelectAll(boolean check) {

        }
    };

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
        binding.setFragment(this);
        LinearLayout bottomNavigationView =
            requireActivity().findViewById(R.id.bottom_navigation);
        bottomNavigationView.setVisibility(View.VISIBLE);
    }

    @SuppressLint("StringFormatMatches")
    @Override
    public void onLoadFavoriteProducts(List<FirebaseProduct> products) {
        total = 0;
        binding.priceTotal.setText(String.format(getString(R.string.price_format, String.valueOf(total))));
        adapter.submitList(products);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onMessage(String message) {
        Toast.makeText(App.getApplication(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onItemClick(FirebaseProduct product) {
        Bundle bundle = new Bundle();
        bundle.putInt("productId", product.getId());
        Navigation.findNavController(binding.getRoot())
            .navigate(R.id.action_cartFragment_to_productFragment, bundle);
    }

    @Override
    public void updateQuantity(int id, int quantity) {
        presenter.updateQuantity(id, quantity);
    }

    @Override
    public void onDeleteProduct(int id) {
        dialog(id);
    }

    @SuppressLint("StringFormatMatches")
    @Override
    public void updateTotal(int price) {
        total+=price;
        binding.priceTotal.setText(String.format(getString(R.string.price_format, String.valueOf(total))));
    }

    public void onSelectAll() {
        CheckBox checkBox = binding.checkAll;
        if(checkBox.isChecked()) {
            onSelectAllListener.onSelectAll(false);
            total = 0;
        } else {
            onSelectAllListener.onSelectAll(true);
        }
    }


    public void dialog(int id) {
        // 1. Instantiate an AlertDialog.Builder with its constructor.
        AlertDialog.Builder builder = new AlertDialog.Builder(requireActivity());

        builder.setMessage(R.string.dialog_message)
            .setPositiveButton(
                R.string.positive_button_title,
                (dialog, which) -> {
                    presenter.deleteProductInFirebase(id);
                }
            )
            .setNegativeButton(R.string.negative_button_title, (dialog, which) -> {
            })
            .setCancelable(true)
            .show();

    }
}