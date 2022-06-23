package com.doubleclick.marktinhome.Repository;

import static com.doubleclick.marktinhome.Model.Constantes.FAVORITE;
import static com.doubleclick.marktinhome.Model.Constantes.PRODUCT;

import android.util.Log;

import androidx.annotation.NonNull;

import com.doubleclick.FavoriteInter;
import com.doubleclick.marktinhome.BaseApplication;
import com.doubleclick.marktinhome.Model.Favorite;
import com.doubleclick.marktinhome.Model.Product;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

/**
 * Created By Eslam Ghazy on 6/23/2022
 */
public class FavoriteRepository extends BaseRepository {

    private FavoriteInter favoriteInter;
    private ArrayList<Product> favorites = new ArrayList<>();

    public FavoriteRepository(FavoriteInter favoriteInter) {
        this.favoriteInter = favoriteInter;
    }


    public void getMyFavorite() {
        reference.child(FAVORITE).child(myId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (BaseApplication.isNetworkConnected()) {
                    try {
                        favoriteInter.count(snapshot.getChildrenCount());
                        favorites.clear();
                        for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                            Favorite favorite = dataSnapshot.getValue(Favorite.class);
                            assert favorite != null;
                            reference.child(PRODUCT).child(favorite.getId()).addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    Product product = snapshot.getValue(Product.class);
                                    favorites.add(product);
                                    favoriteInter.myFavorite(favorites);
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {

                                }
                            });
                        }
                    } catch (Exception e) {
                        Log.e("FavoriteExp", e.getMessage());
                    }
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }


    public void IsFavorite(String idProduct) {
        if (BaseApplication.isNetworkConnected()) {
            try {
                reference.child(FAVORITE).child(myId).child(idProduct).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.exists()) {
                            favoriteInter.isFavorite(true);
                        } else {
                            favoriteInter.isFavorite(false);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

            } catch (Exception e) {
                Log.e("FavoriteExp", e.getMessage());
            }
        }

    }

}
