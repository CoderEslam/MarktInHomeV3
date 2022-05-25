package com.doubleclick.marktinhome.Model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import java.io.Serializable;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import kotlinx.android.parcel.Parcelize;

/**
 * Created By Eslam Ghazy on 3/1/2022
 */
@Parcelize
public class Product implements Parcelable {

    private String productId;
    private double price;
    private String description;
    private long date;
    private String adminId;
    private String productName;
    private double lastPrice;
    private String tradeMark;
    private String parentCategoryName;
    private String childCategoryName;
    private String parentCategoryId;
    private String childCategoryId;
    private int totalRating;
    private double discount;
    private String keywords;
    private String images;
    private String sizes;
    private String colors;
    private String colorsName;
    private float ratingSeller;


    public Product(String productId, double price, String description, long date, String adminId, String productName, double lastPrice, String tradeMark, String parentCategoryName, String childCategoryName, String parentCategoryId, String childCategoryId, int totalRating, double discount, String keywords, String images, String sizes, float ratingSeller) {
        this.productId = productId;
        this.price = price;
        this.description = description;
        this.date = date;
        this.adminId = adminId;
        this.productName = productName;
        this.lastPrice = lastPrice;
        this.tradeMark = tradeMark;
        this.parentCategoryName = parentCategoryName;
        this.childCategoryName = childCategoryName;
        this.parentCategoryId = parentCategoryId;
        this.childCategoryId = childCategoryId;
        this.totalRating = totalRating;
        this.discount = discount;
        this.keywords = keywords;
        this.images = images;
        this.sizes = sizes;
        this.ratingSeller = ratingSeller;
    }


    protected Product(Parcel in) {
        productId = in.readString();
        price = in.readDouble();
        description = in.readString();
        date = in.readLong();
        adminId = in.readString();
        productName = in.readString();
        lastPrice = in.readDouble();
        tradeMark = in.readString();
        parentCategoryName = in.readString();
        childCategoryName = in.readString();
        parentCategoryId = in.readString();
        childCategoryId = in.readString();
        totalRating = in.readInt();
        discount = in.readDouble();
        keywords = in.readString();
        images = in.readString();
        sizes = in.readString();
        colors = in.readString();
        colorsName = in.readString();
        ratingSeller = in.readFloat();
    }

    public static final Creator<Product> CREATOR = new Creator<Product>() {
        @Override
        public Product createFromParcel(Parcel in) {
            return new Product(in);
        }

        @Override
        public Product[] newArray(int size) {
            return new Product[size];
        }
    };

    public Product(String productId, double price, String description, long date, String adminId, String productName, double lastPrice, String tradeMark, String parentCategoryName, String childCategoryName, String parentCategoryId, String childCategoryId, int totalRating, double discount, String keywords, String images, String sizes, String colors, String colorsName, float ratingSeller) {
        this.productId = productId;
        this.price = price;
        this.description = description;
        this.date = date;
        this.adminId = adminId;
        this.productName = productName;
        this.lastPrice = lastPrice;
        this.tradeMark = tradeMark;
        this.parentCategoryName = parentCategoryName;
        this.childCategoryName = childCategoryName;
        this.parentCategoryId = parentCategoryId;
        this.childCategoryId = childCategoryId;
        this.totalRating = totalRating;
        this.discount = discount;
        this.keywords = keywords;
        this.images = images;
        this.sizes = sizes;
        this.colors = colors;
        this.colorsName = colorsName;
        this.ratingSeller = ratingSeller;
    }

    public String getOnlyImage() {
        try {
            List<String> image = Arrays.asList(getImages().replace("[", "").replace("]", "").replace(" ", "").trim().split(","));
            return image.get(0);
        } catch (NullPointerException e) {
            return "";
        }
    }


    public Product() {
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(productId);
        dest.writeDouble(price);
        dest.writeString(description);
        dest.writeLong(date);
        dest.writeString(adminId);
        dest.writeString(productName);
        dest.writeDouble(lastPrice);
        dest.writeString(tradeMark);
        dest.writeString(parentCategoryName);
        dest.writeString(childCategoryName);
        dest.writeString(parentCategoryId);
        dest.writeString(childCategoryId);
        dest.writeInt(totalRating);
        dest.writeDouble(discount);
        dest.writeString(keywords);
        dest.writeString(images);
        dest.writeString(sizes);
        dest.writeString(colors);
        dest.writeString(colorsName);
        dest.writeFloat(ratingSeller);
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }

    public String getAdminId() {
        return adminId;
    }

    public void setAdminId(String adminId) {
        this.adminId = adminId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public double getLastPrice() {
        return lastPrice;
    }

    public void setLastPrice(double lastPrice) {
        this.lastPrice = lastPrice;
    }

    public String getTradeMark() {
        return tradeMark;
    }

    public void setTradeMark(String tradeMark) {
        this.tradeMark = tradeMark;
    }

    public String getParentCategoryName() {
        return parentCategoryName;
    }

    public void setParentCategoryName(String parentCategoryName) {
        this.parentCategoryName = parentCategoryName;
    }

    public String getChildCategoryName() {
        return childCategoryName;
    }

    public void setChildCategoryName(String childCategoryName) {
        this.childCategoryName = childCategoryName;
    }

    public String getParentCategoryId() {
        return parentCategoryId;
    }

    public void setParentCategoryId(String parentCategoryId) {
        this.parentCategoryId = parentCategoryId;
    }

    public String getChildCategoryId() {
        return childCategoryId;
    }

    public void setChildCategoryId(String childCategoryId) {
        this.childCategoryId = childCategoryId;
    }

    public int getTotalRating() {
        return totalRating;
    }

    public void setTotalRating(int totalRating) {
        this.totalRating = totalRating;
    }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }

    public String getKeywords() {
        return keywords;
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }

    public String getImages() {
        return images;
    }

    public void setImages(String images) {
        this.images = images;
    }

    public String getSizes() {
        return sizes;
    }

    public void setSizes(String sizes) {
        this.sizes = sizes;
    }

    public String getColors() {
        return colors;
    }

    public void setColors(String colors) {
        this.colors = colors;
    }

    public String getColorsName() {
        return colorsName;
    }

    public void setColorsName(String colorsName) {
        this.colorsName = colorsName;
    }

    public float getRatingSeller() {
        return ratingSeller;
    }

    public void setRatingSeller(float ratingSeller) {
        this.ratingSeller = ratingSeller;
    }

    @Override
    public String toString() {
        return "Product{" +
                "productId='" + productId + '\'' +
                ", price=" + price +
                ", description='" + description + '\'' +
                ", date=" + date +
                ", adminId='" + adminId + '\'' +
                ", productName='" + productName + '\'' +
                ", lastPrice=" + lastPrice +
                ", tradeMark='" + tradeMark + '\'' +
                ", parentCategoryName='" + parentCategoryName + '\'' +
                ", childCategoryName='" + childCategoryName + '\'' +
                ", parentCategoryId='" + parentCategoryId + '\'' +
                ", childCategoryId='" + childCategoryId + '\'' +
                ", totalRating=" + totalRating +
                ", discount=" + discount +
                ", keywords='" + keywords + '\'' +
                ", images='" + images + '\'' +
                ", sizes='" + sizes + '\'' +
                ", colors=" + colors +
                ", colorsName='" + colorsName + '\'' +
                ", ratingSeller=" + ratingSeller +
                '}';
    }
}

