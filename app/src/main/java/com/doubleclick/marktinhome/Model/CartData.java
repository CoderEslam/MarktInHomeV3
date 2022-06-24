package com.doubleclick.marktinhome.Model;

import java.util.Objects;

/**
 * Created By Eslam Ghazy on 6/24/2022
 */
public class CartData {

    private Cart cart;
    private Product product;

    public CartData(Cart cart, Product product) {
        this.cart = cart;
        this.product = product;
    }

    public CartData() {
    }

    public Cart getCart() {
        return cart;
    }

    public void setCart(Cart cart) {
        this.cart = cart;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CartData)) return false;
        CartData cartData = (CartData) o;
        return getCart().equals(cartData.getCart()) && getProduct().equals(cartData.getProduct());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getCart(), getProduct());
    }

    @Override
    public String toString() {
        return "CartData{" +
                "cart=" + cart +
                ", product=" + product +
                '}';
    }
}
