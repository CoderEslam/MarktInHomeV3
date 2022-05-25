package com.doubleclick.marktinhome.Model;

import java.util.Arrays;
import java.util.List;

/**
 * Created By Eslam Ghazy on 3/12/2022
 */
public class Orders {

    private String productId;
    private double price;
    private long quantity;
    private double lastPrice;
    private String productName;
    private String images;
    private String id;
    private String buyerId;
    private String sellerId;
    private double TotalPrice;
    private String phone;
    private String anotherPhone;
    private String address;
    private String name;
    private long date;
    private String toggleItem;

    public String getLocationUri() {
        return locationUri;
    }

    public void setLocationUri(String locationUri) {
        this.locationUri = locationUri;
    }

    private String locationUri;

    public Orders() {
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }


    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public long getQuantity() {
        return quantity;
    }

    public void setQuantity(long quantity) {
        this.quantity = quantity;
    }

    public double getLastPrice() {
        return lastPrice;
    }

    public void setLastPrice(double lastPrice) {
        this.lastPrice = lastPrice;
    }

    public double getTotalPrice() {
        return TotalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        TotalPrice = totalPrice;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }

    public String getImages() {
        return images;
    }

    public String getOnlyImage() {
        List<String> image = Arrays.asList(images.replace("[", "").replace("]", "").replace(" ", "").trim().split(","));
        return image.get(0);
    }

    public void setImages(String images) {
        this.images = images;
    }

    public String getToggleItem() {
        return toggleItem;
    }

    public void setToggleItem(String toggleItem) {
        this.toggleItem = toggleItem;
    }

}
