package com.doubleclick.ViewModel;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.doubleclick.FavoriteInter;
import com.doubleclick.marktinhome.Model.Favorite;
import com.doubleclick.marktinhome.Model.Product;
import com.doubleclick.marktinhome.Repository.FavoriteRepository;

import java.util.ArrayList;

/**
 * Created By Eslam Ghazy on 6/23/2022
 */
public class FavoriteViewModel extends ViewModel implements FavoriteInter {


    MutableLiveData<Boolean> booleanMutableLiveData = new MutableLiveData<>();
    MutableLiveData<Long> longMutableLiveData = new MutableLiveData<>();
    MutableLiveData<ArrayList<Product>> arrayListMutableLiveData = new MutableLiveData<>();
    FavoriteRepository favoriteRepository = new FavoriteRepository(this);

    public FavoriteViewModel() {
        favoriteRepository.getMyFavorite();
    }

    public LiveData<Boolean> isFavorite(String id) {
        favoriteRepository.IsFavorite(id);
        return booleanMutableLiveData;
    }


    public LiveData<ArrayList<Product>> getmyFavorite() {
        return arrayListMutableLiveData;
    }

    public LiveData<Long> getCount() {
        return longMutableLiveData;
    }

    @Override
    public void isFavorite(boolean b) {
        booleanMutableLiveData.setValue(b);
    }

    @Override
    public void count(long c) {
        longMutableLiveData.setValue(c);
    }

    @Override
    public void myFavorite(@NonNull ArrayList<Product> favorites) {
        arrayListMutableLiveData.setValue(favorites);
    }
}
