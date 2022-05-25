package com.doubleclick.marktinhome.Repository;

import static com.doubleclick.marktinhome.BaseApplication.isNetworkConnected;
import static com.doubleclick.marktinhome.Model.Constantes.CHAT_LIST;
import static com.doubleclick.marktinhome.Model.Constantes.USER;

import android.util.Log;

import androidx.annotation.NonNull;

import com.doubleclick.UserInter;
import com.doubleclick.marktinhome.Model.ChatList;
import com.doubleclick.marktinhome.Model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

/**
 * Created By Eslam Ghazy on 4/23/2022
 */

// to get All my Chat List
public class ChatListRepository extends BaseRepository {

    private UserInter userInter;
    private ArrayList<User> ChatList = new ArrayList<>();

    public ChatListRepository(UserInter userInter) {
        this.userInter = userInter;
    }

    public void ChatList() {
        reference.child(CHAT_LIST).child(myId).orderByChild("time").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                try {
                    if (isNetworkConnected()) {
                        if (dataSnapshot.exists()) {
                            ChatList.clear();
                            for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                                ChatList chatList = snapshot.getValue(ChatList.class);
                                reference.child(USER).child(chatList.getId()).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<DataSnapshot> task) {
                                        if (task.getResult().exists()) {
                                            DataSnapshot dataSnapshot = task.getResult();
                                            User user = dataSnapshot.getValue(User.class);
                                            ChatList.add(user);
                                            userInter.AllUser(ChatList);
                                        }
                                    }
                                });
                            }
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
