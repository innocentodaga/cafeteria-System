package com.example.cafe;

import java.util.ArrayList;
import java.util.List;

public class Cart {
    private List<CartItem> items;

    private static Cart instance;

    private Cart() {
        items = new ArrayList<>();
    }

    public static Cart getInstance() {
        if (instance == null) {
            instance = new Cart();
        }
        return instance;
    }

    public void addItem(CartItem item) {
        items.add(item);
    }

    public List<CartItem> getItems() {
        return items;
    }

    // Additional method to get the total price of items in the cart
    public double getTotalPrice() {
        double totalPrice = 0;
        for (CartItem item : items) {
            totalPrice += item.getPrice();
        }
        return totalPrice;
    }

    // Additional method to check if the cart is empty
    public boolean isEmpty() {
        return items.isEmpty();
    }

    // Additional method to remove an item from the cart
    public void removeItem(CartItem item) {
        items.remove(item);
    }
}
