package com.doubleclick.marktinhome.Model;

/**
 * Created By Eslam Ghazy on 6/25/2022
 */
public class RecentOrderData {

    private RecentOrder recentOrder;
    private Product product;
    private User user;

    public RecentOrderData(RecentOrder recentOrder, Product product, User user) {
        this.recentOrder = recentOrder;
        this.product = product;
        this.user = user;
    }

    public RecentOrderData() {
    }

    public RecentOrder getRecentOrder() {
        return recentOrder;
    }

    public void setRecentOrder(RecentOrder recentOrder) {
        this.recentOrder = recentOrder;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
