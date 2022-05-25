package com.doubleclick.marktinhome.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.doubleclick.marktinhome.Model.Comments;
import com.doubleclick.marktinhome.R;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created By Eslam Ghazy on 3/17/2022
 */
public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.CommentViewHolder> {

    private ArrayList<Comments> comments = new ArrayList<>();

    public CommentAdapter(ArrayList<Comments> comments) {
        this.comments = comments;
    }

    @NonNull
    @Override
    public CommentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CommentViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.comments_layout, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull CommentViewHolder holder, int position) {
        holder.comment.setText(comments.get(position).getComment());
        holder.userName.setText(comments.get(position).getUserName());
        Glide.with(holder.itemView.getContext()).load(comments.get(position).getImage()).into(holder.imageUser);
        holder.ratingBar.setRating(comments.get(position).getRateStar());
    }

    @Override
    public int getItemCount() {
        return comments.size();
    }

    public class CommentViewHolder extends RecyclerView.ViewHolder {
        private CircleImageView imageUser;
        private TextView userName;
        private TextView comment;
        private RatingBar ratingBar;

        public CommentViewHolder(@NonNull View itemView) {
            super(itemView);
            imageUser = itemView.findViewById(R.id.imageUser);
            userName = itemView.findViewById(R.id.userName);
            comment = itemView.findViewById(R.id.comment);
            ratingBar = itemView.findViewById(R.id.ratingBar);
        }

    }
}
