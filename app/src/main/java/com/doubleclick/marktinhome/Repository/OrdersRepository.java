package com.doubleclick.marktinhome.Repository;

import static com.doubleclick.marktinhome.BaseApplication.isNetworkConnected;
import static com.doubleclick.marktinhome.Model.Constantes.ORDERS;

import android.util.Log;

import androidx.annotation.NonNull;

import com.doubleclick.OrderLisinter;
import com.doubleclick.marktinhome.Model.Orders;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;

import java.util.ArrayList;

/**
 * Created By Eslam Ghazy on 3/12/2022
 */
public class OrdersRepository extends BaseRepository {

    private ArrayList<Orders> ordersArrayList = new ArrayList<>();
    private OrderLisinter orderLisinter;

    public OrdersRepository(OrderLisinter orderLisinter) {
        this.orderLisinter = orderLisinter;
    }

    public void getOrders() {
        reference.child(ORDERS).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                try {
                    if (isNetworkConnected()) {
                        if (task.getResult().exists()) {
                            DataSnapshot snapshot = task.getResult();
                            for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                                Orders orders = dataSnapshot.getValue(Orders.class);
                                ordersArrayList.add(orders);
                            }
                            orderLisinter.OnOrder(ordersArrayList);
                        } else {
                            ShowToast("you don't have an orders");
                        }
                    } else {
                        ShowToast("No Internet Connection");
                    }
                } catch (Exception e) {
                    Log.e("ExceptionOrders", e.getMessage());
                }

            }
        });

    }

}
