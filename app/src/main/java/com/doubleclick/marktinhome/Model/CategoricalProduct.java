package com.doubleclick.marktinhome.Model;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created By Eslam Ghazy on 5/30/2022
 */
public class CategoricalProduct implements Parcelable {

    private ArrayList<Product> product;
    private String name;
    private String id;


    public CategoricalProduct(ArrayList<Product> product, String name, String id) {
        this.product = product;
        this.name = name;
        this.id = id;
    }

    public CategoricalProduct() {
    }

    protected CategoricalProduct(Parcel in) {
        product = in.createTypedArrayList(Product.CREATOR);
        name = in.readString();
        id = in.readString();
    }

    public static final Creator<CategoricalProduct> CREATOR = new Creator<CategoricalProduct>() {
        @Override
        public CategoricalProduct createFromParcel(Parcel in) {
            return new CategoricalProduct(in);
        }

        @Override
        public CategoricalProduct[] newArray(int size) {
            return new CategoricalProduct[size];
        }
    };

    public ArrayList<Product> getProduct() {
        return product;
    }

    public void setProduct(ArrayList<Product> product) {
        this.product = product;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "CaregoricalProduct{" +
                "product=" + product +
                ", name='" + name + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(product);
        dest.writeString(name);
        dest.writeString(id);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
