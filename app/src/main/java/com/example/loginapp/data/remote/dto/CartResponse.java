package com.example.loginapp.data.remote.dto;

import java.util.List;

public class CartResponse {

    private List<Cart> carts;

    private int total;

    private int skip;

    private int limit;

    public List<Cart> getCarts() {
        return carts;
    }

    public int getTotal() {
        return total;
    }

    public int getSkip() {
        return skip;
    }

    public int getLimit() {
        return limit;
    }

    public static class Cart {

        private int id;


        private List<Product> products;


        private int total;


        private int discountedTotal;


        private int userId;


        private int totalProducts;


        private int totalQuantity;

        public int getId() {
            return id;
        }

        public List<Product> getProducts() {
            return products;
        }

        public int getTotal() {
            return total;
        }

        public int getDiscountedTotal() {
            return discountedTotal;
        }

        public int getUserId() {
            return userId;
        }

        public int getTotalProducts() {
            return totalProducts;
        }

        public int getTotalQuantity() {
            return totalQuantity;
        }
    }
}
