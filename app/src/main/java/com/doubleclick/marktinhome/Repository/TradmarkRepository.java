package com.doubleclick.marktinhome.Repository;

import static com.doubleclick.marktinhome.BaseApplication.isNetworkConnected;
import static com.doubleclick.marktinhome.Model.Constantes.TRADEMARK;

import android.util.Log;

import androidx.annotation.NonNull;

import com.doubleclick.Tradmarkinterface;
import com.doubleclick.marktinhome.Model.Trademark;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created By Eslam Ghazy on 3/8/2022
 */
public class TradmarkRepository extends BaseRepository {

    ArrayList<Trademark> tradmarks = new ArrayList<>();
    List<String> names = new ArrayList<>();
    Tradmarkinterface tradmarkinterface;

    public TradmarkRepository(Tradmarkinterface tradmarkinterface) {
        this.tradmarkinterface = tradmarkinterface;
    }

    public void getTradmark() {
        reference.child(TRADEMARK).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                tradmarks.clear();
                try {
                    if (isNetworkConnected() && snapshot.exists()) {
                        for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                            Trademark tradmark = dataSnapshot.getValue(Trademark.class);
                            tradmarks.add(tradmark);
                        }
                        tradmarkinterface.AllTradmark(tradmarks);
                    }
                } catch (Exception e) {
                    ShowToast("No Internet Connection");
                    Log.e("Exception", e.getMessage());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    public void getNameTradmark() {
        reference.child(TRADEMARK).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                try {
                    if (isNetworkConnected()) {
                        if (snapshot.exists()) {
                            for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                                Trademark tradmark = dataSnapshot.getValue(Trademark.class);
                                assert tradmark != null;
                                names.add(tradmark.getName());
                            }
                            tradmarkinterface.AllNameTradmark(names);
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
