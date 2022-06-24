package com.doubleclick.marktinhome.Repository;

import static com.doubleclick.marktinhome.BaseApplication.isNetworkConnected;
import static com.doubleclick.marktinhome.Model.Constantes.CART;
import static com.doubleclick.marktinhome.Model.Constantes.PRODUCT;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.doubleclick.OnCartLisnter;
import com.doubleclick.marktinhome.Model.Cart;
import com.doubleclick.marktinhome.Model.CartData;
import com.doubleclick.marktinhome.Model.Product;
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
        reference.child(CART).child(myId).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                try {
                    if (isNetworkConnected()) {
                        if (snapshot.exists()) {
                            Cart cart = snapshot.getValue(Cart.class);
                            CartData cartData = new CartData();
                            cartData.setCart(cart);
                            reference.child(PRODUCT).child(cart.getProductId()).addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    cartData.setProduct(snapshot.getValue(Product.class));
                                    onCartLisnter.getCart(cartData);
                                    Log.e("cartData",cartData.toString());
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {

                                }
                            });
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
                CartData cartData = new CartData();
                cartData.setCart(cart);
                reference.child(PRODUCT).child(cart.getProductId()).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        cartData.setProduct(snapshot.getValue(Product.class));
                        onCartLisnter.OnAddItemOrder(cartData, 0);
                        onCartLisnter.OnMinsItemOrder(cartData, 0);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {
                Cart cart = snapshot.getValue(Cart.class);
                CartData cartData = new CartData();
                cartData.setCart(cart);
                reference.child(PRODUCT).child(cart.getProductId()).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        cartData.setProduct(snapshot.getValue(Product.class));
                        onCartLisnter.OnDeleteItemOrder(cartData, 0);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
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
