package com.doubleclick.marktinhome.Repository;

import static com.doubleclick.marktinhome.BaseApplication.isNetworkConnected;
import static com.doubleclick.marktinhome.Model.Constantes.RATE;

import android.util.Log;

import androidx.annotation.NonNull;

import com.doubleclick.Rateing;
import com.doubleclick.marktinhome.Model.Rate;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;

import java.util.ArrayList;
import java.util.Objects;

/**
 * Created By Eslam Ghazy on 3/6/2022
 */
public class RateingRepository extends BaseRepository {


    private Rateing rateing;
    private ArrayList<Rate> rates = new ArrayList<>();

    public RateingRepository(Rateing rateing) {
        this.rateing = rateing;
    }

    public void getMyRate(String myId, String productId) {
        reference.child(RATE).child(myId + ":" + productId).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                try {
                    if (isNetworkConnected() && task.isComplete() && task.getResult().exists()) {
                        DataSnapshot dataSnapshot = task.getResult();
                        Rate rate = dataSnapshot.getValue(Rate.class);
                        rateing.MyRate(rate);
                    }
                } catch (Exception e) {
                    Log.e("Exception", e.getMessage());
                }
            }
        });

    }


    public void getAllRate(String productId) {
        reference.child(RATE).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                try {
                    if (isNetworkConnected()) {
                        if (task.getResult().exists()) {
                            DataSnapshot dataSnapshot = task.getResult();
                            rates.clear();
                            for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                                Rate rate = snapshot.getValue(Rate.class);
                                if (productId.equals(Objects.requireNonNull(rate).getProductId())) {
                                    rates.add(rate);
                                }
                            }
                        }
                    } else {
                        ShowToast("No Internet Connection");
                    }
                    rateing.AllRate(rates);
                } catch (Exception e) {
                    Log.e("ExceptionRating", e.getMessage());
                }
            }
        });

    }

}
