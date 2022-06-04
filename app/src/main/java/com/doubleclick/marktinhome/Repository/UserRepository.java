package com.doubleclick.marktinhome.Repository;

import static com.doubleclick.marktinhome.BaseApplication.isNetworkConnected;
import static com.doubleclick.marktinhome.Model.Constantes.CHAT_LIST;
import static com.doubleclick.marktinhome.Model.Constantes.USER;

import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.doubleclick.UserInter;
import com.doubleclick.marktinhome.BaseApplication;
import com.doubleclick.marktinhome.Model.ChatList;
import com.doubleclick.marktinhome.Model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseException;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Objects;

/**
 * Created By Eslam Ghazy on 3/1/2022
 */
public class UserRepository extends BaseRepository {

    private UserInter userInter;
    private ArrayList<User> userArrayList = new ArrayList<>();

    public UserRepository(UserInter userInter) {
        this.userInter = userInter;
    }

    public void getUser() {
        reference.child(USER).child(myId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                try {
                    if (isNetworkConnected()) {
                        if (snapshot.exists()) {
                            User user = snapshot.getValue(User.class);
                            userInter.ItemUser(user);
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


    public void getInfoUserById(String id) {
        try {
            reference.child(USER).child(id).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    try {
                        if (isNetworkConnected()) {
                            if (snapshot.exists()) {
                                User user = snapshot.getValue(User.class);
                                userInter.ItemUserInfoById(user);
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
        } catch (DatabaseException ignored) {

        }

    }

    public void getAllUser(String name) {
        reference.child(USER).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                try {
                    if (isNetworkConnected()) {
                        if (dataSnapshot.exists()) {
                            userArrayList.clear();
                            for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                                User user = snapshot.getValue(User.class);
                                assert user != null;
                                if (!user.getId().equals(myId) || user.getName().toLowerCase().equals(name.toLowerCase())) {
                                    userArrayList.add(user);
                                }
                            }
                            userInter.AllUser(userArrayList);
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
