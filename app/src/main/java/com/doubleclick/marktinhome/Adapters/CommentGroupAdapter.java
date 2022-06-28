package com.doubleclick.marktinhome.Adapters;

import static com.doubleclick.marktinhome.Model.Constantes.LIKES_ON_COMMENTS;
import static com.doubleclick.marktinhome.Model.Constantes.REPLY_ON_COMMENTS;
import static com.doubleclick.marktinhome.Model.Constantes.USER;

import android.annotation.SuppressLint;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.doubleclick.marktinhome.Model.CommentsGroupData;
import com.doubleclick.marktinhome.Model.CommentsReply;
import com.doubleclick.marktinhome.Model.CommentsReplyData;
import com.doubleclick.marktinhome.Model.User;
import com.doubleclick.marktinhome.R;
import com.doubleclick.marktinhome.Views.socialtextview.SocialTextView;
import com.doubleclick.marktinhome.ui.MainScreen.Frgments.BottomDialogComment;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.todkars.shimmer.ShimmerRecyclerView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Objects;

import de.hdodenhof.circleimageview.CircleImageView;


/**
 * Created By Eslam Ghazy on 4/27/2022
 * import de.hdodenhof.circleimageview.CircleImageView;
 */
public class CommentGroupAdapter extends RecyclerView.Adapter<CommentGroupAdapter.CommentsViewHolder> {

    private ArrayList<CommentsGroupData> commentsGroupData;
    private DatabaseReference reference;
    String myId = Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid().toString();
    private boolean LikeChecker = false;
    @SuppressLint("SimpleDateFormat")
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEE, dd/MM/yyyy HH:mm aa");


    public CommentGroupAdapter(ArrayList<CommentsGroupData> commentsGroupData) {
        this.commentsGroupData = commentsGroupData;
    }

    @NonNull
    @Override
    public CommentsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        reference = FirebaseDatabase.getInstance().getReference();
        return new CommentsViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.comments_group_layout, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull CommentsViewHolder holder, int position) {

        Glide.with(holder.itemView.getContext()).load(commentsGroupData.get(holder.getBindingAdapterPosition()).getUser().getImage()).into(holder.imageUser);
        holder.userName.setText(commentsGroupData.get(holder.getBindingAdapterPosition()).getUser().getName());
        holder.comment.setText(commentsGroupData.get(holder.getBindingAdapterPosition()).getCommentsGroup().getComment());
        holder.time.setText(String.format("%s ", simpleDateFormat.format(commentsGroupData.get(holder.getBindingAdapterPosition()).getCommentsGroup().getTime())));
        holder.like.setOnClickListener(v -> {
            LikeChecker = true;
            reference.child(LIKES_ON_COMMENTS).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    //CHECKING IF THE POST IS LIKED OR NOT.....
                    if (LikeChecker == true) {
                        if (dataSnapshot.child(commentsGroupData.get(holder.getBindingAdapterPosition()).getCommentsGroup().getId()).hasChild(myId)) {
                            reference.child(LIKES_ON_COMMENTS).child(commentsGroupData.get(holder.getBindingAdapterPosition()).getCommentsGroup().getId()).child(myId).removeValue();
                            LikeChecker = false;
                        } else {
                            reference.child(LIKES_ON_COMMENTS).child(commentsGroupData.get(holder.getBindingAdapterPosition()).getCommentsGroup().getId()).child(myId).setValue(true);
                            LikeChecker = false;
                        }
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        });
        holder.setLike(commentsGroupData.get(holder.getAdapterPosition()).getCommentsGroup().getId());

        holder.reply.setOnClickListener(v -> {
            holder.openBottomSheet(commentsGroupData.get(holder.getBindingAdapterPosition()).getCommentsGroup().getId(), commentsGroupData.get(holder.getAdapterPosition()).getCommentsGroup().getGroupId());
        });
        holder.loadReply(commentsGroupData.get(holder.getBindingAdapterPosition()).getCommentsGroup().getId(), commentsGroupData.get(holder.getAdapterPosition()).getCommentsGroup().getGroupId());
    }

    @Override
    public int getItemCount() {
        return commentsGroupData.size();
    }

    public class CommentsViewHolder extends RecyclerView.ViewHolder {
        private CircleImageView imageUser;
        private TextView userName, time, like, reply;
        private ImageView img_like;
        private ShimmerRecyclerView RecyclerReplay;
        private NestedScrollView nestedScroll;
        private SocialTextView comment;

        public CommentsViewHolder(@NonNull View itemView) {
            super(itemView);
            imageUser = itemView.findViewById(R.id.imageUser);
            userName = itemView.findViewById(R.id.userName);
            comment = itemView.findViewById(R.id.comment);
            time = itemView.findViewById(R.id.time);
            like = itemView.findViewById(R.id.like);
            reply = itemView.findViewById(R.id.reply);
            img_like = itemView.findViewById(R.id.img_like);
            RecyclerReplay = itemView.findViewById(R.id.RecyclerReplay);
            nestedScroll = itemView.findViewById(R.id.nestedScroll);
        }

        public void loadReply(String postId, String groupId) {
            reference.child(REPLY_ON_COMMENTS).child(groupId).child(postId).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (snapshot.exists()) {
//                        commentsReplies.clear();
                        ArrayList<CommentsReplyData> commentsReplies = new ArrayList<>();
                        for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                            CommentsReply commentsReply = dataSnapshot.getValue(CommentsReply.class);
                            CommentsReplyData commentsReplyData = new CommentsReplyData();
                            commentsReplyData.setCommentsReply(commentsReply);
                            assert commentsReply != null;
                            reference.child(USER).child(commentsReply.getUserId()).get().addOnCompleteListener(task -> {
                                User user = task.getResult().getValue(User.class);
                                assert user != null;
                                commentsReplyData.setUser(user);
                                nestedScroll.setVisibility(View.VISIBLE);
                                commentsReplies.add(commentsReplyData);
                                RecyclerReplay.setAdapter(new ReplyAdapter(commentsReplies));
                            });

                        }
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });

        }

        public void setLike(String PostKey) {
            reference.child(LIKES_ON_COMMENTS).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    try {
                        if (dataSnapshot.child(PostKey).hasChild(myId)) {
                            like.setText(String.format("%s Like", String.valueOf(dataSnapshot.child(commentsGroupData.get(getBindingAdapterPosition()).getCommentsGroup().getId()).getChildrenCount())));
                            img_like.setImageResource(R.drawable.like_thumb_up);
                        } else {
                            like.setText(String.format("%s Like", String.valueOf(dataSnapshot.child(commentsGroupData.get(getBindingAdapterPosition()).getCommentsGroup().getId()).getChildrenCount())));
                            img_like.setImageResource(R.drawable.ic_like_circle);
                        }
                    } catch (ArrayIndexOutOfBoundsException e) {
                        Log.e("Exption", e.getMessage());
                    }

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }

        private void openBottomSheet(String commentId, String groupId) {
            BottomDialogComment bottomDialogComment = new BottomDialogComment(commentId, groupId);
            bottomDialogComment.show(((FragmentActivity) itemView.getContext()).getSupportFragmentManager(), "comment");
        }
    }


}
