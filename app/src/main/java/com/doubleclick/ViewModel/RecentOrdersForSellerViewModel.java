package com.doubleclick.ViewModel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.doubleclick.marktinhome.Model.RecentOrder;
import com.doubleclick.marktinhome.Repository.RecentOrdersForSellerRepository;

import java.util.ArrayList;

/**
 * Created By Eslam Ghazy on 3/23/2022
 */
public class RecentOrdersForSellerViewModel extends ViewModel implements RecentOrdersForSellerRepository.recentOrder {

    RecentOrdersForSellerRepository recentOrdersForSellerRepository = new RecentOrdersForSellerRepository(this);
    MutableLiveData<ArrayList<RecentOrder>> mutableLiveData = new MutableLiveData<>();
    MutableLiveData<ArrayList<ArrayList<ArrayList<Integer>>>> listMutableLiveDataYear = new MutableLiveData<>();
    MutableLiveData<Double> mutableLiveDataMoney = new MutableLiveData<>();

    public RecentOrdersForSellerViewModel() {
        recentOrdersForSellerRepository.getRecentOrders();
    }

    public LiveData<ArrayList<RecentOrder>> getRecentOrderLiveData() {
        return mutableLiveData;
    }

    public LiveData<ArrayList<ArrayList<ArrayList<Integer>>>> getYearLiveData() {
        return listMutableLiveDataYear;
    }

    public LiveData<Double> getMoneyLiveData() {
        return mutableLiveDataMoney;
    }

    @Override
    public void recentOrder(ArrayList<RecentOrder> recentOrderArrayList) {
        mutableLiveData.setValue(recentOrderArrayList);
    }

    @Override
    public void ListOfYear(ArrayList<ArrayList<ArrayList<Integer>>> years) {
        listMutableLiveDataYear.setValue(years);
    }

    @Override
    public void recentMoney(double money) {
        mutableLiveDataMoney.setValue(money);
    }


}
