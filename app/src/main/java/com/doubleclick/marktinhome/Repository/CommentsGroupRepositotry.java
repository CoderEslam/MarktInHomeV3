package com.doubleclick.marktinhome.Repository;

import static com.doubleclick.marktinhome.BaseApplication.isNetworkConnected;
import static com.doubleclick.marktinhome.Model.Constantes.COMMENTS_GROUP;
import static com.doubleclick.marktinhome.Model.Constantes.USER;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.doubleclick.marktinhome.Model.CommentsGroup;
import com.doubleclick.marktinhome.Model.CommentsGroupData;
import com.doubleclick.marktinhome.Model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;

import java.util.ArrayList;

/**
 * Created By Eslam Ghazy on 4/27/2022
 */
public class CommentsGroupRepositotry extends BaseRepository {

    private CommentData commentData;

    public CommentsGroupRepositotry(CommentData commentData) {
        this.commentData = commentData;
    }

    public void loadComments(String groupId, String postId) {
        reference.child(COMMENTS_GROUP).child(groupId).child(postId).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                try {
                    if (isNetworkConnected()) {
                        if (snapshot.exists()) {
                            CommentsGroup commentsGroup = snapshot.getValue(CommentsGroup.class);
                            assert commentsGroup != null;
                            CommentsGroupData commentGroupData = new CommentsGroupData();
                            commentGroupData.setCommentsGroup(commentsGroup);
                            reference.child(USER).child(commentsGroup.getUserId()).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<DataSnapshot> task) {
                                    User user = task.getResult().getValue(User.class);
                                    commentGroupData.setUser(user);
                                    commentData.Comments(commentGroupData);
                                }
                            });

                        }
                    }
                } catch (Exception e) {

                }
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

    public interface CommentData {
        void Comments(CommentsGroupData commentsGroupData);
    }
}
