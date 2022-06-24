package com.doubleclick.marktinhome.Repository;

import static com.doubleclick.marktinhome.BaseApplication.isNetworkConnected;
import static com.doubleclick.marktinhome.Model.Constantes.ORDERS;
import static com.doubleclick.marktinhome.Model.Constantes.PRODUCT;

import android.util.Log;

import androidx.annotation.NonNull;

import com.doubleclick.OrderLisinter;
import com.doubleclick.marktinhome.Model.Orders;
import com.doubleclick.marktinhome.Model.OrdersDate;
import com.doubleclick.marktinhome.Model.Product;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

/**
 * Created By Eslam Ghazy on 3/12/2022
 */
public class OrdersRepository extends BaseRepository {

    private ArrayList<OrdersDate> ordersArrayList = new ArrayList<>();
    private OrderLisinter orderLisinter;

    public OrdersRepository(OrderLisinter orderLisinter) {
        this.orderLisinter = orderLisinter;
    }

    public void getOrders() {
        reference.child(ORDERS).child(myId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                try {
                    if (isNetworkConnected()) {
                        if (snapshot.exists()) {
                            orderLisinter.CountOrder(snapshot.getChildrenCount());
                            for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                                Orders orders = dataSnapshot.getValue(Orders.class);
                                Log.e("orders", orders.toString());
                                OrdersDate ordersDate = new OrdersDate();
                                ordersDate.setOrders(orders);
                                reference.child(PRODUCT).child(orders.getProductId()).addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                                        Product product = snapshot.getValue(Product.class);
                                        Log.e("orders", product.toString());
                                        ordersDate.setProduct(product);
                                        Log.e("orders", ordersDate.toString());
                                        ordersArrayList.add(ordersDate);
                                        orderLisinter.OnOrder(ordersArrayList);
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError error) {

                                    }
                                });
                            }
                        }
                    } else {
                        ShowToast("No Internet Connection");
                    }
                } catch (Exception e) {
                    Log.e("ExceptionOrders", e.getMessage());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

}
