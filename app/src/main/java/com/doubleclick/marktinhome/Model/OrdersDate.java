package com.doubleclick.marktinhome.Model;

/**
 * Created By Eslam Ghazy on 6/24/2022
 */
public class OrdersDate {

    private Orders orders;
    private Product product;

    public OrdersDate(Orders orders, Product product) {
        this.orders = orders;
        this.product = product;
    }

    public OrdersDate() {
    }

    public Orders getOrders() {
        return orders;
    }

    public void setOrders(Orders orders) {
        this.orders = orders;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    @Override
    public String toString() {
        return "OrdersDate{" +
                "orders=" + orders +
                ", product=" + product +
                '}';
    }
}
