package com.example.loginapp.model.interator;

import androidx.annotation.NonNull;

import com.example.loginapp.data.remote.api.dto.Product;
import com.example.loginapp.data.remote.service.Constant;
import com.example.loginapp.model.listener.FavoriteListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class FavoriteInterator {
    private final DatabaseReference favoriteRef =
        FirebaseDatabase.getInstance().getReference().child(Constant.FAVORITE_PRODUCT_REF);

    private final FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();

    private FavoriteListener listener;

    public FavoriteInterator(FavoriteListener listener) {
        this.listener = listener;
    }

    public void getFavoriteProductFromFirebase() {
        assert currentUser != null;
        String id = currentUser.getUid();
        favoriteRef.child(id).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                List<Product> products = new ArrayList<>();
                for (DataSnapshot postSnapshot: dataSnapshot.getChildren()) {
                    Product product = postSnapshot.getValue(Product.class);
                    products.add(product);
                }
                listener.onLoadFavoriteProducts(products);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                listener.onMessage("Error, Try again");
            }
        });
    }
}
