package com.doubleclick.marktinhome.Repository;

import static com.doubleclick.marktinhome.BaseApplication.isNetworkConnected;
import static com.doubleclick.marktinhome.Model.Constantes.PRODUCT;
import static com.doubleclick.marktinhome.Model.Constantes.RECENTORDER;
import static com.doubleclick.marktinhome.Model.Constantes.USER;

import android.util.Log;

import androidx.annotation.NonNull;

import com.doubleclick.RecentOrderInterface;
import com.doubleclick.marktinhome.Model.Product;
import com.doubleclick.marktinhome.Model.RecentOrder;
import com.doubleclick.marktinhome.Model.RecentOrderData;
import com.doubleclick.marktinhome.Model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Objects;

/**
 * Created By Eslam Ghazy on 3/12/2022
 */
public class RecentOrderRepository extends BaseRepository {

    private ArrayList<RecentOrderData> recentOrderArrayList = new ArrayList<>();
    private RecentOrderInterface recentOrderInterface;

    public RecentOrderRepository(RecentOrderInterface recentOrderInterface) {
        this.recentOrderInterface = recentOrderInterface;
    }

    public void getRecentOrder() {
        reference.child(RECENTORDER).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                try {
                    if (isNetworkConnected()) {
                        if (snapshot.exists()) {
                            for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                                RecentOrder recentOrder = dataSnapshot.getValue(RecentOrder.class);
                                RecentOrderData recentOrderData = new RecentOrderData();
                                recentOrderData.setRecentOrder(recentOrder);
                                if (Objects.requireNonNull(recentOrder).getBuyerId().equals(myId)) {
                                    reference.child(PRODUCT).child(recentOrder.getProductId()).addListenerForSingleValueEvent(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                                            Product product = snapshot.getValue(Product.class);
                                            recentOrderData.setProduct(product);
                                            reference.child(USER).child(recentOrder.getBuyerId()).addListenerForSingleValueEvent(new ValueEventListener() {
                                                @Override
                                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                                    User user = snapshot.getValue(User.class);
                                                    recentOrderData.setUser(user);
                                                    recentOrderArrayList.add(recentOrderData);
                                                    recentOrderInterface.getMyRecentOrder(recentOrderArrayList);
                                                }

                                                @Override
                                                public void onCancelled(@NonNull DatabaseError error) {

                                                }
                                            });
                                        }

                                        @Override
                                        public void onCancelled(@NonNull DatabaseError error) {

                                        }
                                    });
                                }
                            }
                        }
                    } else {
                        ShowToast("No internet Connection");
                    }

                } catch (Exception e) {
                    Log.e("RecentOrderException", e.getMessage());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

}
