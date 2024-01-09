package com.example.loginapp.model.interator;

import androidx.annotation.NonNull;

import com.example.loginapp.adapter.cart_adapter.CartItemClickListener;
import com.example.loginapp.data.remote.api.dto.Product;
import com.example.loginapp.data.remote.service.Constant;
import com.example.loginapp.model.entity.FirebaseProduct;
import com.example.loginapp.model.listener.CartListener;
import com.example.loginapp.model.listener.FavoriteListener;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class CartInterator {
    private final DatabaseReference cartRef =
        FirebaseDatabase.getInstance().getReference().child(Constant.CART_REF);

    private final FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();

    private final CartListener listener;

    public CartInterator(CartListener listener) {
        this.listener = listener;
    }

    public void getFavoriteProductFromFirebase() {
        assert currentUser != null;
        String id = currentUser.getUid();
        cartRef.child(id).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                List<FirebaseProduct> products = new ArrayList<>();
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    FirebaseProduct product = postSnapshot.getValue(FirebaseProduct.class);
                    products.add(product);
                }
                listener.onLoadFavoriteProducts(products);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                
            }
        });
    }

    public void updateQuantity(int id, int quantity) {
        cartRef
            .child(currentUser.getUid())
            .child(String.valueOf(id))
            .child("quantity").setValue(String.valueOf(quantity));
    }

    public void  deleteProductInFirebase(int id) {
        cartRef.child(currentUser.getUid())
            .child(String.valueOf(id)).removeValue();
    }
}
