package com.doubleclick.marktinhome.Model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.Arrays;
import java.util.List;

/**
 * Created By Eslam Ghazy on 3/1/2022
 */
public class Cart implements Parcelable {

    @NonNull
    private String productId;
    private double price;
    private double quantity;
    private double lastPrice;
    @NonNull
    private String productName;
    @NonNull
    private String images;
    @NonNull
    private String id;
    @NonNull
    private String buyerId;
    @NonNull
    private String sellerId;
    private double totalPrice;
    @NonNull
    private String toggleItem;

    public String getToggleItem() {
        return toggleItem;
    }

    public void setToggleItem(String toggleItem) {
        this.toggleItem = toggleItem;
    }


    protected Cart(Parcel in) {
        productId = in.readString();
        price = in.readLong();
        quantity = in.readLong();
        lastPrice = in.readDouble();
        productName = in.readString();
        images = in.readString();
        id = in.readString();
        buyerId = in.readString();
        sellerId = in.readString();
        totalPrice = in.readLong();
    }

    public static final Creator<Cart> CREATOR = new Creator<Cart>() {
        @Override
        public Cart createFromParcel(Parcel in) {
            return new Cart(in);
        }

        @Override
        public Cart[] newArray(int size) {
            return new Cart[size];
        }
    };

    public Cart() {
        productId = "";
        productName = "";
        images = "";
        id = "";
        buyerId = "";
        sellerId = "";
        toggleItem = "";
    }

    public String getSellerId() {
        return sellerId;
    }

    public void setSellerId(String sellerId) {
        this.sellerId = sellerId;
    }


    public String getBuyerId() {
        return buyerId;
    }

    public void setBuyerId(String buyerId) {
        this.buyerId = buyerId;
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


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(productId);
        dest.writeDouble(price);
        dest.writeDouble(quantity);
        dest.writeDouble(lastPrice);
        dest.writeString(productName);
        dest.writeString(images);
        dest.writeString(id);
        dest.writeString(buyerId);
        dest.writeString(sellerId);
        dest.writeDouble(totalPrice);
    }

    @Override
    public String toString() {
        return "Cart{" +
                "ProductId='" + productId + '\'' +
                ", price='" + price + '\'' +
                ", Quantity='" + quantity + '\'' +
                ", lastPrice='" + lastPrice + '\'' +
                ", productName='" + productName + '\'' +
                ", images='" + images + '\'' +
                ", id='" + id + '\'' +
                ", BuyerId='" + buyerId + '\'' +
                ", SellerId='" + sellerId + '\'' +
                ", TotalPrice='" + totalPrice + '\'' +
                '}';
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getQuantity() {
        return quantity;
    }

    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }

    public double getLastPrice() {
        return lastPrice;
    }

    public void setLastPrice(double lastPrice) {
        this.lastPrice = lastPrice;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
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

    @Override
    public boolean equals(@Nullable Object obj) {
        if (obj == null) return false;
        if (obj == this) return true;
        if (!(obj instanceof Cart)) return false;
        Cart cart = (Cart) obj;
        return cart.getId().equals(this.getId());
    }
}
