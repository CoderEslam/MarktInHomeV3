package com.doubleclick.marktinhome.Repository;

import static com.doubleclick.marktinhome.BaseApplication.isNetworkConnected;
import static com.doubleclick.marktinhome.Model.Constantes.ADVERTISEMENT;

import android.util.Log;

import androidx.annotation.NonNull;

import com.doubleclick.AdvInterface;
import com.doubleclick.marktinhome.Model.Advertisement;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

/**
 * Created By Eslam Ghazy on 3/8/2022
 */
public class AdvertisementRepository extends BaseRepository {

    private ArrayList<Advertisement> advertisements = new ArrayList<>();
    private AdvInterface advInterface;

    public AdvertisementRepository(AdvInterface advInterface) {
        this.advInterface = advInterface;
    }

    public void getAdvertisement() {
        reference.child(ADVERTISEMENT).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                try {
                    if (isNetworkConnected()) {
                        if (snapshot.exists()) {
                            for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                                Advertisement advertisement = dataSnapshot.getValue(Advertisement.class);
                                advertisements.add(advertisement);
                            }
                            advInterface.AllAdvertisement(advertisements);
                        }
                    } else {
                        ShowToast("No Internet Connection");
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

}
