package com.doubleclick.ViewModel;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.doubleclick.ViewMore;
import com.doubleclick.marktinhome.Model.Product;
import com.doubleclick.marktinhome.Repository.ViewMoreRepository;

import java.util.ArrayList;

/**
 * Created By Eslam Ghazy on 5/5/2022
 */
public class LoadMoreViewModel extends ViewModel implements ViewMore {


    ViewMoreRepository viewMoreRepository = new ViewMoreRepository(this);
    MutableLiveData<Product> productMutableLiveData = new MutableLiveData<>();

    public LoadMoreViewModel() {
        viewMoreRepository.LoadMore();
    }

    public LiveData<Product> LoadMoreLive() {
        return productMutableLiveData;
    }

    @Override
    public void getViewMore(@NonNull ArrayList<Product> product) {

    }

    @Override
    public void getViewMore(@NonNull Product product) {
        productMutableLiveData.setValue(product);

    }
}
