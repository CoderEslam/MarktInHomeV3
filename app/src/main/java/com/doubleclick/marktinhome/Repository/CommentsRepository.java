package com.doubleclick.marktinhome.Repository;

import static com.doubleclick.marktinhome.BaseApplication.isNetworkConnected;
import static com.doubleclick.marktinhome.Model.Constantes.COMMENTS_PRODUCT;
import static com.doubleclick.marktinhome.Model.Constantes.USER;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.doubleclick.CommentsInterface;
import com.doubleclick.marktinhome.Model.Comments;
import com.doubleclick.marktinhome.Model.CommentsProductData;
import com.doubleclick.marktinhome.Model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Objects;

/**
 * Created By Eslam Ghazy on 3/17/2022
 */
public class CommentsRepository extends BaseRepository {

    private CommentsInterface commentsInterface;

    public CommentsRepository(CommentsInterface commentsInterface) {
        this.commentsInterface = commentsInterface;
    }

    public void getAllComments(String idproduct) {
        reference.child(COMMENTS_PRODUCT).orderByChild("idProduct").equalTo(idproduct).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                try {
                    if (isNetworkConnected()) {
                        if (snapshot.exists()) {
                            Comments comments = snapshot.getValue(Comments.class);
                            Log.e("COMMENT", comments.getComment());
                            CommentsProductData commentsProductData = new CommentsProductData();
                            assert comments != null;
                            commentsProductData.setComments(comments);
                            reference.child(USER).child(comments.getUserId()).addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    commentsProductData.setUser(Objects.requireNonNull(snapshot.getValue(User.class)));
                                    commentsInterface.getComment(commentsProductData);
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {

                                }
                            });
                        }
                    } else {
                        ShowToast("No internet conection");
                    }
                } catch (Exception e) {
                    Log.e("ExceptionComments", e.getMessage());
                }
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                try {
                    if (isNetworkConnected()) {
                        if (snapshot.exists()) {
                            Comments comments = snapshot.getValue(Comments.class);
                            assert comments != null;
                            Log.e("COMMENT", comments.getComment());
                            CommentsProductData commentsProductData = new CommentsProductData();
                            commentsProductData.setComments(comments);
                            reference.child(USER).child(comments.getUserId()).addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    commentsProductData.setUser(Objects.requireNonNull(snapshot.getValue(User.class)));
                                    commentsInterface.getCommentChanged(commentsProductData);
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {

                                }
                            });
                        }
                    } else {
                        ShowToast("No internet connection");
                    }
                } catch (Exception e) {
                    Log.e("ExceptionComments", e.getMessage());
                }
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {
                try {
                    if (isNetworkConnected()) {
                        if (snapshot.exists()) {
                            Comments comments = snapshot.getValue(Comments.class);
                            assert comments != null;
                            Log.e("COMMENT", comments.getComment());
                            CommentsProductData commentsProductData = new CommentsProductData();
                            commentsProductData.setComments(comments);
                            reference.child(USER).child(comments.getUserId()).addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    commentsProductData.setUser(Objects.requireNonNull(snapshot.getValue(User.class)));
                                    commentsInterface.getCommentDeleted(commentsProductData);
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {

                                }
                            });
                        }
                    } else {
                        ShowToast("No internet connection");
                    }
                } catch (Exception e) {
                    Log.e("ExceptionComments", e.getMessage());
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
