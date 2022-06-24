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
    private double quantity;
    @NonNull
    private String id;
    @NonNull
    private String buyerId;
    @NonNull
    private String toggleItemColor;
    @NonNull
    private String toggleItemSize;


    public Cart() {
        productId = "";
        id = "";
        buyerId = "";
        toggleItemColor = "";
        toggleItemSize = "";
    }

    protected Cart(Parcel in) {
        productId = in.readString();
        quantity = in.readDouble();
        id = in.readString();
        buyerId = in.readString();
        toggleItemColor = in.readString();
        toggleItemSize = in.readString();
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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }


    @Override
    public String toString() {
        return "Cart{" +
                "ProductId='" + productId + '\'' +
                ", Quantity='" + quantity + '\'' +
                ", id='" + id + '\'' +
                ", BuyerId='" + buyerId + '\'' +
                '}';
    }


    public double getQuantity() {
        return quantity;
    }

    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }


    @Override
    public boolean equals(@Nullable Object obj) {
        if (obj == null) return false;
        if (obj == this) return true;
        if (!(obj instanceof Cart)) return false;
        Cart cart = (Cart) obj;
        return cart.getId().equals(this.getId());
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
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(productId);
        parcel.writeDouble(quantity);
        parcel.writeString(id);
        parcel.writeString(buyerId);
        parcel.writeString(toggleItemColor);
        parcel.writeString(toggleItemSize);
    }

}
