package com.doubleclick.marktinhome.Repository;

import static com.doubleclick.marktinhome.BaseApplication.isNetworkConnected;
import static com.doubleclick.marktinhome.Model.Constantes.CHAT_LIST;
import static com.doubleclick.marktinhome.Model.Constantes.USER;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.doubleclick.ChatListInter;
import com.doubleclick.UserInter;
import com.doubleclick.marktinhome.Model.ChatList;
import com.doubleclick.marktinhome.Model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.ChildEventListener;
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

    private ChatListInter chatListInter;

    public ChatListRepository(ChatListInter chatListInter) {
        this.chatListInter = chatListInter;
    }

    public void ChatList() {
        reference.child(CHAT_LIST).child(myId).orderByChild("time").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                try {
                    if (isNetworkConnected()) {
                        if (snapshot.exists()) {
                            ChatList chatList = snapshot.getValue(ChatList.class);
                            assert chatList != null;
                            chatListInter.insert(chatList);
                        }
                    } else {
                        ShowToast("No Internet Connection");
                    }
                } catch (Exception e) {
                    Log.e("Exception", e.getMessage());
                }
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                try {
                    if (isNetworkConnected()) {
                        if (snapshot.exists()) {
                            ChatList chatList = snapshot.getValue(ChatList.class);
                            assert chatList != null;
                            chatListInter.update(chatList);
                            Log.e("chatList", chatList.toString());
                        }
                    } else {
                        ShowToast("No Internet Connection");
                    }
                } catch (Exception e) {
                    Log.e("Exception", e.getMessage());
                }
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {
                try {
                    if (isNetworkConnected()) {
                        if (snapshot.exists()) {
                            ChatList chatList = snapshot.getValue(ChatList.class);
                            assert chatList != null;
                            chatListInter.delete(chatList);
                        }
                    } else {
                        ShowToast("No Internet Connection");
                    }
                } catch (Exception e) {
                    Log.e("Exception", e.getMessage());
                }
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
