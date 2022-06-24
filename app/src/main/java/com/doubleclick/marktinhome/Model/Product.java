package com.doubleclick.marktinhome.Model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import java.io.Serializable;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

import kotlinx.android.parcel.Parcelize;

/**
 * Created By Eslam Ghazy on 3/1/2022
 */
@Parcelize
public class Product implements Parcelable {

    @NonNull
    private String productId;
    private double price;
    @NonNull
    private String description;
    private long date;
    @NonNull
    private String adminId;
    @NonNull
    private String productName;
    private double lastPrice;
    @NonNull
    private String tradeMark;
    @NonNull
    private String parentCategoryName;
    @NonNull
    private String childCategoryName;
    @NonNull
    private String parentCategoryId;
    @NonNull
    private String childCategoryId;
    private double discount;
    @NonNull
    private String keywords;
    @NonNull
    private String images;
    @NonNull
    private String sizes;
    @NonNull
    private String colors;
    @NonNull
    private String colorsName;
    @NonNull
    private String type; // for new or use
    @NonNull
    private String totalPercentage;


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
        this.discount = discount;
        this.keywords = keywords;
        this.images = images;
        this.sizes = sizes;

    }

    public Product(String productId, double price, String description, long date, String adminId, String productName, double lastPrice, String tradeMark, String parentCategoryName, String childCategoryName, String parentCategoryId, String childCategoryId, int totalRating, double discount, String keywords, String images, String sizes, String colors, String colorsName, String type) {
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
        this.discount = discount;
        this.keywords = keywords;
        this.images = images;
        this.sizes = sizes;
        this.colors = colors;
        this.colorsName = colorsName;
        this.type = type;
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
        discount = in.readDouble();
        keywords = in.readString();
        images = in.readString();
        sizes = in.readString();
        colors = in.readString();
        colorsName = in.readString();
        type = in.readString();
        totalPercentage = in.readString();
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
        dest.writeDouble(discount);
        dest.writeString(keywords);
        dest.writeString(images);
        dest.writeString(sizes);
        dest.writeString(colors);
        dest.writeString(colorsName);
        dest.writeString(type);
        dest.writeString(totalPercentage);
    }

    @Override
    public int describeContents() {
        return 0;
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

    public String getOnlyImage() {
        try {
            List<String> image = Arrays.asList(getImages().replace("[", "").replace("]", "").replace(" ", "").trim().split(","));
            return image.get(0);
        } catch (NullPointerException e) {
            return "";
        }
    }


    public Product() {
        productId = "";
        description = "";
        adminId = "";
        type = "";
        productName = "";
        tradeMark = "";
        parentCategoryName = "";
        childCategoryName = "";
        parentCategoryId = "";
        childCategoryId = "";
        keywords = "";
        images = "";
        sizes = "";
        colors = "";
        colorsName = "";
        totalPercentage = "0.0";
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


    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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
                ", discount=" + discount +
                ", keywords='" + keywords + '\'' +
                ", images='" + images + '\'' +
                ", sizes='" + sizes + '\'' +
                ", colors=" + colors +
                ", colorsName='" + colorsName + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Product)) return false;
        Product product = (Product) o;
        return Double.compare(product.getPrice(), getPrice()) == 0 && getDate() == product.getDate() && Double.compare(product.getLastPrice(), getLastPrice()) == 0 && Double.compare(product.getDiscount(), getDiscount()) == 0 && getProductId().equals(product.getProductId()) && getDescription().equals(product.getDescription()) && getAdminId().equals(product.getAdminId()) && getProductName().equals(product.getProductName()) && getTradeMark().equals(product.getTradeMark()) && getParentCategoryName().equals(product.getParentCategoryName()) && getChildCategoryName().equals(product.getChildCategoryName()) && getParentCategoryId().equals(product.getParentCategoryId()) && getChildCategoryId().equals(product.getChildCategoryId()) && getKeywords().equals(product.getKeywords()) && getImages().equals(product.getImages()) && getSizes().equals(product.getSizes()) && getColors().equals(product.getColors()) && getColorsName().equals(product.getColorsName()) && getType().equals(product.getType());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getProductId(), getPrice(), getDescription(), getDate(), getAdminId(), getProductName(), getLastPrice(), getTradeMark(), getParentCategoryName(), getChildCategoryName(), getParentCategoryId(), getChildCategoryId(), getDiscount(), getKeywords(), getImages(), getSizes(), getColors(), getColorsName(), getType());
    }

    @NonNull
    public String getTotalPercentage() throws NumberFormatException {
        return totalPercentage;
    }

    public void setTotalPercentage(@NonNull String totalPercentage) {
        this.totalPercentage = totalPercentage;
    }
}

