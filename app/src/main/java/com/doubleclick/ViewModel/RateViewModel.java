package com.doubleclick.ViewModel;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.doubleclick.Rateing;
import com.doubleclick.marktinhome.Repository.RateingRepository;

import java.util.ArrayList;

/**
 * Created By Eslam Ghazy on 3/6/2022
 */
public class RateViewModel extends ViewModel implements Rateing {


    RateingRepository rateingRepository = new RateingRepository(this);
    MutableLiveData<String> rateMutableLiveData = new MutableLiveData<>();
    MutableLiveData<ArrayList<String>> listMutableLiveData = new MutableLiveData<>();


    public RateViewModel() {

    }

    public LiveData<String> getMyRate(String myId, String productId) {
        rateingRepository.getMyRate(myId, productId);
        return rateMutableLiveData;
    }

    public LiveData<ArrayList<String>> getAllRate(String productId) {
        rateingRepository.getAllRate(productId);
        return listMutableLiveData;
    }


    @Override
    public void MyRate(@NonNull String rate) {
        rateMutableLiveData.setValue(rate);
    }

    @Override
    public void AllRate(@NonNull ArrayList<String> arrayRate) {
        listMutableLiveData.setValue(arrayRate);
    }
}
