package com.doubleclick.ViewModel;

import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.doubleclick.Rateing;
import com.doubleclick.marktinhome.Model.Rate;
import com.doubleclick.marktinhome.Repository.RateingRepository;

import java.util.ArrayList;

/**
 * Created By Eslam Ghazy on 3/6/2022
 */
public class RateViewModel extends ViewModel implements Rateing {


    RateingRepository rateingRepository = new RateingRepository(this);
    MutableLiveData<Rate> rateMutableLiveData = new MutableLiveData<>();
    MutableLiveData<ArrayList<Rate>> listMutableLiveData = new MutableLiveData<>();


    public RateViewModel() {

    }

    public void getMyRate(String myId, String productId) {
        rateingRepository.getMyRate(myId, productId);
    }

    public void getAllRate(String productId) {
        rateingRepository.getAllRate(productId);
    }

    public LiveData<Rate> getMyRateing() {
        return rateMutableLiveData;
    }

    public LiveData<ArrayList<Rate>> getAllRateing() {
        return listMutableLiveData;
    }


    @Override
    public void MyRate(@Nullable Rate rate) {
        rateMutableLiveData.setValue(rate);
    }

    @Override
    public void AllRate(@Nullable ArrayList<Rate> arrayRate) {
        listMutableLiveData.setValue(arrayRate);
    }
}
