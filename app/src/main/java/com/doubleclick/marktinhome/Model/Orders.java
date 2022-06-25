package com.doubleclick.marktinhome.Model;

import androidx.annotation.NonNull;

import java.util.Arrays;
import java.util.List;

/**
 * Created By Eslam Ghazy on 3/12/2022
 */
public class Orders {

    @NonNull
    private String productId;
    private long quantity;
    @NonNull
    private String id;
    @NonNull
    private String buyerId;
    @NonNull
    private String phone;
    @NonNull
    private String anotherPhone;
    @NonNull
    private String address;
    @NonNull
    private String name;
    private long date;
    @NonNull
    private String toggleItemColor;
    @NonNull
    private String toggleItemSize;
    @NonNull
    private String locationUri;


    public Orders() {
        productId = "";
        id = "";
        buyerId = "";
        phone = "";
        anotherPhone = "";
        address = "";
        name = "";
        date = 0;
        toggleItemColor = "";
        locationUri = "";
        quantity = 0;
    }

    @NonNull
    public String getProductId() {
        return productId;
    }

    public void setProductId(@NonNull String productId) {
        this.productId = productId;
    }

    public long getQuantity() {
        return quantity;
    }

    public void setQuantity(long quantity) {
        this.quantity = quantity;
    }

    @NonNull
    public String getId() {
        return id;
    }

    public void setId(@NonNull String id) {
        this.id = id;
    }

    @NonNull
    public String getBuyerId() {
        return buyerId;
    }

    public void setBuyerId(@NonNull String buyerId) {
        this.buyerId = buyerId;
    }

    @NonNull
    public String getPhone() {
        return phone;
    }

    public void setPhone(@NonNull String phone) {
        this.phone = phone;
    }

    @NonNull
    public String getAnotherPhone() {
        return anotherPhone;
    }

    public void setAnotherPhone(@NonNull String anotherPhone) {
        this.anotherPhone = anotherPhone;
    }

    @NonNull
    public String getAddress() {
        return address;
    }

    public void setAddress(@NonNull String address) {
        this.address = address;
    }

    @NonNull
    public String getName() {
        return name;
    }

    public void setName(@NonNull String name) {
        this.name = name;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
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

    @NonNull
    public String getLocationUri() {
        return locationUri;
    }

    public void setLocationUri(@NonNull String locationUri) {
        this.locationUri = locationUri;
    }

    @NonNull
    @Override
    public String toString() {
        return "Orders{" +
                "productId='" + productId + '\'' +
                ", quantity=" + quantity +
                ", id='" + id + '\'' +
                ", buyerId='" + buyerId + '\'' +
                ", phone='" + phone + '\'' +
                ", anotherPhone='" + anotherPhone + '\'' +
                ", address='" + address + '\'' +
                ", name='" + name + '\'' +
                ", date=" + date +
                ", toggleItemColor='" + toggleItemColor + '\'' +
                ", toggleItemSize='" + toggleItemSize + '\'' +
                ", locationUri='" + locationUri + '\'' +
                '}';
    }
}
