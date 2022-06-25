package com.doubleclick.marktinhome.Model;

import androidx.annotation.NonNull;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * Created By Eslam Ghazy on 3/12/2022
 */
public class RecentOrder {

    @NonNull
    private String productId;
    private double price;
    private int quantity;
    @NonNull
    private String id;
    @NonNull
    private String buyerId;
    @NonNull
    private String sellerId;
    private double totalPrice;
    private long date;
    @NonNull
    private String address;
    @NonNull
    private String phone;
    @NonNull
    private String anotherPhone;
    @NonNull
    private String locationUri;
    @NonNull
    private String toggleItemColor;
    @NonNull
    private String toggleItemSize;

    public RecentOrder() {
        productId = "";
        price = 0;
        quantity = 0;
        id = "";
        buyerId = "";
        sellerId = "";
        totalPrice = 0;
        date = 0;
        address = "";
        phone = "";
        anotherPhone = "";
        locationUri = "";
        toggleItemColor = "";
        toggleItemSize = "";
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBuyerId() {
        return buyerId;
    }

    public void setBuyerId(String buyerId) {
        this.buyerId = buyerId;
    }

    public String getSellerId() {
        return sellerId;
    }

    public void setSellerId(String sellerId) {
        this.sellerId = sellerId;
    }

    public long getQuantity() {
        return quantity;
    }


    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }


    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAnotherPhone() {
        return anotherPhone;
    }

    public void setAnotherPhone(String anotherPhone) {
        this.anotherPhone = anotherPhone;
    }

    public String getLocationUri() {
        return locationUri;
    }

    public void setLocationUri(String locationUri) {
        this.locationUri = locationUri;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @NonNull
    public String getToggleItemColor() {
        return toggleItemColor;
    }

    public void setToggleItemColor(@NonNull String toggleItemColor) {
        this.toggleItemColor = toggleItemColor;
    }

    @NonNull
    public String getToggleItemSize() {
        return toggleItemSize;
    }

    public void setToggleItemSize(@NonNull String toggleItemSize) {
        this.toggleItemSize = toggleItemSize;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof RecentOrder)) return false;
        RecentOrder that = (RecentOrder) o;
        return Double.compare(that.getPrice(), getPrice()) == 0 && getQuantity() == that.getQuantity() && Double.compare(that.getTotalPrice(), getTotalPrice()) == 0 && getDate() == that.getDate() && getProductId().equals(that.getProductId()) && getId().equals(that.getId()) && getBuyerId().equals(that.getBuyerId()) && getSellerId().equals(that.getSellerId()) && getAddress().equals(that.getAddress()) && getPhone().equals(that.getPhone()) && getAnotherPhone().equals(that.getAnotherPhone()) && getLocationUri().equals(that.getLocationUri()) && getToggleItemColor().equals(that.getToggleItemColor()) && getToggleItemSize().equals(that.getToggleItemSize());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getProductId(), getPrice(), getQuantity(), getId(), getBuyerId(), getSellerId(), getTotalPrice(), getDate(), getAddress(), getPhone(), getAnotherPhone(), getLocationUri(), getToggleItemColor(), getToggleItemSize());
    }
}
