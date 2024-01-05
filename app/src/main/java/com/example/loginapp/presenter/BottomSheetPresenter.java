package com.example.loginapp.presenter;

import com.example.loginapp.data.remote.api.dto.Product;
import com.example.loginapp.model.interator.BottomSheetInterator;
import com.example.loginapp.model.listener.BottomSheetListener;
import com.example.loginapp.view.fragment.bottom_sheet.SheetView;

public class BottomSheetPresenter implements BottomSheetListener {
    private SheetView view;
    private BottomSheetInterator interator;

    public BottomSheetPresenter(SheetView view) {
        this.view = view;
        interator = new BottomSheetInterator(this);
    }

    public void getProduct(int id) {
        interator.getProduct(id);
    }
    @Override
    public void onLoadProduct(Product product) {
        view.onLoadProduct(product);
    }
}
