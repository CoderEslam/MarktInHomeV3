package com.doubleclick.ViewModel;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.doubleclick.OnCartLisnter;
import com.doubleclick.marktinhome.Model.Cart;
import com.doubleclick.marktinhome.Model.CartData;
import com.doubleclick.marktinhome.Repository.CartRepository;

import java.util.ArrayList;

/**
 * Created By Eslam Ghazy on 3/2/2022
 */
public class CartViewModel extends ViewModel implements OnCartLisnter {

    MutableLiveData<CartData> mutableLiveData = new MutableLiveData<>();

    MutableLiveData<CartData> mutableLiveDataplusone = new MutableLiveData<>();

    MutableLiveData<CartData> mutableLiveDataminsone = new MutableLiveData<>();
    MutableLiveData<CartData> mutableLiveDatadelete = new MutableLiveData<>();

    CartRepository cartRepository = new CartRepository(this);

    public CartViewModel() {
        cartRepository.getCart();
    }


    public LiveData<CartData> CartLiveData() {
        return mutableLiveData;
    }

    public LiveData<CartData> CartAddLiveData() {
        return mutableLiveDataplusone;
    }

    public LiveData<CartData> CartMinsLiveData() {
        return mutableLiveDataminsone;
    }

    public LiveData<CartData> CartDeleteLiveData() {
        return mutableLiveDatadelete;
    }


    @Override
    public void getCart(@NonNull CartData cart) {
        mutableLiveData.setValue(cart);
    }

    @Override
    public void OnAddItemOrder(@Nullable CartData cart, int pos) {
        mutableLiveDataplusone.setValue(cart);
    }

    @Override
    public void OnMinsItemOrder(@Nullable CartData cart, int pos) {
        mutableLiveDataminsone.setValue(cart);
    }

    @Override
    public void OnDeleteItemOrder(@Nullable CartData cart, int pos) {
        mutableLiveDatadelete.setValue(cart);
    }
}
