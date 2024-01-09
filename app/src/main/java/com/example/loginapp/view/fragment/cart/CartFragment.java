package com.example.loginapp.view.fragment.cart;

import static com.example.loginapp.view.fragment.product_favorite.FavoriteProductFragment.TAG_Origin;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.example.loginapp.R;
import com.example.loginapp.adapter.SwipeToDeleteCallback;
import com.example.loginapp.adapter.cart_adapter.CartAdapter;
import com.example.loginapp.adapter.cart_adapter.CartItemClickListener;
import com.example.loginapp.adapter.cart_adapter.CheckboxListener;
import com.example.loginapp.databinding.FragmentCartBinding;
import com.example.loginapp.model.entity.FirebaseProduct;
import com.example.loginapp.presenter.CartPresenter;

import java.util.ArrayList;
import java.util.List;

public class CartFragment extends Fragment implements CartView, CartItemClickListener {
    private final String TAG = this.toString();

    private int total = 0;

    private CartPresenter presenter;

    private FragmentCartBinding binding;

    private RecyclerView recyclerView;

    private CartAdapter adapter;

    List<FirebaseProduct> selectedProducts;

    List<FirebaseProduct> cartProducts;

    private FirebaseProduct swipedProduct;

    private int positionTemp = -1;

    private boolean isMainCheckboxChecked = false;

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
        selectedProducts = new ArrayList<>();
        swipedProduct = new FirebaseProduct();
        adapter = new CartAdapter(this, recyclerView);
    }

    @SuppressLint("StringFormatMatches")
    @Override
    public void onLoadFavoriteProducts(List<FirebaseProduct> products) {
        cartProducts = new ArrayList<>();
        cartProducts.addAll(products);
        Log.d(TAG, String.valueOf(cartProducts.size()));
        total = 0;
        setTotal(total);
        adapter.submitList(products);
        recyclerView.setAdapter(adapter);
        enableSwipeToDeleteAndUndo();
    }

    public void onSelectAll() {
        isMainCheckboxChecked = !isMainCheckboxChecked;
        adapter.checkAllItems(isMainCheckboxChecked);
        if (binding.checkAll.isChecked()) {
            selectedProducts.addAll(cartProducts);
            for (FirebaseProduct product : selectedProducts) {
                total += (Math.multiplyExact(
                    product.getPrice(),
                    Integer.parseInt(product.getQuantity())
                ));
            }
            setTotal(total);
        } else {
            selectedProducts.clear();
            total = 0;
            setTotal(0);
        }
    }

    @Override
    public void onMessage(String message) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show();
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
        isMainCheckboxChecked = false;
        binding.checkAll.setChecked(isMainCheckboxChecked);
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.d(TAG, "onDestroyView: cartFragment");
    }

    @Override
    public void onDeleteProduct(int id) {
        dialog(id);
    }

    @Override
    public void saveToListSelected(FirebaseProduct product) {
        selectedProducts.add(product);
        total += (Math.multiplyExact(product.getPrice(), Integer.parseInt(product.getQuantity())));
        setTotal(total);
        Log.d(TAG, String.valueOf(selectedProducts.size()));
    }

    @Override
    public void deleteFromList(FirebaseProduct product) {
        selectedProducts.remove(product);
        total -= (Math.multiplyExact(product.getPrice(), Integer.parseInt(product.getQuantity())));
        setTotal(total);
        Log.d(TAG, String.valueOf(selectedProducts.size()));
    }

    public void setTotal(int total) {
        binding.priceTotal.setText(requireContext().getString(
            R.string.price_format,
            String.valueOf(total)
        ));

    }

    public void dialog(int id) {
        // 1. Instantiate an AlertDialog.Builder with its constructor.
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());

        builder.setMessage(R.string.dialog_message)
            .setPositiveButton(
                R.string.positive_button_title,
                (dialog, which) -> {
                    presenter.deleteProductInFirebase(id);
                }
            )
            .setNegativeButton(R.string.negative_button_title, (dialog, which) -> {
                if (positionTemp != -1 && swipedProduct.getId() > 0) {
                    adapter.restoreItem(swipedProduct, positionTemp);
                }
                positionTemp = -1;
            })
            .setCancelable(false)
            .show();
    }

    public void onBuyClick() {
        Navigation.findNavController(binding.getRoot())
            .navigate(R.id.action_cartFragment_to_checkoutInfoFragment);
    }

    private void enableSwipeToDeleteAndUndo() {
        SwipeToDeleteCallback swipeToDeleteCallback = new SwipeToDeleteCallback(requireContext()) {
            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
                final int position = viewHolder.getAdapterPosition();
                final FirebaseProduct item = adapter.getCurrentList().get(position);
                adapter.removeItem(position);
                positionTemp = position;
                swipedProduct = item;
            }
        };
        ItemTouchHelper itemTouchhelper = new ItemTouchHelper(swipeToDeleteCallback);
        itemTouchhelper.attachToRecyclerView(recyclerView);
    }
}