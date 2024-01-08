package com.example.loginapp.model.entity;

import java.text.NumberFormat;

public class FirebaseProduct {
    private int id;
    private String title;
    private String description;
    private int price;
    private double discountPercentage;
    private double rating;
    private int stock;
    private String brand;
    private String category;
    private String thumbnail;
//    private List<String> images;

    private String quantity;

    public void setTitle(String title) {
        this.title = title;
    }

    public FirebaseProduct() {
    }

    public FirebaseProduct(
        int id,
        String title,
        String description,
        int price,
        double discountPercentage,
        double rating,
        int stock,
        String brand,
        String category,
        String thumbnail,
        String quantity
    ) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.price = price;
        this.discountPercentage = discountPercentage;
        this.rating = rating;
        this.stock = stock;
        this.brand = brand;
        this.category = category;
        this.thumbnail = thumbnail;
        this.quantity = quantity;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public int getPrice() {
        return price;
    }

    public double getDiscountPercentage() {
        return discountPercentage;
    }

    public double getRating() {
        return rating;
    }

    public int getStock() {
        return stock;
    }

    public String getBrand() {
        return brand;
    }

    public String getCategory() {
        return category;
    }

    public String getThumbnail() {
        return thumbnail;
    }

//    public List<String> getImages() {
//        return images;
//    }


    public String getQuantity() {
        return quantity;
    }

    public String intToString() {
        NumberFormat numberFormat = NumberFormat.getInstance();
        return numberFormat.format(price);
    }
}

