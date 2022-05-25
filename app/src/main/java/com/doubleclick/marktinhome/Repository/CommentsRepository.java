package com.doubleclick.marktinhome.Repository;

import static com.doubleclick.marktinhome.BaseApplication.isNetworkConnected;
import static com.doubleclick.marktinhome.Model.Constantes.COMMENTS;

import android.util.Log;

import androidx.annotation.NonNull;

import com.doubleclick.CommentsInterface;
import com.doubleclick.marktinhome.Model.Comments;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;

import java.util.ArrayList;

/**
 * Created By Eslam Ghazy on 3/17/2022
 */
public class CommentsRepository extends BaseRepository {

    private ArrayList<Comments> commentsArrayList = new ArrayList<>();
    private CommentsInterface commentsInterface;

    public CommentsRepository(CommentsInterface commentsInterface) {
        this.commentsInterface = commentsInterface;
    }

    public void getAllComments(String idproduct) {
        reference.child(COMMENTS).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                try {
                    if (isNetworkConnected()) {
                        if (task.getResult().exists()) {
                            DataSnapshot dataSnapshot = task.getResult();
                            for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                                Comments comments = snapshot.getValue(Comments.class);
                                if (comments.getIdProduct().equals(idproduct)){
                                    commentsArrayList.add(comments);
                                }
                            }
                            commentsInterface.getComment(commentsArrayList);
                        }
                    } else {
                        ShowToast("No internet conection");
                    }
                } catch (Exception e) {
                    Log.e("ExceptionComments", e.getMessage());
                }
            }
        });

    }
}
