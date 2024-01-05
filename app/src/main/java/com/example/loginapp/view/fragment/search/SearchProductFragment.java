package com.example.loginapp.view.fragment.search;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.example.loginapp.R;
import com.example.loginapp.adapter.product_adapter.ProductAdapter;
import com.example.loginapp.adapter.search_favorite_cart_adapter.CommonProductAdapter;
import com.example.loginapp.data.remote.api.dto.Product;
import com.example.loginapp.databinding.FragmentHomeBinding;
import com.example.loginapp.databinding.FragmentSearchProductBinding;
import com.example.loginapp.presenter.SearchPresenter;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.List;

public class SearchProductFragment extends Fragment implements SearchView {

    private FragmentSearchProductBinding binding;

    private SearchPresenter presenter;

    private RecyclerView recyclerView;
    private final CommonProductAdapter adapter = new CommonProductAdapter();

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
        recyclerView = binding.productRecyclerview;
        presenter = new SearchPresenter(this);

        LinearLayout bottomNavigationView =
            requireActivity().findViewById(R.id.bottom_navigation);
        bottomNavigationView.setVisibility(View.VISIBLE);

        binding.query.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH || (event != null && event.getAction() == KeyEvent.ACTION_DOWN && event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) {
                    // Xử lý khi người dùng nhấn phím "Enter" trên bàn phím
                    performSearch();
                    return true;
                }
                return false;
            }
        });
    }

    private void performSearch() {
        Log.d(this.toString(), "Clicked");
        InputMethodManager imm = (InputMethodManager) requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(binding.query.getWindowToken(), 0);
        String query = binding.query.getText().toString().trim();
        if(query.isEmpty()) {
            onLoadError("Please input Information");
        } else {
            presenter.onSearchProduct(query);
        }
    }

    @Override
    public void showProcessBar(Boolean show) {
//        if (show) {
//            binding.loadSearchProcess.setVisibility(View.VISIBLE);
//            binding.searchRecyclerview.setVisibility(View.GONE);
//        } else {
//            binding.loadSearchProcess.setVisibility(View.GONE);
//            binding.searchRecyclerview.setVisibility(View.VISIBLE);
//        }
    }

    @Override
    public void onListEmpty(Boolean show) {
        if (show) {
            binding.titleEmpty.setVisibility(View.VISIBLE);
            binding.productRecyclerview.setVisibility(View.GONE);
        } else {
            binding.titleEmpty.setVisibility(View.GONE);
            binding.productRecyclerview.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onLoadProducts(List<Product> products) {
        if (products.size() == 0) {

        }

        adapter.setOnItemClickListener(product -> goProductScreen(product.getId()));
        adapter.submitList(products);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onLoadError(String message) {
        Toast.makeText(this.getContext(), message, Toast.LENGTH_SHORT).show();
    }

    private void goProductScreen(int productId) {
        Bundle bundle = new Bundle();
        bundle.putInt("productId", productId);
        Navigation.findNavController(binding.getRoot())
            .navigate(R.id.action_searchProductFragment_to_productFragment, bundle);
    }

}
