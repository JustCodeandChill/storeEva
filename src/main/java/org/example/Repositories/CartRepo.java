package org.example.Repositories;

import org.example.Models.Cart;

public class CartRepo {
    Cart cart;
    private static CartRepo instance;

    public CartRepo() {
    }

    public static CartRepo getInstance() {
        if (instance == null) {
            instance = new CartRepo();
        }
        return instance;
    }

    public CartRepo(Cart cart) {
        this.cart = cart;
    }

    public Cart getCart() {
        return cart;
    }

    public void setCart(Cart cart) {
        this.cart = cart;
    }
}
