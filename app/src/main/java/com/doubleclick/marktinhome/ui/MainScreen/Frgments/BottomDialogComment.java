package com.doubleclick.marktinhome.ui.MainScreen.Frgments;

import static com.doubleclick.marktinhome.Model.Constantes.REPLY_ON_COMMENTS;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.doubleclick.marktinhome.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created By Eslam Ghazy on 4/28/2022
 */
public class BottomDialogComment extends BottomSheetDialogFragment {


    private String commentId;/*  comment id*/
    private String groupId; /* group id*/
    private DatabaseReference reference;

    public BottomDialogComment(String commentId/* comment id*/, String groupId) {
        this.commentId = commentId;
        this.groupId = groupId;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.bottom_dialog_comment, container, false);
        reference = FirebaseDatabase.getInstance().getReference();
        EditText commentReply = view.findViewById(R.id.commentReplay);
        ImageView send = view.findViewById(R.id.send);
        send.setOnClickListener(v -> {
            if (!commentReply.getText().toString().equals("")) {
                Map<String, Object> map = new HashMap<>();
                String pushId = reference.push().getKey().toString();
                map.put("id", pushId);
                map.put("replyComment", commentReply.getText().toString());
                map.put("groupId", groupId);
                map.put("commentId", commentId/* comment id*/);
                map.put("time", new Date().getTime());
                map.put("userId", FirebaseAuth.getInstance().getCurrentUser().getUid().toString());
                reference.child(REPLY_ON_COMMENTS).child(groupId).child(commentId).child(pushId).updateChildren(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            commentReply.setText("");
                        }
                    }
                });
            }
        });
        return view;
    }
}
