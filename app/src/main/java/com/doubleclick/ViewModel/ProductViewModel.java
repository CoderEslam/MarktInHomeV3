package com.doubleclick.ViewModel;

import static com.doubleclick.marktinhome.BaseApplication.isNetworkConnected;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.doubleclick.Products;
import com.doubleclick.marktinhome.Model.CategoricalProduct;
import com.doubleclick.marktinhome.Model.ChildCategory;
import com.doubleclick.marktinhome.Model.ClassificationPC;
import com.doubleclick.marktinhome.Model.ParentCategory;
import com.doubleclick.marktinhome.Model.Product;
import com.doubleclick.marktinhome.Repository.ProductRepository;

import java.util.ArrayList;

/**
 * Created By Eslam Ghazy on 3/2/2022
 */
public class ProductViewModel extends ViewModel implements Products {


    private MutableLiveData<ArrayList<ArrayList<ArrayList<Product>>>> mutableLiveData = new MutableLiveData<>();
    private MutableLiveData<ArrayList<ParentCategory>> parentCategory = new MutableLiveData<>();
    private MutableLiveData<ArrayList<ChildCategory>> childCategory = new MutableLiveData<>();
    private MutableLiveData<ArrayList<ChildCategory>> childrenCategory = new MutableLiveData<>();
    private MutableLiveData<ArrayList<Product>> products = new MutableLiveData<>();
    private MutableLiveData<ArrayList<Product>> ProductWithTrademark = new MutableLiveData<>();
    private MutableLiveData<ArrayList<Product>> mutableLiveDataQueryProducts = new MutableLiveData<>();
    private MutableLiveData<ArrayList<Product>> mutableLiveDataFilterByParent = new MutableLiveData<>();
    private MutableLiveData<ArrayList<Product>> mutableLiveDataFilterByChild = new MutableLiveData<>();
    private MutableLiveData<ArrayList<Product>> mutableLiveDataTopDeals = new MutableLiveData<>();
    private MutableLiveData<ArrayList<Product>> mutableLiveDataidProcuct = new MutableLiveData<>();
    private MutableLiveData<ArrayList<ClassificationPC>> classificationPC = new MutableLiveData<>();
    private MutableLiveData<ArrayList<CategoricalProduct>> mutableLiveDatacaregoricalProduct = new MutableLiveData<>();

    private ProductRepository productRepository = new ProductRepository(this);

    public ProductViewModel() {
        if (isNetworkConnected()) {
            productRepository.getParents();
            productRepository.getChild();
//            productRepository.getProduct();
            productRepository.TopDeals();
//            productRepository.LoadCategorical();
        }
    }


    public void getChildren(String child) {
        if (isNetworkConnected()) {
            productRepository.getChildren(child);
        }
    }

    public void getSearch(String search) {
        if (isNetworkConnected()) {
            productRepository.getQuery(search);
        }
    }


    public void getSearchByChild(String ChildId) {
        if (isNetworkConnected()) {
            productRepository.getSearchByChild(ChildId);
        }
    }

    public void getSearchByIdProduct(String idProduct) {
        productRepository.getProductById(idProduct);
    }

    public LiveData<ArrayList<Product>> getSearchByIdProductLiveData() {
        return mutableLiveDataidProcuct;
    }

    public void ProductWithTrademark(String name) {
        productRepository.ProductWithTrademark(name);
    }

    public void FilterByParent(String patentId) {
        productRepository.FilterByParent(patentId);
    }

    // run in constractor
    public void RunTopDeals() {
        productRepository.TopDeals();
    }

    public LiveData<ArrayList<Product>> getTopDealsLiveData() {
        return mutableLiveDataTopDeals;
    }

    public LiveData<ArrayList<Product>> getSearchLiveData() {
        return mutableLiveDataQueryProducts;

    }

    public LiveData<ArrayList<ArrayList<ArrayList<Product>>>> getArranged() {
        return mutableLiveData;
    }

    public LiveData<ArrayList<ParentCategory>> getParent() {
        return parentCategory;
    }

    public LiveData<ArrayList<ChildCategory>> getChildren() {
        return childrenCategory;
    }

    public LiveData<ArrayList<Product>> getProduct() {
        return products;
    }

    public LiveData<ArrayList<ClassificationPC>> getClassificationPC() {
        return classificationPC;
    }

    public LiveData<ArrayList<Product>> FilterByChildLiveDate() {
        return mutableLiveDataFilterByChild;
    }

    public LiveData<ArrayList<Product>> FilterByParentLiveDate() {
        return mutableLiveDataFilterByParent;
    }

    public LiveData<ArrayList<Product>> ProductWithTrademarkLiveDate() {
        return ProductWithTrademark;
    }

    public LiveData<ArrayList<CategoricalProduct>> getCategorical() {
        return mutableLiveDatacaregoricalProduct;
    }


    @Override
    public void product(ArrayList<ArrayList<ArrayList<Product>>> products) {
        mutableLiveData.setValue(products);
    }


    @Override
    public void Parentproduct(ArrayList<ParentCategory> Parentproduct) {
        parentCategory.setValue(Parentproduct);
    }

    @Override
    public void Childproduct(ArrayList<ChildCategory> Childproduct) {
        childCategory.setValue(Childproduct);

    }

    @Override
    public void Childrenproduct(@Nullable ArrayList<ChildCategory> Childproduct) {
        childrenCategory.setValue(Childproduct);
    }

    @Override
    public void getProduct(@Nullable ArrayList<Product> Product) {
        products.setValue(Product);
    }

    @Override
    public void getClassificationPC(@Nullable ArrayList<ClassificationPC> Product) {
        classificationPC.setValue(Product);
    }

    @Override
    public void getQueryProducts(@Nullable ArrayList<Product> QueryProducts) {
        mutableLiveDataQueryProducts.setValue(QueryProducts);
    }

    @Override
    public void getQueryByParents(@Nullable ArrayList<Product> QueryProducts) {
        mutableLiveDataFilterByParent.setValue(QueryProducts);
    }

    @Override
    public void getQueryByChild(@Nullable ArrayList<Product> QueryProducts) {
        mutableLiveDataFilterByChild.setValue(QueryProducts);
    }

    @Override
    public void getProductWithTrademark(@Nullable ArrayList<Product> productWithTrademark) {
        ProductWithTrademark.setValue(productWithTrademark);
    }

    @Override
    public void getProductTopDeals(@Nullable ArrayList<Product> topDeals) {
        mutableLiveDataTopDeals.setValue(topDeals);
    }


    @Override
    public void getProductById(@Nullable ArrayList<Product> productById) {
        mutableLiveDataidProcuct.setValue(productById);
    }


    @Override
    public void loadCategorical(@NonNull ArrayList<CategoricalProduct> categoricalProduct) {
        mutableLiveDatacaregoricalProduct.setValue(categoricalProduct);
    }
}
