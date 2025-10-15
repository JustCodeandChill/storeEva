package org.example.Models;

import java.util.HashMap;
import java.util.Map;

public class Cart {
    Map<Long, Integer> cart = new HashMap<>();

    public Cart(Map<Long, Integer> cart) {
        this.cart = cart;
    }

    public Cart() {
    }

    public Map<Long, Integer> getCart() {
        return cart;
    }

    public void setCart(Map<Long, Integer> cart) {
        this.cart = cart;
    }


    @Override
    public String toString() {
        return "Cart{" +
                "cart=" + cart +
                '}';
    }
}
