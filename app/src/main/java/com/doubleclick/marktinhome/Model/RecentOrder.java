package com.doubleclick.marktinhome.Model;

import java.util.Arrays;
import java.util.List;

/**
 * Created By Eslam Ghazy on 3/12/2022
 */
public class RecentOrder {

    private String productId;
    private double price;
    private int quantity;
    private String productName;
    private String images;
    private String id;
    private String buyerId;
    private String sellerId;
    private double totalPrice;
    private long date;
    private String address;
    private String phone;
    private String anotherPhone;
    private String locationUri;
    private String toggleItem;

    public RecentOrder() {
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

    public String getToggleItem() {
        return toggleItem;
    }

    public void setToggleItem(String toggleItem) {
        this.toggleItem = toggleItem;
    }


    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return "RecentOrder{" +
                "ProductId='" + productId + '\'' +
                ", price=" + price +
                ", Quantity=" + quantity +
                ", productName='" + productName + '\'' +
                ", images='" + images + '\'' +
                ", id='" + id + '\'' +
                ", BuyerId='" + buyerId + '\'' +
                ", SellerId='" + sellerId + '\'' +
                ", TotalPrice=" + totalPrice +
                ", date=" + date +
                ", address='" + address + '\'' +
                ", phone='" + phone + '\'' +
                ", anotherPhone='" + anotherPhone + '\'' +
                ", locationUri='" + locationUri + '\'' +
                ", ToggleItem='" + toggleItem + '\'' +
                '}';
    }
}
