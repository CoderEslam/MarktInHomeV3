package com.doubleclick.marktinhome.Repository;

import static com.doubleclick.marktinhome.BaseApplication.isNetworkConnected;
import static com.doubleclick.marktinhome.Model.Constantes.RECENTORDER;

import android.util.Log;

import androidx.annotation.NonNull;

import com.doubleclick.RecentOrderInterface;
import com.doubleclick.marktinhome.Model.RecentOrder;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;

import java.util.ArrayList;
import java.util.Objects;

/**
 * Created By Eslam Ghazy on 3/12/2022
 */
public class RecentOrderRepository extends BaseRepository {

    private ArrayList<RecentOrder> recentOrderArrayList = new ArrayList<>();
    private RecentOrderInterface recentOrderInterface;

    public RecentOrderRepository(RecentOrderInterface recentOrderInterface) {
        this.recentOrderInterface = recentOrderInterface;
    }

    public void getRecentOrder() {
        reference.child(RECENTORDER).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                try {
                    if (isNetworkConnected()) {
                        if (task.getResult().exists()) {
                            DataSnapshot dataSnapshot = task.getResult();
                            for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                                RecentOrder recentOrder = snapshot.getValue(RecentOrder.class);
                                if (Objects.requireNonNull(recentOrder).getBuyerId().equals(myId)) {
                                    recentOrderArrayList.add(recentOrder);
                                }
                            }
                            recentOrderInterface.getMyRecentOrder(recentOrderArrayList);
                        }
                    } else {
                        ShowToast("No internet Connection");
                    }

                } catch (Exception e) {
                    Log.e("RecentOrderException", e.getMessage());
                }

            }
        });
    }

}
