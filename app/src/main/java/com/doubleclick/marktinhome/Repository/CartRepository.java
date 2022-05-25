package com.doubleclick.marktinhome.Repository;

import static com.doubleclick.marktinhome.BaseApplication.isNetworkConnected;
import static com.doubleclick.marktinhome.Model.Constantes.CART;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.doubleclick.OnCartLisnter;
import com.doubleclick.marktinhome.Model.Cart;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Objects;

/**
 * Created By Eslam Ghazy on 3/2/2022
 */
public class CartRepository extends BaseRepository {

    private OnCartLisnter onCartLisnter;

    public CartRepository(OnCartLisnter onCartLisnter) {
        this.onCartLisnter = onCartLisnter;
    }

    // for User Orders
    public void getCart() {
        reference.child(CART).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                try {
                    if (isNetworkConnected()) {
                        if (snapshot.exists()) {
                            Cart cart = snapshot.getValue(Cart.class);
                            if (Objects.requireNonNull(cart).getBuyerId().equals(myId)) {
                                onCartLisnter.getCart(cart);
                            }
                        }
                    } else {
                        ShowToast("No Internet Connection");
                    }
                } catch (Exception e) {
                    Log.e("ExceptionCart", e.getMessage());
                }
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                Cart cart = snapshot.getValue(Cart.class);
                if (Objects.requireNonNull(cart).getBuyerId().equals(myId)) {
                    onCartLisnter.OnAddItemOrder(cart, 0);
                    onCartLisnter.OnMinsItemOrder(cart, 0);
                }
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {
                Cart cart = snapshot.getValue(Cart.class);
                if (Objects.requireNonNull(cart).getBuyerId().equals(myId)) {
                    onCartLisnter.OnDeleteItemOrder(cart, 0);
                }
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

}
