package com.doubleclick.ViewModel;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.doubleclick.RecentOrderInterface;
import com.doubleclick.marktinhome.Model.RecentOrder;
import com.doubleclick.marktinhome.Model.RecentOrderData;
import com.doubleclick.marktinhome.Repository.RecentOrderRepository;

import java.util.ArrayList;

/**
 * Created By Eslam Ghazy on 3/12/2022
 */
public class RecentOrderViewModel extends ViewModel implements RecentOrderInterface {

    MutableLiveData<ArrayList<RecentOrderData>> arrayListMutableLiveData = new MutableLiveData<>();

    RecentOrderRepository recentOrderRepository = new RecentOrderRepository(this);

    public RecentOrderViewModel() {
        recentOrderRepository.getRecentOrder();
    }

    public LiveData<ArrayList<RecentOrderData>> getMyRecentOrderLiveData() {
        return arrayListMutableLiveData;
    }

    @Override
    public void getMyRecentOrder(@NonNull ArrayList<RecentOrderData> recentOrder) {
        arrayListMutableLiveData.setValue(recentOrder);
    }
}
