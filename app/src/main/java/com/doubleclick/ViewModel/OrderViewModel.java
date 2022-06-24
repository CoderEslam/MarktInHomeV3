package com.doubleclick.ViewModel;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.doubleclick.OrderLisinter;
import com.doubleclick.marktinhome.Model.Cart;
import com.doubleclick.marktinhome.Model.Orders;
import com.doubleclick.marktinhome.Model.OrdersDate;
import com.doubleclick.marktinhome.Repository.OrdersRepository;

import java.util.ArrayList;

/**
 * Created By Eslam Ghazy on 3/12/2022
 */
public class OrderViewModel extends ViewModel implements OrderLisinter {

    MutableLiveData<ArrayList<OrdersDate>> mutableLiveData = new MutableLiveData<>();
    MutableLiveData<Long> longMutableLiveData = new MutableLiveData<>();

    OrdersRepository ordersRepository = new OrdersRepository(this);

    public OrderViewModel() {
        ordersRepository.getOrders();
    }

    public LiveData<ArrayList<OrdersDate>> getMyOrderLiveData() {
        return mutableLiveData;
    }

    public LiveData<Long> getMyOrderCountLiveData() {
        return longMutableLiveData;
    }


    @Override
    public void CountOrder(long c) {
        longMutableLiveData.setValue(c);
    }

    @Override
    public void OnOrder(@NonNull ArrayList<OrdersDate> orders) {
        mutableLiveData.setValue(orders);
    }
}
