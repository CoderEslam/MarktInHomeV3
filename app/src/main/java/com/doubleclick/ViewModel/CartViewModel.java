package com.doubleclick.ViewModel;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.doubleclick.OnCartLisnter;
import com.doubleclick.marktinhome.Model.Cart;
import com.doubleclick.marktinhome.Repository.CartRepository;

import java.util.ArrayList;

/**
 * Created By Eslam Ghazy on 3/2/2022
 */
public class CartViewModel extends ViewModel implements OnCartLisnter {

    MutableLiveData<Cart> mutableLiveData = new MutableLiveData<>();

    MutableLiveData<Cart> mutableLiveDataplusone = new MutableLiveData<>();

    MutableLiveData<Cart> mutableLiveDataminsone = new MutableLiveData<>();
    MutableLiveData<Cart> mutableLiveDatadelete = new MutableLiveData<>();

    CartRepository cartRepository = new CartRepository(this);

    public CartViewModel() {
        cartRepository.getCart();
    }


    public LiveData<Cart> CartLiveData() {
        return mutableLiveData;
    }

    public LiveData<Cart> CartAddLiveData() {
        return mutableLiveDataplusone;
    }

    public LiveData<Cart> CartMinsLiveData() {
        return mutableLiveDataminsone;
    }

    public LiveData<Cart> CartDeleteLiveData() {
        return mutableLiveDatadelete;
    }


    @Override
    public void getCart(@NonNull Cart cart) {
        mutableLiveData.setValue(cart);
    }

    @Override
    public void OnAddItemOrder(@Nullable Cart cart, int pos) {
        mutableLiveDataplusone.setValue(cart);
    }

    @Override
    public void OnMinsItemOrder(@Nullable Cart cart, int pos) {
        mutableLiveDataminsone.setValue(cart);
    }

    @Override
    public void OnDeleteItemOrder(@Nullable Cart cart, int pos) {
        mutableLiveDatadelete.setValue(cart);
    }
}
