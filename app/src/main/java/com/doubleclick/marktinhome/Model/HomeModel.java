package com.doubleclick.marktinhome.Model;

import com.doubleclick.OnItem;
import com.doubleclick.OnProduct;
import com.doubleclick.Tradmarkinterface;
import com.doubleclick.ViewMore;

import java.util.ArrayList;

/**
 * Created By Eslam Ghazy on 3/8/2022
 */
public class HomeModel {

    public static final int TopCategory = 0;
    public static final int Advertisement = 1;
    public static final int TopDeal = 2;
    public static final int Products = 3;
    public static final int Trademarks = 4;
    public static final int RecentSearch = 5;
    public static final int Categorical = 6;
    private ArrayList<ParentCategory> parentCategories;
    private ArrayList<Advertisement> advertisements;
    private ArrayList<Product> productsTopDaels;
    private ArrayList<Product> productArrayList;
    private ArrayList<CategoricalProduct> categoricalProducts;
    private int type;
    private OnItem onItemPerantTop;
    private OnProduct onProduct;
    public ViewMore viewMore;

    public HomeModel(ArrayList<CategoricalProduct> categoricalProducts, OnProduct onProduct, int type) {
        this.categoricalProducts = categoricalProducts;
        this.onProduct = onProduct;
        this.type = type;
    }

    public ArrayList<Product> getProductsRecentSearch() {
        return productsRecentSearch;
    }

    public void setProductsRecentSearch(ArrayList<Product> productsRecentSearch) {
        this.productsRecentSearch = productsRecentSearch;
    }

    private ArrayList<Product> productsRecentSearch;

    public HomeModel(ArrayList<Product> productsTopDaels, int type, OnProduct onProduct, ViewMore viewMore) {
        this.productsTopDaels = productsTopDaels;
        this.type = type;
        this.onProduct = onProduct;
        this.viewMore = viewMore;
    }

    public HomeModel(int type, ArrayList<Product> productsRecentSearch, OnProduct onProduct, ViewMore viewMore, int fake) {
        this.productsRecentSearch = productsRecentSearch;
        this.type = type;
        this.onProduct = onProduct;
        this.viewMore = viewMore;
    }

    public Tradmarkinterface getTradmarkinterface() {
        return tradmarkinterface;
    }

    public void setTradmarkinterface(Tradmarkinterface tradmarkinterface) {
        this.tradmarkinterface = tradmarkinterface;
    }

    private Tradmarkinterface tradmarkinterface;

    public HomeModel(ArrayList<Trademark> trademarks, int type, Tradmarkinterface tradmarkinterface) {
        this.trademarkArrayList = trademarks;
        this.type = type;
        this.tradmarkinterface = tradmarkinterface;
    }


    public ArrayList<Trademark> getTrademarkArrayList() {
        return trademarkArrayList;
    }

    public void setTrademarkArrayList(ArrayList<Trademark> trademarkArrayList) {
        this.trademarkArrayList = trademarkArrayList;
    }

    private ArrayList<Trademark> trademarkArrayList;

    public HomeModel(ArrayList<Advertisement> advertisements, int type) {
        this.advertisements = advertisements;
        this.type = type;
    }

    public OnItem getOnItemPerantTop() {
        return onItemPerantTop;
    }

    public void setOnItemPerantTop(OnItem onItemPerantTop) {
        this.onItemPerantTop = onItemPerantTop;
    }

    public OnProduct getOnProduct() {
        return onProduct;
    }

    public void setOnProduct(OnProduct onProduct) {
        this.onProduct = onProduct;
    }

    public ArrayList<Product> getProductArrayList() {
        return productArrayList;
    }

    public void setProductArrayList(ArrayList<Product> productArrayList) {
        this.productArrayList = productArrayList;
    }


    public ArrayList<CategoricalProduct> getCategoricalProducts() {
        return categoricalProducts;
    }

    public void setCategoricalProducts(ArrayList<CategoricalProduct> categoricalProducts) {
        this.categoricalProducts = categoricalProducts;
    }


    public HomeModel(ArrayList<ParentCategory> parentCategories, int type, OnItem onItemPerantTop) {
        this.parentCategories = parentCategories;
        this.type = type;
        this.onItemPerantTop = onItemPerantTop;
    }

    public HomeModel(ArrayList<Product> products, int type, OnProduct onProduct) {
        this.productArrayList = products;
        this.type = type;
        this.onProduct = onProduct;
    }

    public static int getTopCategory() {
        return TopCategory;
    }

    public ArrayList<ParentCategory> getParentCategories() {
        return parentCategories;
    }

    public void setParentCategories(ArrayList<ParentCategory> parentCategories) {
        this.parentCategories = parentCategories;
    }

    public static int getAdvertisement() {
        return Advertisement;
    }

    public ArrayList<Advertisement> getAdvertisements() {
        return advertisements;
    }

    public void setAdvertisements(ArrayList<Advertisement> advertisements) {
        this.advertisements = advertisements;
    }

    public static int getTopDeal() {
        return TopDeal;
    }

    public ArrayList<Product> getProductsTopDaels() {
        return productsTopDaels;
    }

    public void setProductsTopDaels(ArrayList<Product> productsTopDaels) {
        this.productsTopDaels = productsTopDaels;
    }


    public static int getTrademarks() {
        return Trademarks;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public ViewMore getViewMore() {
        return viewMore;
    }

    public void setViewMore(ViewMore viewMore) {
        this.viewMore = viewMore;
    }
}
