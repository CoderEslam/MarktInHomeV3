package com.doubleclick.marktinhome.Adapters;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.PopupMenu;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.doubleclick.marktinhome.BaseFragment;
import com.doubleclick.marktinhome.Model.Comments;
import com.doubleclick.marktinhome.Model.CommentsProductData;
import com.doubleclick.marktinhome.R;
import com.doubleclick.marktinhome.Repository.BaseRepository;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created By Eslam Ghazy on 3/17/2022
 */
public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.CommentViewHolder> {

    private ArrayList<CommentsProductData> comments;
    private OnDeleteComment onDeleteComment;


    public CommentAdapter(ArrayList<CommentsProductData> comments, OnDeleteComment onDeleteComment) {
        this.comments = comments;
        this.onDeleteComment = onDeleteComment;
    }

    @NonNull
    @Override
    public CommentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CommentViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.comments_layout, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull CommentViewHolder holder, int position) {
        holder.comment.setText(comments.get(holder.getAdapterPosition()).getComments().getComment());
        holder.userName.setText(comments.get(holder.getAdapterPosition()).getUser().getName());
        Glide.with(holder.itemView.getContext()).load(comments.get(holder.getAdapterPosition()).getUser().getImage()).into(holder.imageUser);
        if (comments.get(holder.getAdapterPosition()).getComments().getUserId().equals(BaseRepository.myId)) {
            holder.delete.setVisibility(View.VISIBLE);
            holder.delete.setOnClickListener(v -> {
                PopupMenu popupMenu = new PopupMenu(holder.itemView.getContext(), v);
                popupMenu.getMenuInflater().inflate(R.menu.delete_comment, popupMenu.getMenu());
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        if (item.getItemId() == R.id.delete) {
                            onDeleteComment.DeleteComment(comments.get(holder.getAdapterPosition()));
                        }
                        return true;
                    }
                });
                popupMenu.show();
            });
        } else {
            holder.delete.setVisibility(View.GONE);
        }

    }

    @Override
    public int getItemCount() {
        return comments.size();
    }

    public class CommentViewHolder extends RecyclerView.ViewHolder {
        private CircleImageView imageUser;
        private ImageView delete;
        private TextView userName;
        private TextView comment;

        public CommentViewHolder(@NonNull View itemView) {
            super(itemView);
            imageUser = itemView.findViewById(R.id.imageUser);
            userName = itemView.findViewById(R.id.userName);
            comment = itemView.findViewById(R.id.comment);
            delete = itemView.findViewById(R.id.delete);
        }
    }

    public interface OnDeleteComment {
        void DeleteComment(CommentsProductData commentsProductData);
    }
}
