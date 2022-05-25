package com.doubleclick.ViewModel;

import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.doubleclick.OrderLisinter;
import com.doubleclick.marktinhome.Model.Cart;
import com.doubleclick.marktinhome.Model.Orders;
import com.doubleclick.marktinhome.Repository.OrdersRepository;

import java.util.ArrayList;

/**
 * Created By Eslam Ghazy on 3/12/2022
 */
public class OrderViewModel extends ViewModel implements OrderLisinter {

    MutableLiveData<ArrayList<Orders>> mutableLiveData = new MutableLiveData<>();

    OrdersRepository ordersRepository = new OrdersRepository(this);

    public OrderViewModel() {
        ordersRepository.getOrders();
    }

    public LiveData<ArrayList<Orders>> getMyOrderLiveData() {
        return mutableLiveData;
    }


    @Override
    public void OnOrder(@Nullable ArrayList<Orders> orders) {
        mutableLiveData.setValue(orders);
    }
}
