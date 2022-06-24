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
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

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
        reference.child(RATE).child(productId).child(myId + ":" + productId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                try {
                    if (isNetworkConnected() && snapshot.exists()) {
                        Rate rate = snapshot.getValue(Rate.class);
                        rateing.MyRate(rate);
                    }
                } catch (Exception e) {
                    Log.e("Exception", e.getMessage());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }


    public void getAllRate(String productId) {
        reference.child(RATE).child(productId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                try {
                    if (isNetworkConnected()) {
                        if (snapshot.exists()) {
                            for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                                Rate rate = dataSnapshot.getValue(Rate.class);
                                rates.add(rate);
                            }
                            rateing.AllRate(rates);
                        }
                    } else {
                        ShowToast("No Internet Connection");
                    }

                } catch (Exception e) {
                    Log.e("ExceptionRating", e.getMessage());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

}
