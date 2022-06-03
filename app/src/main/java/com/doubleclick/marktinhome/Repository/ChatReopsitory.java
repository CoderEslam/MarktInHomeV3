package com.doubleclick.marktinhome.Repository;

import static com.doubleclick.marktinhome.Model.Constantes.CHATS;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.doubleclick.marktinhome.Model.Chat;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseException;

import java.util.ArrayList;
import java.util.Objects;

import io.realm.Realm;

/**
 * Created By Eslam Ghazy on 3/17/2022
 */
public class ChatReopsitory extends BaseRepository {

    private ArrayList<Chat> myChats = new ArrayList<>();
    private Chats chats;
    private StatusChat statusChat;


    public ChatReopsitory(Chats chats) {
        this.chats = chats;
    }

    public void getChats(String userId, StatusChat statusChat) {
//        reference.child(CHATS).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
//            @Override
//            public void onComplete(@NonNull Task<DataSnapshot> task) {
//                try {
//                    if (isNetworkConnected()) {
//                        DataSnapshot snapshot = task.getResult();
//                        if (snapshot.exists()) {
//                            for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
//                                Chat chat = dataSnapshot.getValue(Chat.class);
//                                assert chat != null;
//                                if ((chat.getReceiver().equals(myId) && chat.getSender().equals(userId)) || (chat.getSender().equals(myId) && chat.getReceiver().equals(userId))) {
//                                    myChats.add(chat);
//                                    Log.e("chat", chat.toString());
//                                }
//                            }
//                            chats.getChat(myChats);
//                        }
//                    }
//                } catch (Exception e) {
//                    Log.e("ExceptionChat", e.getMessage());
//                }
//            }
//        });
        try {
            reference.child(CHATS).child(myId).child(userId).addChildEventListener(new ChildEventListener() {
                @Override
                public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                    try {
                        Chat chat = snapshot.getValue(Chat.class);
                        Log.e("onChildAdded", Objects.requireNonNull(snapshot.getValue(Chat.class)).toString());
                        assert chat != null;
                        if (chat.getMessage().contains("@$@this@message@deleted")) {
                            chats.deleteMessageRemotly(chat);
                        }
                        if (!chat.getMessage().contains("@$@this@message@deleted")) {
                            chats.newInsertChat(chat);
                        }
                    } catch (Exception ignored) {

                    }


                }

                @Override
                public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                    try {
                        Chat chat = snapshot.getValue(Chat.class);
                        assert chat != null;
                        if (!chat.getMessage().contains("@$@this@message@deleted")) {
                            if (chat.getStatusMessage().equals("beenSeen") && chat.getReceiver().equals(myId) && chat.isSeen()) {
                                statusChat.BeenSeenForFriend(chat);
                            }
                            if (chat.getStatusMessage().equals("beenSeen") && chat.getSender().equals(myId) && chat.isSeen()) {
                                statusChat.BeenSeenForMe(chat);
                            }
                        }
                        if (chat.getMessage().contains("@$@this@message@deleted")) {
                            statusChat.deleteForAll(snapshot.getValue(Chat.class));
                        }
                    } catch (Exception ignored) {

                    }

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
        } catch (DatabaseException e) {
            ShowToast("it's not exist");
        } catch (Exception e) {
            ShowToast("it's not exist");
        }


    }

    public interface Chats {
        void newInsertChat(Chat chat);

        void deleteMessageRemotly(Chat chat);
    }

    public interface StatusChat {
        void BeenSeenForFriend(Chat chat);

        void BeenSeenForMe(Chat chat);

        void deleteForAll(Chat chat);

    }

}
