package com.example.loginapp.model.interator;

import androidx.annotation.NonNull;

import com.example.loginapp.data.remote.api.ApiStatus;
import com.example.loginapp.data.remote.api.AppApiService;
import com.example.loginapp.data.remote.api.dto.Product;
import com.example.loginapp.data.remote.service.Constant;
import com.example.loginapp.model.entity.FirebaseProduct;
import com.example.loginapp.model.listener.ProductListener;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductInterator {
    private final DatabaseReference favoriteProductRef =
        FirebaseDatabase.getInstance().getReference().child(Constant.FAVORITE_PRODUCT_REF);

    private ApiStatus apiProductStatus = ApiStatus.ERROR;

    private ApiStatus firebaseProductStatus = ApiStatus.ERROR;

    private final DatabaseReference productInCartRef =
        FirebaseDatabase.getInstance().getReference().child(Constant.CART_REF);

    private final FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();

    private final ProductListener listener;

    public ProductInterator(ProductListener listener) {
        this.listener = listener;
    }

    public List<Product> products;

    public void getProduct(int id) {
        products = new ArrayList<>();
        apiProductStatus = ApiStatus.REQUEST;
        firebaseProductStatus = ApiStatus.REQUEST;
        Call<Product> call = AppApiService.retrofit.getProduct(id);
        call.enqueue(new Callback<Product>() {
            @Override
            public void onResponse(Call<Product> call, Response<Product> response) {
                if (response.isSuccessful()) {
                    apiProductStatus = ApiStatus.SUCCESS;
                    Product product = response.body();
                    if (product != null) {
                        listener.onGetProduct(product, isFinished());
                        products.add(product);
                    }
                }
            }

            @Override
            public void onFailure(Call<Product> call, Throwable t) {
                listener.onMessage(t.getMessage());
                apiProductStatus = ApiStatus.ERROR;
                listener.onGetProduct(null, isFinished());
            }
        });

        assert currentUser != null;
        favoriteProductRef.child(currentUser.getUid()).child(String.valueOf(id)).get()
            .addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DataSnapshot> task) {
                    if (!task.isSuccessful()) {
                        firebaseProductStatus = ApiStatus.ERROR;
                        listener.onGetProduct(null, isFinished());
                    } else {
                        firebaseProductStatus = ApiStatus.SUCCESS;
                        Product product = task.getResult().getValue(Product.class);
                        products.add(product);
                        listener.onGetProduct(product, isFinished());
                    }
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    firebaseProductStatus = ApiStatus.ERROR;
                    listener.onGetProduct(null, isFinished());
                }
            });
    }

    public void compare(Product apiProduct, Product firebaseProduct) {
        if (apiProduct != null && firebaseProduct != null && apiProduct.getId() == firebaseProduct.getId()) {
            listener.enableFavorite(true);
        } else {
            listener.enableFavorite(false);
        }
    }

    private boolean isFinished() {
        return apiProductStatus != ApiStatus.REQUEST && firebaseProductStatus != ApiStatus.REQUEST;
    }

    public void saveFavoriteProduct(Product product) {
        String productId = String.valueOf(product.getId());
        assert currentUser != null;
        favoriteProductRef.child(currentUser.getUid()).child(productId).setValue(product)
            .addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    listener.onMessage("Product added");
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    listener.onMessage("An error occurred. Please try again later");
                }
            });
    }

    public void saveCart(Product product) {
        String productId = String.valueOf(product.getId());
        productInCartRef.child(currentUser.getUid()).child(productId).setValue(
            new FirebaseProduct(
                product.getId(),
                product.getTitle(),
                product.getDescription(),
                product.getPrice(),
                product.getDiscountPercentage(),
                product.getRating(),
                product.getStock(),
                product.getBrand(),
                product.getCategory(),
                product.getThumbnail(),
                String.valueOf(1)
            )
            )
            .addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    listener.onMessage("Product added to cart");
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    listener.onMessage("An error occurred. Please try again later");
                }
            });
    }
}
