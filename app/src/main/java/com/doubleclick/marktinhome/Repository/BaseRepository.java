package com.doubleclick.marktinhome.Repository;

import static com.doubleclick.marktinhome.BaseApplication.context;

import android.content.Context;
import android.net.ConnectivityManager;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created By Eslam Ghazy on 3/1/2022
 */
public class BaseRepository {

    public static DatabaseReference reference;
    public static FirebaseAuth mAuth;
    public static String myId;

    public BaseRepository() {
        reference = FirebaseDatabase.getInstance("https://marketinhome-99d25-default-rtdb.firebaseio.com/").getReference();
        mAuth = FirebaseAuth.getInstance();
        if (mAuth.getCurrentUser() != null) {
            myId = mAuth.getCurrentUser().getUid().toString();
            reference.keepSynced(true);
        }
    }

    public void ShowToast(String msg) {
        Toast.makeText(context, msg + "", Toast.LENGTH_LONG).show();
    }

}
