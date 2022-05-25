package com.doubleclick.marktinhome.Repository;

import static com.doubleclick.marktinhome.Model.Constantes.PRODUCT;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.doubleclick.ViewMore;
import com.doubleclick.marktinhome.Model.Product;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;

/**
 * Created By Eslam Ghazy on 5/5/2022
 */
public class ViewMoreRepository extends BaseRepository {

    private ViewMore loadMore;

    public ViewMoreRepository(ViewMore loadMore) {
        this.loadMore = loadMore;
    }

    public void LoadMore() {
        reference.child(PRODUCT).orderByChild("discount").limitToLast(1000).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                Product product = snapshot.getValue(Product.class);
                assert product != null;
                loadMore.getViewMore(product);
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

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
