package com.doubleclick.ViewModel;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.doubleclick.RecentSearchInterface;
import com.doubleclick.marktinhome.Model.Product;
import com.doubleclick.marktinhome.Model.RecentSearch;
import com.doubleclick.marktinhome.Repository.RecentSearchRepository;

import java.util.ArrayList;
import java.util.List;

/**
 * Created By Eslam Ghazy on 3/11/2022
 */
public class RecentSearchViewModel extends ViewModel implements RecentSearchInterface {

    MutableLiveData<ArrayList<Product>> recentSearchMutableLiveDataOneTime = new MutableLiveData<>();
    MutableLiveData<RecentSearch> recentSearch = new MutableLiveData<>();
    RecentSearchRepository recentSearchRepository = new RecentSearchRepository(this);

    public RecentSearchViewModel() {
        recentSearchRepository.getLastSearchOneTime();
        recentSearchRepository.getRecentSearch();
    }

    public LiveData<ArrayList<Product>> getLastSearchListLiveDataOneTime() {
        return recentSearchMutableLiveDataOneTime;
    }

    public LiveData<RecentSearch> getRecentSearchLiveData() {
        return recentSearch;
    }


    @Override
    public void getRecentSearch(@NonNull RecentSearch recent) {
        recentSearch.setValue(recent);
    }



    @Override
    public void getLastListSearchAboutProductOneTime(@Nullable ArrayList<Product> recentSearchaboutProduct) {
        recentSearchMutableLiveDataOneTime.setValue(recentSearchaboutProduct);
    }
}
