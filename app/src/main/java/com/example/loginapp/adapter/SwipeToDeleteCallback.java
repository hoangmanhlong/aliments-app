package com.example.loginapp.adapter;

import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.example.loginapp.adapter.search_favorite_cart_adapter.CommonProductAdapter;

public class SwipeToDeleteCallback extends ItemTouchHelper.SimpleCallback {

    private final CommonProductAdapter mAdapter;

    public SwipeToDeleteCallback(CommonProductAdapter adapter) {
        super(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT);
        mAdapter = adapter;
    }

    @Override
    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
        return false;
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
        int position = viewHolder.getAdapterPosition();
//        mAdapter.deleteItem(position);
    }
}

