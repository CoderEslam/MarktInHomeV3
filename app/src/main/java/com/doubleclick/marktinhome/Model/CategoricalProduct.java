package com.doubleclick.marktinhome.Model;

import java.util.ArrayList;

/**
 * Created By Eslam Ghazy on 5/30/2022
 */
public class CategoricalProduct {

    private ArrayList<Product> product;
    private String name;


    public CategoricalProduct(ArrayList<Product> product, String name) {
        this.product = product;
        this.name = name;
    }

    public CategoricalProduct() {
    }

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
}
