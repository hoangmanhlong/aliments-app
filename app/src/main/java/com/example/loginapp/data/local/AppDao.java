package com.example.loginapp.data.local;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.loginapp.data.remote.dto.Product;

import java.util.List;

@Dao
public interface AppDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public void insertProduct(List<Product> products);

    @Query("SELECT * FROM product WHERE id = :id")
    Product getProduct(int id);

    @Query("SELECT * FROM product ORDER BY id ASC")
    LiveData<List<Product>> getProduct();
}
